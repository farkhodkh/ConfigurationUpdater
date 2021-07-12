package com.farkhodkhaknazarov.configurationupdater.ui.dialog

import android.content.Context
import android.os.Environment
import android.os.Environment.getExternalStorageDirectory
import android.text.InputType
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.files.FileCallback
import com.afollestad.materialdialogs.files.FileFilter
import com.afollestad.materialdialogs.files.fileChooser
import com.afollestad.materialdialogs.input.getInputField
import com.afollestad.materialdialogs.input.input
import com.farkhodkhaknazarov.configurationupdater.R
import java.io.File

object Dialogs {
    fun requirePasswordDialog(
        windowContext: Context,
        passwordEnterResult: (String, Boolean) -> (Unit)
    ): MaterialDialog {
        return MaterialDialog(windowContext).show {
            title(R.string.question_online_pin)

            input(
                inputType = InputType.TYPE_CLASS_TEXT,
                waitForPositiveButton = false,
                allowEmpty = false
            )

            positiveButton(android.R.string.ok) {
                val pass = getInputField().text.toString()
                passwordEnterResult(pass, false)
            }

            negativeButton(android.R.string.cancel) {
                passwordEnterResult("", true)
            }

            cancelOnTouchOutside(false)
        }
    }

    fun xmlFileChooserDialog(
        windowContext: Context,
        callback: FileCallback
    ): MaterialDialog {

        val initialFolder = File(getExternalStorageDirectory(), "Download")

        val filter: FileFilter =
            {
                it.isDirectory || it.extension.endsWith(windowContext.getString(R.string.file_extension_xml),true)
            }

        return MaterialDialog(windowContext).apply {
            fileChooser(
                initialDirectory = initialFolder,
                filter = filter,
                selection = callback,
                context = windowContext
            )

            negativeButton(android.R.string.cancel)
        }
    }
}