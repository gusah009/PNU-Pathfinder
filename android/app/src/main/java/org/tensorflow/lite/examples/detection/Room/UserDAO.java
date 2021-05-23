package org.tensorflow.lite.examples.detection.Room;


import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import java.util.List;

@Dao
public interface UserDAO {

    @Insert
    void insert(User user);

    @Update
    void update(User user);

    @Delete
    void delete(User user);


    @Query("DELETE FROM user_table")
    void  deleteAll();


    @Query("SELECT * FROM user_table WHERE user_table.userId LIKE :first")
    User findByUserId(String first);







}
