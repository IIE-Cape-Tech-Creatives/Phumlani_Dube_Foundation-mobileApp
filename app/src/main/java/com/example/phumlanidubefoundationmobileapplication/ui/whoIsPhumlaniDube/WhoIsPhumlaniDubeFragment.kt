package com.example.phumlanidubefoundationmobileapplication.ui.whoIsPhumlaniDube

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.phumlanidubefoundationmobileapplication.BaseActivity
import com.example.phumlanidubefoundationmobileapplication.R
import com.example.phumlanidubefoundationmobileapplication.databinding.FragmentSlideshowBinding
import com.example.phumlanidubefoundationmobileapplication.ui.slideshow.SlideshowViewModel
import com.example.phumlanidubefoundationmobileapplication.databinding.FragmentWhoIsPhumlaniDubeBinding
import androidx.lifecycle.Observer


class WhoIsPhumlaniDubeFragment : Fragment() {

    private var _binding: FragmentSlideshowBinding? = null
    private lateinit var viewModel: WhoIsPhumlaniDubeViewModel
    private lateinit var Bbinding: FragmentWhoIsPhumlaniDubeBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflating the layout and set up ViewBinding
        Bbinding = FragmentWhoIsPhumlaniDubeBinding.inflate(inflater, container, false)

//        val whoIsPhumlaniDubeViewModel =
//            ViewModelProvider(this).get(WhoIsPhumlaniDubeViewModel::class.java)
//
//        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
//        val root: View = binding.root

        // Access BaseActivity and apply font size changes if needed
        val baseActivity = activity as? BaseActivity
        baseActivity?.applyFontSize()  // Call the font size method from BaseActivity

        return Bbinding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Obtain ViewModel instance using ViewModelProvider
        viewModel = ViewModelProvider(this).get(WhoIsPhumlaniDubeViewModel::class.java)

        // Observe ViewModel data and update UI when data changes
        viewModel.infoText.observe(viewLifecycleOwner, Observer { infoText ->
            Bbinding.textPhumlaniDubeDetails1.text = infoText // Assuming 'infoTextView' is a TextView in your XML
        })
    }
}

/*
class SlideshowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val slideshowViewModel =
            ViewModelProvider(this).get(SlideshowViewModel::class.java)

        _binding = FragmentSlideshowBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textSlideshow
        slideshowViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
    */
