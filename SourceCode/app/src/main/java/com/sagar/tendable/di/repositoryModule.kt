package com.sagar.tendable.di

import android.annotation.SuppressLint
import com.sagar.tendable.repository.LoginServiceRepo
import com.sagar.tendable.repository.RegisterServiceRepo
import org.koin.dsl.module

@SuppressLint("SuspiciousIndentation")
val repositoryModule = module {
    single {
		LoginServiceRepo(get()) //singleton obj of MainRepo which is injected in ViewModel
	}
	single {
		RegisterServiceRepo(get())
	}
}