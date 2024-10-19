package com.example.phumlanidubefoundationmobileapplication.ui.newsletter

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.phumlanidubefoundationmobileapplication.R
import android.view.GestureDetector
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView

class Newsletter : AppCompatActivity() {
    private lateinit var textView: TextView
    private lateinit var textArray: Array<String>
    private var currentTextIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Horizontal text container
        val horizontalTextContainer = findViewById<LinearLayout>(R.id.horizontalTextContainer)


        textArray = arrayOf(
            "Phumulani Dude Foundation: August Highlights |\n•| Youth mentorship programs launched |\n•| Community clean-up events |\n| Exciting projects lined up for the future!",
            "Phumulani Dude Foundation: September Highlights |\n•| New partnerships formed |\n•| Successful community events",
            "Phumulani Dude Foundation: October Highlights |\n•| Upcoming projects |\n•| Volunteer opportunities available |"
        )


        // Setup GestureDetector
        val gestureDetector = GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent?,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (e1 != null) {
                    if (e1.x - e2.x > 50) { // Swipe left
                        nextText()
                        return true
                    } else if (e2.x - e1.x > 50) { // Swipe right
                        previousText()
                        return true
                    }
                }
                return false
            }
        })

        // Set touch listener for the horizontal container
        horizontalTextContainer.setOnTouchListener { _, event ->
            gestureDetector.onTouchEvent(event)
            true
        }

        // Handle Download Button Click
        val downloadButton = findViewById<Button>(R.id.downloadButton)
        downloadButton.setOnClickListener {
            // Handle download action
        }

        // Handle Learn More Button Click
        val learnMoreButton = findViewById<Button>(R.id.learnMoreButton)
        learnMoreButton.setOnClickListener {
            // Handle navigation to insights
        }
    }

    private fun nextText() {
        currentTextIndex = (currentTextIndex + 1) % textArray.size
        textView.text = textArray[currentTextIndex]
    }

    private fun previousText() {
        currentTextIndex = (currentTextIndex - 1 + textArray.size) % textArray.size
        textView.text = textArray[currentTextIndex]
    }
}