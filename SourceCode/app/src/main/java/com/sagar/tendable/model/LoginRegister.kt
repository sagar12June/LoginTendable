package com.sagar.tendable.model

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class LoginRegister(

	@field:SerializedName("password")
	var password: String? = null,

	@field:SerializedName("email")
	var email: String? = null
)
