package com.example.cosmeticdiary.retrofit;

import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.model.ProfileModel;
import com.example.cosmeticdiary.model.SearchResultModel;

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
    Call<SearchResultModel> getSearchCosmetic(@Field("name") String name);

    @FormUrlEncoded
    @POST(Common.SearchWritingURL)
    Call<SearchResultModel> getSearchWriting(@Field("title") String title);

    @FormUrlEncoded
    @POST(Common.SearchProfileURL)
    Call<ProfileModel> getSearchProfile(@Field("id") String userID);

    @FormUrlEncoded
    @POST(Common.FindIdURL)
    Call<LoginModel> getFindId(@Field("name") String userID,
                               @Field("email") String email);

    @FormUrlEncoded
    @POST(Common.FindPwURL)
    Call<LoginModel> getFindPw(@Field("id") String userID,
                               @Field("password") String password,
                               @Field("email") String email);

    @FormUrlEncoded
    @POST(Common.SendWritingURL)
    Call<LoginModel> getWriting(@Field("id") String id,
                                @Field("cosmetic") String cosmetic,
                                @Field("image") String image,
                                @Field("satisfy") String satisfy,
                                @Field("content") String content,
                                @Field("ingredient") String ingredient,
                                @Field("jopssal") String jopssal,
                                @Field("dry") String dry,
                                @Field("hwanongsung") String hwanongsung,
                                @Field("good") String good,
                                @Field("trouble") String trouble,
                                @Field("etc") String etc
    );

//    @Multipart
//    @POST(Common.SendWritingURL)
//    Call<LoginModel> userEdit(@Part MultipartBody.Part postImg, @PartMap HashMap<String, RequestBody> data);
//    @GET(Common.LoginURL)
//    Call<ArrayList<GenderCompositionModel>> getGenderComposition(@Query("pID") int pID);

}
