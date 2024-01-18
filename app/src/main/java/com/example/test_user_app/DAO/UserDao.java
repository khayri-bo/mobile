package com.example.test_user_app.DAO;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.test_user_app.entities.User;


import java.util.List;

@Dao
public interface UserDao {



    @Insert
    void addUser(User u);
    @Query("SELECT username FROM users")
    List<String> getAllUsernames();
    @Query("SELECT * FROM users WHERE  users.username= :username")
    User getUserByUsername(String username);
    @Query("SELECT * FROM users WHERE Email = :Email AND password = :password")
    User Login(String Email, String password);
    @Query("SELECT * FROM users WHERE  users.Email = :Email")
    User getUserByEmail(String Email);
    @Query("SELECT * FROM users WHERE  users.id = :ID")
    User getUserById(Long ID);

    @Query("select * from users")
    List<User> getAll();


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(User user);

    @Update
    void updateUser(User user);
    @Query("UPDATE users SET username = :newUsername , Email=:Email , Telephone = :Telephonee   WHERE id = :userId")
    void updateUsername(long userId, String newUsername,String Email,String Telephonee);

    @Query("UPDATE users SET password = :Password  WHERE id = :userId")
    void UpdatePassword(long userId, String Password);
    @Delete
    void deleteUser(User user);
    @Query("DELETE FROM users WHERE id = :userId")
    void deleteUserById(long userId);


    @Query("SELECT * FROM users WHERE id = :userId")
    User getUserById(long userId);

}