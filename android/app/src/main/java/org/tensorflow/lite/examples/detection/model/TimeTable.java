package org.tensorflow.lite.examples.detection.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "timetable")
public class TimeTable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String name;
    private String time;
    private String place;
    private String professor;
    private String loginId;

    @Ignore
    public TimeTable(String name, String time, String place, String professor, String loginId){
        this.name = name;
        this.time = time;
        this.place = place;
        this.professor = professor;
        this.loginId = loginId;
    }

    public TimeTable(int id,String name, String time, String place, String professor, String loginId){
        this.id = id;
        this.name = name;
        this.time = time;
        this.place = place;
        this.professor = professor;
        this.loginId = loginId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }

    public String getLoginId() {
        return loginId;
    }

    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }

    @Override
    public String toString(){
        return "Table{" +
                "id= " + id + ", name= " + name + ", Time= " +time +", place= " + place + ", professor= " + professor + ", loginId = " + loginId + "}";
    }

}
