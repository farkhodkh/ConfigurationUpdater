package com.farkhodkhaknazarov.configurationupdater.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.farkhodkhaknazarov.configurationupdater.api.TemInvokerExecutor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(val temInvokerExecutor: TemInvokerExecutor) :
    ViewModel() {

    fun onDestroy() {
        viewModelScope.launch {
            temInvokerExecutor.closeService()
        }
    }
}