package com.furkanmulayim.cardholder.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.furkanmulayim.cardholder.data.entity.Cards

@Dao
interface CardsDao {

    //coroutine ile asenkron çalışacak suspend
    @Query("SELECT * FROM kartlar WHERE kart_background = :car")
    suspend fun allCards(car:String): List<Cards>

    @Insert
    suspend fun addCard(cards: Cards)

    @Update
    suspend fun updateCard(cards: Cards)

    @Delete
    suspend fun deleteCard(cards: Cards)

    @Query("SELECT * FROM kartlar WHERE kart_background = :tur")
    suspend fun tcGet(tur:String):List<Cards>
}