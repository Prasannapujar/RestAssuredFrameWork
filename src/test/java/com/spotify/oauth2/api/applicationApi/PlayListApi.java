package com.spotify.oauth2.api.applicationApi;

import com.spotify.oauth2.api.RestResource;
import com.spotify.oauth2.pojo.Playlist;
import io.restassured.response.Response;

public class PlayListApi {

    public static Response get(String playlistId, String accessToken) {
        return RestResource.get("/playlists/" + playlistId, accessToken);

    }

    public static Response post(Playlist playlist, String userId, String accessToken) {
        System.out.println("Creating playlist with userId: " + userId + " and accessToken: " + accessToken);
        return RestResource.post("/users/"+userId+"/playlists", playlist, accessToken);

    }

    public static Response put(String playlistId, Playlist playlist, String accessToken) {
        return RestResource.put("/playlists/" + playlistId, playlist, accessToken);

    }
}
