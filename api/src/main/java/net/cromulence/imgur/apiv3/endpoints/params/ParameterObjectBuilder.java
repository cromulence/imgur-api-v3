package net.cromulence.imgur.apiv3.endpoints.params;

@FunctionalInterface
public interface ParameterObjectBuilder<P extends ParameterObject> {
    P build();
}
