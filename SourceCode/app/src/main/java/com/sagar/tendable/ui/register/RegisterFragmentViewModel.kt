package com.sagar.tendable.ui.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sagar.tendable.repository.RegisterServiceRepo
import com.sagar.tendable.util.ApiResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class RegisterFragmentViewModel : ViewModel(), KoinComponent {

    private val registerServiceRepo : RegisterServiceRepo by inject()
    val stateFlowRegisterRepo = MutableStateFlow<ApiResponse<String>>(ApiResponse.Empty)

    fun saveRegisterData(registerReq : String){
        viewModelScope.launch {
            registerServiceRepo.saveRegisterData(registerReq).collect {
                stateFlowRegisterRepo.value = it
            }
        }
    }
}