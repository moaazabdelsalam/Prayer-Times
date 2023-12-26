package com.task.praytimes.times.data.local

import android.content.Context
import android.content.SharedPreferences
import com.task.praytimes.times.Constants

class PrayerSharedPreferences(private val context: Context) {

    fun mPreference(): SharedPreferences =
        context.getSharedPreferences(Constants.PREFERENCE_NAME, Context.MODE_PRIVATE)
}

inline fun SharedPreferences.mEdit(operation: (SharedPreferences.Editor) -> Unit) {
    val mEdit = edit()
    operation(mEdit)
    mEdit.apply()
}

var SharedPreferences.lastUpdateDate
    get() = getString(Constants.LAST_UPDATE_DATE, Constants.NO_STORED_DATE)
    set(value) {
        mEdit {
            it.putString(Constants.LAST_UPDATE_DATE, value)
        }
    }