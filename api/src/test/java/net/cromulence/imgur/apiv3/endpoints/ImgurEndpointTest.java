package net.cromulence.imgur.apiv3.endpoints;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import net.cromulence.datawrapper.DataWrapper;
import net.cromulence.datawrapper.DataWrapperException;
import net.cromulence.datawrapper.DataWrapperImpl;
import net.cromulence.datawrapper.properties.PropertiesFileDataStoreConnector;
import net.cromulence.imgur.apiv3.ImgurDatastore;
import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthData;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;
import net.cromulence.imgur.apiv3.auth.PersistingOAuthHandler;
import net.cromulence.imgur.apiv3.datamodel.AuthResponse;
import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

import static junit.framework.TestCase.fail;

public class ImgurEndpointTest {

    private static final Logger LOG = LoggerFactory.getLogger(ImgurEndpointTest.class);

    private static File propertiesFile;

    private static ImgurDatastore data;

    private static Imgur user1ImgurUnderTest;
    private static Imgur user2ImgurUnderTest;

    private static PersistingOAuthHandler user1Auth;
    private static PersistingOAuthHandler user2Auth;

    private static String[] propertiesPaths = new String[]{ "./api/src/test/resources", "./src/test/resources", "/home/travis" };

    @BeforeClass
    public static void setup() throws Exception {
        setup(true);
    }
    public static void setup(boolean failIfNoValidTokens) throws IOException, ApiRequestException, DataWrapperException, DataWrapperException {
        propertiesFile = null;

        List<String> possibleFiles = new ArrayList<>();

        try {
            System.out.println(new File(".").listFiles());
            System.out.println(new File("./api").listFiles());
            System.out.println(new File("./api/src").listFiles());
            System.out.println(new File("./api/src/test").listFiles());
            System.out.println(new File("./api/src/test/resources").listFiles());
            URL resource = ImgurEndpointTest.class.getClassLoader().getResource("/test.properties");
            if(resource != null) {
                propertiesFile = new File(resource.toURI().getPath());
            }
        } catch (URISyntaxException e) {
            // nothing needed
        }

        if(propertiesFile == null) {
            for (String path : propertiesPaths) {
                File parent = new File(path);
                File file = new File(parent, "test.properties");
                possibleFiles.add(file.getAbsolutePath());
                if (file.exists() && file.isFile() && file.canRead() && file.length() > 0) {
                    propertiesFile = file;
                    LOG.info("found test.properties at {}", file.getAbsolutePath());
                    break;
                }
            }
        }

        if(propertiesFile == null) {
            throw new IllegalStateException("Unable to find test.properties in " + possibleFiles);
        }

        PropertiesFileDataStoreConnector propertiesFileDataStoreConnector = new PropertiesFileDataStoreConnector(propertiesFile);

        DataWrapper dw = new DataWrapperImpl(propertiesFileDataStoreConnector);

        data = new ImgurDatastore(dw);

        ImgurOAuthData user1AuthData = new ImgurDatastore(propertiesFileDataStoreConnector, "alice");
        ImgurOAuthData user2AuthData = new ImgurDatastore(propertiesFileDataStoreConnector, "bob");

        user1Auth = new PersistingOAuthHandler(user1AuthData);
        user2Auth = new PersistingOAuthHandler(user2AuthData);

        user1ImgurUnderTest = new Imgur(data, user1Auth);
        user2ImgurUnderTest = new Imgur(data, user2Auth);

        if (!user1Auth.hasValidAccessToken()) {
            if(failIfNoValidTokens) {
                fail("User 1 does not have a valid access token");
            } else {
                LOG.info("User 1 does not have a valid access token");
                getToken(user1ImgurUnderTest, user1Auth, user1AuthData.getUsername());
            }
        }

        if (!user2Auth.hasValidAccessToken()) {
            if(failIfNoValidTokens) {
                fail("User 2 does not have a valid access token");
            } else {
                LOG.info("User 2 does not have a valid access token");
                getToken(user2ImgurUnderTest, user2Auth, user2AuthData.getUsername());
            }
        }

        getUser1ImgurUnderTest().auth.refreshToken();
        getUser2ImgurUnderTest().auth.refreshToken();
    }

    @Before
    public void refreshTokens() throws Exception {
        getUser1ImgurUnderTest().auth.refreshToken();
        getUser2ImgurUnderTest().auth.refreshToken();
    }

    public static Imgur getUser1ImgurUnderTest() {
        return user1ImgurUnderTest;
    }

    public static Imgur getUser2ImgurUnderTest() {
        return user2ImgurUnderTest;
    }

    public ImgurOAuthHandler getUser1AuthHandler() {
        return user1Auth;
    }

    public ImgurOAuthHandler getUser2AuthHandler() {
        return user2Auth;
    }

    public static void dump(Logger logger, Object o) {
        logger.info(new GsonBuilder().setPrettyPrinting().create().toJson(o));
    }

    public static void dump(Logger logger, String label, Object o) {
        logger.info("\n\n\n" + label + "\n" + new GsonBuilder().setPrettyPrinting().create().toJson(o));
    }

    public static void main(String[] args) throws Exception {
        setup(false);
    }

    protected void pause(Logger logger, String s, long i) {
        logger.info(s);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            // Do nothing
            Thread.currentThread().interrupt();
        }
    }

    private static void getToken(Imgur imgur, ImgurOAuthHandler ah, String user) throws IOException, ApiRequestException {

        String tokenLoginUrl = String.format("%s/authorize?response_type=token&client_id=%s&state=%s", imgur.auth.getEndpointUrl(), imgur.data.getClientId(), user);

        System.out.println("log in to imgur as " + user + " and go to the following URL");
        System.out.println("Once redirected, copy redirect page URL from browser and paste it in here");
        System.out.println(tokenLoginUrl);

        // http://localhost:9876/oauth/callback?username=name#other=args&for=oauth

        BufferedReader bin = new BufferedReader(new InputStreamReader(System.in));
        String readLine = bin.readLine();

        // Everything after the ?
        readLine = readLine.substring(readLine.indexOf("?") + 1);

        // Turn the fragment into a query parameter so we cen handle everything the same
        readLine.replace("#", "&");

        // Get the KVPs
        String[] split = readLine.split("#|&");

        // Split them into Ks and Vs in a map
        Map<String, String> params = new HashMap<>();
        Arrays.stream(split).forEach(kvp -> params.put(kvp.split("=")[0], kvp.split("=")[1]));

        // Convert the map to an auth response
        final Gson gson = new Gson();
        ah.handleAuth(gson.fromJson(gson.toJson(params), AuthResponse.class));
    }

    private static void getAndExchangePin(Imgur imgur, ImgurOAuthHandler ah, String user) throws IOException, ApiRequestException {

        String pinLoginUrl = String.format("%s/authorize?response_type=pin&client_id=%s&state=%s", imgur.auth.getEndpointUrl(), imgur.data.getClientId(), user);

        System.out.println(user);
        System.out.println(pinLoginUrl);

        BufferedReader bin = new BufferedReader(new InputStreamReader(System.in));

        String readLine = bin.readLine();

        imgur.auth.exchangePin(readLine, ah);
    }
}
