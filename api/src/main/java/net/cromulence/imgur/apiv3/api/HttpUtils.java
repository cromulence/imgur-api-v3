package net.cromulence.imgur.apiv3.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.api.exceptions.EmailAlreadyVerifiedException;
import net.cromulence.imgur.apiv3.api.exceptions.ErrorResponseException;
import net.cromulence.imgur.apiv3.api.exceptions.ImgurApiTimeoutException;
import net.cromulence.imgur.apiv3.api.exceptions.ImgurRequestException;
import net.cromulence.imgur.apiv3.api.exceptions.ImgurServerException;
import net.cromulence.imgur.apiv3.api.exceptions.NotFoundException;
import net.cromulence.imgur.apiv3.api.exceptions.ResponseTypeException;
import net.cromulence.imgur.apiv3.api.exceptions.UploadTooLargeException;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;
import net.cromulence.imgur.apiv3.datamodel.Album;
import net.cromulence.imgur.apiv3.datamodel.AlbumImpl;
import net.cromulence.imgur.apiv3.datamodel.ApiError;
import net.cromulence.imgur.apiv3.datamodel.Comment;
import net.cromulence.imgur.apiv3.datamodel.Conversation;
import net.cromulence.imgur.apiv3.datamodel.ErrorDetails;
import net.cromulence.imgur.apiv3.datamodel.GalleryAlbumImpl;
import net.cromulence.imgur.apiv3.datamodel.GalleryDetails;
import net.cromulence.imgur.apiv3.datamodel.GalleryDetailsImpl;
import net.cromulence.imgur.apiv3.datamodel.GalleryEntry;
import net.cromulence.imgur.apiv3.datamodel.GalleryImageImpl;
import net.cromulence.imgur.apiv3.datamodel.Image;
import net.cromulence.imgur.apiv3.datamodel.ImageImpl;
import net.cromulence.imgur.apiv3.datamodel.Notifiable;

import org.apache.http.Header;
import org.apache.http.HttpEntityEnclosingRequest;
import org.apache.http.HttpHeaders;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

public class HttpUtils implements HttpInspector {

    private static final Logger LOG = LoggerFactory.getLogger(HttpUtils.class.getName());

    private static final int CONNECTION_TIMEOUT_MS = 10 * 1000;

    // GSON instance which is configured with deserialisers
    private final Gson GSON;

    // Simple GSON instance
    private final Gson simpleGson = new Gson();

    // Imgur instance, used for getting other components
    private final Imgur imgur;

    // httpclient stuff
    private final HttpClient client;
    private final RequestConfig requestConfig;

    // Request / respsonse interceptors
    private final List<HttpInspector> httpInspectors = new ArrayList<HttpInspector>();

    // TODO make this configurable
    private int MAX_RETRIES = 3;

    HttpUtils(Imgur imgur) {
        this.imgur = imgur;

        requestConfig = getRequestConfig();

        client = HttpClients.createDefault();

        GSON = getGson();
    }

    HttpUtils(Imgur imgur, SSLContext ctx) {

        this.imgur = imgur;

        requestConfig = getRequestConfig();

        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(ctx);

        client = HttpClients.custom().setSSLSocketFactory(sslsf).build();

        GSON = getGson();
    }

    private static RequestConfig getRequestConfig() {
        return RequestConfig.custom()
            .setConnectionRequestTimeout(CONNECTION_TIMEOUT_MS)
            .setConnectTimeout(CONNECTION_TIMEOUT_MS)
            .setSocketTimeout(CONNECTION_TIMEOUT_MS)
            .build();
    }

    /*
     * This is the section of JSON shame. All of the bodges required to get the
     * right objects from the JSON we get out of the API. Field type overloading
     * is mostly to blame, and trying to solve it with inheritance
     */

    final JsonDeserializer imageDeserializer = new JsonDeserializer<Image>() {

        @Override
        public Image deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return simpleGson.fromJson(json, ImageImpl.class);
        }
    };

    final JsonDeserializer albumDeserializer = new JsonDeserializer<Album>() {

        @Override
        public Album deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            Image[] images = new Image[0];

            if (json.getAsJsonObject().has("images")) {
                JsonArray imagesJson = json.getAsJsonObject().get("images").getAsJsonArray();
                json.getAsJsonObject().remove("images");

                images = new Image[imagesJson.size()];

                // TODO do the images
                for (int i = 0; i < imagesJson.size(); i++) {
                    images[i] = context.deserialize(imagesJson.get(i), ImageImpl.class);
                }
            }

            AlbumImpl album = simpleGson.fromJson(json, AlbumImpl.class);
            album.setImages(images);

            return album;
        }
    };

    final JsonDeserializer galleryDeserializer = new JsonDeserializer<GalleryDetails>() {
        @Override
        public GalleryDetails deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return simpleGson.fromJson(json, GalleryDetailsImpl.class);
        }
    };

    final JsonDeserializer galleryEntryDeserializer = new JsonDeserializer<GalleryEntry>() {

        @Override
        public GalleryEntry deserialize(JsonElement json,
            Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            GalleryDetails gd = context.deserialize(json, GalleryDetailsImpl.class);

            boolean is_album = json.getAsJsonObject().get("is_album").getAsBoolean();

            if (is_album) {
                Album a = context.deserialize(json, AlbumImpl.class);
                return new GalleryAlbumImpl(a, gd);
            } else {
                Image i = context.deserialize(json, ImageImpl.class);
                return new GalleryImageImpl(i, gd);
            }
        }
    };

    final JsonDeserializer notifiableDeserializer = new JsonDeserializer<Notifiable>() {
        @Override
        public Notifiable deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            if (json.getAsJsonObject().has("comment")) {
                return simpleGson.fromJson(json, Comment.class);
            } else if (json.getAsJsonObject().has("with_account_id")) {
                return simpleGson.fromJson(json, Conversation.class);
            }

            throw new JsonParseException("Unknown notification type");
        }
    };

    final JsonDeserializer<ApiError> apiErrorJsonDeserializer = new JsonDeserializer<ApiError>() {
        @Override
        public ApiError deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            JsonObject jo = json.getAsJsonObject();
            if (jo.has("error")) {
                if (jo.get("error").isJsonObject()) {
                    return simpleGson.fromJson(json, typeOfT);
                } else {
                    // It's a string, not an object. Fake out an object based on it
                    String errorMessage = jo.remove("error").getAsString();
                    ApiError e = simpleGson.fromJson(json, typeOfT);

                    ErrorDetails ed = new ErrorDetails();
                    ed.setMessage(errorMessage);
                    ed.setCode(-1);
                    ed.setType("ConvertedSimpleError");

                    e.setErrorDetails(ed);

                    return e;
                }
            } else {
                return simpleGson.fromJson(json, typeOfT);
            }
        }
    };

    final JsonDeserializer<Comment[]> commentArrayJsonDeserializer = new JsonDeserializer<Comment[]>() {
        @Override
        public Comment[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            if (json.isJsonArray()) {
                return simpleGson.fromJson(json, Comment[].class);
            } else {
                // Single comments dont come back in an array
                return new Comment[] {simpleGson.fromJson(json, Comment.class)};
            }
        }
    };

    final JsonDeserializer<String[]> stringArrayJsonDeserializer = new JsonDeserializer<String[]>() {
        @Override
        public String[] deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

            List<String> toReturn = new ArrayList<>();

            if (json.isJsonArray()) {
                JsonArray ja = json.getAsJsonArray();

                for (int i = 0; i < ja.size(); i++) {
                    JsonElement jsonElement = ja.get(i);

                    if (jsonElement.isJsonPrimitive()) {
                        JsonPrimitive asJsonPrimitive = jsonElement.getAsJsonPrimitive();


                        if (asJsonPrimitive.isNumber()) {
                            toReturn.add(Long.toString(asJsonPrimitive.getAsNumber().longValue()));
                        } else {
                            toReturn.add(asJsonPrimitive.toString());
                        }
                    }
                }
            }

            return toReturn.toArray(new String[toReturn.size()]);
        }
    };

    private Gson getGson() {
        return new GsonBuilder().
            registerTypeHierarchyAdapter(Image.class, imageDeserializer).
            registerTypeHierarchyAdapter(Album.class, albumDeserializer).
            registerTypeHierarchyAdapter(GalleryDetails.class, galleryDeserializer).
            registerTypeHierarchyAdapter(GalleryEntry.class, galleryEntryDeserializer).
            registerTypeHierarchyAdapter(Notifiable.class, notifiableDeserializer).
            registerTypeHierarchyAdapter(ApiError.class, apiErrorJsonDeserializer).
            registerTypeHierarchyAdapter(Comment[].class, commentArrayJsonDeserializer).
            registerTypeHierarchyAdapter(String[].class, stringArrayJsonDeserializer).
            create();
    }

     /*
      * Here ends JSON shame. Regular shame resumes.
      */

    public void addHttpInspector(HttpInspector hi) {
        httpInspectors.add(hi);
    }

    private Imgur getImgur() {
        return imgur;
    }

    /**
     * Perform a HTTP request for an initial auth request (logging in)
     */
    public ApiResponse initialAuth(String url, List<NameValuePair> params) throws ApiRequestException, ImgurApiTimeoutException {
        return auth(url, params, false);
    }

    /**
     * Perform a HTTP request for an auth request
     */
    public ApiResponse auth(String url, List<NameValuePair> params, boolean authenticated) throws ApiRequestException, ImgurApiTimeoutException {
        ApiResponse apiResponse = rawPost(url, params, authenticated);

        LOG.debug("Auth Response: {}", apiResponse);

        return apiResponse;
    }

    /**
     * Perform a HTTP POST. Request will be authenticated
     * @param url URL to post to
     * @return Imgur Basic Response
     * @throws ApiRequestException If there is a problem with the request
     * @throws ImgurApiTimeoutException If a response is not received in a timely fashion
     */
    public BasicResponse post(String url) throws ApiRequestException, ImgurApiTimeoutException {
        return post(url, true);
    }

    /**
     * Perform a HTTP POST. Request will be authenticated
     * @param url URL to post to
     * @param params Request parameters
     * @return Imgur Basic Response
     * @throws ApiRequestException If there is a problem with the request
     * @throws ImgurApiTimeoutException If a response is not received in a timely fashion
     */
    public BasicResponse post(String url, List<NameValuePair> params) throws ApiRequestException, ImgurApiTimeoutException {
        return post(url, params, true);
    }

    /**
     * erform a HTTP POST
     * @param url URL to post to
     * @param authenticated Set true if the reqiest should be authenticated
     * @return Imgur Basic Response
     * @throws ApiRequestException If there is a problem with the request
     * @throws ImgurApiTimeoutException If a response is not received in a timely fashion
     */
    public BasicResponse post(String url, boolean authenticated) throws ApiRequestException, ImgurApiTimeoutException {
        return post(url, null, authenticated);
    }

    /**
     * erform a HTTP POST
     * @param url URL to post to
     * @param authenticated Set true if the reqiest should be authenticated
     * @param params Request parameters
     * @return Imgur Basic Response
     * @throws ApiRequestException If there is a problem with the request
     * @throws ImgurApiTimeoutException If a response is not received in a timely fashion
     */
    public BasicResponse post(String url, List<NameValuePair> params, boolean authenticated) throws ApiRequestException, ImgurApiTimeoutException {
        ApiResponse apiResponse = rawPost(url, params, authenticated);

        BasicResponse response = getAsBasicResponse(apiResponse);

        if (!response.isSuccess()) {
            // Throw the appropriate exception
            LOG.error("Error response to post: {}", apiResponse);
            getExceptionFromErrorResponse(url, response);
        }

        return response;
    }

    public ApiResponse rawPost(String url) throws ApiRequestException, ImgurApiTimeoutException {
        return rawPost(url, null, true);
    }

    public ApiResponse rawPost(String url, List<NameValuePair> params) throws ApiRequestException, ImgurApiTimeoutException {
        return rawPost(url, params, true);
    }

    public ApiResponse rawPost(String url, List<NameValuePair> params, boolean authenticated) throws ApiRequestException, ImgurApiTimeoutException {
        LOG.debug("Posting to url[" + url + "] values[" + params + "]");

        HttpPost post = new HttpPost(url);
        post.setConfig(requestConfig);
        addParams(params, post);

        return http(post, authenticated);
    }

    public <T> T typedPost(String URL, TypeToken<T> type, boolean authenticated) throws ApiRequestException {
        return typedPost(URL, type, null, authenticated);
    }

    public <T> T typedPost(String URL, TypeToken<T> type, List<NameValuePair> params, boolean authenticated) throws ApiRequestException {
        BasicResponse postResponse = post(URL, params, authenticated);

        return typeResponse(postResponse, type);
    }

    public <T> T typedPost(String URL, Class<T> clazz, boolean authenticated) throws ApiRequestException {
        return typedPost(URL, clazz, null, authenticated);
    }

    public <T> T typedPost(String URL, Class<T> clazz, List<NameValuePair> params, boolean authenticated) throws ApiRequestException {
        return typedPost(URL, TypeToken.get(clazz), params, authenticated);
    }

    public BasicResponse get(String url) throws ApiRequestException {
        return get(url, true);
    }

    private BasicResponse get(String url, boolean authenticated) throws ApiRequestException {
        ApiResponse getResponse = rawGet(url, authenticated);

        BasicResponse response = getAsBasicResponse(getResponse);

        if (!response.isSuccess()) {
            // Throw the appropriate exception
            LOG.error("Error response to get: {}", getResponse);
            getExceptionFromErrorResponse(url, response);
        }

        return response;
    }

    public BasicResponse delete(String url) throws ApiRequestException {
        return delete(url, null, true);
    }

    public BasicResponse delete(String url, boolean authenticated) throws ApiRequestException {
        return delete(url, null, authenticated);
    }

    public BasicResponse delete(String url, List<NameValuePair> params) throws ApiRequestException {
        return delete(url, params, true);
    }

    public BasicResponse delete(String url, List<NameValuePair> params, boolean authenticated) throws ApiRequestException {
        ApiResponse deleteResponse = rawDelete(url, params, authenticated);

        BasicResponse response = getAsBasicResponse(deleteResponse);

        if (!response.isSuccess()) {
            // Throw the appropriate exception
            LOG.error("Error response to delete: {}", deleteResponse);
            getExceptionFromErrorResponse(url, response);
        }

        return response;
    }

    public BasicResponse put(String url, List<NameValuePair> params) throws ApiRequestException {
        return put(url, params, true);
    }

    public BasicResponse put(String url, List<NameValuePair> params, boolean authenticated) throws ApiRequestException {
        ApiResponse putResponse = rawPut(url, params, authenticated);

        BasicResponse response = getAsBasicResponse(putResponse);

        if (!response.isSuccess()) {
            // Throw the appropriate exception
            LOG.error("Error response to put: {}", putResponse);
            getExceptionFromErrorResponse(url, response);
        }

        return response;
    }

    public ApiResponse rawPut(String url, List<NameValuePair> params, boolean authenticated) throws ApiRequestException, ImgurApiTimeoutException {
        LOG.debug("Putting to url[" + url + "] values[" + params + "]");

        HttpPut put = new HttpPut(url);
        put.setConfig(requestConfig);
        addParams(params, put);

        return http(put, authenticated);
    }

    public ApiResponse rawGet(String url) throws ApiRequestException {
        return rawGet(url, true);
    }

    public ApiResponse rawGet(String url, boolean authenticated) throws ApiRequestException {
        LOG.debug("Getting url[" + url + "]");

        HttpGet get = new HttpGet(url);
        get.setConfig(requestConfig);

        return http(get, authenticated);
    }

    public ApiResponse rawDelete(String url) throws ApiRequestException {
        return rawDelete(url, null, true);
    }

    public ApiResponse rawDelete(String url, List<NameValuePair> params, boolean authenticated) throws ApiRequestException {
        LOG.debug("Deleting from url[" + url + "]");

        HttpEntityEnclosingRequestBase delete = new TerribleDelete(url);
        addParams(params, delete);

        delete.setConfig(requestConfig);

        return http(delete, authenticated);
    }

    private class TerribleDelete extends HttpPost {
        public TerribleDelete(String url) {
            super(url);
        }

        @Override
        public String getMethod() {
            return HttpDelete.METHOD_NAME;
        }

    }

    public <T> T typedGet(String url, Class<T> clazz) throws ApiRequestException {
        return typedGet(url, clazz, true);
    }

    public <T> T typedGet(String URL, Class<T> clazz, boolean authenticated) throws ApiRequestException {
        return typedGet(URL, TypeToken.get(clazz), authenticated);
    }

    public <T> T typedGet(String URL, TypeToken<T> type, boolean authenticated) throws ApiRequestException {

        Throwable lastThrowable = null;

        // The Imgur API has a habit of timing out, so we automatically retry requests
        for (int attempt = 0; attempt < MAX_RETRIES; attempt++) {

            try {
                if (attempt > 0) {
                    LOG.debug("typedGet reattempt {}", attempt);
                }

                BasicResponse getResponse = get(URL, authenticated);

                return typeResponse(getResponse, type);
            } catch (ImgurApiTimeoutException | ImgurServerException e) {
                lastThrowable = e;
                if (attempt < MAX_RETRIES) {
                    // Sleep for a moment before trying again
                    try {
                        Thread.sleep(2500L);
                    } catch (InterruptedException ie) {
                        // Noop
                    }
                }

                continue;
            }
        }

        if (lastThrowable != null) {
            throw new ApiRequestException("Retries failed, rethrowing last exception", lastThrowable);
        }

        return null;
    }

    private void getExceptionFromErrorResponse(String url, BasicResponse response) throws ImgurRequestException, ImgurServerException, ApiRequestException {

        // TODO identify some common errors in here and throw more accurate exceptions
        final String responseData = GSON.toJson(response.getData());
        final ApiError error = GSON.fromJson(responseData, ApiError.class);

        if (error.getErrorDetails() == null) {
            switch (response.getStatus()) {
                case 404:
                    throw new NotFoundException(url);
            }
        } else {
            switch (error.getErrorDetails().getCode()) {
                case 1002: // File is over the size limit
                    throw new UploadTooLargeException();
            }

            if (error.getErrorDetails().getMessage().equals("User Previously Verified")) {
                throw new EmailAlreadyVerifiedException();
            }
        }

        throw new ErrorResponseException(response.getStatus(), error);
    }

    private BasicResponse getAsBasicResponse(ApiResponse response) throws ResponseTypeException {
        BasicResponse fromJson;
        try {
            fromJson = GSON.fromJson(response.getResponseBody(), BasicResponse.class);
        } catch (JsonSyntaxException e) {
            throw new ResponseTypeException("Unable to instantiate response as " + BasicResponse.class.getName() + "\n" + response, e);
        }

        fromJson.setRawApiResponse(response);
//        fromJson.setRawBody(response.getResponseBody())

        return fromJson;
    }

    /**
     * This method assumes a success response
     * @param response
     * @param type
     * @param <T>
     * @return
     * @throws ErrorResponseException
     * @throws ResponseTypeException
     */
    private <T> T typeResponse(BasicResponse response, TypeToken<T> type) throws ErrorResponseException, ResponseTypeException {
        final String responseData = GSON.toJson(response.getData());

        try {
            T tt = GSON.fromJson(responseData, type.getType());

            return tt;
        } catch (JsonSyntaxException e) {
            throw new ResponseTypeException("Unable to instantiate response data as " + type.getRawType().getName() + "\n" + responseData, e);
        }
    }

    /**
     * Make a HTTP request
     * @param req Request to execute
     * @param authenticated
     * @return HTTP response
     * @throws ApiRequestException
     * @throws ImgurApiTimeoutException
     */
    private ApiResponse http(HttpRequestBase req, boolean authenticated) throws ApiRequestException, ImgurApiTimeoutException {

        ImgurOAuthHandler ah;

        if (authenticated) {
            ah = getImgur().AUTH;
            req.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + ah.getAccessToken());
        } else {
            ah = null;
            req.addHeader(HttpHeaders.AUTHORIZATION, "Client-ID " + getImgur().DATA.getClientId());
        }

        preExecute(req, ah);

        ApiResponse apiResponse;

        try {
            HttpResponse resp = client.execute(req);
            apiResponse = new ApiResponse(resp);
        } catch (SocketTimeoutException e) {
            throw new ImgurApiTimeoutException(e);
        } catch (IOException e) {
            throw new ApiRequestException("HTTP IO error", e);
        } finally {
            req.releaseConnection();
        }

        postExecute(req, apiResponse.getResponse(), ah);

        final Header[] clHeaders = apiResponse.getResponse().getHeaders("Content-Length");

        String cl = "unknown";

        if (clHeaders != null) {
            if (clHeaders.length == 1) {
                cl = clHeaders[0].getValue();
            } else if (clHeaders.length == 0) {
                cl = "none specified";
            } else {
                cl = "multiple specified";
            }
        }

        LOG.debug(String.format("http response: code[%d] message[%s] size[%s]", apiResponse.getStatusCode(), apiResponse.getReasonPhrase(), cl));

        // TODO Do we need to deal with redirect?
        if (apiResponse.getStatusCode() > 299) {
            LOG.debug("Unable to " + req.getClass().getSimpleName() + " url[" + req.getURI() + "] statuscode[" + apiResponse.getStatusCode() + "] reason[" + apiResponse.getReasonPhrase() + "]");
        }

        // TODO add these here
//        if(status >= 400) {
//            LOG.debug("doToken: error response");
//            LOG.debug("doToken: response text: " + doPost.getResponseBody());
//
//            if(status == 403) {
//                LOG.error("doToken: forbidden response");
//            } else if(status == 429) {
//                LOG.error("doToken: rate limited");
//                printHeaders(doPost.getResponse(), new String[] {"X-RateLimit-UserLimit", "X-RateLimit-UserRemaining", "X-RateLimit-UserReset", "X-RateLimit-ClientLimit", "X-RateLimit-ClientRemaining", "X-Post-Rate-Limit-Limit", "X-Post-Rate-Limit-Remaining", "X-Post-Rate-Limit-Reset"});
//            } else {
//                LOG.error("doToken: unknown error response:" + status);
//            }
//
//            // TODO any others?
//
//            return false;
//        }

        if (apiResponse.getStatusCode() == 503) {
            throw new ImgurServerException(apiResponse);
        }

        return apiResponse;
    }

    @Override
    public void preExecute(HttpRequestBase req, ImgurOAuthHandler ah) {
        for (HttpInspector hi : httpInspectors) {
            hi.preExecute(req, ah);
        }
    }

    @Override
    public void postExecute(HttpRequestBase req, HttpResponse response, ImgurOAuthHandler ah) {
        for (HttpInspector hi : httpInspectors) {
            hi.postExecute(req, response, ah);
        }
    }

    public static String readStream(InputStream content) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(content));

        StringBuilder all = new StringBuilder();
        String read;
        try {
            while ((read = br.readLine()) != null) {
                all.append(read).append("\n");
            }

            return all.toString();
        } finally {
            br.close();
        }
    }

    private void addParams(List<NameValuePair> params, HttpEntityEnclosingRequest req) throws ApiRequestException {
        if (params != null) {
            try {
                req.setEntity(new UrlEncodedFormEntity(params));
            } catch (UnsupportedEncodingException e) {
                throw new ApiRequestException("Unable to construct request", e);
            }
        }
    }
}
