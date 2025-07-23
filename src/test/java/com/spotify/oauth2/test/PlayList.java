package com.spotify.oauth2.test;

import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlayList {
RequestSpecification requestSpecification;
ResponseSpecification responseSpecification;
    String accessToken="BQDlNHpEJo6Yoqp3Hhhxj8GDelFH55WHjSqir2qz4WzNVgDsvNGzQne2MQNlIrJ4N6iVrkfC1tSac60Q3sY9Yh8zX6zyHNwhYTDD_y8TFljRKffHQEsKYuFgXVKkp51m5EZX6ouWVNXGCykWy-L2NSfdVUlpl40hlvD-UnzjBAv1YHLr5ozVR7Az6zgbPP-8F8AjGxgf0jLGNpkfsSGxFf-AZFlU42XCQN7a9v-VLS5PZ74YlUhMgBxQlda0s6Te5VSCNtcxl2LmuCmgAjfJ-dQdifG0Vfpk8VpLY5jkl5fPK_rcsZnk4Zpn";
    @BeforeClass
    public void setup() {
        // Setup code for the PlayList test class
        System.out.println("Setting up PlayList tests");

        RequestSpecBuilder builder= new RequestSpecBuilder().setBaseUri("https://api.spotify.com")
                .setBasePath("/v1")
                .addHeader("Authorization", "Bearer "+accessToken)
                .addHeader("Content-Type", "application/json").
                 log(io.restassured.filter.log.LogDetail.ALL);

        ResponseSpecBuilder responseSpecBuilder= new ResponseSpecBuilder()
             //   .expectContentType("application/json")
                .log(io.restassured.filter.log.LogDetail.ALL);


      requestSpecification = builder.build();
       responseSpecification = responseSpecBuilder.build();
    }

    @Test
    public void testCreatePlaylist() {
        // Example test method for creating a playlist
        System.out.println("Running testCreatePlaylist");

        // Here you would add the code to create a playlist using the Spotify API
        // For example:
        // given().body("{\"name\": \"New Playlist\", \"description\": \"My new playlist\", \"public\": false}")
        //         .when().post("/users/{user_id}/playlists")
        //         .then().statusCode(201);

        given().spec(requestSpecification)
                .pathParam("user_id", "31athfybzridlo4tgkv4l5w2sybi") // Replace with actual user ID
                .body("{\"name\": \"New Playlist\", \"description\": \"My new playlist\", \"public\": false}")
                .when()
                .post("/users/{user_id}/playlists")
                .then().spec(responseSpecification)
                .statusCode(201)
                .body("name", equalTo("New Playlist"))
                .body("description", equalTo("My new playlist"))
                .body("public", equalTo(false))
                .log().all();
    }

    @Test
    public void shouldNotCreatePlaylistWithInvalidToken() {
        // Example test method for creating a playlist with an invalid token
        System.out.println("Running shouldNotCreatePlaylistWithInvalidToken");

        given().baseUri("https://api.spotify.com")
                .basePath("/v1")
                .contentType("application/json")
                .pathParam("user_id", "31athfybzridlo4tgkv4l5w2sybi") // Replace with actual user ID
                .header("Authorization", "Bearer invalid_token")
                .body("{\"name\": \"New Playlist\", \"description\": \"My new playlist\", \"public\": false}")
                .when()
                .post("/users/{user_id}/playlists")
                .then()
                .statusCode(401)
                .body("error.status", equalTo(401))
                .body("error.message", equalTo("Invalid access token"))
                .log().all();
    }

    @Test
    public  void shouldBeAbleToCreatePlaylistWithPojo() {
        // Example test method for getting a playlist
        System.out.println("Running shouldbeAbleToGetPlaylist");
        Playlist playlistExpected= new Playlist()
                .setName("This is a test playlist")
                .setPublic(false)
                 .setDescription("This playlist is created for testing purposes");


       Playlist actualPlaylist= given().spec(requestSpecification)
                .pathParam("user_id", "31athfybzridlo4tgkv4l5w2sybi")
                .body(playlistExpected)// Replace with actual playlist ID
                .when()
               .post("/users/{user_id}/playlists")
                .then().spec(responseSpecification)
                .statusCode(201)
                .extract()
                .response()
                .as(Playlist.class);

       assertThat(actualPlaylist.getName(), equalTo(playlistExpected.getName()));
       assertThat(actualPlaylist.getDescription(), equalTo(playlistExpected.getDescription()));
       assertThat(actualPlaylist.getPublic(), equalTo(playlistExpected.getPublic()));

    }

    @Test
    public void shouldNotCreatePlaylistWithInvalidTokenWithPojo() {
        // Example test method for creating a playlist with an invalid token
        System.out.println("Running shouldNotCreatePlaylistWithInvalidToken");

      com.spotify.oauth2.pojo.Error error= given().baseUri("https://api.spotify.com")
                .basePath("/v1")
                .contentType("application/json")
                .pathParam("user_id", "31athfybzridlo4tgkv4l5w2sybi") // Replace with actual user ID
                .header("Authorization", "Bearer invalid_token")
                .body("{\"name\": \"New Playlist\", \"description\": \"My new playlist\", \"public\": false}")
                .when()
                .post("/users/{user_id}/playlists")
                .then()
                .statusCode(401)
                .extract()
                .response()
                .as(Error.class);
      assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
        assertThat(error.getError().getStatus(), equalTo(401));


    }

    @Test
    public void shouldBeAbleToGetPlaylist() {
        // Example test method for getting a playlist
        System.out.println("Running shouldbeAbleToGetPlaylist");

        given().spec(requestSpecification)
                .pathParam("playlist_id", "1IOVY2ej9hmMOsYBOwzwm7") // Replace with actual playlist ID
                .when()
                .get("/playlists/{playlist_id}")
                .then().spec(responseSpecification)
                .statusCode(200)
                .body("name", equalTo("Updated Playlist Name"))
                .body("description", equalTo("Updated playlist description"))
                .log().all();
    }
    @Test
    public void updateplaylistPojo() {
        // Example test method for updating a playlist
        System.out.println("Running updateplaylistPojo");

        Playlist playlistExpected = new Playlist()
                .setName("Updated Playlist Name2")
                .setDescription("Updated playlist description2");

        given().spec(requestSpecification)
                .pathParam("playlist_id", "1IOVY2ej9hmMOsYBOwzwm7") // Replace with actual playlist ID
                .body(playlistExpected)
                .when()
                .put("/playlists/{playlist_id}")
                .then().spec(responseSpecification)
                .statusCode(200);

    }
}



