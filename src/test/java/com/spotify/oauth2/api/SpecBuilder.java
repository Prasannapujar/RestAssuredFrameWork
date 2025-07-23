package com.spotify.oauth2.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class SpecBuilder {
public static String accessToken="BQDlNHpEJo6Yoqp3Hhhxj8GDelFH55WHjSqir2qz4WzNVgDsvNGzQne2MQNlIrJ4N6iVrkfC1tSac60Q3sY9Yh8zX6zyHNwhYTDD_y8TFljRKffHQEsKYuFgXVKkp51m5EZX6ouWVNXGCykWy-L2NSfdVUlpl40hlvD-UnzjBAv1YHLr5ozVR7Az6zgbPP-8F8AjGxgf0jLGNpkfsSGxFf-AZFlU42XCQN7a9v-VLS5PZ74YlUhMgBxQlda0s6Te5VSCNtcxl2LmuCmgAjfJ-dQdifG0Vfpk8VpLY5jkl5fPK_rcsZnk4Zpn";

public static RequestSpecification getRequestSpec() {
    System.out.println("Creating Request Specification with base URI: https://api.spotify.com");
        return new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .setContentType(ContentType.JSON)
              //  .addHeader("Authorization", "Bearer " + accessToken)
                .log(LogDetail.ALL)
                .build();
    }

    public static RequestSpecification getRequestSpecWithAuth(String accessToken) {
        return new RequestSpecBuilder()
                .setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization", "Bearer " + accessToken)
                .log(LogDetail.ALL)
                .build();

    }

    public static ResponseSpecification getResponseSpecification()
    {
        return new ResponseSpecBuilder()
                .log(LogDetail.ALL)
                .build();
    }
}