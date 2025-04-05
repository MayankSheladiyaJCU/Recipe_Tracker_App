package com.mayank122.recipe_tracker_app.viewmodels

import androidx.lifecycle.*
import com.mayank122.recipe_tracker_app.data.BookmarkDao
import com.mayank122.recipe_tracker_app.data.BookmarkedMeal
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch


class BookmarkViewModel(private val bookmarkDao: BookmarkDao) : ViewModel() {

    // Expose the flow of bookmarked meals
    val bookmarkedMeals: StateFlow<List<BookmarkedMeal>> = bookmarkDao.getAllBookmarks()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList() // Start with an empty list
        )

    // Optional: Function to remove a bookmark directly from the list view
    fun removeBookmark(mealId: String) {
        viewModelScope.launch {
            bookmarkDao.deleteBookmark(mealId)
        }
    }
}

// --- ViewModel Factory (if not using Hilt) ---
class BookmarkViewModelFactory(private val bookmarkDao: BookmarkDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BookmarkViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BookmarkViewModel(bookmarkDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
