package com.example.phumlanidubefoundationmobileapplication

import android.os.Bundle
import android.widget.Toast
import android.content.Intent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.phumlanidubefoundationmobileapplication.databinding.ActivityRegisterBinding
import com.google.firebase.auth.FirebaseAuth
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Register : AppCompatActivity() {

	private lateinit var firebaseAuth: FirebaseAuth
	private lateinit var binding: ActivityRegisterBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Enable edge-to-edge mode for immersive UI
		enableEdgeToEdge()

		// Inflate layout using ViewBinding
		binding = ActivityRegisterBinding.inflate(layoutInflater)
		setContentView(binding.root)

		// Initialize FirebaseAuth
		firebaseAuth = FirebaseAuth.getInstance()

		// Register Button Click
		binding.registerButton.setOnClickListener {
			val username = binding.usernameInput.text.toString().trim()
			val password = binding.passwordInput.text.toString().trim()
			val email = binding.emailPhoneInput.text.toString().trim()
			val confirmPassword = binding.confirmPasswordInput.text.toString().trim()

			if (username.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
				if (password == confirmPassword) {
					firebaseAuth.createUserWithEmailAndPassword(email, password)
						.addOnCompleteListener { task ->
							if (task.isSuccessful) {
								val intent = Intent(this, MainActivity::class.java)
								startActivity(intent)
								finish() // Optional: finish the Register activity so the user can't go back
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
