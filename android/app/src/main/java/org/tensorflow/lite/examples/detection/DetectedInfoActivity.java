package org.tensorflow.lite.examples.detection;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.tensorflow.lite.examples.detection.constants.Constants;
import org.tensorflow.lite.examples.detection.database.AppDatabase;
import org.tensorflow.lite.examples.detection.database.AppExecutors;
import org.tensorflow.lite.examples.detection.model.TimeTable;

import java.sql.Time;

public class DetectedInfoActivity extends AppCompatActivity {

    private TextView name, professor, time, user;
    Intent intent;
    private AppDatabase db;

    String detected;

    @Override
    protected void onCreate(final Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detectedinfo);
        db = AppDatabase.getInstance(getApplicationContext());
        intent = getIntent();

        name = findViewById(R.id.name);
        user = findViewById(R.id.user);
        professor = findViewById(R.id.professor);
        time = findViewById(R.id.time);

        if (intent != null && intent.hasExtra(Constants.Show_Detected_Info)) {
            detected = intent.getStringExtra(Constants.Show_Detected_Info);
            System.out.println(detected+"!!!!!!!!!");

            if(detected == "keyboard"){
                detected = "201";
            }

            AppExecutors.getInstance().diskIO().execute(new Runnable() {
                @Override
                public void run() {
                    TimeTable timeTable =db.timeTableDAO().findByPlace("201");
//                    for(info : timeTable)
                    System.out.println(timeTable.toString());
                    name.setText(timeTable.getName());
                    professor.setText(timeTable.getProfessor());
                    time.setText(timeTable.getTime());
                    user.setText(timeTable.getLoginId()+"님이 "+timeTable.getPlace()+"에서 듣는 수업");
                }
            });

        }

    }

    private void populateUI(TimeTable timeTable) {

        if (timeTable == null) {
            return;
        }


        System.out.println(timeTable.toString());
    }

}
