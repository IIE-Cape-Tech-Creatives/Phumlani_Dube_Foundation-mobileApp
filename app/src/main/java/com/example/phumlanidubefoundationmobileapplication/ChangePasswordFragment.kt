package com.example.phumlanidubefoundationmobileapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Button
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import android.content.Intent
import com.example.phumlanidubefoundationmobileapplication.Login


class ChangePasswordFragment : Fragment() {
	private lateinit var newPasswordEditText: EditText
	private lateinit var changePasswordBtn: Button
	private lateinit var firebaseAuth: FirebaseAuth
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val view = inflater.inflate(R.layout.fragment_change_password, container, false)

		// Access BaseActivity and apply font size changes if needed
		val baseActivity = activity as? BaseActivity
		baseActivity?.applyFontSize()  // Call the font size method from BaseActivity

		newPasswordEditText = view.findViewById(R.id.newPasswordEditText)
		changePasswordBtn = view.findViewById(R.id.changePasswordBtn)
		
		changePasswordBtn.setOnClickListener {
			val newPassword = newPasswordEditText.text.toString()
			if (newPassword.isNotEmpty()) {
				changePassword(newPassword)
			} else {
				Toast.makeText(requireContext(), "Please type in your new password", Toast.LENGTH_SHORT).show()
			}
		}
		
		return view
	}
	
	private fun changePassword(email: String) {
		firebaseAuth.sendPasswordResetEmail(email)
			.addOnCompleteListener { task ->
				if (task.isSuccessful) {
					Toast.makeText(requireContext(), "Password reset link sent to your email.", Toast.LENGTH_SHORT).show()
				} else {
					Toast.makeText(requireContext(), "Error Please use the email you created your password with: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
				}
			}
	}
	
	private fun navigateToLoginActivity() {
		val intent = Intent(requireContext(), Login::class.java)
		// Clear the activity stack so that the user cannot go back to the previous activity
		intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
		startActivity(intent)
		requireActivity().finish() // Close the current activity
	}
}