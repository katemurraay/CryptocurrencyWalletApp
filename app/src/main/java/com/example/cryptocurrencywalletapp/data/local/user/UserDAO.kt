package com.example.cryptocurrencywalletapp.data.local.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
@Dao
interface UserDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userEntity: UserEntity)

    @Query("SELECT * FROM user_table WHERE username = :username")
    suspend fun getUserByUsername(username: String): UserEntity?


    @Query("DELETE FROM user_table")
    suspend fun clearUserAccounts()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUsers(users: List<UserEntity>)

    @Query("UPDATE user_table SET name = :name, username = :username, email = :email, password = :password WHERE userId = :id")
    suspend fun updateUser(id: Long?, name: String?, username: String, password: String, email:String)
}


