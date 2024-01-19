package data;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface data_dao {


    @Query("SELECT * FROM user_table")
    List<User> getalluser();
    @Query("SELECT id FROM user_table ORDER BY id DESC LIMIT 1")
    int getLastUser();
    @Insert
    void insertcelamation(Reclamation reclamation);

    @Insert
    void insertAll(List<User> users);
    @Query("SELECT * FROM user_reclamation")
    List<Reclamation> getAll();
    @Query("SELECT * FROM user_reclamation WHERE id=:idc")
    Reclamation getreclamation(int idc);
    @Insert
    void insert(User user);
    @Query("SELECT * FROM user_table WHERE id=:idc")
    User getuser(int idc);

    @Query("UPDATE user_reclamation SET Status = :newStatus WHERE id = :reclamationId")
    void updateReclamationStatus(int reclamationId, String newStatus);
    @Query("DELETE FROM user_reclamation WHERE id = :reclamationId")
    void deleteReclamationById(int reclamationId);
}
