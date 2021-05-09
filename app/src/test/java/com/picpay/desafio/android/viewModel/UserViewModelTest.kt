package com.picpay.desafio.android.viewModel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.nhaarman.mockitokotlin2.*
import com.picpay.desafio.android.BaseViewModelTest
import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.useCase.UserUseCase
import io.reactivex.Completable
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserViewModelTest : BaseViewModelTest() {

    private val successListLiveData: Observer<List<User>> = mock()
    private val errorLiveData: Observer<Boolean> = mock()
    private val cachedLoadListUserLiveData: Observer<Boolean> = mock()
    private val loadLiveData: Observer<Boolean> = mock()
    private val useCase: UserUseCase = mock()
    private lateinit var viewModel: UserViewModel

    @Rule
    @JvmField
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun init() {
        viewModel = UserViewModel(useCase, true)
        viewModel.errorResult.observeForever(errorLiveData)
        viewModel.resultSuccess.observeForever(successListLiveData)
        viewModel.resultCach.observeForever(cachedLoadListUserLiveData)
        viewModel.loadResult.observeForever(loadLiveData)
    }

    @Test
    fun `getUser with success in cache`() {
        val expectedListLocal =
            listOf(User("imgLocal", "nameLocal", 2, "userNameLocal"))
        whenever(useCase.getLocalUsers()).thenReturn(Single.just(expectedListLocal))

        viewModel.getUsersLocal()

        verify(loadLiveData).onChanged(true)
        verify(errorLiveData).onChanged(false)
        verify(successListLiveData).onChanged(expectedListLocal)
        verify(cachedLoadListUserLiveData).onChanged(true)
    }

    @Test
    fun `getUser with error in cache`() {
        val exception = Exception("error")
        whenever(useCase.getLocalUsers()).thenReturn(Single.error(exception))
        viewModel.getUsersLocal()
        verify(loadLiveData).onChanged(true)
        verify(errorLiveData).onChanged(true)
    }

    @Test
    fun `getUser with success in network`() {
        val expectedListLocal =
            listOf(User("imgLocal", "nameLocal", 2, "userNameLocal"))
        whenever(useCase.getUsers()).thenReturn(Single.just(expectedListLocal))
        whenever(useCase.saveUserLocal(any())).thenReturn(Completable.complete())

        viewModel.getNetworkUsers()

        verify(loadLiveData).onChanged(true)
        verify(loadLiveData).onChanged(false)
        verify(errorLiveData).onChanged(false)
        verify(cachedLoadListUserLiveData).onChanged(false)
        verify(successListLiveData).onChanged(expectedListLocal)
    }

    @Test
    fun `getUser with error in network`() {
        val exception = Exception("error")
        whenever(useCase.getUsers()).thenReturn(Single.error(exception))
        viewModel.getNetworkUsers()
        verify(loadLiveData).onChanged(true)
        verify(loadLiveData).onChanged(false)
    }
}