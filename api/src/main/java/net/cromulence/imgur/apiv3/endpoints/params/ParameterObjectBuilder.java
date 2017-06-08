package net.cromulence.imgur.apiv3.endpoints.params;

public interface ParameterObjectBuilder<PO extends ParameterObject> {
    PO build();
}
