package com.example.phumlanidubefoundationmobileapplication.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.phumlanidubefoundationmobileapplication.databinding.FragmentHomeBinding
import com.example.phumlanidubefoundationmobileapplication.BaseActivity

class HomeFragment : Fragment() {
	
	private var _binding: FragmentHomeBinding? = null
	
	// This property is only valid between onCreateView and
	// onDestroyView.
	private val binding get() = _binding!!
	
	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View {
		val homeViewModel =
			ViewModelProvider(this).get(HomeViewModel::class.java)

		// Access BaseActivity and apply font size changes if needed
		val baseActivity = activity as? BaseActivity
		baseActivity?.applyFontSize()  // Call the font size method from BaseActivity

		_binding = FragmentHomeBinding.inflate(inflater, container, false)
		val root: View = binding.root
		
		val textView: TextView = binding.textHome
		homeViewModel.text.observe(viewLifecycleOwner) {
			textView.text = it
		}
		return root
	}
	
	override fun onDestroyView() {
		super.onDestroyView()
		_binding = null
	}
}