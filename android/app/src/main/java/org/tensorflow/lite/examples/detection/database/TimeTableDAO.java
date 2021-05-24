package org.tensorflow.lite.examples.detection.database;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import org.tensorflow.lite.examples.detection.model.TimeTable;

import java.sql.Time;
import java.util.List;

@Dao
public interface TimeTableDAO {
    @Query("SELECT * FROM timetable ORDER BY ID")
    List<TimeTable> getAll();

    @Query("SELECT * FROM timetable WHERE timetable.loginId LIKE :loginId")
    TimeTable findByUserId(String loginId);

    @Query("SELECT * FROM timetable WHERE timetable.name LIKE :name")
    TimeTable findByName(String name);

    @Query("SELECT * FROM timetable WHERE timetable.place LIKE :place")
    TimeTable findByPlace(String place);

    @Insert
    void insertTimetable(TimeTable timeTable);

}
