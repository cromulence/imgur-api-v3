package net.cromulence.imgur.apiv3;

import net.cromulence.imgur.apiv3.api.Imgur;
import net.cromulence.imgur.apiv3.api.exceptions.ApiRequestException;
import net.cromulence.imgur.apiv3.auth.ImgurOAuthHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ImgurOAuthUtils {

    public static void getAndExchangePin(Imgur imgur, ImgurOAuthHandler ah, String user) throws IOException, ApiRequestException {

        String pinLoginUrl = String.format("%s/authorize?response_type=pin&client_id=%s&state=%s", imgur.AUTH_UTILS.getEndpointUrl(), imgur.DATA.getClientId(), user);

        System.out.println(user);
        System.out.println(pinLoginUrl);

        BufferedReader bin = new BufferedReader(new InputStreamReader(System.in));

        String readLine = bin.readLine();

        imgur.AUTH_UTILS.exchangePin(readLine, ah);
    }
}
