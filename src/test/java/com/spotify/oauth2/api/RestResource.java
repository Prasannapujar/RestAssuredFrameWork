package com.spotify.oauth2.api;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class RestResource {

    public static Response get(String path,String accessToken) {
        return given().spec(SpecBuilder.getRequestSpec())
                .header("Authorization", "Bearer " + accessToken)
               // . when()
                .get(path)
                .then()
                .spec(SpecBuilder.getResponseSpecification())
                .extract()
                .response();
    }

    public static Response post(String path, Object body, String accessToken) {
        System.out.println("Creating resource at path: " + path + " with accessToken: " + accessToken);
        return given(SpecBuilder.getRequestSpec())
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .post(path)
                .then()
                .spec(SpecBuilder.getResponseSpecification())
                .extract()
                .response();
    }

    public static Response put(String path, Object body,String accessToken) {
        return given(SpecBuilder.getRequestSpec())
                .header("Authorization", "Bearer " + accessToken)
                .body(body)
                .when()
                .put(path)
                .then()
                .spec(SpecBuilder.getResponseSpecification())
                .extract()
                .response();
    }

    public static Response delete(String path) {
        return SpecBuilder.getRequestSpec()
                .when()
                .delete(path)
                .then()
                .spec(SpecBuilder.getResponseSpecification())
                .extract()
                .response();
    }
}
