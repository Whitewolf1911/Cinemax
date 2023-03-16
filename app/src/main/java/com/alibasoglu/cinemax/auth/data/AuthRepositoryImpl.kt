package com.alibasoglu.cinemax.auth.data

import com.alibasoglu.cinemax.auth.domain.AuthRepository
import com.alibasoglu.cinemax.utils.Resource
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {

    override suspend fun signUpWithEmailPassword() {
        TODO("Not yet implemented")
    }

    override suspend fun loginWithEmailPassword(email: String, password: String): Flow<Resource<Unit>> {
        var errorMessage: String? = null
        var isSuccess: Boolean? = null
        return flow {
            emit(Resource.Loading(true))
            try {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener() {
                    isSuccess = it.isSuccessful
                    errorMessage = it.exception?.localizedMessage
                }.await()
            } catch (e: Exception) {
                emit(Resource.Error(message = e.localizedMessage as String))
                return@flow
            }
            errorMessage?.let {
                emit(Resource.Error(message = errorMessage as String))
            }
            isSuccess?.let {
                if (isSuccess as Boolean) {
                    emit(Resource.Success(Unit))
                }
            }
        }
    }

    override suspend fun loginWithGoogle() {
        TODO("Not yet implemented")
    }

    override suspend fun loginWithFacebook() {
        TODO("Not yet implemented")
    }
}
