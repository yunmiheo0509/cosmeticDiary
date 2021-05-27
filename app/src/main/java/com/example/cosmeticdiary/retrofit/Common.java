package com.example.cosmeticdiary.retrofit;

public class Common {

    //*실제 device
//    public static final String BASE_SERVER_URL = "http://192.168.200.131:8080";
    //*에뮬레이터
//    public static final String BASE_SERVER_URL = "http://192.168.200.106:4000";
//    public static final String BASE_SERVER_URL = "http://192.168.1.178:4000";
    public static final String BASE_SERVER_URL = "https://23094551eadd.ngrok.io";

    public static final String SearchUserURL = "/users/search"; //유저검색
    public static final String RegisterURL = "/register"; //회원가입
    public static final String LoginURL = "/login"; //로그인
    public static final String DupCheckURL = "/dupCheck"; //중복확인
    public static final String SearchCosmeticURL = "/search_cosmetic"; //화장품검색
    public static final String SearchWritingURL = "/search_writing"; //글검색
    public static final String SearchProfileURL = "/"; //프로필정보검색

    //

}
