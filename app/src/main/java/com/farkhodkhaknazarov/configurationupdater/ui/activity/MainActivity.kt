package com.farkhodkhaknazarov.configurationupdater.ui.activity

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.farkhodkhaknazarov.configurationupdater.R
import com.farkhodkhaknazarov.configurationupdater.api.TerminalConfigurationsRepositoryFactory
import com.farkhodkhaknazarov.configurationupdater.databinding.ActivityMainBinding
import com.farkhodkhaknazarov.configurationupdater.ui.fragment.ConfigurationFragment
import com.farkhodkhaknazarov.configurationupdater.ui.fragment.HomeFragment
import com.farkhodkhaknazarov.configurationupdater.ui.view.CommonView
import com.farkhodkhaknazarov.configurationupdater.ui.viewmodel.MainActivityViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.farkhodkhaknazarov.configurationupdater.feature.persistence.api.TerminalConfigurationRepository
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, CommonView {
    private val viewModel: MainActivityViewModel by viewModels()
    private lateinit var binding: ActivityMainBinding

    @Inject
    lateinit var terminalConfigurationRepository: TerminalConfigurationRepository

    @Inject
    lateinit var terminalConfigurationsRepositoryFactory: TerminalConfigurationsRepositoryFactory

    companion object {
        lateinit var instance: MainActivity

        fun changeFragmentToMain() {
            if (::instance.isInitialized) {
                instance.setFragment(HomeFragment.TAG)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenCreated { initView() }
        instance = this
    }

    @SuppressLint("RestrictedApi")
    override fun initView() {
        binding.bottomNavView.setOnNavigationItemSelectedListener(this)
        binding.bottomNavView.selectedItemId = R.id.menu_home
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val (tag, result) = when (item.itemId) {
            R.id.menu_home -> HomeFragment.TAG to true

            R.id.menu_settings -> ConfigurationFragment.TAG to true

            else -> null to false
        }

        if (tag != null) {
            setFragment(tag)
        }

        return result
    }

    private fun setFragment(tag: String) {
        getFragment(tag)?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment, it)
                .commit()
        }
    }

    private fun getFragment(tag: String): Fragment? = when (tag) {
        HomeFragment.TAG -> HomeFragment()
        ConfigurationFragment.TAG -> ConfigurationFragment()
        else -> null
    }

    override suspend fun initObservers() {}
}