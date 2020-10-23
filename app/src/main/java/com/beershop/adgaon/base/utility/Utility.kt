package com.beershop.adgaon.base.utility

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log

class Utility {
    private val PLAY_STORE_APP_URL = "market://details?id=%s"
    private val PLAY_STORE_WEB_URL = "https://play.google.com/store/apps/details?id=%s"
    private val PLAY_STORE_PDF_URL = "https://play.google.com/store/search?q=.pdf&c=apps"

    fun redirectToExternalLink(context: Context, url: String) {
        //val fullUrl = autocompleteUrl(url)
        //redirectToExternalLink(context, Uri.parse(fullUrl))
    }

    fun openApp(context: Context, packageName: String) {
        val launchIntent = context.packageManager.getLaunchIntentForPackage(packageName)
        if (launchIntent != null) {
            context.startActivity(launchIntent)
        } else {
            openPlayStore(context, packageName)
        }
    }

    fun openPlayStore(context: Context, packageName: String) {
        try {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(PLAY_STORE_APP_URL.format(packageName))
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
        } catch (e: ActivityNotFoundException) {
            context.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(PLAY_STORE_WEB_URL.format(packageName))
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
        }
    }

    fun openPlayStore(context: Context) {
        try {
            context.startActivity(
                Intent(Intent.ACTION_VIEW).apply {
                    data = Uri.parse(PLAY_STORE_PDF_URL)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                })
        } catch (e: ActivityNotFoundException) {
            Log.e("TAG", "Failed to open play store")
        }
    }

    fun openSystemAppSettings(context: Context) {
        val uri = Uri.fromParts("package", context.packageName, null)
        val intent = Intent().apply {
            action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            data = uri
        }
        context.startActivity(intent)
    }

    fun openSystemSecuritySettings(context: Context) =
        context.startActivity(Intent(Settings.ACTION_SECURITY_SETTINGS))

    fun redirectToExternalLink(context: Context, uri: Uri) {
        val intent = Intent(Intent.ACTION_VIEW, uri)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        try {
            context.startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            // Avoid crashing in some devices
            intent.data = Uri.parse(PLAY_STORE_APP_URL.format("com.android.chrome"))
            context.startActivity(intent)
        }
    }
}