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
                        System.out.println( login_id.toString());
                        System.out.println( login_pw.toString());
                        Map<String, String> data = new HashMap<>();
                        data.put("id", login_id.toString());
                        data.put("pw", login_pw.toString());

                        Connection.Response res = Jsoup.connect("https://sso.pusan.ac.kr/LoginServlet?method=idpwProcessEx&ssid=49")
                                .data(data)
                                .method(Connection.Method.POST)
                                .execute();

                        Document doc = res.parse();
                        Map<String, String> cookies = res.cookies();

                        Document doc2 = Jsoup.connect("https://e-onestop.pusan.ac.kr/index?home=home")
                                .cookies(cookies)
                                .get();
                        System.out.println(doc2.html());
                        System.out.println(cookies);

//                        Document doc2 = Jsoup.connect("https://e-onestop.pusan.ac.kr/middleware/study/privateTimeTable/getPrivateRegistrationYear")
//                                .cookie(cookies)
//                                .get();
//                        System.out.println(cookies);
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


