package com.furkanmulayim.cardholder.di

import android.content.Context
import androidx.room.Room
import com.furkanmulayim.cardholder.data.repo.CardsDaoRepository
import com.furkanmulayim.cardholder.room.CardsDao
import com.furkanmulayim.cardholder.room.Database
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule  {

    @Provides
    @Singleton
    fun provideCardsDaoRepository(cdao:CardsDao)  : CardsDaoRepository{
        return CardsDaoRepository(cdao )
    }

    @Provides
    @Singleton
    fun provideCardsDao (@ApplicationContext context:Context)  : CardsDao {
        val vt = Room.databaseBuilder(context, Database::class.java, name = "carda.db")
            .createFromAsset("carda.db").build()
        return vt.getCardDao()
    }
}