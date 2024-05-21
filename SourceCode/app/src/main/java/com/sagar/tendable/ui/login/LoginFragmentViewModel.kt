package com.sagar.tendable.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagar.tendable.repository.LoginServiceRepo
import com.sagar.tendable.util.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class LoginFragmentViewModel : ViewModel(), KoinComponent {

    private val loginServiceRepo: LoginServiceRepo by inject()

    val stateFlowLoginResponse = MutableStateFlow<ApiResponse<String>>(ApiResponse.Empty)

    fun saveLoginData(loginReq : String){
        viewModelScope.launch{
            loginServiceRepo.saveLoginData(loginReq).collect {
                stateFlowLoginResponse.value = it
            }
        }
    }
}