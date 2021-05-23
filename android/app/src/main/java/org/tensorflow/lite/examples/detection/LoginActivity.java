package org.tensorflow.lite.examples.detection;

import android.content.Context;
import android.content.DialogInterface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText login_id, login_pw;
    Button login;




    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if(isConnected() == false) isNotConnected_showAlert();

        login = findViewById(R.id.login_btn);
        login.setOnClickListener((view -> {
            login_id = findViewById(R.id.login_id);
            login_pw = findViewById(R.id.login_password);
            final Bundle bundle = new Bundle();

            new Thread(){
                @Override
                public void run(){
                    try {


                        Map<String, String> data = new HashMap<>();
                        data.put("userid", login_id.toString());
                        data.put("password", login_pw.toString());
                        data.put("redirect", "/");

                        // 로그인(POST)
                        Connection.Response response = Jsoup.connect("https://everytime.kr/user/login")
                                .userAgent("Mozilla/5.0")
                                .timeout(3000)
                                .header("Origin", "https://everytime.kr")
                                .header("Referer", "https://everytime.kr/")
                                .header("Host", "api.everytime.kr")
                                .header("Sec-Fetch-Dest", "document")
                                .header("sec-ch-ua-mobile", "?0")
                                .header("Sec-Fetch-Mode", "navigate")
                                .header("Sec-Fetch-Site", "same-origin")
                                .header("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"")
                                .header("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
                                .header("Content-Type", "application/x-www-form-urlencoded")
                                .header("Accept-Encoding", "gzip, deflate, br")
                                .header("Accept-Language", "ko-KR,ko;q=0.9,en;q=0.8")
                                .header("Sec-Fetch-User", "?1")
                                .header("Upgrade-Insecure-Requests", "1")
                                .data("userid",login_id.getText().toString())
                                .data("password",login_pw.getText().toString())
                                .data("redirect","/")
                                .method(Connection.Method.POST)
                                .execute();

                        Map<String, String> loginCookie = response.cookies();
                        String cookie = response.cookie("etsid");

                        System.out.println(cookie);


                        Connection.Response response2 = Jsoup.connect("https://api.everytime.kr/find/timetable/table/list/semester")
                                .header("Accept", "*/*")
                                .header("Accept-Encoding", "gzip, deflate, br")
                                .header("Accept-Language", "ko-KR,ko;q=0.9,en;q=0.8")
                                .header("Connection", "keep-alive")
                                .header("Content-Length", "20")
                                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36")
                                .header("Origin", "https://everytime.kr")
                                .header("Referer", "https://everytime.kr/")
                                .header("Host", "api.everytime.kr")
                                .header("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"")
                                .header("sec-ch-ua-mobile", "?0")
                                .header("Sec-Fetch-Dest", "empty")
                                .header("Sec-Fetch-Mode", "cors")
                                .header("Sec-Fetch-Site", "same-site")
                                .data("year","2021")
                                .data("semester","1")
                                .timeout(10000)
                                .cookie("etsid",cookie)
                                .method(Connection.Method.POST)
                                .execute();


//                        System.out.println(response2.parse());

                        String id_for_std = response2.parse().selectFirst("table").id();
                        System.out.println(id_for_std);

                        Connection.Response response3 = Jsoup.connect("https://api.everytime.kr/find/timetable/table")
                                .header("Accept", "*/*")
                                .header("Accept-Encoding", "gzip, deflate, br")
                                .header("Accept-Language", "ko-KR,ko;q=0.9,en;q=0.8")
                                .header("Connection", "keep-alive")
                                .header("Content-Length", "11")
                                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                                .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.85 Safari/537.36")
                                .header("Origin", "https://everytime.kr")
                                .header("Referer", "https://everytime.kr/")
                                .header("Host", "api.everytime.kr")
                                .header("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"")
                                .header("sec-ch-ua-mobile", "?0")
                                .header("Sec-Fetch-Dest", "empty")
                                .header("Sec-Fetch-Mode", "cors")
                                .header("Sec-Fetch-Site", "same-site")
                                .data("id",id_for_std)
                                .timeout(10000)
                                .cookie("etsid",cookie)
                                .method(Connection.Method.POST)
                                .execute();

                        System.out.println(response3.parse().select("table"));


//                        Map<String, String> data2 = new HashMap<>();
//                        data2.put("id", "******");
//                        data2.put("limit_num", "20");
//                        data2.put("start_num", "0");
//                        data2.put("moiminfo", "true");
//
//                        Connection.Response response2 = Jsoup.connect("https://api.everytime.kr/find/board/article/list")
//                                .userAgent("Mozilla/5.0")
//                                .timeout(3000)
//                                .header("Origin", "https://everytime.kr")
//                                .header("Referer", "https://everytime.kr/")
//                                .header("Accept", "*/*")
//                                .header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
//                                .header("Accept-Encoding", "gzip, deflate, br")
//                                .header("Accept-Language", "ko-KR,ko;q=0.9,en;q=0.8")
//                                .header("Connection", "keep-alive")
//                                .header("Content-Length", "48")
//                                .header("Host", "api.everytime.kr")
//                                .header("sec-ch-ua", "\"Google Chrome\";v=\"89\", \"Chromium\";v=\"89\", \";Not A Brand\";v=\"99\"")
//                                .header("sec-ch-ua-mobile", "?0")
//                                .header("Sec-Fetch-Dest", "empty")
//                                .header("Sec-Fetch-Mode", "cors")
//                                .header("Sec-Fetch-Site", "same-site")
//                                .data(data2)
//                                .cookies(loginCookie)
//                                .method(Connection.Method.POST)
//                                .execute();
//
//                        System.out.println(respon);
//                        Elements infos = response2.select("");
//                        System.out.println(infos);
//                        System.out.println( login_id.toString());
//                        System.out.println( login_pw.toString());
//                        Map<String, String> data = new HashMap<>();
//                        data.put("id", login_id.toString());
//                        data.put("pw", login_pw.toString());
//
//                        Connection.Response res = Jsoup.connect("https://sso.pusan.ac.kr/LoginServlet?method=idpwProcessEx&ssid=49")
//                                .data(data)
//                                .method(Connection.Method.POST)
//                                .execute();
//
//                        Document doc = res.parse();
//                        Map<String, String> cookies = res.cookies();
//
//                        Document doc2 = Jsoup.connect("https://e-onestop.pusan.ac.kr/index?home=home")
//                                .cookies(cookies)
//                                .get();
//                        System.out.println(doc2.html());
//                        System.out.println(cookies);
//
////                        Document doc2 = Jsoup.connect("https://e-onestop.pusan.ac.kr/middleware/study/privateTimeTable/getPrivateRegistrationYear")
////                                .cookie(cookies)
////                                .get();
////                        System.out.println(cookies);
                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }.start();

        }));

    }
    private void isNotConnected_showAlert(){
        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
        builder.setTitle("네트워크 연결 오류");
        builder.setMessage("사용 가능한 무선 네트워크가 없습니다. \n 네트워크 연결상태를 확인해 주세요.")
        .setCancelable(false)
        .setPositiveButton("확인", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
                Process.killProcess(Process.myPid());
            }
        });
        AlertDialog alert = builder.create();
        alert.show();


    }
    private boolean isConnected(){
        boolean connected = false;
        try {
            ConnectivityManager connect_m = (ConnectivityManager)getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo n_info = connect_m.getActiveNetworkInfo();
            connected = n_info != null && n_info.isAvailable() && n_info.isConnected();
            return connected;
        }catch (Exception e) {
            Log.e("Connectivity Exception", e.getMessage());
        }
        return connected;
    }
}


