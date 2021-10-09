package com.floriv.moviereferences.core.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PermissionUtils @Inject constructor(@ApplicationContext private val context: Context) {

    private val pref: SharedPreferences

    fun useRunTimePermissions(): Boolean {
        // Replace Build.VERSION_CODES.LOLLIPOP_MR1 with
        // Build.VERSION_CODES.LOLLIPOP_MR1
        // if something wrong happen
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }

    fun allPermissionsGranted(activity: Activity, permissions: Array<String>): Boolean {
        return if (useRunTimePermissions()) {
            permissions.all {
                activity.checkSelfPermission(it) == PackageManager.PERMISSION_GRANTED
            }
        } else true
    }

    fun hasPermission(activity: Activity, permission: String): Boolean {
        return if (useRunTimePermissions()) {
            activity.checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
        } else true
    }

    fun requestPermissions(activity: Activity, permission: Array<String>) {
        if (useRunTimePermissions()) {
            activity.requestPermissions(permission, PERMISSION_CODE)
        }
    }

    fun requestPermission(activity: Activity, permission: String) {
        if (useRunTimePermissions()) {
            activity.requestPermissions(arrayOf(permission), PERMISSION_CODE)
        }
    }

    fun shouldShowRational(activity: Activity, permission: String): Boolean {
        return if (useRunTimePermissions()) {
            activity.shouldShowRequestPermissionRationale(permission)
        } else false
    }

    fun shouldAskForPermission(activity: Activity, permission: String): Boolean {
        return if (useRunTimePermissions()) {
            !hasPermission(activity, permission) &&
                    (!hasAskedForPermission(activity, permission) ||
                            shouldShowRational(activity, permission))
        } else false
    }

    fun toAppSettings(activity: Activity) {
        val intent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", activity.packageName, null)
        )
        intent.addCategory(Intent.CATEGORY_DEFAULT)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        activity.startActivity(intent)
    }

    fun hasAskedForPermission(activity: Activity?, permission: String?): Boolean {
        return pref.getBoolean(HAS_ASKED, false)
    }

    fun markPermissionAsAsked(activity: Activity?, permission: String?) {
        pref.edit().putBoolean(permission, true).apply()
    }

    companion object {
        private const val PREF_NAME = "phoenix_permissions"
        private const val HAS_ASKED = "has_asked"
        const val PERMISSION_CODE = 202
    }

    init {
        val privateMode = 0
        pref = context.getSharedPreferences(PREF_NAME, privateMode)
    }
}