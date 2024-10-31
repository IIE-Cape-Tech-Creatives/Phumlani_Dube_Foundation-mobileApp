package com.example.donationform

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.phumlanidubefoundationmobileapplication.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etAmount = findViewById<EditText>(R.id.etAmount)
        val btnSubmit = findViewById<Button>(R.id.btnSubmit)
        val btnBack = findViewById<Button>(R.id.btnBack)

        // Submit button logic
        btnSubmit.setOnClickListener {
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val amountStr = etAmount.text.toString().trim()

            // Validate Name
            if (name.isEmpty()) {
                etName.error = "Please enter your name"
                etName.requestFocus()
                return@setOnClickListener
            }

            // Validate Email
            if (!isValidEmail(email)) {
                etEmail.error = "Please enter a valid email"
                etEmail.requestFocus()
                return@setOnClickListener
            }

            // Validate Donation Amount
            if (amountStr.isEmpty()) {
                etAmount.error = "Please enter a donation amount"
                etAmount.requestFocus()
                return@setOnClickListener
            }

            val amount = amountStr.toDoubleOrNull()
            if (amount == null || amount <= 0) {
                etAmount.error = "Please enter a valid amount greater than 0"
                etAmount.requestFocus()
                return@setOnClickListener
            }

            // If validation passes, submit the form
            Toast.makeText(this, "Thank you for your donation, $name!", Toast.LENGTH_LONG).show()

            // Send an email to the business
            sendEmail(name, email, amount)

            // Redirect back to the previous (home) page
            finish()  // Closes the current activity and goes back
        }

        // Back button logic
        btnBack.setOnClickListener {
            finish()  // Closes the current activity and returns to the previous page
        }
    }

    // Email validation function using Android's Patterns utility
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    // Function to send an email using Intent
    private fun sendEmail(name: String, email: String, amount: Double?) {
        val subject = "Thank you for your Donation"
        val body = """
            Dear $name,
            
            Thank you for your generous donation of R$amount.
            Your support is greatly appreciated!
            
            Best regards,
            The Phumlani Foundation Team
        """.trimIndent()

        /// Create email Intent
        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:")  // Only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf(email))  // Recipient email (user's email)
            putExtra(Intent.EXTRA_SUBJECT, subject)  // Email subject
            putExtra(Intent.EXTRA_TEXT, body)  // Email body
        }

        // Check if there's an app that can handle email Intent
        if (emailIntent.resolveActivity(packageManager) != null) {
            startActivity(emailIntent)
        } else {
            Toast.makeText(this, "No email apps installed", Toast.LENGTH_SHORT).show()
        }
    }
}
