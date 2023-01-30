package com.datechnologies.androidtest.api;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostLoginService {
    @FormUrlEncoded
    @POST("Tests/scripts/login.php")
    Call<RetroLoginResponse> login(@Field("email") String username, @Field("password") String password);
    //@Field – send data as form-urlencoded. This requires a @FormUrlEncoded annotation attached with the method.
}
