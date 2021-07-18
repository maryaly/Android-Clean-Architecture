package com.example.myapplication.extensions

import android.graphics.Typeface
import android.view.Gravity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.myapplication.R
import com.example.myapplication.util.Constants
import com.tapadoo.alerter.Alerter

fun Fragment.showErrorAlertDialog(message: String) {
    showAlertDialog(ContextCompat.getColor(requireContext(), R.color.alert_dialog_error), message)
}

private fun Fragment.showAlertDialog(
    colorId: Int,
    message: String,
) {

    Alerter.create(requireActivity())
        .setTitle(Constants.EMPTY_STRING)
        .setBackgroundColorInt(colorId)
        .setContentGravity(Gravity.CENTER)
        .hideIcon()
        .setTextTypeface(Typeface.SANS_SERIF)
        .setText(message)
        .show()
}