package com.alibasoglu.cinemax.core

import android.app.Activity
import android.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.viewbinding.ViewBinding
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.utils.clearBlurEffect
import com.alibasoglu.cinemax.utils.showBlurEffect

abstract class BaseDialog(
    private val activity: Activity,
    val binding: ViewBinding
) {
    var dialog: AlertDialog? = null

    fun showDialog() {
        val mBuilder = AlertDialog.Builder(activity).setView(binding.root)

        dialog = mBuilder.show()
        dialog?.window?.setBackgroundDrawable(ContextCompat.getDrawable(activity, R.drawable.bg_transparent_view))
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.setCancelable(true)
        dialog?.setOnCancelListener {
            clearBlurEffect(activity)
        }
        showBlurEffect(activity)
    }

    fun dismissDialog() {
        dialog?.dismiss()
        clearBlurEffect(activity)
    }

}
