package com.alibasoglu.cinemax.customviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.content.res.AppCompatResources
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.core.fragment.ToolbarConfiguration
import com.alibasoglu.cinemax.databinding.CustomToolbarBinding
import com.alibasoglu.cinemax.utils.setImageDrawableWithAnimation
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding

class CustomToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = viewBinding(CustomToolbarBinding::inflate)

    fun configure(toolbarConfiguration: ToolbarConfiguration?) {
        if (toolbarConfiguration == null) {
            isVisible = false
            return
        }
        with(toolbarConfiguration) {
            initTitle(titleResId)
            configureStartButton(startIconResId, startIconClick)
            configureEndButton(endIconResId, endIconClick)
        }
        initBackgroundColor(R.color.primary_dark)
        isVisible = true
    }

    private fun initTitle(titleResId: Int?) {
        with(binding.toolbarTitleTextView) {
            isVisible = titleResId != null
            if (titleResId != null) setText(titleResId)
        }
    }

    fun setTitle(title: String?) {
        with(binding.toolbarTitleTextView) {
            isVisible = title != null
            text = title.orEmpty()
        }
    }

    private fun configureStartButton(resId: Int?, clickAction: (() -> Unit)?) {
        binding.startImageButton.apply {
            if (resId == null) {
                visibility = View.INVISIBLE
                return
            }
            setImageResource(resId)
            setOnClickListener { clickAction?.invoke() }
            isVisible = true
        }
    }

    private fun configureEndButton(resId: Int?, clickAction: (() -> Unit)?) {
        binding.endImageButton.apply {
            if (resId == null) {
                visibility = View.INVISIBLE
                return
            }
            setImageResource(resId)
            setOnClickListener { clickAction?.invoke() }
            isVisible = true
        }
    }

    fun changeEndButtonDrawable(resId: Int) {
        binding.endImageButton.apply {
            val drawable = AppCompatResources.getDrawable(context, resId)
            setImageDrawableWithAnimation(drawable)
        }
    }


    private fun initBackgroundColor(resId: Int?) {
        resId?.let {
            binding.root.setBackgroundResource(resId)
        }
    }

}
