package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.tensorflow.lite.examples.detection.adaptor.TimeTableAdaptor;
import org.tensorflow.lite.examples.detection.constants.Constants;
import org.tensorflow.lite.examples.detection.database.AppDatabase;
import org.tensorflow.lite.examples.detection.database.AppExecutors;
import org.tensorflow.lite.examples.detection.model.TimeTable;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetectedInfoActivity extends AppCompatActivity {

    private TextView user,building1,building2, non;
    private TimeTableAdaptor mAdaptor;
    private int count  = -1;
    Intent intent;
    private AppDatabase db;



    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detectedinfo);



        RecyclerView mRecyclerView = (RecyclerView) findViewById(R.id.rview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        mAdaptor = new TimeTableAdaptor(this);
        mRecyclerView.setAdapter(mAdaptor);
        user = findViewById(R.id.user);
        non = findViewById(R.id.non);
        db = AppDatabase.getInstance(getApplicationContext());
        intent = getIntent();
        building1 = findViewById(R.id.building_1);
        building2= findViewById(R.id.building_2);
        String detected;

        if (intent != null && intent.hasExtra(Constants.Show_Detected_Info)) {
            detected = intent.getStringExtra(Constants.Show_Detected_Info);
            building1.setText(detected);
            building2.setText(detected);
            String build1 = "6공학관";
            String build2 = "융합기계관";
            String build3= "운죽정";
            String find;
            if(detected.trim().equals(build1)) {
                find = "201";
                System.out.println("1");
            }else if(detected.trim().equals(build2)) {
                find = "105";
                System.out.println("2");
            }else if(detected.trim().equals(build3)) {
                find = "202";
                System.out.println("3");
            }else{
                find = "201";
            }
            System.out.println(detected + " "+find);
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        List<TimeTable> timeTable = db.timeTableDAO().findByPlace(find);
                        List<TimeTable> test = db.timeTableDAO().getAll();
                        System.out.println(timeTable.toString());
                        System.out.println(test);
                        if (timeTable.size() > 0) {
                            mAdaptor.setTasks(timeTable);
                            user.setText(timeTable.get(0).getLoginId());
                        } else {
                            non.setText("듣는 수업이 없습니다.");
                        }

                    }
                });


            }

        }





}


