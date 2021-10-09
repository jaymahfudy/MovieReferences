package com.floriv.moviereferences.core.utils

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class PrefUtils @Inject constructor(@ApplicationContext private val context: Context) {

    private val pref: SharedPreferences

    var email: String?
        get() = pref.getString(EMAIL, "")
        set(email) {
            val editor = pref.edit()
            editor.putString(EMAIL, email)
            editor.apply()
        }

    var uid: String?
        get() = pref.getString(UID, "")
        set(uid) {
            val editor = pref.edit()
            editor.putString(UID, uid)
            editor.apply()
        }

    var jwtToken: String?
        get() = pref.getString(JWT_TOKEN, "")
        set(token) {
            val editor = pref.edit()
            editor.putString(JWT_TOKEN, token)
            editor.apply()
        }

    var jsonConfig: String?
        get() = pref.getString(JSON_CONFIG, "")
        set(config) {
            val editor = pref.edit()
            editor.putString(JSON_CONFIG, config)
            editor.apply()
        }

    var project: String?
        get() = pref.getString(PROJECT, "")
        set(project) {
            val editor = pref.edit()
            editor.putString(PROJECT, project)
            editor.apply()
        }

    companion object {
        private const val PREF_NAME = "phoenix_auth"
        private const val EMAIL = "email"
        private const val UID = "uid"
        private const val JWT_TOKEN = "jwt_token"
        private const val JSON_CONFIG = "json_config"
        private const val PROJECT = "project"
    }

    init {
        val privateMode = 0
        pref = context.getSharedPreferences(PREF_NAME, privateMode)
    }
}
