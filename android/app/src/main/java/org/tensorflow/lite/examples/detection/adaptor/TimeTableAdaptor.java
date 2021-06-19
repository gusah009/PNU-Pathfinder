package org.tensorflow.lite.examples.detection.adaptor;

import java.sql.Time;
import java.util.List;

import android.content.Context;
import android.content.Intent;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.tensorflow.lite.examples.detection.R;
import org.tensorflow.lite.examples.detection.database.AppDatabase;
import org.tensorflow.lite.examples.detection.model.TimeTable;

public class TimeTableAdaptor extends RecyclerView.Adapter<TimeTableAdaptor.MyViewHolder> {
        private Context context;
        private List<TimeTable> timeTableList;

        public TimeTableAdaptor(Context context) {
            this.context = context;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(context).inflate(R.layout.detecteditem, viewGroup, false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull TimeTableAdaptor.MyViewHolder myViewHolder, int i) {
            myViewHolder.name.setText(timeTableList.get(i).getName());
            myViewHolder.professor.setText(timeTableList.get(i).getProfessor());
            myViewHolder.time.setText(timeTableList.get(i).getTime());
        }

        @Override
        public int getItemCount() {
            if (timeTableList == null) {
                return 0;
            }
            return timeTableList.size();

        }

        public void setTasks(List<TimeTable> list) {
            timeTableList = list;
            notifyDataSetChanged();
        }

        public List<TimeTable> getTasks() {
            return timeTableList;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView name, professor, time;
            AppDatabase mDb;

            MyViewHolder(@NonNull final View itemView) {
                super(itemView);
                mDb = AppDatabase.getInstance(context);
                name = itemView.findViewById(R.id.name);
                professor = itemView.findViewById(R.id.professor);
                time = itemView.findViewById(R.id.time);
            }
        }
    }

