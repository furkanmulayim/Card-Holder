package com.furkanmulayim.cardholder.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.furkanmulayim.cardholder.data.entity.Cards

@Database(entities = [Cards::class], version = 1)
abstract class Database: RoomDatabase()  {
    abstract fun getCardDao(): CardsDao

}