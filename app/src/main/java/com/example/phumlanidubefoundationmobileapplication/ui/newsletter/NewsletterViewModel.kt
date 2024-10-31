package com.example.phumlanidubefoundationmobileapplication.ui.newsletter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class NewsletterViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    private val _categories = MutableLiveData<List<Category>>()
    val categories: LiveData<List<Category>> = _categories

    private val _events = MutableLiveData<List<Event>>()
    val events: LiveData<List<Event>> = _events

    private val _announcements = MutableLiveData<List<Announcement>>()
    val announcements: LiveData<List<Announcement>> = _announcements

    // Fetch categories
    fun fetchCategories() {
        db.collection("categories")
            .get()
            .addOnSuccessListener { snapshot ->
                _categories.value = snapshot.documents.mapNotNull { it.toObject(Category::class.java) }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching categories", e)
            }
    }

    // Fetch events by category
    fun fetchEventsByCategory(categoryName: String) {
        db.collection("events")
            .whereEqualTo("category", categoryName)
            .get()
            .addOnSuccessListener { snapshot ->
                _events.value = snapshot.documents.mapNotNull { it.toObject(Event::class.java) }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching events", e)
            }
    }

    // Fetch announcements
    fun fetchAnnouncements() {
        db.collection("announcements")
            .get()
            .addOnSuccessListener { snapshot ->
                _announcements.value = snapshot.documents.mapNotNull { it.toObject(Announcement::class.java) }
            }
            .addOnFailureListener { e ->
                Log.e("Firestore", "Error fetching announcements", e)
            }
    }
}

