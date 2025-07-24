package com.spotify.oauth2.test;

import com.spotify.oauth2.api.TokenManager;
import com.spotify.oauth2.api.applicationApi.PlayListApi;
import com.spotify.oauth2.pojo.Error;
import com.spotify.oauth2.pojo.Playlist;
import com.spotify.oauth2.util.ConfigLoader;
import com.spotify.oauth2.util.DataLoader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

public class PlayListV2 {

    String accessToken="BQBSRa_Z_mpzgkgC656bjjJ3afIaXsJXr6yyD-wFsb8nn4461_2h568mBtN7IWOucbNI4iF_j9YQ1ioailFJNrELHE0BFN2HYKISOWbcqg0I_DXgvXU8xRP0ZqZSyGh8KzVhmxzHGNp9lpZIoggUETfCfbfQl3gk46ilvYOixSbFyH30PiM565JaHEESsHwMwKbsPqW04U2_x-pGf2AXPk4k7nrEwg6eiMneHda1Sg8K3LQuZchbcXqpbnxMnmDs61WsH0azHEqExGLRPAQWar43GBzSHoWQnYZKuXjKT9hySCl5ZhzJeZJV";
    @Test
    public  void shouldBeAbleToCreatePlaylistWithPojo() {
        // Example test method for getting a playlist
        System.out.println("Running shouldbeAbleToGetPlaylist");

        Playlist playlistExpected= new Playlist()
                .setName("This is a test playlist")
                .setPublic(false)
                 .setDescription("This playlist is created for testing purposes");
        System.out.println("Playlist Expected: " + playlistExpected);
        Response response= PlayListApi.post(playlistExpected, DataLoader.getInstance().getUser(), TokenManager.getAccessesToken());
       Playlist actualPlaylist= response.as(Playlist.class);

       assertThat(actualPlaylist.getName(), equalTo(playlistExpected.getName()));
       assertThat(actualPlaylist.getDescription(), equalTo(playlistExpected.getDescription()));
       assertThat(actualPlaylist.getPublic(), equalTo(playlistExpected.getPublic()));

    }

    @Test
    public void shouldNotCreatePlaylistWithInvalidTokenWithPojo() {
        // Example test method for creating a playlist with an invalid token
        System.out.println("Running shouldNotCreatePlaylistWithInvalidToken");
        Playlist playlistExpected= new Playlist()
                .setName("This is a test playlist")
                .setPublic(false)
                .setDescription("This playlist is created for testing purposes");

        Response response=PlayListApi.post(playlistExpected,DataLoader.getInstance().getUser(), "123213");
        Error error= response.as(Error.class);

        assertThat(error.getError().getMessage(), equalTo("Invalid access token"));
        assertThat(error.getError().getStatus(), equalTo(401));


    }

    @Test
    public void shouldBeAbleToGetPlaylist() {
        // Example test method for getting a playlist
        System.out.println("Running shouldbeAbleToGetPlaylist");

        Response response= PlayListApi.get(DataLoader.getInstance().getGetPlaylistId(), TokenManager.getAccessesToken());

               Playlist actualPlaylist = response.as(Playlist.class);


        assertThat(actualPlaylist.getName(), equalTo("Updated Playlist Name2"));
        assertThat(actualPlaylist.getDescription(), equalTo("Updated playlist description2"));

    }
    @Test
    public void updateplaylistPojo() {
        // Example test method for updating a playlist
        System.out.println("Running updateplaylistPojo");

        Playlist playlistExpected = new Playlist()
                .setName("Updated Playlist Name2")
                .setDescription("Updated playlist description2");

        Response response=PlayListApi.put(DataLoader.getInstance().getUpdatePlaylistId(), playlistExpected, TokenManager.getAccessesToken());
        response.then().statusCode(200);



    }
}



