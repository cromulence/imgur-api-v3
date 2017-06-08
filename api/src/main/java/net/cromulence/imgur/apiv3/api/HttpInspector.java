package net.cromulence.imgur.apiv3.api;

import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;

public interface HttpInspector {

    void postExecute(HttpRequestBase req, HttpResponse response, ImgurOAuthHandler ah);

    void preExecute(HttpRequestBase req, ImgurOAuthHandler ah);

}