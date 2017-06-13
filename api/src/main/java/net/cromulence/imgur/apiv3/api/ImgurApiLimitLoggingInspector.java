package net.cromulence.imgur.apiv3.api;

import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;
import net.cromulence.imgur.apiv3.datamodel.Credits;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImgurApiLimitLoggingInspector implements HttpInspector {

    private static final Logger LOG = LoggerFactory.getLogger(ImgurApiLimitLoggingInspector.class);

    private static final String POST_PREFIX = "X-Post-Rate-Limit-";
    private static final String CLIENT_PREFIX = "X-RateLimit-Client";
    private static final String USER_PREFIX = "X-RateLimit-User";

    private static final String HEADER_POST_LIMIT = POST_PREFIX + "Limit";
    private static final String HEADER_POST_REMAINING = POST_PREFIX + "Remaining";
    private static final String HEADER_POST_RESET = POST_PREFIX + "Reset";

    private static final String HEADER_CLIENT_LIMIT = CLIENT_PREFIX + "Limit";
    private static final String HEADER_CLIENT_REMAINING = CLIENT_PREFIX + "Remaining";

    private static final String HEADER_USER_LIMIT = USER_PREFIX + "Limit";
    private static final String HEADER_USER_REMAINING = USER_PREFIX + "Remaining";
    private static final String HEADER_USER_RESET = USER_PREFIX + "Reset";

    /** Fraction at which warnings start */
    private static final int FRACTION = 10;

    private final Imgur imgur;

    public ImgurApiLimitLoggingInspector(Imgur imgur) {
        this.imgur = imgur;
    }

    @Override
    public void preExecute(HttpRequestBase req, ImgurOAuthHandler ah) {
        // Nothing to be done before
    }

    @Override
    public void postExecute(HttpRequestBase req, HttpResponse response, ImgurOAuthHandler ah) {

        if(req.getURI().toString().equals(imgur.ACCOUNT.getEndpointUrl()) ||
           req.getURI().toString().contains(imgur.CREDITS.getEndpointUrl())) {
            // account and credits endpoints don't return limit headers - no cost endpoints?
            return;
        }

        Credits creds = new Credits();

        {
            // Check the response headers for the post limit count
            Header postLimitHeader = response.getFirstHeader(HEADER_POST_LIMIT);
            Header postRemainingHeader = response.getFirstHeader(HEADER_POST_REMAINING);
            Header postResetHeader = response.getFirstHeader(HEADER_POST_RESET);

            if (req.getMethod().equalsIgnoreCase("POST")) {
                if (postLimitHeader == null || postRemainingHeader == null || postResetHeader == null) {
                    LOG.info("App post rate limit headers not available on endpoint {}", req.getURI());
                } else {

                    Integer postLimit = Integer.parseInt(postLimitHeader.getValue());
                    Integer postRemain = Integer.parseInt(postRemainingHeader.getValue());
                    String postReset = postResetHeader.getValue();

                    logLimit("Post", postLimit, postRemain, postReset);

                    creds.setPostLimit(postLimit);
                    creds.setPostRemaining(postRemain);
                    creds.setPostReset(Long.parseLong(postReset));
                }
            }
        }

        {
            Header clientLimitHeader = response.getFirstHeader(HEADER_CLIENT_LIMIT);
            Header clientRemainHeader = response.getFirstHeader(HEADER_CLIENT_REMAINING);
            Header userLimitHeader = response.getFirstHeader(HEADER_USER_LIMIT);
            Header userRemainHeader = response.getFirstHeader(HEADER_USER_REMAINING);
            Header userResetHeader = response.getFirstHeader(HEADER_USER_RESET);

            if (clientLimitHeader == null || clientRemainHeader == null || userLimitHeader == null || userRemainHeader == null || userResetHeader == null) {
                LOG.info("Client and user rate limit headers not available for endpoint {}", req.getURI());
            } else {
                Integer clientLimit = Integer.parseInt(clientLimitHeader.getValue());
                Integer clientRemain = Integer.parseInt(clientRemainHeader.getValue());
                String clientReset = "?";

                logLimit("Client", clientLimit, clientRemain, clientReset);

                creds.setClientLimit(clientLimit);
                creds.setClientRemaining(clientRemain);

                Integer userLimit = Integer.parseInt(userLimitHeader.getValue());
                Integer userRemain = Integer.parseInt(userRemainHeader.getValue());
                String userReset = userResetHeader.getValue();

                logLimit("User", userLimit, userRemain, userReset);

                creds.setUserLimit(userLimit);
                creds.setUserRemaining(userRemain);
                creds.setUserReset(Long.parseLong(userReset));
            }
        }

        imgur.CREDITS.setCreditsFromResponse(creds);
    }

    private void logLimit(String type, int limit, int remain, String reset) {
        if (remain == 0) {
            // We done fucked up bad
            LOG.error("API {} Limit reached, resets on {}", type, reset);
        } else if ((limit * FRACTION) < remain) {
            // things are getting ropey
            LOG.warn("API {} Limit approaching - {} of {} remain, resets on {}", type, remain, limit, reset);
        } else {
            // All good
            LOG.debug("API {} Limit ok - {} of {} remain, resets on {}", type, remain, limit, reset);
        }
    }
}
