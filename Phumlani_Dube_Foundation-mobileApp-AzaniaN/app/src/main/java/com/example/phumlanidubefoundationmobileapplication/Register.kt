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
		enableEdgeToEdge()
		setContentView(R.layout.activity_register)
		
		binding = ActivityRegisterBinding.inflate(layoutInflater)
		
		
		setContentView(binding.root)
		
		firebaseAuth = FirebaseAuth.getInstance()
		binding.registerButton.setOnClickListener(){
			/*
			the input textboxes for user to register their names passwords
			 */
			val username = binding.usernameInput.text.toString()
			val password = binding.passwordInput.text.toString()
			val email = binding.emailPhoneInput.text.toString()
			val confirmPassword = binding.confirmPasswordInput.text.toString()
			
			if (username.isNotEmpty()&&email.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
				if (password == confirmPassword) {
					
					firebaseAuth.createUserWithEmailAndPassword(email, password)
						.addOnCompleteListener {
							if (it.isSuccessful) {
								val intent = Intent(this, MainActivity::class.java)
								//for now its main activity but real life its newsletter and updates screen
								//above: if the registration is a success, then they transffered to about/updates screen
								startActivity(intent)
							} else {
								Toast.makeText(
									this,
									it.exception.toString(),
									Toast.LENGTH_SHORT
								).show()
							}
						}
				} else {
					Toast.makeText(this, "The Password is not matching. Please check your password or try again", Toast.LENGTH_SHORT).show()
				}
			} else {
				Toast.makeText(this, "Something is wrong. It seems you did not type in anything. Please check if you typed everything", Toast.LENGTH_SHORT).show()
			}
		}
		
		binding.loginButton.setOnClickListener(){
			val intent = Intent(this, Login::class.java)
			//when the user clicks, they are sent to login screen
			startActivity(intent)
		}

		ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
			val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
			v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
			insets
		}
		
	}
}