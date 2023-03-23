package com.alibasoglu.cinemax.utils

import android.app.Activity
import android.view.LayoutInflater
import android.widget.Toast
import com.alibasoglu.cinemax.core.BaseDialog
import com.alibasoglu.cinemax.databinding.DialogShareBinding

class ShareDialog(
    private val activity: Activity
) : BaseDialog(
    activity = activity,
    binding = DialogShareBinding.inflate(LayoutInflater.from(activity))
) {
    init {
        binding as DialogShareBinding
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

}
