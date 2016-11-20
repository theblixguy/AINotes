package com.ssrij.ainotes;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface LUISInterface {

    String ENDPOINT = "https://api.projectoxford.ai";

    @GET("/luis/v2.0/apps/{appid}")
    Call<LUISResponse> getSearchResult(@Path("appid") String appId, @Query("subscription-key") String apiKey, @Query("q") String query);
}
