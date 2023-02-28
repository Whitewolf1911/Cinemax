package com.alibasoglu.cinemax.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.databinding.DialogShareBinding

@SuppressLint("NewApi")
class ShareDialog(
    private val activity: Activity
) {
    var dialog: AlertDialog? = null

    fun startDialog() {
        val binding = DialogShareBinding.inflate(LayoutInflater.from(activity))
        val mBuilder = AlertDialog.Builder(activity).setView(binding.root)

        dialog = mBuilder.show()
        dialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.bg_transparent_view))
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.setCancelable(true)
        dialog?.setOnCancelListener {
            clearBlurEffect(activity)
        }
        showBlurEffect(activity)

        with(binding) {
            closeButton.setOnClickListener {
                dismissDialog()
            }
            facebookButton.setOnClickListener {
                Toast.makeText(activity.baseContext, "Share Facebook", Toast.LENGTH_SHORT).show()
            }
            instagramButton.setOnClickListener {
                Toast.makeText(activity.baseContext, "Share Instagram", Toast.LENGTH_SHORT).show()
            }
            telegramButton.setOnClickListener {
                Toast.makeText(activity.baseContext, "Share Telegram", Toast.LENGTH_SHORT).show()
            }
            messengerButton.setOnClickListener {
                Toast.makeText(activity.baseContext, "Share Messenger", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun dismissDialog() {
        if (dialog != null) {
            dialog?.dismiss()
            clearBlurEffect(activity)
        }
    }
}
