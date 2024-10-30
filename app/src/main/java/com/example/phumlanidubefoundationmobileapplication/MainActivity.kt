package com.example.phumlanidubefoundationmobileapplication

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import com.example.phumlanidubefoundationmobileapplication.databinding.ActivityMainBinding
import com.example.phumlanidubefoundationmobileapplication.ui.settings.SettingsActivity
import com.example.phumlanidubefoundationmobileapplication.ui.whoIsPhumlaniDube.WhoIsPhumlaniDubeViewModel
import android.widget.Toast
import androidx.navigation.ui.NavigationUI
import com.example.phumlanidubefoundationmobileapplication.ui.newsletter.NewsletterFragment


class MainActivity : BaseActivity() {
	
	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Apply the font size before setting the content view
		applyFontSize()

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

//		setSupportActionBar(binding.appBarMain.toolbar)
//
//		binding.appBarMain.gotToSettings.setOnClickListener { view ->
//			val intentSettings = Intent(this, SettingsActivity::class.java) // Use SettingsActivity
//			startActivity(intentSettings) // Start the activity
//		}
		// Initialize Toolbar
		val toolbar: androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)
		setSupportActionBar(toolbar)

		val drawerLayout: DrawerLayout = binding.drawerLayout
		val navView: NavigationView = binding.navView
		val navController = findNavController(R.id.nav_host_fragment_content_main)

		// Set up action bar with nav controller
		appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.nav_whoIsPhumlaniDube,
				R.id.nav_Newsletter,
				R.id.nav_home,
				R.id.nav_gallery,
				R.id.nav_slideshow,
				R.id.nav_settings,
				R.id.nav_register,
				R.id.nav_login

				// Add your fragment ID here

			), drawerLayout
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)


		// Set up custom click listener for navigation items
		navView.setNavigationItemSelectedListener { item ->
			when (item.itemId) {
				R.id.nav_whoIsPhumlaniDube -> {
					// Navigate to the WhoIsPhumlaniDubeFragment
					navController.navigate(R.id.nav_whoIsPhumlaniDube)
					showToast("Connect with Phumlani Dube")
					drawerLayout.closeDrawers()
					true
				}

				R.id.nav_Newsletter-> {
					// Navigate to the Newsletter Fragment
					navController.navigate(R.id.nav_Newsletter)
					showToast("Newsletter selected")
					drawerLayout.closeDrawers()
					true
				}

				R.id.nav_home -> {
					// Custom logic for Home
					showToast("Home selected")
					navController.navigate(R.id.nav_home) // Navigate to Home Fragment
					drawerLayout.closeDrawers() // Close the drawer after click
					true
				}
				R.id.nav_gallery -> {
					// Custom logic for Gallery
					showToast("Gallery selected")
					navController.navigate(R.id.nav_gallery)
					drawerLayout.closeDrawers()
					true
				}
				R.id.nav_settings -> {
					// Open Settings Activity directly instead of a fragment
					val intentSettings = Intent(this, SettingsActivity::class.java)
					startActivity(intentSettings)
					showToast("Settings selected")
					drawerLayout.closeDrawers()
					true
				}
				R.id.nav_register -> {
					//open Register Activity directly instead of a fragment
					val intentRegister = Intent(this,Register::class.java)
					startActivity((intentRegister))
					showToast("Account registertration sign up selected ")
					drawerLayout.closeDrawers()
					true
				}

				R.id.nav_login -> {
					//open Register Activity directly instead of a fragment
					val intentLogin = Intent(this,Login::class.java)
					startActivity((intentLogin ))
					showToast("Login another existing account  selected ")
					drawerLayout.closeDrawers()
					true
				}

				else -> {
					// Default behavior for other items
					NavigationUI.onNavDestinationSelected(item, navController)
					drawerLayout.closeDrawers()
					true
				}
			}
		}
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		// Inflate the menu; this adds items to the action bar if it is present.
		menuInflater.inflate(R.menu.main, menu)
		return true
	}

	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nav_host_fragment_content_main)
		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}

	private fun showToast(message: String) {
		Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
	}
}