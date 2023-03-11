package com.example.cryptocurrencywalletapp.data.repository

import android.util.Log
import com.example.cryptocurrencywalletapp.data.local.CryptoDatabase
import com.example.cryptocurrencywalletapp.data.local.user.UserEntity
import com.example.cryptocurrencywalletapp.data.mapper.toUser
import com.example.cryptocurrencywalletapp.data.mapper.toUserEntity
import com.example.cryptocurrencywalletapp.domain.model.User
import com.example.cryptocurrencywalletapp.domain.repository.UserRepository
import com.example.cryptocurrencywalletapp.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val db: CryptoDatabase
): UserRepository {

    private val dao = db.getUserDao()
    override fun getUserByUsername(username: String): Flow<Resource<User>> {
        return flow{
           emit(Resource.Loading())
            try {

                val data = dao.getUserByUsername(username)
                if (data == null){
                    emit(Resource.Error("No User Found"))
                }
                val userEntity = data as UserEntity
                val user = userEntity.toUser()
                emit(Resource.Success(user))
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(e.toString()))

            }
        }
    }



    override fun updateUserDetails(user: User):Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            try {
                val userEntity = user.toUserEntity()
                dao.updateUser(id = userEntity.userId, name = userEntity.name, username = userEntity.username, password= userEntity.password, email = userEntity.email)
                val response = dao.getUserByUsername(user.username)
                if (response == null) {
                    emit(Resource.Error("User Update Failed"))
                } else{
                    emit(Resource.Success(response.toUser()))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.toString()))
            }
          }
    }

    override  fun registerUser(user: User): Flow<Resource<User>> {
        return flow {
            emit(Resource.Loading())
            try {
                val userEntity = user.toUserEntity()
                val data = dao.getUserByUsername(user.username)
                if (data == null) {
                    dao.insertUser(userEntity)
                    val response = dao.getUserByUsername(user.username)
                    if (response == null) {
                        emit(Resource.Error("Registration Failed"))
                    } else {
                        emit(Resource.Success(response.toUser()))
                    }
                } else {
                    emit(Resource.Error("Username Unavailable"))
                }

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error(e.toString()))
            }
        }
    }

    override fun loginUser(username: String, password: String): Flow<Resource<User>>  {
        return flow {
            emit(Resource.Loading())
            try {
            val data = dao.getUserByUsername(username)
            if (data == null){
                emit(Resource.Error("No User Found"))
            }else if (password !=data.password){
                emit(Resource.Error("Incorrect Password"))
            }else{
                val user = data.toUser()
                emit(Resource.Success(user))
            }
            } catch (e: IOException){
                e.printStackTrace()
                emit(Resource.Error(e.toString()))

            }
        }
    }
}