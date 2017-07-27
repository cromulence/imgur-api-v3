package net.cromulence.imgur.apiv3;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;
import net.cromulence.imgur.apiv3.datamodel.constants.AuthResponseType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImgurOAuthUtils {

    /**
     * Utility method to help authenticate a console-based session
     * @param imgur Imgur API object
     * @param ah OAuth Handler
     * @throws IOException If there are problems reading from stdin
     * @throws ApiRequestException If there are problems communicating with Imgur
     */
    public static void getAndExchangePin(Imgur imgur, ImgurOAuthHandler ah) throws IOException, ApiRequestException {

        String pinLoginUrl = getAuthUrl(imgur, AuthResponseType.PIN);

        System.out.println("Go to this URL in your browser, log in as the user you wish to use");
        System.out.println(pinLoginUrl);
        System.out.println();
        System.out.println("Then paste in the PIN you are given here:");
        System.out.println("> ");

        BufferedReader bin = new BufferedReader(new InputStreamReader(System.in));
        final String readLine = bin.readLine();
        imgur.auth.exchangePin(readLine, ah);
    }

    /**
     * Utility method to generate an auth URL
     * @param imgur Imgur API object
     * @param authResponseType pin etc
     * @return Auth URL
     */
    public static String getAuthUrl(Imgur imgur, AuthResponseType authResponseType) {
        return String.format("%s/authorize?response_type=%sn&client_id=%s", imgur.auth.getEndpointUrl(), authResponseType, imgur.data.getClientId());
    }
}