package com.mayank122.recipe_tracker_app.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mayank122.recipe_tracker_app.data.BookmarkDao
import com.mayank122.recipe_tracker_app.data.RetrofitInstance
import com.mayank122.recipe_tracker_app.data.toBookmarkedMeal
import com.mayank122.recipe_tracker_app.model.Meal
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class SearchMealViewModel(private val bookmarkDao: BookmarkDao) : ViewModel() {

    private val _bookmarkedIds = MutableStateFlow<Set<String>>(emptySet())
    val bookmarkedMealIds: StateFlow<Set<String>> = _bookmarkedIds.asStateFlow()

    private val _meals = MutableStateFlow<List<Meal>>(emptyList())
    val meals: StateFlow<List<Meal>> = _meals

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchInitialBookmarks()
    }

    fun fetchInitialBookmarks() {
        viewModelScope.launch {
            _bookmarkedIds.value = bookmarkDao.getAllBookmarkIdsOnce().toSet()
        }
    }


    fun toggleBookmark(meal: Meal) {
        viewModelScope.launch {
            val currentBookmarks = _bookmarkedIds.value
            if (currentBookmarks.contains(meal.idMeal)) {
                bookmarkDao.deleteBookmark(meal.idMeal)

                _bookmarkedIds.value = currentBookmarks - meal.idMeal
            } else {
                bookmarkDao.insertBookmark(meal.toBookmarkedMeal())
                _bookmarkedIds.value = currentBookmarks + meal.idMeal
            }
        }
    }

    class SearchMealViewModelFactory(private val bookmarkDao: BookmarkDao) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SearchMealViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST") return SearchMealViewModel(bookmarkDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }

    fun fetchMeals(category: String) {

        Log.i("TAG###", "fetchMeals: $category")

        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = RetrofitInstance.api.searchMeals(category)
                _meals.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                _error.value = "Failed to fetch meals: ${e.localizedMessage}"
                e.printStackTrace()
                _meals.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun fetchCategoryMeals(category: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val response = RetrofitInstance.api.queryMealsByCategory(category)
                _meals.value = response.meals ?: emptyList()
            } catch (e: Exception) {
                _error.value = "Failed to fetch meals: ${e.localizedMessage}"
                e.printStackTrace()
                _meals.value = emptyList()
            } finally {
                _isLoading.value = false
            }
        }
    }


}
