package com.mayank122.recipe_tracker_app.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope

import com.mayank122.recipe_tracker_app.data.User
import com.mayank122.recipe_tracker_app.data.Recipe_TrackerDB
import com.mayank122.recipe_tracker_app.data.Recipe_TrackerDao
import kotlinx.coroutines.launch

class Recipe_Tracker_ViewModel (application: Application):AndroidViewModel(application){
    private val Recipe_TrackerDao:Recipe_TrackerDao = Recipe_TrackerDB.getDatabase(application).Recipe_TrackerDao()
    val allUsers: List<User> = Recipe_TrackerDao.getAllUsers()
    fun insert(user: User) = viewModelScope.launch {
        Recipe_TrackerDao.insert(user)
    }
}
