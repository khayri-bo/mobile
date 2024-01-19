package data;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "user_reclamation")
public class Reclamation {

    @PrimaryKey(autoGenerate = true)
    public int id;
    public int id_user;
    public String body;
    public String date;
    public String Status;
    public void setData(String newbody,String newDate,int iduser,String newStatus) {

        this.date=newDate;
        this.id_user=iduser;
        this.Status=newStatus;

        this.body=newbody;
    }


}
