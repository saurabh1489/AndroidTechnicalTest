package com.bridge.androidtechnicaltest.util

import com.bridge.androidtechnicaltest.db.Pupil

class DummyDataUtil {
    companion object {
        fun createPupil(): Pupil {
            return Pupil(12345, "Test", "India", "http://lorempixel.com/640/480/business?name=Test", 0.0, 0.0)
        }

        fun createPupilList(): MutableList<Pupil> {
            return mutableListOf(
                    Pupil(12345, "Test", "India", "http://lorempixel.com/640/480/business?name=Test", 0.0, 0.0),
                    Pupil(12346, "Test1", "India", "http://lorempixel.com/640/480/business?name=Test1", 0.0, 0.0),
                    Pupil(12347, "Test2", "India", "http://lorempixel.com/640/480/business?name=Test2", 0.0, 0.0),
                    Pupil(12348, "Test3", "India", "http://lorempixel.com/640/480/business?name=Test3", 0.0, 0.0),
                    Pupil(12349, "Test4", "India", "http://lorempixel.com/640/480/business?name=Test4", 0.0, 0.0))
        }
    }
}