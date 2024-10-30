package com.example.phumlanidubefoundationmobileapplication.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.phumlanidubefoundationmobileapplication.R
import androidx.preference.ListPreference
import androidx.preference.PreferenceManager
import android.content.Intent
import com.example.phumlanidubefoundationmobileapplication.BaseActivity
import android.util.Log
import com.example.phumlanidubefoundationmobileapplication.ChangePasswordFragment
import com.example.phumlanidubefoundationmobileapplication.databinding.ActivitySettingsBinding
import com.example.phumlanidubefoundationmobileapplication.databinding.FragmentSettingsBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.fragment.app.Fragment
import android.view.View
import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatDelegate
import android.app.NotificationManager

class SettingsFragment : Fragment() {
	private lateinit var sharedPreferences: SharedPreferences
	private lateinit var firebaseAuth: FirebaseAuth
	private var _binding: FragmentSettingsBinding? = null
	private val binding get() = _binding!!

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		// Inflate the layout for this fragment
		_binding = FragmentSettingsBinding.inflate(inflater, container, false)
		return binding.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		// Initialize FirebaseAuth
		firebaseAuth = FirebaseAuth.getInstance()

		// Initialize SharedPreferences
		sharedPreferences = PreferenceManager.getDefaultSharedPreferences(requireContext())

		// Access SettingsActivity and apply font size changes if needed
		val settingsActivity = activity as? SettingsActivity
		settingsActivity?.applyFontSize()

		// Retrieve font size from SharedPreferences as a String, then convert it to an Int
		val fontSizeString = sharedPreferences.getString("font_size", "16") // Default font size is 16 as a String
		val fontSize = fontSizeString?.toInt() ?: 16 // Convert to Int, use 16 if null
		applyFontSize(fontSize)

		// Listen for font size changes
		sharedPreferences.registerOnSharedPreferenceChangeListener { sharedPrefs, key ->
			if (key == "font_size") {
				val newFontSizeString = sharedPrefs.getString(key, "16") // Default font size is 16 as a String
				val newFontSize = newFontSizeString?.toInt() ?: 16 // Convert to Int, use 16 if null
				applyFontSize(newFontSize)

				// Log the new font size
				Log.d("SettingsFragment", "Font size changed to: $newFontSize")
			}
		}

		// Handle Forgot Password Button Click
		binding.changePasswordBtn.setOnClickListener {
			val changePasswordFragment = ChangePasswordFragment()
			parentFragmentManager.beginTransaction()
				.replace(R.id.fragment_container, changePasswordFragment)
				.addToBackStack(null)
				.commit()
		}
//allowing the user to switch modes of device (light mode or dark mode)

		// Set initial state of the switch based on current theme
		val isDarkMode = sharedPreferences.getBoolean("DARK_MODE", false)
		binding.themeSwitch.isChecked = isDarkMode

		// Handle theme switch
		binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
			if (isChecked) {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
			} else {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
			}
			// Save the state in SharedPreferences
			sharedPreferences.edit().putBoolean("DARK_MODE", isChecked).apply()
		}
	}

	/**
	 * This method is called to apply changes to the font size setting.
	 */
	private fun applyFontSize(fontSize: Int) {
		// Get the currently applied font size from SharedPreferences
		val currentFontSize = sharedPreferences.getInt("app_font_size", 16)

		// Only apply changes and recreate the activity if the font size has changed
		if (currentFontSize != fontSize) {
			// Store the chosen font size in SharedPreferences
			sharedPreferences.edit().putInt("app_font_size", fontSize).apply()

			// Notify the app to apply the new font size (this could trigger a restart)
			activity?.recreate()
		} else {
			// Log to confirm that no changes were made
			Log.d("SettingsFragment", "Font size is already set to: $fontSize. No need to recreate activity.")
		}
	}

	//allows user to switch from light mode to dark mode or the other way around
	private fun applyDarkLight() {
		// Set initial state of the switch based on current theme
		val isDarkMode = sharedPreferences.getBoolean("DARK_MODE", false)
		binding.themeSwitch.isChecked = isDarkMode

		binding.themeSwitch.setOnCheckedChangeListener { _, isChecked ->
			if (isChecked) {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
			} else {
				AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
			}
			// Save the state in SharedPreferences
			sharedPreferences.edit().putBoolean("DARK_MODE", isChecked).apply()
		}
	}

	//------------------------------------------------------------------------------------------//
	//notification for when a new newsletter is posted
	//the user must allow to receieve the notifications


	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}

}