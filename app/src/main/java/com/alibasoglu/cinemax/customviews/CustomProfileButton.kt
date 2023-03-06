package com.alibasoglu.cinemax.customviews

import android.content.Context
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import com.alibasoglu.cinemax.R
import com.alibasoglu.cinemax.databinding.CustomProfileButtonBinding
import com.alibasoglu.cinemax.utils.viewbinding.viewBinding

class CustomProfileButton @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {

    private val binding = viewBinding(CustomProfileButtonBinding::inflate)

    init {
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CustomProfileButton,
            0, 0
        ).apply {

            try {
                binding.textView.text = getString(R.styleable.CustomProfileButton_text)
                binding.startIconImageView.setImageDrawable(getDrawable(R.styleable.CustomProfileButton_startIconDrawable))
            } finally {
                recycle()
            }
        }
    }


}