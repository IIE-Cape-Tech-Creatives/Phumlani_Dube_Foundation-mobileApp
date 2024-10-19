package com.example.phumlanidubefoundationmobileapplication

import android.os.Bundle
import android.content.Intent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.phumlanidubefoundationmobileapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.FirebaseException
import java.util.concurrent.TimeUnit
import com.example.phumlanidubefoundationmobileapplication.ui.whoIsPhumlaniDube.WhoIsPhumlaniDubeFragment

class Login : AppCompatActivity() {

	private lateinit var binding: ActivityLoginBinding
	private lateinit var firebaseAuth: FirebaseAuth
	private lateinit var storedVerificationId: String
	private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		// Inflate layout using ViewBinding
		binding = ActivityLoginBinding.inflate(layoutInflater)
		setContentView(binding.root)

		// Initialize FirebaseAuth
		firebaseAuth = FirebaseAuth.getInstance()

		// Handle Register Button Click
		binding.registerButton.setOnClickListener {
			val intent = Intent(this, Register::class.java)
			startActivity(intent)
		}

		// Handle Forgot Password Button Click
		binding.forgotPasswordButton.setOnClickListener {
			val email = binding.emailPhoneInput.text.toString()
			if (email.isNotEmpty()) {
				sendPasswordResetEmail(email)
			} else {
				Toast.makeText(this, "Please enter your email.", Toast.LENGTH_SHORT).show()
			}
		}
		// Handle Login Button Click
		binding.loginButton.setOnClickListener {
			val email = binding.emailPhoneInput.text.toString()
			val password = binding.passwordInput.text.toString()

			if (email.isNotEmpty() && password.isNotEmpty()) {
				loginUser(email, password)
			} else {
				Toast.makeText(this, "Please enter both email and password.", Toast.LENGTH_SHORT).show()
			}
		}

		// Handle Send Code Button Click
		binding.sendCodeBtn.setOnClickListener {
			val phoneNumber = binding.emailPhoneInput.text.toString()
			if (phoneNumber.isNotEmpty()) {
				sendVerificationCode(phoneNumber)
			} else {
				Toast.makeText(this, "Please enter your phone number.", Toast.LENGTH_SHORT).show()
			}
		}
	}
	// Function to log in user
	private fun loginUser(email: String, password: String) {
		firebaseAuth.signInWithEmailAndPassword(email, password)
			.addOnCompleteListener(this) { task ->
				if (task.isSuccessful) {
					// Login successful, navigate to MainActivity (or another activity)
					val intent = Intent(this, WhoIsPhumlaniDubeFragment::class.java)
					startActivity(intent)
					//finish() // Close the login activity so that the user can't go back to it with the back button
				} else {
					// If sign-in fails, display a message to the user
					Toast.makeText(
						this,
						task.exception?.message ?: "Login failed",
						Toast.LENGTH_SHORT
					).show()
				}
			}
	}

	private fun sendPasswordResetEmail(email: String) {
		firebaseAuth.sendPasswordResetEmail(email)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					Toast.makeText(this, "Password reset link sent to your email.", Toast.LENGTH_SHORT).show()
				} else {
					Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
				}
			}
	}

	private fun sendVerificationCode(phoneNumber: String) {
		val options = PhoneAuthOptions.newBuilder(firebaseAuth)
			.setPhoneNumber(phoneNumber)
			.setTimeout(60L, TimeUnit.SECONDS)
			.setActivity(this)
			.setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
				override fun onVerificationCompleted(credential: PhoneAuthCredential) {
					// Handle auto-retrieval or instant verification
				}

				override fun onVerificationFailed(e: FirebaseException) {
					Toast.makeText(this@Login, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
				}

				override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
					storedVerificationId = verificationId
					resendToken = token
					// Show verification input fields when the code is sent
					binding.verificationCodeEditText.visibility = android.view.View.VISIBLE
					binding.verifyCodeBtn.visibility = android.view.View.VISIBLE
				}
			})
			.build()

		PhoneAuthProvider.verifyPhoneNumber(options)

	}
}