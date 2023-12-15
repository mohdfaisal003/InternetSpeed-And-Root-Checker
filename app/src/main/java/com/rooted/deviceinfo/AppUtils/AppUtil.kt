package com.rooted.deviceinfo.AppUtils

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.rooted.deviceinfo.Base.BaseFragment
import com.rooted.deviceinfo.R

object AppUtil {
    val MANUFACTURER = Build.MANUFACTURER
    val DEVICE_NAME = "${MANUFACTURER} ${Build.MODEL}"

    fun osName(): String {
        val fields = Build.VERSION_CODES::class.java.fields
        val codeName =
            fields.firstOrNull { it.getInt(Build.VERSION_CODES::class) == Build.VERSION.SDK_INT }?.name
                ?: "UNKNOWN"
        return codeName
    }

    val ANDROID_VERSION = "${Build.VERSION.RELEASE} (${osName()})"

    /* Toast */
    fun showMessage(context: Context, value: String?) {
        Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
    }

    /* Add Or Remove Fragment */
    fun replaceOrAddFragment(
        manager: FragmentManager,
        containerId: Int,
        fragment: BaseFragment,
        isReplace: Boolean,
        bundle: Bundle?
    ): AppUtil {
        if (fragment != null) {
            val fragmentManager: FragmentManager = manager
            val fragmentTransaction = fragmentManager.beginTransaction()
            if (bundle != null) {
                fragment.arguments = bundle
            }

            if (!isReplace) {
                fragmentTransaction.add(containerId, fragment)
            } else {
                fragmentTransaction.replace(containerId, fragment)
            }

            fragmentTransaction.commit()
        }
        return this
    }

    /* Copy to Clipboard */
    fun copyToClipboard(context: Context, text: String?) {
        try {
            val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
            var clipData: ClipData =
                ClipData.newPlainText(context.getString(R.string.app_name), text)
            clipboardManager.setPrimaryClip(clipData)
            showMessage(context, "Quote Copied")
        } catch (exception: Exception) {
            exception.printStackTrace()
        }
    }

    /* Share Text to Other Apps */
    fun share(context: Context, text: String?) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, text)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, context.getString(R.string.app_name))
        context.startActivity(shareIntent)
    }
}