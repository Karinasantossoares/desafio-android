package com.picpay.desafio.android.viewModel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.picpay.desafio.android.model.data.User
import com.picpay.desafio.android.useCase.UserUseCase
import io.reactivex.disposables.CompositeDisposable


class UserViewModel(
    private val useCase: UserUseCase,
    isTest: Boolean = false
) : ViewModel() {
    var disposable = CompositeDisposable()
    var successListUserLiveData = MutableLiveData<List<User>>()
    var cachedLoadListUserLiveData = MutableLiveData<Boolean>()
    var errorLiveData = MutableLiveData<Boolean>()
    var loadLiveData = MutableLiveData<Boolean>()

    init {
        if (!isTest) {
            start()
        }
    }


    fun start() {
        getUsersLocal()
        getNetworkUsers()
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getNetworkUsers() {
        loadLiveData.postValue(true)
        disposable.add(useCase.getUsers().subscribe { res, error ->
            if (res != null) {
                errorLiveData.value = false
                cachedLoadListUserLiveData.value = false
                successListUserLiveData.value = res
                saveUserLocal(res)
            }
            loadLiveData.value = false
        })
    }

    private fun saveUserLocal(user: List<User>) {
        disposable.add(useCase.saveUserLocal(user).subscribe())
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getUsersLocal() {
        loadLiveData.value = true
        disposable.add(useCase.getLocalUsers().subscribe { res, _ ->
            if (!res.isNullOrEmpty()) {
                errorLiveData.value = false
                successListUserLiveData.value = res
                cachedLoadListUserLiveData.value = true
            } else {
                errorLiveData.value = true
            }
        })
    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}