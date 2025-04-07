package com.mayank122.recipe_tracker_app.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mayank122.recipe_tracker_app.data.BookmarkDao
import com.mayank122.recipe_tracker_app.data.RetrofitInstance
import com.mayank122.recipe_tracker_app.model.MealDetail
import com.mayank122.recipe_tracker_app.model.toBookmarkedMeal
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class MealDetailViewModel(
    private val bookmarkDao: BookmarkDao

) : ViewModel() {
    var meal by mutableStateOf<MealDetail?>(null)
        private set
    var isLoading by mutableStateOf(false)
        private set
    var error by mutableStateOf<String?>(null)
        private set

    private val bookmarkedMealIds: StateFlow<Set<String>> = bookmarkDao.getAllBookmarkIds()
        .map { it.toSet() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptySet()
        )

    val isCurrentMealBookmarked: StateFlow<Boolean> = combine(
        snapshotFlow { meal },
        bookmarkedMealIds
    ) { currentMeal, bookmarks ->
        currentMeal != null && bookmarks.contains(currentMeal.idMeal)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = false
    )

// Fetch the Meal data from API
    fun fetchMeal(mealId: String) {
        if (isLoading || (meal?.idMeal == mealId && meal != null)) {
            return
        }

        viewModelScope.launch {
            isLoading = true
            error = null
            try {
                val response = RetrofitInstance.api.getMealById(mealId)
                meal = response.meals?.firstOrNull()
                if (meal == null) {
                    error = "Meal not found."
                }
            } catch (e: Exception) {
                error = "Failed to load details: ${e.localizedMessage}"
                e.printStackTrace()
            } finally {
                isLoading = false
            }
        }
    }

    fun toggleBookmark() {
        val currentMeal = meal ?: return

        viewModelScope.launch {
            val isCurrentlyBookmarked =
                bookmarkDao.isBookmarked(currentMeal.idMeal).firstOrNull() ?: false
            if (isCurrentlyBookmarked) {
                bookmarkDao.deleteBookmark(currentMeal.idMeal)
            } else {
                bookmarkDao.insertBookmark(currentMeal.toBookmarkedMeal())
            }
        }
    }

    class MealDetailViewModelFactory(private val bookmarkDao: BookmarkDao) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MealDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MealDetailViewModel(bookmarkDao) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }


}
