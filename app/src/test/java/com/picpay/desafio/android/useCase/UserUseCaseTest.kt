package com.picpay.desafio.android.useCase

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.RxImmediateSchedulerRule
import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.repository.local.UserLocalReposirory
import com.picpay.desafio.android.repository.network.UserNetworkRepository
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UserUseCaseTest {
    @Mock
    private lateinit var localRepository: UserLocalReposirory

    @Mock
    private lateinit var networkRepository: UserNetworkRepository
    private lateinit var useCase: UserUseCase

    @Rule
    @JvmField
    val testRule = RxImmediateSchedulerRule()

    @Before
    fun init() {
        useCase = UserUseCase.UserUseCaseImpl(networkRepository, localRepository)
    }

    @Test
    fun `when getUsers network with success then return listUser`() {
        val expected = listOf(User("img", "name", 1, "userName"))
        whenever(networkRepository.getUsers()).thenReturn(Single.just(expected))

        useCase.getUsers()
            .test()
            .assertResult(expected)
    }

    @Test
    fun `when getUsers network with error then return error`() {
        val expectedError = Exception("Erro useCase")
        whenever(networkRepository.getUsers()).thenReturn(Single.error(expectedError))

        useCase.getUsers()
            .test()
            .assertError(expectedError)
    }

    @Test
    fun `when getUsers local with success then return listUser`() {
        val expected = listOf(User("img", "name", 1, "userName"))
        whenever(localRepository.loadUser()).thenReturn(Single.just(expected))

        useCase.loadUserLocal()
            .test()
            .assertResult(expected)
    }

    @Test
    fun `when getUsers local with error then return error`() {
        val expectedError = Exception("Erro useCase")
        whenever(localRepository.loadUser()).thenReturn(Single.error(expectedError))

        useCase.loadUserLocal()
            .test()
            .assertError(expectedError)
    }

    @Test
    fun `when saveUser local with success then return success`() {
        whenever(localRepository.saveUser(listOf())).thenReturn(Completable.complete())
        useCase.saveUserLocal(listOf())
            .test()
            .assertComplete()
    }
}