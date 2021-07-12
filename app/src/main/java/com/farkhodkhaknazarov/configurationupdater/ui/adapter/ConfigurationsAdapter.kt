package com.farkhodkhaknazarov.configurationupdater.ui.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.farkhodkhaknazarov.configurationupdater.R
import com.farkhodkhaknazarov.configurationupdater.api.TemInvokerResultEnum
import com.farkhodkhaknazarov.configurationupdater.api.TemInvokerState
import com.farkhodkhaknazarov.configurationupdater.core.model.TerminalConfiguration

class ConfigurationsAdapter(
    var configurationsList: MutableList<TerminalConfiguration>,
    val invokeConfiguration: (TerminalConfiguration) -> Unit
) : RecyclerView.Adapter<ConfigurationsAdapter.ConfigurationHolder>() {
    var selectedPosition: Int = 0

    inner class ConfigurationHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private var isSelected: Boolean = false
        private var updateResultText: String = ""
        private var stateImageResource: Int = R.drawable.ic_construction_24

        private val itemContainer: ConstraintLayout = itemView.findViewById(R.id.itemContainer)
        private val serverUrl: TextView = itemView.findViewById(R.id.serverUrl)

        private val updateResultTextView: TextView = itemView.findViewById(R.id.updateResultTextView)
        private val itemImageView: ImageView = itemView.findViewById(R.id.itemImageView)

        fun bind(configuration: TerminalConfiguration) {
            itemView.setBackgroundColor(if (isSelected) Color.LTGRAY else 0x00000000)
            itemImageView.setImageResource(stateImageResource)

            itemContainer.setOnClickListener {
                selectedPosition = adapterPosition
                onInvokeConfiguration(configuration)
            }
            serverUrl.text = configuration.serverUrl
            updateResultTextView.isVisible = updateResultText.isNotEmpty()
            updateResultTextView.text = updateResultText
        }

        fun updateView(viewState: TemInvokerState.Invoking) {
            isSelected = viewState.invoking
            updateResultText = viewState.description

            stateImageResource = when (viewState.invokeResult) {
                TemInvokerResultEnum.UNKNOWN -> {
                    R.drawable.ic_construction_24
                }
                TemInvokerResultEnum.COMPLETE, TemInvokerResultEnum.SUCCESS -> {
                    R.drawable.ic_done_24
                }
                TemInvokerResultEnum.ERROR -> {
                    R.drawable.ic_error_outline_24
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConfigurationHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.configuration_recyclerview_item, parent, false)
        return ConfigurationHolder(itemView)
    }

    override fun onBindViewHolder(holder: ConfigurationHolder, position: Int) {
        holder.bind(configurationsList[position])
    }

    override fun getItemCount() = configurationsList.size

    private fun onInvokeConfiguration(configuration: TerminalConfiguration) {
        invokeConfiguration(configuration)
    }
}