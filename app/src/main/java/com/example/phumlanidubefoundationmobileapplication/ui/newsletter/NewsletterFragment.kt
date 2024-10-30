package com.example.phumlanidubefoundationmobileapplication.ui.newsletter

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.phumlanidubefoundationmobileapplication.R
import com.example.phumlanidubefoundationmobileapplication.databinding.FragmentNewsletterBinding
import android.widget.TextView
import android.view.MotionEvent
import android.widget.Button
import android.widget.LinearLayout
import android.view.GestureDetector
import com.example.phumlanidubefoundationmobileapplication.BaseActivity
import com.example.phumlanidubefoundationmobileapplication.databinding.FragmentSettingsBinding


class NewsletterFragment : Fragment() {

    private lateinit var Bbinding: FragmentNewsletterBinding
    //private val binding get() = _binding!!
    private lateinit var textArray: Array<String>
    private var currentTextIndex = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        Bbinding = FragmentNewsletterBinding.inflate(inflater, container, false)
        // Access BaseActivity and apply font size changes if needed
        val baseActivity = activity as? BaseActivity
        baseActivity?.applyFontSize()  // Call the font size method from BaseActivity

        return Bbinding.root
    }

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            // Horizontal text container
            val horizontalTextContainer = Bbinding.horizontalTextContainer
            val textView: TextView = Bbinding.eventDescription1
            textArray = arrayOf(
                "Phumulani Dude Foundation: August Highlights |\n•| Youth mentorship programs launched |\n•| Community clean-up events |\n| Exciting projects lined up for the future!",
                "Phumulani Dude Foundation: September Highlights |\n•| New partnerships formed |\n•| Successful community events",
                "Phumulani Dude Foundation: October Highlights |\n•| Upcoming projects |\n•| Volunteer opportunities available |"
            )

            // Setup GestureDetector
            val gestureDetector =
                GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
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
            Bbinding.downloadButton.setOnClickListener {
                // Handle download action
            }

            // Handle Learn More Button Click
            Bbinding.learnMoreButton.setOnClickListener {
                // Handle navigation to insights
            }

            // Set initial text
            textView.text = textArray[currentTextIndex]
        }

        private fun nextText() {
            currentTextIndex = (currentTextIndex + 1) % textArray.size
            Bbinding.eventDescription1.text = textArray[currentTextIndex]
        }

        private fun previousText() {
            currentTextIndex = (currentTextIndex - 1 + textArray.size) % textArray.size
            Bbinding.eventDescription1.text = textArray[currentTextIndex]
        }

//        override fun onDestroyView() {
//            super.onDestroyView()
//            Bbinding = null
//
//        }

}