package com.picpay.desafio.android.repository

import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.whenever
import com.picpay.desafio.android.RxImmediateSchedulerRule
import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.model.dto.UserDto
import com.picpay.desafio.android.repository.network.UserNetworkRepository
import com.picpay.desafio.android.service.PicPayService
import io.reactivex.Single
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.lang.Exception

@RunWith(MockitoJUnitRunner::class)
class UserRepositoryTest {
    @Mock
    private lateinit var service: PicPayService
    private lateinit var repository: UserNetworkRepository.UserNetworkRepositoryImpl

    @Rule
    @JvmField
    val testRule = RxImmediateSchedulerRule()

    @Before
    fun init() {
        repository = UserNetworkRepository.UserNetworkRepositoryImpl(service)
    }

    @Test
    fun `when getCharacter with success then return listUser`() {
        val fakeResponse = listOf(UserDto("img", "name", 1, "userName"))
        whenever(service.getUsers()).thenReturn(Single.just(fakeResponse))
        val expected = fakeResponse.map { it.toUser() }

        repository.getUsers()
            .test()
            .assertResult(expected)
    }

    @Test
    fun `when getCharacter with error then return error`() {
        val expectedError = Exception("Erro repository")
        whenever(service.getUsers()).thenReturn(Single.error(expectedError))

        repository.getUsers()
            .test()
            .assertError(expectedError)
    }
}