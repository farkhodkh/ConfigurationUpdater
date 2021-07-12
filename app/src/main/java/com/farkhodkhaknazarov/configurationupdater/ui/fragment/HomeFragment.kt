package com.farkhodkhaknazarov.configurationupdater.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.farkhodkhaknazarov.configurationupdater.api.TemInvokerState
import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration
import com.farkhodkhaknazarov.configurationupdater.databinding.FragmentHomeBinding
import com.farkhodkhaknazarov.configurationupdater.ui.adapter.ConfigurationsAdapter
import com.farkhodkhaknazarov.configurationupdater.ui.view.CommonView
import com.farkhodkhaknazarov.configurationupdater.ui.viewmodel.HomeFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment(), CommonView {

    companion object {
        val TAG: String = HomeFragment::class.java.simpleName
    }

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: HomeFragmentViewModel by viewModels()

    private lateinit var configurationAdapter: ConfigurationsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        lifecycleScope.launchWhenCreated {
            initView()
            viewModel.checkServiceState()
        }

        lifecycleScope.launchWhenStarted { initObservers() }

        return binding.root
    }

    override suspend fun initObservers() {
        viewModel.state.collect { viewState ->
            when (viewState) {
                is TemInvokerState.TerminalConfigurationRead -> {
                    configurationAdapter.configurationsList.clear()
                    configurationAdapter.configurationsList.addAll(viewState.configurationList)
                    configurationAdapter.notifyDataSetChanged()
                }
                is TemInvokerState.ConfigurationUpdateResult -> {
                    showToastMessage(viewState.description)
                }
                is TemInvokerState.Invoking -> {
                    updateRecyclerViewItem(viewState)
                    setViewInvoking(viewState.invoking)
                }
                else -> {
                    binding.txtVServiceState.text = viewState.description
                }
            }
        }
    }

    private fun updateRecyclerViewItem(viewState: TemInvokerState.Invoking) {
        binding
            .configurationsRecyclerView.also { recyclerView ->
                recyclerView.findViewHolderForAdapterPosition(configurationAdapter.selectedPosition)
                    ?.also { holder ->
                        (holder as ConfigurationsAdapter.ConfigurationHolder).updateView(viewState)
                    }
            }
        configurationAdapter.notifyDataSetChanged()
    }

    private fun showToastMessage(description: String) {
        requireContext().also { context ->
            Toast.makeText(context, description, Toast.LENGTH_LONG).show()
        }
    }

    override fun initView() {
        configurationAdapter = ConfigurationsAdapter(
            emptyList<TerminalConfiguration>().toMutableList(),
            this::invokeConfiguration
        )

        binding.configurationsRecyclerView.layoutManager = LinearLayoutManager(activity)
        binding.configurationsRecyclerView.adapter = configurationAdapter
        binding.fabInvokeAll.setOnClickListener {
            lifecycleScope.launch {
                configurationAdapter.configurationsList.indices.forEach {
                    configurationAdapter.selectedPosition = it
                    invokeConfigurationSuspend(configurationAdapter.configurationsList[it])
                }
            }
        }
    }

    private fun invokeConfiguration(configuration: TerminalConfiguration) {
        lifecycleScope.launch {
            viewModel.invokeConfiguration(configuration)
        }
    }

    private suspend fun invokeConfigurationSuspend(configuration: TerminalConfiguration) {
        viewModel.invokeConfiguration(configuration)
    }

    private fun setViewInvoking(state: Boolean) {
        binding.fabInvokeAll.isEnabled = !state
        binding.invokingProgressBar.isVisible = state
    }
}