package com.example.phumlanidubefoundationmobileapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.phumlanidubefoundationmobileapplication.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import android.widget.Toast
import android.content.Intent

class Register : AppCompatActivity() {
	private lateinit var firebaseAuth: FirebaseAuth
	private lateinit var binding: ActivityRegisterBinding
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		
		// Enable edge-to-edge mode for immersive UI
		enableEdgeToEdge()
		
		// Inflate the layout using ViewBinding
		binding = ActivityRegisterBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		// Initialize Firebase authentication instance
		firebaseAuth = FirebaseAuth.getInstance()
		
		// Handle Register button click
		binding.registerButton.setOnClickListener {
			// Retrieve user input from text fields
			val username = binding.usernameInput.text.toString().trim()
			val email = binding.emailPhoneInput.text.toString().trim()
			val password = binding.passwordInput.text.toString().trim()
			val confirmPassword = binding.confirmPasswordInput.text.toString().trim()
			
			// Validate the input fields
			if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
				if (password == confirmPassword) {
					// Create a new user with email and password
					firebaseAuth.createUserWithEmailAndPassword(email, password)
						.addOnCompleteListener { task ->
							if (task.isSuccessful) {
								// Redirect to MainActivity or another screen after successful registration
								val intent = Intent(this, MainActivity::class.java)
								startActivity(intent)
								finish() // Optional: finish the Register activity so user can't go back
							} else {
								// Show error message
								Toast.makeText(this, task.exception?.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
							}
						}
				} else {
					// Passwords do not match
					Toast.makeText(this, "Passwords do not match. Please try again.", Toast.LENGTH_SHORT).show()
				}
			} else {
				// One or more fields are empty
				Toast.makeText(this, "Please fill out all fields.", Toast.LENGTH_SHORT).show()
			}
		}
		
		// Handle Login button click to navigate to the Login screen
		binding.loginButton.setOnClickListener {
			val intentLogin = Intent(this, Login::class.java)
			startActivity(intentLogin)
		}
		
		// Adjust window insets for immersive layout (handling system bars padding)
		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
	}
}
