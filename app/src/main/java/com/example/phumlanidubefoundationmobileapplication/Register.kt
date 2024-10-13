package com.example.phumlanidubefoundationmobileapplication

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.example.phumlanidubefoundationmobileapplication.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth

class Register : AppCompatActivity() {

	private lateinit var firebaseAuth: FirebaseAuth
	private lateinit var binding: ActivityRegisterBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Inflate layout using ViewBinding
		binding = ActivityRegisterBinding.inflate(layoutInflater)
		setContentView(binding.root)

		// Initialize FirebaseAuth
		firebaseAuth = FirebaseAuth.getInstance()

		// Register Button Click
		binding.registerButton.setOnClickListener {
			val username = binding.usernameInput.text.toString()
			val password = binding.passwordInput.text.toString()
			val email = binding.emailPhoneInput.text.toString()
			val confirmPassword = binding.confirmPasswordInput.text.toString()

			if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
				if (password == confirmPassword) {
					firebaseAuth.createUserWithEmailAndPassword(email, password)
						.addOnCompleteListener { task ->
							if (task.isSuccessful) {
								val intent = Intent(this, MainActivity::class.java)
								startActivity(intent)
							} else {
								Toast.makeText(this, task.exception?.message ?: "Registration failed", Toast.LENGTH_SHORT).show()
							}
						}
				} else {
					Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show()
				}
			} else {
				Toast.makeText(this, "Please fill in all fields.", Toast.LENGTH_SHORT).show()
			}
		}

		// Login Button Click
		binding.goToLoginButton.setOnClickListener {
			val intent = Intent(this, Login::class.java)
			startActivity(intent)
		}
	}
}
