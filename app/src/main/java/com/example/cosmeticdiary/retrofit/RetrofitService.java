package com.example.cosmeticdiary.retrofit;

import com.example.cosmeticdiary.model.LoginModel;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {
    @FormUrlEncoded
    @POST(Common.LoginURL)
    Call<LoginModel> getLoginCheck(@Field("id") String userID,
                                   @Field("password") String password);
//    @GET(Common.LoginURL)
//    Call<ArrayList<GenderCompositionModel>> getGenderComposition(@Query("pID") int pID);

}
