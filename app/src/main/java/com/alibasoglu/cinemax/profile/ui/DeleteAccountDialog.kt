package com.alibasoglu.cinemax.profile.ui

import android.app.Activity
import android.view.LayoutInflater
import com.alibasoglu.cinemax.core.BaseDialog
import com.alibasoglu.cinemax.databinding.DialogDeleteAccountBinding

class DeleteAccountDialog(
    activity: Activity,
    private val listener: DeleteAccountListener
) : BaseDialog(
    activity = activity,
    binding = DialogDeleteAccountBinding.inflate(LayoutInflater.from(activity))
) {

    init {
        binding as DialogDeleteAccountBinding
        binding.cancelButton.setOnClickListener {
            dismissDialog()
        }
        binding.deleteAccountButton.setOnClickListener {
            listener.deleteAccount()
            dismissDialog()
        }
    }

    fun interface DeleteAccountListener {
        fun deleteAccount()
    }

}
