package com.vadym.gvd.bestfriendskotlin.easter_egg

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [CoinEntity::class], version = 1, exportSchema = false)
abstract class CoinDatabase : RoomDatabase() {

    abstract fun coinDao(): CoinDao

    companion object {
        @Volatile private var INSTANCE: CoinDatabase? = null

        fun getInstance(context: Context): CoinDatabase =
            INSTANCE ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    CoinDatabase::class.java,
                    "coins_db"
                )
                    .fallbackToDestructiveMigration()  // ← додай це
                    .build().also { INSTANCE = it }
            }
    }
}