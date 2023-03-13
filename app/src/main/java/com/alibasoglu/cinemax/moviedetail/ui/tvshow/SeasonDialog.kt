package com.alibasoglu.cinemax.moviedetail.ui.tvshow

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.view.LayoutInflater
import androidx.core.content.ContextCompat
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.databinding.DialogSeasonBinding
import com.alibasoglu.cinemax.profile.settings.SeasonListItem
import com.alibasoglu.cinemax.utils.clearBlurEffect
import com.alibasoglu.cinemax.utils.showBlurEffect

@SuppressLint("NewApi")
class SeasonDialog(
    private val activity: Activity,
    private val listener: OnDifferentSeasonSelect,
    private val seasonList: List<SeasonListItem>
) {
    var dialog: AlertDialog? = null
    lateinit var binding: DialogSeasonBinding

    fun startDialog() {
        val binding = DialogSeasonBinding.inflate(LayoutInflater.from(activity))

        val seasonSelectionAdapter = SeasonSelectionAdapter(::onDifferentSeasonSelected)
        binding.seasonRecyclerView.adapter = seasonSelectionAdapter
        seasonSelectionAdapter.setItems(seasonList)
        binding.closeButton.setOnClickListener {
            dismissDialog()
        }
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

    private fun dismissDialog() {
        if (dialog != null) {
            dialog?.dismiss()
            clearBlurEffect(activity)
        }
    }

    private fun onDifferentSeasonSelected(seasonListItem: SeasonListItem) {
        listener.selectSeason(seasonListItem)
        dismissDialog()
    }

    fun interface OnDifferentSeasonSelect {
        fun selectSeason(seasonListItem: SeasonListItem)
    }

}
