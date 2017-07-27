package net.cromulence.imgur.apiv3.datamodel.constants;

public enum AuthResponseType {
    PIN,
    AUTHORIZATION_CODE,
    REFRESH_TOKEN;

    @Override
    public String toString() {
        return super.toString().toLowerCase();
    }
}
