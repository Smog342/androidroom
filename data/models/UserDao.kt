package com.example.kotlinapp.data.models

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {


    @Query("SELECT * FROM users WHERE phoneNumber = :first LIMIT 1")
    suspend fun findByNumber(first: String): UserModel

    @Query("SELECT * FROM users WHERE username = :first LIMIT 1")
    suspend fun findByUsername(first: String): UserModel

    @Query("UPDATE users SET username = :newlogin, password = :password WHERE username = :login")
    suspend fun UpdateUser(login: String, newlogin: String, password: String)

    @Insert
    suspend fun insertAll(vararg users: UserModel)

    @Delete
    suspend fun delete(user: UserModel)
}

/*
@Query("SELECT * FROM users")
suspend fun getAll(): List<UserModel>

@Query("SELECT * FROM users WHERE uid IN (:userIds)")
suspend fun loadAllByIds(userIds: IntArray): List<UserModel>*/
