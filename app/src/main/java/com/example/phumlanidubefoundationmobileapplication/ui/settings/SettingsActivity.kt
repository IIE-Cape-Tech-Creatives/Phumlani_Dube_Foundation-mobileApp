package com.example.phumlanidubefoundationmobileapplication.ui.settings


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.phumlanidubefoundationmobileapplication.R
import com.example.phumlanidubefoundationmobileapplication.databinding.ActivitySettingsBinding
import androidx.fragment.app.FragmentTransaction
import com.example.phumlanidubefoundationmobileapplication.ChangePasswordFragment
import com.example.phumlanidubefoundationmobileapplication.databinding.FragmentChangePasswordBinding
import com.example.phumlanidubefoundationmobileapplication.databinding.FragmentSettingsBinding
import com.example.phumlanidubefoundationmobileapplication.BaseActivity
import androidx.navigation.findNavController
import com.example.phumlanidubefoundationmobileapplication.databinding.ActivityMainBinding

class SettingsActivity : BaseActivity() {

    private lateinit var binding: ActivitySettingsBinding
    private lateinit var Binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize the binding
        binding = ActivitySettingsBinding.inflate(layoutInflater)

        // Set the content view to the binding's root view
        setContentView(binding.root)

        // Now you can safely use `binding`
        //setSupportActionBar(Binding.toolbar)

        // Add the SettingsFragment to the activity
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.Setting_fragment_container, SettingsFragment())
                .commit()
        }


    }

//    override fun onSupportNavigateUp(): Boolean {
//        val navController = findNavController(R.id.nav_host_fragment_content_settings)
//        return navController.navigateUp(appBarConfiguration)
//                || super.onSupportNavigateUp()
//
//
//    }
}

//private lateinit var bind: FragmentSettingsBinding
////    private lateinit var binding: ActivitySettingsBinding
////    private lateinit var bindings : FragmentChangePasswordBinding
//
//
//override fun onCreate(savedInstanceState: Bundle?) {
//    super.onCreate(savedInstanceState)
//
//    // Initialize the binding
//    //bind = FragmentSettingsBinding.inflate(layoutInflater)
//    setContentView(R.layout.activity_settings)
//    //setContentView(R.layout.activity_settings) // Make sure you have this layout created
//
////        // Add the SettingsFragment to the activity
////        if (savedInstanceState == null) {
////            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
////            transaction.replace(R.id.Setting_fragment_container, SettingsFragment())
////            transaction.commit()
////        }
//
////        // Check if the fragment is already added to avoid adding it multiple times
//    if (savedInstanceState == null) {
//        supportFragmentManager.beginTransaction()
//            .replace(
//                R.id.Setting_fragment_container,
//                SettingsFragment()
//            ) // Use your fragment's container ID
//            .commit()}
////        setSupportActionBar(binding.toolbar)
////
////        val navController = findNavController(R.id.nav_host_fragment_content_settings)
////        appBarConfiguration = AppBarConfiguration(navController.graph)
////        setupActionBarWithNavController(navController, appBarConfiguration)
////
////        binding.fab.setOnClickListener { view ->
////            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
////                .setAction("Action", null)
////                .setAnchorView(R.id.fab).show()
////        }
//    //}
//
////    override fun onSupportNavigateUp(): Boolean {
////        val navController = findNavController(R.id.nav_host_fragment_content_settings)
////        return navController.navigateUp(appBarConfiguration)
////                || super.onSupportNavigateUp()
////    }
//    //change username or email
////using email notification so the user can change their password or email via email link
//    // Handle Forgot Password Button Click
//    bind.changePasswordBtn.setOnClickListener {
//        val changePasswordFragment = ChangePasswordFragment()
//
//        // Replace the current fragment with the ChangePasswordFragment
//        supportFragmentManager.beginTransaction()
//            .replace(R.id.fragment_container, changePasswordFragment) // fragment_container is the ID of your FrameLayout
//            .addToBackStack(null) // Optional: Adds the transaction to the back stack, so the user can navigate back
//            .commit()
//    }
//}