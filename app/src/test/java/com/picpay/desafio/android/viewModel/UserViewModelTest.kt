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
        viewModel.errorLiveData.observeForever(errorLiveData)
        viewModel.successListUserLiveData.observeForever(successListLiveData)
        viewModel.cachedLoadListUserLiveData.observeForever(cachedLoadListUserLiveData)
        viewModel.loadLiveData.observeForever(loadLiveData)
    }

    @Test
    fun `getUser with success in cache`() {
        val expectedListLocal =
            listOf(User("imgLocal", "nameLocal", 2, "userNameLocal"))
        whenever(useCase.loadUserLocal()).thenReturn(Single.just(expectedListLocal))

        viewModel.getUsersLocal()

        verify(loadLiveData).onChanged(true)
        verify(errorLiveData).onChanged(false)
        verify(successListLiveData).onChanged(expectedListLocal)
        verify(cachedLoadListUserLiveData).onChanged(true)
    }

    @Test
    fun `getUser with error in cache`() {
        val expectedList = listOf(User("img", "name", 1, "userName"))
        val expectedListLocal =
            listOf(User("imgLocal", "nameLocal", 2, "userNameLocal"))
        whenever(useCase.getUsers()).thenReturn(Single.just(expectedList))
        whenever(useCase.loadUserLocal()).thenReturn(Single.just(expectedListLocal))
        whenever(useCase.saveUserLocal(any())).thenReturn(Completable.complete())
        viewModel = UserViewModel(useCase)

        viewModel.getUsersLocal()
        verify(loadLiveData, times(2)).onChanged(true)
        verify(errorLiveData, times(3)).onChanged(false)
        verify(successListLiveData, times(2)).onChanged(expectedListLocal)
        verify(cachedLoadListUserLiveData, times(2)).onChanged(true)
    }

    @Test
    fun `getUser with success in network`() {

    }

    @Test
    fun `getUser with error in network`() {

    }
}