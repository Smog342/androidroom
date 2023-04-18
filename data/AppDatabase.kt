package com.example.kotlinapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kotlinapp.data.models.UserDao
import com.example.kotlinapp.data.models.UserModel


@Database(entities = [UserModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
