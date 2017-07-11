package net.cromulence.imgur.apiv3;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImgurOAuthUtils {

    public static void getAndExchangePin(Imgur imgur, ImgurOAuthHandler ah) throws IOException, ApiRequestException {
        getAndExchangePin(imgur, ah, null);
    }

    public static void getAndExchangePin(Imgur imgur, ImgurOAuthHandler ah, String state) throws IOException, ApiRequestException {

        String pinLoginUrl = String.format("%s/authorize?response_type=pin&client_id=%s%s", imgur.auth.getEndpointUrl(), imgur.data.getClientId(), (state == null ? "" : "&state=" + state));

        System.out.println("Go to this URL in your browser, log in as the user you wish to use");
        System.out.println(pinLoginUrl);
        System.out.println();
        System.out.println("Then paste in the PIN you are given here:");
        System.out.println("> ");

        BufferedReader bin = new BufferedReader(new InputStreamReader(System.in));
        final String readLine = bin.readLine();
        imgur.auth.exchangePin(readLine, ah);
    }
}
