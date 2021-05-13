package com.picpay.desafio.android.viewModel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
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
    private val _successListUserLiveData = MutableLiveData<List<User>>()
    val resultSuccess: LiveData<List<User>>
        get() = _successListUserLiveData
    private var _cachedLoadListUserLiveData = MutableLiveData<Boolean>()
    val resultCach: LiveData<Boolean>
        get() = _cachedLoadListUserLiveData
    private val _errorLiveData = MutableLiveData<Boolean>()
    val errorResult: LiveData<Boolean>
        get() = _errorLiveData
    private val _loadLiveData = MutableLiveData<Boolean>()
    val loadResult: LiveData<Boolean>
        get() = _loadLiveData


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
        _loadLiveData.value = true
        disposable.add(useCase.getUsers().subscribe { res, error ->
            if (res != null) {
                _errorLiveData.value = false
                _cachedLoadListUserLiveData.value = false
                _successListUserLiveData.value = res
                saveUserLocal(res)
            }
            _loadLiveData.value = false
        })
    }

    private fun saveUserLocal(user: List<User>) {
        disposable.add(useCase.saveUserLocal(user).subscribe())
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    fun getUsersLocal() {
        _loadLiveData.value = true
        disposable.add(useCase.getLocalUsers().subscribe { res, _ ->
            if (!res.isNullOrEmpty()) {
                _errorLiveData.value = false
                _successListUserLiveData.value = res
                _cachedLoadListUserLiveData.value = true
            } else {
                _errorLiveData.value = true
            }
            _loadLiveData.value = false
        })

    }

    override fun onCleared() {
        disposable.clear()
        super.onCleared()
    }
}