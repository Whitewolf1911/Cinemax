package com.alibasoglu.cinemax.auth.domain

import com.alibasoglu.cinemax.utils.Resource
import kotlinx.coroutines.flow.Flow

interface AuthRepository {

    suspend fun signUpWithEmailPassword()

    suspend fun loginWithEmailPassword(email: String, password: String): Flow<Resource<Unit>>

    suspend fun loginWithGoogle()

    suspend fun loginWithFacebook()
}
