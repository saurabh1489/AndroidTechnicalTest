package com.bridge.androidtechnicaltest.db;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.paging.DataSource;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface PupilDao {

    @Query("SELECT * FROM Pupils ORDER BY name ASC")
    DataSource.Factory<Integer, Pupil> getPupils();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Completable insertPupils(List<Pupil> pupilList);

    @Query("SELECT * FROM Pupils WHERE pupil_id = :pupilId")
    LiveData<Pupil> getPupil(Long pupilId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertPupil(@NotNull Cache pupil);

    @Query("SELECT * FROM Cache")
    Single<List<Cache>> getCachePupils();

    @Query("DELETE FROM Cache")
    void clearCache();
}
