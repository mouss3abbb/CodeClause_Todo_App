package com.example.codeclause_todo_app

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {


    @Singleton
    @Provides
    fun provideDao(app:Application): TodoDao = Room.databaseBuilder(app.applicationContext,TodoDatabase::class.java,"todo_db").build().getDao()

}