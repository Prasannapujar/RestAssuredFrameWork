package com.spotify.oauth2.api;

import com.spotify.oauth2.util.ConfigLoader;
import io.restassured.response.Response;

import java.time.Instant;
import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class TokenManager {


  static   HashMap<String,String> Parms = new HashMap<String,String>();
    static private String refreshToken ="AQBk9Mpw77jMAnAC1tPNyAnUSaRx6QB4sabcsYWxpsA_AOncIy2XZ3Y0T4ghBg-9q_uiXrQttp3_i-kT5RZ6pKTSv4GlscY8kXxwOULoDY03Bh4s4C3EA81ZkdwRXJRyRTQ";
    static private String accessToken;
    static private  Instant expiresAt;
   // insert your client_id and client_secret here in formParms


    public static String  getAccessesToken()
    {
        if(accessToken == null || expiresAt == null || Instant.now().isAfter(expiresAt)) {
            System.out.println("Getting new access token");
            accessToken = getToken();

             // Assuming token is valid for 1 hour
        }else
        {
            System.out.println("Using existing access token");
        }

        return accessToken;
    }



    private static  String getToken()
    {

        Parms.put("client_id", ConfigLoader.getInstance().getClientId());
        Parms.put("client_secret", ConfigLoader.getInstance().getClientSecret());
        Parms.put("grant_type", ConfigLoader.getInstance().getGrantType());
        Parms.put("redirect_uri","https://localhost:8080");
        Parms.put("refresh_token", ConfigLoader.getInstance().getRefreshToken());

        Response response= given().spec(SpecBuilder.getAccountRequestSpec())
                .formParams(Parms)
                .when()
                .post("/api/token")
                .then()
                .spec(SpecBuilder.getResponseSpecification())
                .extract()
                .response();

                if(response.statusCode() != 200) {
                    throw new RuntimeException("Failed to refresh access token: " + response.getBody().asString());
                }
                int expirestAt=response.jsonPath().getInt("expires_in");
                expiresAt = Instant.now().plusSeconds(expirestAt);
                // Subtracting 5 minutes for safety margin
                expiresAt = expiresAt.minusSeconds(300); // 5 minutes safety margin
        return response.jsonPath().getString("access_token");

    }
}
