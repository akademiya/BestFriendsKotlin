package com.vadym.gvd.bestfriendskotlin.easter_egg

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface CoinDao {
    @Query("SELECT * FROM coins")
    fun getAllCoins(): LiveData<List<CoinEntity>>

    @Query("SELECT * FROM coins WHERE isCollected = 0")
    suspend fun getUncollectedCoins(): List<CoinEntity>

    @Query("SELECT * FROM coins WHERE isVisibleToday = 1 LIMIT 1")
    suspend fun getTodayCoin(): CoinEntity?

    @Query("SELECT COUNT(*) FROM coins WHERE isCollected = 1")
    fun getCollectedCount(): LiveData<Int>

    @Update
    suspend fun updateCoin(coin: CoinEntity)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAll(coins: List<CoinEntity>)
}