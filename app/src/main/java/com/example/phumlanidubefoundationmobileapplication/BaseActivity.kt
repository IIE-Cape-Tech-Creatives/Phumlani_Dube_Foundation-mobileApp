package com.example.phumlanidubefoundationmobileapplication

import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        // Apply font size before setting the content view
        applyFontSize()

        super.onCreate(savedInstanceState)
    }

    // Method to apply font size based on user settings
    fun applyFontSize() {
        // Get the stored font size from SharedPreferences
        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val fontSize = sharedPreferences.getInt("app_font_size", 14) // Default to 14sp

        // Apply the font size by adjusting the configuration
        val configuration = resources.configuration
        configuration.fontScale = fontSize / 14.0f // Normalize based on default 14sp
        resources.updateConfiguration(configuration, resources.displayMetrics)
    }
}