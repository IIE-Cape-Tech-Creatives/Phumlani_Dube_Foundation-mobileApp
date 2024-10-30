package com.example.phumlanidubefoundationmobileapplication

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.FirebaseException
import java.util.concurrent.TimeUnit



class ForgtPassVerifwPhoneNo : Fragment() {
	
	private lateinit var verifyCodeBtn: Button
	private lateinit var resendCodeBtn: Button // Button to resend the verification code
	private lateinit var verificationCodeEditText: EditText
	private var storedVerificationId: String? = null // You'll pass this from the first fragment
	private lateinit var firebaseAuth: FirebaseAuth
	private lateinit var resendToken: PhoneAuthProvider.ForceResendingToken
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_forgt_pass_verifw_phone_no, container, false)

		// Access BaseActivity and apply font size changes if needed
		val baseActivity = activity as? BaseActivity
		baseActivity?.applyFontSize()  // Call the font size method from BaseActivity

		// Initialize FirebaseAuth
		firebaseAuth = FirebaseAuth.getInstance()
		
		// Initialize views
		verifyCodeBtn = view.findViewById(R.id.verifyCodeBtn)
		resendCodeBtn = view.findViewById(R.id.resendCodeBtn) // Initialize the resend button
		verificationCodeEditText = view.findViewById(R.id.verificationCodeEditText)
		
		// Get the phone number and stored verification ID from the previous fragment
		val phoneNumber = arguments?.getString("phoneNumber")
		storedVerificationId = arguments?.getString("storedVerificationId")
		
		// Check if storedVerificationId is null
		if (storedVerificationId == null) {
			Toast.makeText(requireContext(), "Verification ID is missing", Toast.LENGTH_SHORT).show()
			// Navigate back to login
			navigateToLoginActivity()
		} else {
			// Make the EditText and Buttons visible
			verificationCodeEditText.visibility = View.VISIBLE
			verifyCodeBtn.visibility = View.VISIBLE
			resendCodeBtn.visibility = View.VISIBLE // Make the resend button visible
			
			// Handling verification code
			verifyCodeBtn.setOnClickListener {
				val code = verificationCodeEditText.text.toString()
				if (code.isNotEmpty()) {
					verifyCode(code)
				} else {
					Toast.makeText(requireContext(), "Please enter the verification code", Toast.LENGTH_SHORT).show()
				}
			}
			
			// Handling resend verification code
			resendCodeBtn.setOnClickListener {
				if (storedVerificationId != null) {
					resendVerificationCode(phoneNumber)
				} else {
					Toast.makeText(requireContext(), "Cannot resend code. Verification ID is missing.Please try again by clicking on Resend code button", Toast.LENGTH_SHORT).show()
					// Navigate back to login
					//navigateToLoginActivity()
				}
			}
		}
		
		return view
	}
	
	private fun verifyCode(code: String) {
		// Verify the code using Firebase's PhoneAuthProvider
		if (storedVerificationId != null) {
			val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
			signInWithPhoneAuthCredential(credential)
		} else {
			Toast.makeText(requireContext(), "Verification ID is empty. Please check your sms and type in what was sent to you", Toast.LENGTH_SHORT).show()
		}
	}
	
	private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
		FirebaseAuth.getInstance().signInWithCredential(credential)
			.addOnCompleteListener(requireActivity()) { task ->
				if (task.isSuccessful) {
					// Code is correct, allow the user to change their password
					navigateToChangePasswordFragment()
				} else {
					Toast.makeText(requireContext(), "Oops. The code does not match. Invalid verification code. Please check your sms for the code and type again", Toast.LENGTH_SHORT).show()
				}
			}
	}
	
	// If anything else goes wrong the user is sent back to login
	private fun navigateToLoginActivity() {
		val intent = Intent(requireContext(), Login::class.java)
		// Clear the activity stack so that the user cannot go back to the previous activity
		intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		startActivity(intent)
		requireActivity().finish() // Close the current activity
	}
	
	private fun navigateToChangePasswordFragment() {
		// Navigate to the Change Password Fragment
		val fragment = ChangePasswordFragment()
		parentFragmentManager.beginTransaction()
			.replace(R.id.fragment_container, fragment)
			.commit()
	}
	
	private fun resendVerificationCode(phoneNumber: String?) {
		if (phoneNumber != null) {
			val options = PhoneAuthOptions.newBuilder(firebaseAuth)
				.setPhoneNumber(phoneNumber)          // Phone number to verify
				.setTimeout(60L, TimeUnit.SECONDS)    // Timeout and unit
				.setActivity(requireActivity())        // Activity (for callback binding)
				.setCallbacks(object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
					override fun onVerificationCompleted(credential: PhoneAuthCredential) {
						// Auto-verification is successful, sign in automatically
						signInWithPhoneAuthCredential(credential)
					}
					
					override fun onVerificationFailed(e: FirebaseException) {
						Toast.makeText(requireContext(), "Verification failed: ${e.message}", Toast.LENGTH_SHORT).show()
					}
					
					override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
						// Store the verification ID and resend token
						storedVerificationId = verificationId
						resendToken = token
						Toast.makeText(requireContext(), "Verification code resent!", Toast.LENGTH_SHORT).show()
					}
				})
				.setForceResendingToken(resendToken) // Use the stored resend token
				.build()
			
			PhoneAuthProvider.verifyPhoneNumber(options) // Resend the verification code
		} else {
			Toast.makeText(requireContext(), "Phone number is missing. Cannot resend verification code.", Toast.LENGTH_SHORT).show()
		}
	}
}

