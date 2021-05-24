package org.tensorflow.lite.examples.detection;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Process;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.tensorflow.lite.examples.detection.database.AppDatabase;
import org.tensorflow.lite.examples.detection.model.TimeTable;


import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private EditText login_id, login_pw;
    private Button login;
    private Handler myHandler;
    private TextView login_check;
    static boolean isLoggedIn;
    private AppDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if(isConnected() == false) isNotConnected_showAlert();


        login = findViewById(R.id.login_btn);
        login_check = (TextView)findViewById(R.id.login_check);
        isLoggedIn = false;


        db = AppDatabase.getInstance(getApplicationContext());


        myHandler = new Handler(){
            @Override
            public void handleMessage(Message msg){
                if(msg.what == 1000) {
                    login_check.setText("아이디 및 비밀번호를 확인해주세요.");
                }else{
                    login_check.setText(" ");
                }
            }
        };

        login.setOnClickListener((view -> {

            login_id = (EditText)findViewById(R.id.login_id);
            login_pw = (EditText)findViewById(R.id.login_password);
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
                                .ignoreHttpErrors(true)
                                .execute();

                        int statusCode = response2.statusCode();
                        if(statusCode < 400){
                            isLoggedIn = true;

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



                            Elements subjects = response3.parse().select("subject");

                            storeTable(login_id.getText().toString(),subjects);
                            myHandler.sendEmptyMessage(0);


                            startActivity(new Intent(getApplicationContext(), DetectorActivity.class));
                            finish();
                        }else{
                            myHandler.sendEmptyMessage(1000);
                            System.out.println(statusCode);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

//                        System.out.println(response2.parse());



                    }
                    catch (IOException e){
                        e.printStackTrace();
                    }
                }
            }.start();

        }));

    }

    public void storeTable(String userId, Elements tables){
        String name, place, time, professor;
        String[] timevalue;

        for(int i =0; i<tables.size(); i++){
            time = "";
            name = tables.get(i).select("name").val();
            place =  tables.get(i).selectFirst("data").attr("place").split("-")[0];
            professor = tables.get(i).select("professor").val();
            timevalue =  tables.get(i).select("time").val().split("<br>") ;
            if(timevalue.length !=0) {
                for (String timeele : timevalue) {
                    time = time + timeele.split(" ")[0] + timeele.split(" ")[1];
                }
            }

            System.out.println(name);
            System.out.println(place);
            System.out.println(professor);
            System.out.println(time);
            if(db.timeTableDAO().findByName(name)==null){
                final TimeTable timeTable = new TimeTable(
                        name,
                        time,
                        place,
                        professor,
                        userId
                );
                db.timeTableDAO().insertTimetable(timeTable);
            }
        }
        return ;
    }



    // UI는 메인 스레드에서만 건드릴 수 있으므로 핸들러를 만들어줌.

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
    void showToast(final CharSequence text) {
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(LoginActivity.this, text, Toast.LENGTH_SHORT).show();
            }
        });
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


