package org.tensorflow.lite.examples.detection.Room;


import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TimeTableDAO {
    @Query("SELECT * FROM timetable")
    List<TimeTable> getAll();

    @Query("DELETE * FROM timetable")
    void deleteAll();


    @Query("SELECT * FROM timetable WHERE timetable.loginId LIKE :first")
    User findByUserId(String first);


}
