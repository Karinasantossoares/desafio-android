package com.picpay.desafio.android.di

import com.picpay.desafio.android.repository.local.UserLocalReposirory
import com.picpay.desafio.android.repository.network.UserNetworkRepository
import com.picpay.desafio.android.retrofit.initRetrofit
import com.picpay.desafio.android.room.AppDataBase
import com.picpay.desafio.android.service.PicPayService
import com.picpay.desafio.android.useCase.UserUseCase
import com.picpay.desafio.android.viewModel.UserViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.viewmodel.dsl.viewModel


import org.koin.dsl.module
import retrofit2.Retrofit
import kotlin.math.sin

val picPayModules = module {
    single { initRetrofit() }
    single<PicPayService> { get<Retrofit>().create(PicPayService::class.java) }
    single<UserNetworkRepository> { UserNetworkRepository.UserNetworkRepositoryImpl(get()) }
    single<UserUseCase> { UserUseCase.UserUseCaseImpl(get(), get()) }
    viewModel { UserViewModel(get()) }
    single { AppDataBase.instance(androidContext()) }
    single { get<AppDataBase>().userDao() }
    single<UserLocalReposirory> { UserLocalReposirory.UserRepositoryImpl(get()) }
}