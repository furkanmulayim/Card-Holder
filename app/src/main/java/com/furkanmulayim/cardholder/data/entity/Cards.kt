package com.furkanmulayim.cardholder.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull
import java.io.Serializable

@Entity(tableName = "kartlar")
data class Cards(
    @PrimaryKey @ColumnInfo("kart_id")  @NotNull( )  var cardId: String,
    @ColumnInfo("kart_isim")  @NotNull( ) var cardName: String,
    @ColumnInfo("kart_numara")  @NotNull( ) var cardNumber: String,
    @ColumnInfo("kart_isim_soyisim") @NotNull var cardPersonName: String,
    @ColumnInfo("kart_ay")  @NotNull( ) var cardMonth: String,
    @ColumnInfo("kart_year")  @NotNull( ) var cardYear: String,
    @ColumnInfo("kart_cvv")  @NotNull( ) var cardCvv: String,
    @ColumnInfo("kart_background")  @NotNull( ) var cardImage: String
) : Serializable {

}