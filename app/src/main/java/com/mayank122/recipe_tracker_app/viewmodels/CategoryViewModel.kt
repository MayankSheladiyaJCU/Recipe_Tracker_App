package com.mayank122.recipe_tracker_app.viewmodels

import android.R.attr.category
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mayank122.recipe_tracker_app.data.RetrofitInstance
import com.mayank122.recipe_tracker_app.model.Category
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewModel : ViewModel() {

    private val _categories = MutableStateFlow<List<Category>>(emptyList())
    val categories: StateFlow<List<Category>> = _categories

    // Optional: Add states for loading and errors for a better UX
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error


    // Fetch categories from API
    fun fetchCategories() {
        viewModelScope.launch {
            _isLoading.value = true // Indicate loading start
            _error.value = null     // Clear previous errors

            try {
                // Use the passed-in category
                val response = RetrofitInstance.api.getCategories()
                _categories.value = response.categories ?: emptyList() // Handle null response from API
            } catch (e: Exception) {
                _error.value = "Failed to fetch meals: ${e.localizedMessage}"
                e.printStackTrace()
                _categories.value = emptyList()
            } finally {
                _isLoading.value = false
            }


        }
    }
}
