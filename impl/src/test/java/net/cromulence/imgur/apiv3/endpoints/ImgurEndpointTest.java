package net.cromulence.imgur.apiv3.endpoints;

import com.google.gson.GsonBuilder;

import net.cromulence.datawrapper.DataWrapper;
import net.cromulence.datawrapper.DataWrapperImpl;
import net.cromulence.datawrapper.properties.PropertiesFileDataStoreConnector;
import net.cromulence.imgur.apiv3.ImgurDatastore;
import net.cromulence.imgur.apiv3.ImgurOAuthDataImpl;
import net.cromulence.imgur.apiv3.ImgurOAuthUtils;
import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;
import net.cromulence.imgur.apiv3.auth.PersistingOAuthHandler;

import org.junit.Before;
import org.junit.BeforeClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

public class ImgurEndpointTest {

    private static Logger LOG = LoggerFactory.getLogger(ImgurEndpointTest.class);

    private static File propertiesFile;

    private static ImgurDatastore data;

    private static Imgur user1ImgurUnderTest;
    private static Imgur user2ImgurUnderTest;

    private static PersistingOAuthHandler user1Auth;
    private static PersistingOAuthHandler user2Auth;

    private static String[] propertiesPaths = new String[]{"./impl/src/test/resources", "./src/test/resources", "/home/travis"};

    @BeforeClass
    public static void setup() throws Exception {

        propertiesFile = null;

        for(String path : propertiesPaths) {
            File parent = new File(path);
            File file = new File(parent, "test.properties");
            if(file.exists() && file.isFile()&& file.canRead() && file.length() > 0) {
                propertiesFile = file;
                LOG.info("found test.properties at {}", file.getAbsolutePath());
                break;
            }
        }

        if(propertiesFile == null) {
            throw new IllegalStateException("Unable to find test.properties");
        }

        PropertiesFileDataStoreConnector propertiesFileDataStoreConnector = new PropertiesFileDataStoreConnector(propertiesFile);

        DataWrapper dw = new DataWrapperImpl(propertiesFileDataStoreConnector);

        data = new ImgurDatastore(dw);

        ImgurOAuthDataImpl user1AuthData = new ImgurOAuthDataImpl(propertiesFileDataStoreConnector, "alice");
        ImgurOAuthDataImpl user2AuthData = new ImgurOAuthDataImpl(propertiesFileDataStoreConnector, "bob");

        user1Auth = new PersistingOAuthHandler(user1AuthData);
        user2Auth = new PersistingOAuthHandler(user2AuthData);

        user1ImgurUnderTest = new Imgur(data, user1Auth);
        user2ImgurUnderTest = new Imgur(data, user2Auth);

        if (!user1Auth.hasValidAccessToken()) {
            ImgurOAuthUtils.getAndExchangePin(user1ImgurUnderTest, user1Auth, user1AuthData.getUsername());
        }

        if (!user2Auth.hasValidAccessToken()) {
            ImgurOAuthUtils.getAndExchangePin(user2ImgurUnderTest, user2Auth, user2AuthData.getUsername());
        }

        getUser1ImgurUnderTest().AUTH_UTILS.refreshToken();
        getUser2ImgurUnderTest().AUTH_UTILS.refreshToken();
    }

    @Before
    public void refreshTokens() throws Exception {
        getUser1ImgurUnderTest().AUTH_UTILS.refreshToken();
        getUser2ImgurUnderTest().AUTH_UTILS.refreshToken();
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
        setup();
    }

    protected void pause(Logger logger, String s, long i) {
        logger.info(s);
        try {
            Thread.sleep(i);
        } catch (InterruptedException e) {
            // noop
        }
    }
}
