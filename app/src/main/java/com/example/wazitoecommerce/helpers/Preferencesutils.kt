package com.example.wazitoecommerce.helpers

import android.content.Context
import android.content.SharedPreferences

class PreferenceUtil(context: Context) {

    companion object {
        private const val PREFS_NAME = "myne_settings"

        // Preference keys
        const val APP_THEME_INT = "theme_settings"
        const val MATERIAL_YOU_BOOL = "material_you"
        const val INTERNAL_READER_BOOL = "internal_reader"
        const val READER_FONT_SIZE_INT = "reader_font_size"
        const val READER_FONT_STYLE_STR = "reader_font_style"
        const val PREFERRED_BOOK_LANG_STR = "preferred_book_language"

        // Temporary preference keys
        const val SHOW_LIBRARY_TOOLTIP_BOOL = "show_library_tooltip"
    }

    private var prefs: SharedPreferences

    init {
        prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun keyExists(key: String): Boolean = prefs.contains(key)

    fun putString(key: String, value: String) {
        val prefsEditor = prefs.edit()
        prefsEditor.putString(key, value)
        prefsEditor.apply()
    }

    fun putInt(key: String, value: Int) {
        val prefsEditor = prefs.edit()
        prefsEditor.putInt(key, value)
        prefsEditor.apply()
    }

    fun putBoolean(key: String, value: Boolean) {
        val prefsEditor = prefs.edit()
        prefsEditor.putBoolean(key, value)
        prefsEditor.apply()
    }

    fun getString(key: String, defValue: String): String? {
        return prefs.getString(key, defValue)
    }

    fun getInt(key: String, defValue: Int): Int {
        return prefs.getInt(key, defValue)
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean {
        return prefs.getBoolean(key, defValue)
    }
}