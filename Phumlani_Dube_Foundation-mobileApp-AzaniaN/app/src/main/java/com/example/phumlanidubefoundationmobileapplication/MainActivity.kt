package com.example.phumlanidubefoundationmobileapplication

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
import androidx.appcompat.app.AppCompatActivity
import com.example.phumlanidubefoundationmobileapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
	
	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)

		setSupportActionBar(binding.appBarMain.toolbar)

		binding.appBarMain.fab.setOnClickListener { view ->
			Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
				.setAction("Action", null)
				.setAnchorView(R.id.fab).show()
		}

		val drawerLayout: DrawerLayout = binding.drawerLayout
		val navView: NavigationView = binding.navView
		val navController = findNavController(R.id.nav_host_fragment_content_main)

		// Set up action bar with nav controller
		appBarConfiguration = AppBarConfiguration(
			setOf(
				R.id.nav_home, R.id.nav_who_is_phumlani_dube // Add your fragment ID here
			), drawerLayout
		)
		setupActionBarWithNavController(navController, appBarConfiguration)
		navView.setupWithNavController(navController)

		// Set up the navigation item selection listener
		navView.setNavigationItemSelectedListener { menuItem ->
			when (menuItem.itemId) {
				R.id.nav_who_is_phumlani_dube -> {
					// Load WhoIsPhumlaniDubeFragment
					supportFragmentManager.beginTransaction()
						.replace(R.id.nav_host_fragment_content_main, WhoIsPhumlaniDubeFragment())
						.addToBackStack(null) // Allow users to navigate back
						.commit()
					drawerLayout.closeDrawers() // Close the drawer
					true
				}
				// Handle other menu items here
				else -> false
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
}