package data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = true)
    public int id;

    public String username;


    public void setbody(String newbody) {
        this.username=newbody;
    }
}
