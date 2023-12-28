package com.task.praytimes.times.data.di

import android.content.Context
import androidx.room.Room
import com.task.praytimes.times.Constants
import com.task.praytimes.times.data.local.db.PrayerTimesDao
import com.task.praytimes.times.data.local.db.PrayerTimesDatabase
import com.task.praytimes.times.data.remote.PrayerTimesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProvidesModule {
    @Provides
    fun provideApiService(
        retrofit: Retrofit
    ): PrayerTimesService {
        return retrofit.create(PrayerTimesService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun provideRoomDao(
        database: PrayerTimesDatabase
    ): PrayerTimesDao {
        return database.prayerDao()
    }

    @Singleton
    @Provides
    fun provideRoomDatabase(
        @ApplicationContext context: Context
    ): PrayerTimesDatabase {
        return Room.databaseBuilder(
            context,
            PrayerTimesDatabase::class.java,
            Constants.DATA_BASE
        ).build()
    }
}