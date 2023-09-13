package net.coblos.rrnet.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.coblos.rrnet.domain.DataState
import net.coblos.rrnet.net.Services
import net.coblos.rrnet.repository.Repository
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    private val _dataState = MutableLiveData<DataState<String>>()
    val dataState: LiveData<DataState<String>>
        get() = _dataState

    fun login(url: String, username: String, pass: String) {
        viewModelScope.launch {
            repository.postLogin(url + "login", username, pass)
                .onEach { state -> _dataState.value = state  }
                .launchIn(viewModelScope)
        }
    }
}