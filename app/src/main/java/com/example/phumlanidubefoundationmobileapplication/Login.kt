package com.example.phumlanidubefoundationmobileapplication

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.phumlanidubefoundationmobileapplication.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import android.widget.Button
import android.content.Intent
import android.widget.EditText
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthOptions
import java.util.concurrent.TimeUnit
import com.google.firebase.FirebaseException

class Login : AppCompatActivity() {
	private lateinit var binding: ActivityLoginBinding
	private lateinit var firebaseAuth: FirebaseAuth
	private lateinit var storedVerificationId: String
	private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		enableEdgeToEdge()
	//	setContentView(R.layout.activity_login)
		val registerButton = findViewById<Button>(R.id.registerButton)
		val forgotPasswordBtn = findViewById<Button>(R.id.forgotPasswordButton)
		val phoneNumberEditText = findViewById<EditText>(R.id.emailPhoneInput)
		val emailEditText = findViewById<EditText>(R.id.emailPhoneInput)
		val verificationCodeEditText = findViewById<EditText>(R.id.verificationCodeEditText)
		val sendCodeBtn = findViewById<Button>(R.id.sendCodeBtn)
		val verifyCodeBtn = findViewById<Button>(R.id.verifyCodeBtn)
		
		binding = ActivityLoginBinding.inflate(layoutInflater)
		setContentView(binding.root)
		
		firebaseAuth = FirebaseAuth.getInstance()
		
		//if user wants to register instead of logging in
		
		registerButton.setOnClickListener {
			val intent = Intent(this, Register::class.java)
			startActivity(intent)
			
		}
//-------------------------------------------------------------------------------------------------
		
		// Step 1: Send verification code
		sendCodeBtn.setOnClickListener {
			val phoneNumber = phoneNumberEditText.text.toString()
			if (phoneNumber.isNotEmpty()) {
				sendVerificationCode(phoneNumber)
				// Navigate to ForgotPassVerifyPhoneNoFragment
				
				val fragment = ForgtPassVerifwPhoneNo()
				//  pass the phone number to the next fragment using a Bundle
				val bundle = Bundle()
				bundle.putString("phoneNumber", phoneNumber)
				fragment.arguments = bundle
				
				// Replace the current fragment with the new one
				supportFragmentManager.beginTransaction()
					.replace(R.id.fragment_container, fragment)
					.addToBackStack(null) // This allows the user to go back to the previous fragment
					.commit()
			} else {
				Toast.makeText(this,"Please type in your phone number", Toast.LENGTH_SHORT).show()
			}
		}
		
	
//-------------------------------------------------------------------------------------------------
		//if user forgets their password, so email for rest
		
		forgotPasswordBtn.setOnClickListener {
			val email = getEmailInput() // Get email from the user input(check button method)
			if (email.isNotEmpty()) {
				sendPasswordResetEmail(email)
			} else {
				Toast.makeText(this, "You need to use your email to change your password. That is where we send the new password.", Toast.LENGTH_SHORT).show()
			}
		}
	
	}

	private fun sendPasswordResetEmail(email: String) {
		firebaseAuth.sendPasswordResetEmail(email)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					Toast.makeText(this, "Password reset is sent to the  email. Check your inbox", Toast.LENGTH_SHORT).show()
				} else {
					Toast.makeText(this, "Error: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
				}
			}
	}
	
	private fun getEmailInput(): String {
		// where the email is used for input
		return findViewById<EditText>(R.id.emailPhoneInput).text.toString()
	}
//-----------------------------------------------------------------------------------------------
	//if user wants to use their phone number
private fun sendVerificationCode(phoneNumber: String) {
	val options = PhoneAuthOptions.newBuilder(firebaseAuth)
		.setPhoneNumber(phoneNumber)          // Phone number to verify
		.setTimeout(60L, TimeUnit.SECONDS)    // Timeout and unit
		.setActivity(this)                    // Activity (for callback binding)
		.setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
			override fun onVerificationCompleted(credential: PhoneAuthCredential) {
				// Auto-retrieval or instant verification is complete
				//signInWithPhoneAuthCredential(credential)
			}
			
			override fun onVerificationFailed(e: FirebaseException) {
				// Handle errors
				Toast.makeText(this@Login, "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
			}
			
			override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
				// Code sent successfully, prompt user to enter it
				storedVerificationId = verificationId
				resendToken = token
				findViewById<EditText>(R.id.verificationCodeEditText).visibility = android.view.View.VISIBLE
				findViewById<Button>(R.id.verifyCodeBtn).visibility = android.view.View.VISIBLE
			}
		})
		.build()
	
	PhoneAuthProvider.verifyPhoneNumber(options)
}
//------------------------------------------------------------------------------------------------

}

