package com.example.cosmeticdiary.retrofit;

import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.model.SearchResult;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface RetrofitService {
    @FormUrlEncoded
    @POST(Common.LoginURL)
    Call<LoginModel> getLoginCheck(@Field("id") String userID,
                                   @Field("password") String password);

    @FormUrlEncoded
    @POST(Common.DupCheckURL)
    Call<LoginModel> getDoubleCheck(@Field("id") String userID);

    @FormUrlEncoded
    @POST(Common.RegisterURL)
    Call<LoginModel> getRegister(@Field("id") String userID,
                                    @Field("password") String password,
                                    @Field("name") String name,
                                    @Field("email") String email);

    @FormUrlEncoded
    @POST(Common.SearchCosmeticURL)
    Call<SearchResult> getSearchCosmetic(@Field("name") String name);

    @FormUrlEncoded
    @POST(Common.SearchWritingURL)
    Call<SearchResult> getSearchWriting(@Field("title") String title);

//    @GET(Common.LoginURL)
//    Call<ArrayList<GenderCompositionModel>> getGenderComposition(@Query("pID") int pID);

}
