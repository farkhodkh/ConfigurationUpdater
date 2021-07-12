package com.farkhodkhaknazarov.configurationupdater.ui.view

interface CommonView {
    suspend fun initObservers()
    fun initView()
}