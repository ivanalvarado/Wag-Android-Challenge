package com.ivanalvaradoapps.wagandroidchallenge.rest;

import com.ivanalvaradoapps.wagandroidchallenge.model.SOUsers;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by ivanalvarado on 2/26/18.
 */

public interface StackOverflowService {
    @GET("users?site=stackoverflow")
    Call<SOUsers> getUsers();
}
