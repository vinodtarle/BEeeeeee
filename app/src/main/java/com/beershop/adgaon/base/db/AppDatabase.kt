package com.beershop.adgaon.base.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.beershop.adgaon.blog.repository.room.BlogCacheEntity
import com.beershop.adgaon.blog.repository.room.BlogDao

@Database(entities = [BlogCacheEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun blogDao(): BlogDao

    companion object {
        val DATABASE_NAME = "app_database"
    }
}