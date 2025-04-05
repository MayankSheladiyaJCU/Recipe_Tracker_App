package com.mayank122.recipe_tracker_app.activities

import com.mayank122.recipe_tracker_app.model.MealDetailsModel


data class MealDetailsScreenState(
    val meal: MealDetailsModel = MealDetailsModel(),

    val isSharingOptionsDialogVisible: Boolean = false,
    val isBookmarked: Boolean = false, // Add bookmark state

    val onBackButtonClick: () -> Unit = { },
    val onShareButtonClick: () -> Unit = { },
    val onSaveButtonClick: () -> Unit = { },
    val onCategoryClick: (String) -> Unit = { },
    val onRegionClick: (String) -> Unit = { },
    val onYoutubeClick: (String) -> Unit = { },
    val onSourceClick: (String) -> Unit = { },
    val onSharingOptionsDialogDismiss: () -> Unit = { },
)