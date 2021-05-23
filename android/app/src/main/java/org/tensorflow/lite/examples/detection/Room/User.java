package org.tensorflow.lite.examples.detection.Room;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {

    @PrimaryKey(autoGenerate = true)
    private int id;
    private String userId;
    private String userPw;

    public User(String userId, String userPw){
        this.userId = userId;
        this.userPw = userPw;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserPw() {
        return userPw;
    }


    @NonNull
    @Override
    public String toString(){
        final StringBuffer sb = new StringBuffer("User{");
        sb.append("id=").append(id);
        sb.append(",userid=").append(userId).append(",");
        sb.append(",userpw=").append(userId).append("}");

        return sb.toString();
    }
}
