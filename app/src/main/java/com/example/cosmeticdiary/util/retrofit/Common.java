package com.example.cosmeticdiary.util.retrofit;

public class Common {
//
    public static final String BASE_SERVER_URL = "http://192.168.219.106:4000";
//    public static final String BASE_SERVER_URL = "https://6b40d6e985af.ngrok.io";

    public static final String RegisterURL = "/register"; //회원가입
    public static final String LoginURL = "/login"; //로그인
    public static final String FindIdURL = "/findId"; //아이디찾기
    public static final String FindPwURL = "/findPw"; //비밀번호찾기
    public static final String ChangePwURL = "/changePw"; //비밀번호찾기
    public static final String DupCheckURL = "/dupCheck"; //중복확인

    public static final String SearchCosmeticURL = "/search_cosmetic"; //화장품검색
    public static final String SearchWritingURL = "/search_writing"; //글검색
    public static final String SearchProfileURL = "/search_profile"; //프로필정보검색
    public static final String SearchCalenderURL = "/search_calender"; //날짜에 맞는 글 띄우기

    public static final String SendWritingURL ="/send_writing";//글작성
    public static final String EditWritingURL ="/edit_writing";//글수정
    public static final String DeleteWritingURL ="/delete_writing";//글삭제

    //

}
