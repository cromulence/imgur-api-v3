package net.cromulence.imgur.apiv3;

import net.cromulence.datawrapper.DataStoreConnector;
import net.cromulence.datawrapper.DataWrapper;
import net.cromulence.datawrapper.DataWrapperImpl;

public class ImgurApplicationDataImpl extends DataWrapperImpl implements ImgurApplicationData {

    private static final String KEY_CLIENT_ID = "key-client-id";

    private static final String KEY_CLIENT_SECRET = "key-client-secret";

    public ImgurApplicationDataImpl(DataStoreConnector connector) {
        super(connector);
    }

    public ImgurApplicationDataImpl(DataStoreConnector connector, String prefix) {
        super(connector, prefix);
    }

    public ImgurApplicationDataImpl(DataWrapper delegateWrapper) {
        super(delegateWrapper);
    }

    public ImgurApplicationDataImpl(DataWrapper delegateWrapper, String prefix) {
        super(delegateWrapper, prefix);
    }

    @Override
    public void setClientId(String clientId) {
        putString(KEY_CLIENT_ID, clientId);
    }

    @Override
    public String getClientId() {
        return getString(KEY_CLIENT_ID);
    }

    @Override
    public void setClientSecret(String clientSecret) {
        putString(KEY_CLIENT_SECRET, clientSecret);
    }

    @Override
    public String getClientSecret() {
        return getString(KEY_CLIENT_SECRET);
    }
}
