package com.example.cosmeticdiary.util.retrofit;

import com.example.cosmeticdiary.model.LoginModel;
import com.example.cosmeticdiary.model.ProfileModel;
import com.example.cosmeticdiary.model.SearchResultModel;
import com.google.gson.JsonObject;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

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
                                 @Field("email") String email,
                                 @Field("alarm") int alarm);
    @FormUrlEncoded
    @POST(Common.SearchProfileURL)
    Call<ProfileModel> getSearchProfile(@Field("id") String userID);

    @FormUrlEncoded
    @POST(Common.SearchCosmeticURL)
    Call<SearchResultModel> getSearchCosmetic(@Field("name") String name);

    @FormUrlEncoded
    @POST(Common.SearchWritingURL)
    Call<SearchResultModel> getSearchWriting(@Field("id") String userID,
                                             @Field("title") String title);

    @FormUrlEncoded
    @POST(Common.SearchCalenderURL)
    Call<SearchResultModel> getSearchCalender(@Field("id") String userID,
                                              @Field("date") String date);


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
    @POST(Common.ChangePwURL)
    Call<LoginModel> getChangePw(@Field("id") String userID,
                                 @Field("password") String password);

    @FormUrlEncoded
    @POST(Common.SendWritingURL)
    Call<LoginModel> getWriting(@Field("id") String id,
                                @Field("cosmetic") String cosmetic,
                                @Field("image") String image,
                                @Field("satisfy") String satisfy,
                                @Field("content") String content,
                                @Field("date") String date,
                                @Field("ingredient") String ingredient,
                                @Field("jopssal") String jopssal,
                                @Field("dry") String dry,
                                @Field("hwanongsung") String hwanongsung,
                                @Field("good") String good,
                                @Field("trouble") String trouble,
                                @Field("etc") String etc);

    @FormUrlEncoded
    @POST(Common.EditWritingURL)
    Call<LoginModel> EditWriting(@Field("id") String id,
                                 @Field("cosmetic") String cosmetic,
                                 @Field("image") String image,
                                 @Field("satisfy") String satisfy,
                                 @Field("content") String content,
                                 @Field("date") String date,
                                 @Field("ingredient") String ingredient,
                                 @Field("jopssal") String jopssal,
                                 @Field("dry") String dry,
                                 @Field("hwanongsung") String hwanongsung,
                                 @Field("good") String good,
                                 @Field("trouble") String trouble,
                                 @Field("etc") String etc,
                                 @Field("cosmeticNameDB") String cosmeticNameDB);

    @FormUrlEncoded
    @POST(Common.DeleteWritingURL)
    Call<LoginModel> deleteWriting(@Field("id") String id,
                                   @Field("content") String content,
                                   @Field("date") String date,
                                   @Field("cosmeticNameDB") String cosmeticNameDB);


    @GET("weather?")
    Call<JsonObject> getWeather(@Query("lat") String lat,
                                @Query("lon") String lon,
                                @Query("APPID") String APPID);

    @FormUrlEncoded
    @POST(Common.EditProfileURL)
    Call<LoginModel> EditProfile(@Field("id") String id,
                                 @Field("name") String name,
                                 @Field("gender") String gender,
                                 @Field("age") String age,
                                 @Field("skintype") String skintype,
                                 @Field("allergy") String allergy);

    @FormUrlEncoded
    @POST(Common.SetAlarmURL)
    Call<LoginModel> SetAlarm(@Field("id") String id,
                              @Field("alarm") int alarm);

}
