package com.farkhodkhaknazarov.configurationupdater.ui.fragment

import android.R
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.farkhodkhaknazarov.configurationupdater.common.Constants
import com.farkhodkhaknazarov.configurationupdater.databinding.FragmentConfigurationBinding
import com.farkhodkhaknazarov.configurationupdater.feature.remote.config.enums.UpdateIntervalEnum
import com.farkhodkhaknazarov.configurationupdater.ui.activity.MainActivity.Companion.changeFragmentToMain
import com.farkhodkhaknazarov.configurationupdater.ui.dialog.Dialogs
import com.farkhodkhaknazarov.configurationupdater.ui.state.ConfigurationState
import com.farkhodkhaknazarov.configurationupdater.ui.view.CommonView
import com.farkhodkhaknazarov.configurationupdater.ui.viewmodel.ConfigurationFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class ConfigurationFragment : Fragment(), CommonView {
    companion object {
        val TAG: String = ConfigurationFragment::class.java.simpleName
    }

    private lateinit var binding: FragmentConfigurationBinding
    private val viewModel: ConfigurationFragmentViewModel by viewModels()

    private lateinit var askReadExternalStoragePermission: ActivityResultLauncher<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentConfigurationBinding.inflate(inflater, container, false)

        askReadExternalStoragePermission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isSuccess ->
            if (isSuccess) {
                Dialogs.xmlFileChooserDialog(requireContext()) { _, file ->
                    binding.editTextFilePath.setText(file.absolutePath.toString())
                    viewModel.importConfigurationFile(file)
                }.show()
            }
        }

        lifecycleScope.launchWhenCreated {
            initView()
        }
        lifecycleScope.launchWhenResumed {
            viewModel.requireConfigurationPassword()
            viewModel.getCurrentSchedule()
        }
        lifecycleScope.launchWhenStarted {
            initObservers()
        }

        return binding.root
    }

    override suspend fun initObservers() {
        viewModel.state.onEach { state ->

            when (state) {
                is ConfigurationState.Unknown -> {
                    binding.txtVResult.text = state.description
                }
                is ConfigurationState.ConfigurationUpdated -> {
                    binding.txtVResult.text = state.description
                }
                is ConfigurationState.ConfigurationSaveError -> {
                    binding.txtVResult.text = state.description
                }
                is ConfigurationState.RequirePassword -> {
                    requireConfigurationPassword()
                }
            }

        }
            .launchIn(lifecycleScope)

        viewModel
            .schedule
            .onEach { schedule ->
                val updateIndex = (binding.spConfUpdateInterval.adapter as ArrayAdapter<String>)
                    .getPosition(schedule.configurationUpdateInterval.toString())
                val invocationIndex =
                    (binding.spInvocationInterval.adapter as ArrayAdapter<String>)
                        .getPosition(schedule.invocationInterval.toString())

                binding.spConfUpdateInterval.setSelection(updateIndex)
                binding.spInvocationInterval.setSelection(invocationIndex)
            }
            .launchIn(lifecycleScope)
    }

    override fun initView() {
        binding.bntBrowse.setOnClickListener {
            activity?.let {
                askReadExternalStoragePermission.launch(Constants.READ_EXTERNAL_STORAGE_PERMISSION)
            }
        }
        context?.let { cont ->
            val intervals = UpdateIntervalEnum.getListOfValues()

            val adapterTids = ArrayAdapter(
                cont,
                R.layout.simple_spinner_item,
                intervals
            )
            binding.spConfUpdateInterval.adapter = adapterTids
            binding.spInvocationInterval.adapter = adapterTids

            binding.btnSaveSchedule.setOnClickListener {
                viewModel.saveConfiguration(
                    binding.spConfUpdateInterval.selectedItem.toString(),
                    binding.spInvocationInterval.selectedItem.toString()
                )
            }
        }
    }

    private fun requireConfigurationPassword() {
        Dialogs.requirePasswordDialog(requireContext()) { pass, canceled ->
            when (canceled) {
                false -> viewModel.requireConfigurationPassword(pass)
                else -> {
                    changeFragmentToMain()
                }
            }
        }
    }
}