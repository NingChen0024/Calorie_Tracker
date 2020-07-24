package com.example.ning.assignmnet3;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import java.util.List;
import static android.arch.persistence.room.OnConflictStrategy.REPLACE;


@Dao
public interface ReportDao {

    @Query("SELECT * FROM report")
    List<Report> getAll();

    @Query("SELECT * FROM report WHERE reportId = :reportId LIMIT 1")
    Report findByID(int reportId);

    @Insert
    void insertAll(Report... report);

    @Insert
    long insert(Report report);

    @Delete
    void delete(Report report);

    @Update(onConflict = REPLACE)
    public void updateReport(Report... report);

    @Query("DELETE FROM report")
    void deleteAll();
}