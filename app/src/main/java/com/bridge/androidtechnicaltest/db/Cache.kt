package com.bridge.androidtechnicaltest.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Cache")
class Cache(
        @PrimaryKey
        @ColumnInfo(name = "pupil_id")
        val pupilId: Long,

        @ColumnInfo(name = "name")
        val name: String,

        @ColumnInfo(name = "country")
        val country: String,

        @ColumnInfo(name = "image")
        val image: String,

        @ColumnInfo(name = "latitude")
        val latitude: Double,

        @ColumnInfo(name = "longitude")
        val longitude: Double
) {
    public fun toPupil() = run {
        Pupil(pupilId, name, country, image, latitude, longitude)
    }
}