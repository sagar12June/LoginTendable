package com.sagar.tendable.util

class Constants {

    companion object{
        const val timeOutInSec: Long = 30

        fun isValidEmail(email: String): Boolean {
            return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }


}