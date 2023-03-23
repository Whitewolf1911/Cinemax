package com.alibasoglu.cinemax.auth.domain.usecase

import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.auth.domain.AuthRepository
import com.alibasoglu.cinemax.utils.Resource
import com.alibasoglu.cinemax.utils.Strings
import com.alibasoglu.cinemax.utils.isValidEmail
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class LoginWithEmailUseCase(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Flow<Resource<Unit>> {
        return when {
            email.isValidEmail() -> authRepository.loginWithEmailPassword(email, password)
            email.isValidEmail().not() -> flowOf(Resource.Error(message = Strings.get(R.string.invalid_email_format)))
            else -> flowOf(Resource.Error(message = "Unknown error"))
        }
    }

}
