package com.baosystems.icrc.psm.utils

import android.content.Intent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.baosystems.icrc.psm.R
import com.google.android.material.snackbar.BaseTransientBottomBar.LENGTH_LONG
import com.google.android.material.snackbar.Snackbar

class ActivityManager {
    companion object {
        @JvmStatic
        fun startActivity(activity: AppCompatActivity, intent: Intent,
                          closeCurrentActivity: Boolean) {
            activity.startActivity(intent);

            if (closeCurrentActivity)
                activity.finish()
        }

        @JvmStatic
        fun showErrorMessage(view: View, message: String) {
            // TODO: Style the backgroundTint of an snackbar in case of errors
            if (message.isNotEmpty())
                // TODO: Move the error and success Snackbar styling to the theme or styles (whichever is appropriate)
                Snackbar.make(view, message, LENGTH_LONG).setBackgroundTint(
                    ContextCompat.getColor(view.context, R.color.error)
                ).show()
        }
    }
}