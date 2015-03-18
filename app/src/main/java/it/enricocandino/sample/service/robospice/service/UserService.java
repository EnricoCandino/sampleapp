package it.enricocandino.sample.service.robospice.service;

import it.enricocandino.sample.service.robospice.response.AccountResponse;
import it.enricocandino.sample.service.robospice.response.ProfileResponse;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by enrico on 20/02/15.
 */
public interface UserService {

    @GET("/user/login")
    public AccountResponse login(
            @Query("user") String user,
            @Query("password") String password
    );

    @GET("/user/profile")
    public ProfileResponse getProfile();

}
