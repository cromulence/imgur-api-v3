package net.cromulence.imgur.apiv3.api;

import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ImgurApiLimitLoggingInspector implements HttpInspector {

    private static final Logger LOG = LoggerFactory.getLogger(ImgurApiLimitLoggingInspector.class);

    private static final String PREFIX = "X-Post-Rate-Limit-";

    private static final String HEADER_LIMIT = PREFIX + "Limit";
    private static final String HEADER_REMAINING = PREFIX + "Remaining";
    private static final String HEADER_RESET = PREFIX + "Reset";

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


        // TODO
        //Access-Control-Expose-Headers:
        // X-RateLimit-ClientLimit,
        // X-RateLimit-ClientRemaining,
        // X-RateLimit-UserLimit,
        // X-RateLimit-UserRemaining,
        // X-RateLimit-UserReset

        // Check the response headers for the limit count
        Header limitHeader = response.getFirstHeader(HEADER_LIMIT);
        Header remainingHeader = response.getFirstHeader(HEADER_REMAINING);
        Header resetHeader = response.getFirstHeader(HEADER_RESET);

        if (limitHeader == null || remainingHeader == null || resetHeader == null) {
            // account endpoint doesn't return limit headers - no cost endpoint?
            if (!req.getURI().toString().contains("://api.imgur.com/3/account") &&
                !req.getURI().toString().contains("://api.imgur.com/3/credits")) {
                LOG.info("App rate limit headers not available for endpoint {}", req.getURI());
            }
            return;
        }

        Integer limit = Integer.parseInt(limitHeader.getValue());
        Integer remaining = Integer.parseInt(remainingHeader.getValue());
        String reset = resetHeader.getValue();

        if (remaining == 0) {
            // We done fucked up bad
            LOG.error("API Limit reached, resets on {}", reset);
        } else if ((limit * FRACTION) < remaining) {
            // things are getting ropey
            LOG.warn("API Limit approaching - {} of {} remain, resets on {}", remaining, limit, reset);
        } else {
            // All good
            LOG.debug("API Limit ok - {} of {} remain, resets on {}", remaining, limit, reset);
        }

        // TODO
        // imgur.CREDITS.setHeaderLimitInfo(limit, remaining, reset);
    }
}
