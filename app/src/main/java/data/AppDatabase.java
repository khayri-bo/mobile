package data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.Arrays;
import java.util.concurrent.Executors;

@Database(entities = {User.class,Reclamation.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

  public abstract data_dao userDao();

  // Singleton pattern to ensure only one instance of the database is created
  private static volatile AppDatabase INSTANCE;

  public static AppDatabase getDatabase(Context context) {
    if (INSTANCE == null) {
      synchronized (AppDatabase.class) {
        if (INSTANCE == null) {
          INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                          AppDatabase.class, "room2_DB_db")
                  .fallbackToDestructiveMigration() // For simplicity, consider using migration strategies
                  .build();
        }
      }
    }
    return INSTANCE;
  }

}
