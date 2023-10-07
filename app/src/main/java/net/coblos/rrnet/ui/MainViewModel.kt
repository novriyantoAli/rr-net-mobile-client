package net.coblos.rrnet.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import net.coblos.rrnet.domain.DataState
import net.coblos.rrnet.domain.model.ClientAuth
import net.coblos.rrnet.domain.model.res.PostMobileRes
import net.coblos.rrnet.repository.Repository
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val repository: Repository): ViewModel() {
    private val _dataState = MutableLiveData<String?>()
    val dataState: LiveData<String?>
        get() = _dataState

    private val _dataStatePostMobile = MutableLiveData<DataState<PostMobileRes>>()
    val dataStatePostMobile: LiveData<DataState<PostMobileRes>>
        get() = _dataStatePostMobile

    private val _dataStatePostVerification = MutableLiveData<DataState<ClientAuth?>>()
    val dataStatePostVerification: LiveData<DataState<ClientAuth?>>
        get() = _dataStatePostVerification

    fun login(user: String) {
        _dataState.value = user
    }

    fun postMobile(url: String, mobile: String) {
        viewModelScope.launch {
            repository.postMobile(url, mobile)
                .onEach { dtState -> _dataStatePostMobile.value = dtState }
                .launchIn(viewModelScope)
        }
    }

    fun postVerification(url: String, verification: String){
        viewModelScope.launch {
            repository.postVerification(url, verification)
                .onEach { dataState -> _dataStatePostVerification.value = dataState }
                .launchIn(viewModelScope)
        }
    }

    fun logout(){
        _dataState.value = null
    }

}