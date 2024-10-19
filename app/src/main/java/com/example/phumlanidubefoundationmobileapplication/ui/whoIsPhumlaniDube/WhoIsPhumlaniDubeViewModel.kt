package com.example.phumlanidubefoundationmobileapplication.ui.whoIsPhumlaniDube

import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class WhoIsPhumlaniDubeViewModel : ViewModel() {
    // LiveData to hold data that can be observed in the Fragment
    private val _infoText = MutableLiveData<String>()
    val infoText: LiveData<String> get() = _infoText

    init {
        // Initialize with data, can be updated dynamically
        _infoText.value = "Welcome to the Phumlani Dube Foundation info centre"
    }

    // Method to update data if needed
    fun updateInfoText(newText: String) {
        _infoText.value = newText
    }
}