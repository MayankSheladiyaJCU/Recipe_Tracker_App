package com.mayank122.recipe_tracker_app.activities

import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.inconceptlabs.designsystem.components.buttons.IconButton
import com.inconceptlabs.designsystem.theme.AppTheme
import com.inconceptlabs.designsystem.theme.LocalContentColor
import com.inconceptlabs.designsystem.theme.attributes.CornerType
import com.inconceptlabs.designsystem.theme.attributes.Size
import com.mayank122.recipe_tracker_app.R
import com.mayank122.recipe_tracker_app.composables.CenteredLoadingIndicator
import com.mayank122.recipe_tracker_app.composables.Label
import com.mayank122.recipe_tracker_app.composables.LabelData
import com.mayank122.recipe_tracker_app.composables.TextButton
import com.mayank122.recipe_tracker_app.data.MealDatabase
import com.mayank122.recipe_tracker_app.listitem.IngredientItem
import com.mayank122.recipe_tracker_app.model.IngredientModel
import com.mayank122.recipe_tracker_app.model.MealDetail
import com.mayank122.recipe_tracker_app.model.MealDetailsModel
import com.mayank122.recipe_tracker_app.ui.theme.Recipe_Tracker_AppTheme
import com.mayank122.recipe_tracker_app.ui.theme.Red900
import com.mayank122.recipe_tracker_app.ui.theme.shimmerBrush
import com.mayank122.recipe_tracker_app.viewmodels.MealDetailViewModel
import com.mayank122.recipe_tracker_app.viewmodels.MealDetailViewModel.MealDetailViewModelFactory


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    id: String, navController: NavHostController, viewModel: MealDetailViewModel = viewModel(
        factory = MealDetailViewModelFactory(
            MealDatabase.getDatabase(LocalContext.current).bookmarkDao()
        )
    )

) {

    LaunchedEffect(Unit) {
        viewModel.fetchMeal(id)
    }

    val meal = viewModel.meal

    val isLoading = viewModel.isLoading
    val error = viewModel.error
    val isBookmarked by viewModel.isCurrentMealBookmarked.collectAsState()

    when {
        isLoading -> {
            CenteredLoadingIndicator()
        }

        error != null -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Error: $error", color = MaterialTheme.colorScheme.error)
            }
        }

        meal != null -> {
            ContentPreview(
                navController = navController,
                meal = meal,
                isBookmarked = isBookmarked,
                onSaveClick = viewModel::toggleBookmark
            )
        }

        else -> {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Meal details could not be loaded.")
            }
        }
    }

}

@Composable
private fun ContentPreview(
    navController: NavHostController,
    meal: MealDetail,
    isBookmarked: Boolean,
    onSaveClick: () -> Unit
) {
    val screenState =
        remember(meal, isBookmarked) {
            MealDetailsScreenState(
                meal = MealDetailsModel(
                    id = meal.idMeal,
                    name = meal.strMeal,
                    category = meal.strCategory,
                    region = meal.strArea,
                    instructions = meal.strInstructions,
                    thumbnail = meal.strMealThumb,
                    youtubeUrl = meal.strYoutube,
                    sourceUrl = meal.strSource,
                    ingredients = meal.let { getIngredientList(it) },
                ),
                isBookmarked = isBookmarked,
                onSaveButtonClick = onSaveClick
            )
        }


    Recipe_Tracker_AppTheme {
        Content(
            screenState = screenState, navController = navController
        )
    }
}

fun getIngredientList(meal: MealDetail): List<IngredientModel> {
    val ingredients = listOf(
        meal.strIngredient1,
        meal.strIngredient2,
        meal.strIngredient3,
        meal.strIngredient4,
        meal.strIngredient5,
        meal.strIngredient6,
        meal.strIngredient7,
        meal.strIngredient8,
        meal.strIngredient9,
        meal.strIngredient10,
        meal.strIngredient11,
        meal.strIngredient12,
        meal.strIngredient13,
        meal.strIngredient14,
        meal.strIngredient15,
        meal.strIngredient16,
        meal.strIngredient17,
        meal.strIngredient18,
        meal.strIngredient19,
        meal.strIngredient20
    )

    val measures = listOf(
        meal.strMeasure1,
        meal.strMeasure2,
        meal.strMeasure3,
        meal.strMeasure4,
        meal.strMeasure5,
        meal.strMeasure6,
        meal.strMeasure7,
        meal.strMeasure8,
        meal.strMeasure9,
        meal.strMeasure10,
        meal.strMeasure11,
        meal.strMeasure12,
        meal.strMeasure13,
        meal.strMeasure14,
        meal.strMeasure15,
        meal.strMeasure16,
        meal.strMeasure17,
        meal.strMeasure18,
        meal.strMeasure19,
        meal.strMeasure20
    )

    return ingredients.zip(measures) { ingredient, measure ->
        if (!ingredient.isNullOrBlank() && !measure.isNullOrBlank()) {
            IngredientModel(name = ingredient, quantity = measure)
        } else null
    }.filterNotNull() // Remove null values
}

@Composable
private fun Content(
    screenState: MealDetailsScreenState, navController: NavHostController,
) = with(screenState) {

    val meal = screenState.meal
    val isBookmarked = screenState.isBookmarked
    val onSaveButtonClick = screenState.onSaveButtonClick
    val context = LocalContext.current

    LazyColumn(
        contentPadding = PaddingValues(horizontal = 20.dp, vertical = 24.dp),
        modifier = Modifier
            .background(AppTheme.colorScheme.BG1)
            .fillMaxSize()
            .systemBarsPadding()
    ) {
        toolbar(
            meal = meal,
            isBookmarked = isBookmarked,
            onBackButtonClick = { navController.popBackStack() },
            onShareButtonClick = { },
            onSaveButtonClick = onSaveButtonClick,
        )

        mealThumbnail(meal = meal)

        mealName(meal = meal)

        labels(meal = meal, onCategoryClick = onCategoryClick, onRegionClick = onRegionClick)

        instructions(meal = meal)

        ingredients(meal = meal)

        externalLinks(meal = meal, {
            val intent = Intent(Intent.ACTION_VIEW, meal.youtubeUrl!!.toUri())
            context.startActivity(intent)

        }, onSourceClick = {
            val intent = Intent(Intent.ACTION_VIEW, meal.sourceUrl!!.toUri())
            context.startActivity(intent)
        })
    }

}

private fun LazyListScope.externalLinks(
    meal: MealDetailsModel,
    onYoutubeClick: (String) -> Unit,
    onSourceClick: (String) -> Unit,
) {
    if (meal.youtubeUrl.isNullOrBlank() && meal.sourceUrl.isNullOrBlank()) return

    item(key = "ExternalLinksTitle") {
        com.inconceptlabs.designsystem.components.core.Text(
            text = stringResource(id = R.string.meal_details_external_sources),
            style = AppTheme.typography.S1,
            modifier = Modifier.padding(top = 32.dp)
        )
    }

    item(key = "ExternalLinksDescription") {
        com.inconceptlabs.designsystem.components.core.Text(
            text = stringResource(id = R.string.meal_details_external_sources_description),
            style = AppTheme.typography.P4,
        )
    }

    item(key = meal.youtubeUrl + meal.sourceUrl) {
        CompositionLocalProvider(
            LocalContentColor provides Color.White
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(top = 16.dp)
                    .fillMaxWidth()
            ) {
                meal.youtubeUrl?.let {
                    TextButton(
                        text = stringResource(id = R.string.meal_details_youtube),
                        icon = painterResource(id = R.drawable.ic_youtube),
                        backgroundColor = Red900,
                        modifier = Modifier.weight(1f),
                        onClick = { onYoutubeClick(it) })
                }

                meal.sourceUrl?.let {
                    TextButton(
                        text = stringResource(id = R.string.meal_details_website),
                        icon = painterResource(id = R.drawable.ic_link),
                        backgroundColor = AppTheme.colorScheme.BG10,
                        modifier = Modifier.weight(1f),
                        onClick = { onSourceClick(it) })
                }
            }
        }
    }
}


private fun LazyListScope.ingredients(meal: MealDetailsModel) {
    if (meal.ingredients.isEmpty()) return

    item(key = "IngredientsTitle") {
        com.inconceptlabs.designsystem.components.core.Text(
            text = stringResource(id = R.string.meal_details_ingredients),
            style = AppTheme.typography.S1,
            modifier = Modifier
                .animateItem()
                .padding(
                    top = 32.dp, bottom = 8.dp
                )
        )
    }

    items(
        key = { it }, items = meal.ingredients
    ) {
        IngredientItem(
            item = it, modifier = Modifier.animateItem()
        )
    }
}

private fun LazyListScope.instructions(meal: MealDetailsModel) {
    if (meal.instructions.isBlank()) return

    item(key = "InstructionsTitle") {
        com.inconceptlabs.designsystem.components.core.Text(
            text = stringResource(R.string.meal_details_cooking_process),
            style = AppTheme.typography.S1,
            modifier = Modifier
                .animateItem()
                .padding(top = 32.dp)
        )
    }

    item(key = meal.instructions) {
        com.inconceptlabs.designsystem.components.core.Text(
            text = meal.instructions,
            style = AppTheme.typography.P4,
            modifier = Modifier
                .animateItem()
                .fillMaxWidth()
                .padding(top = 8.dp)
        )
    }
}


private fun LazyListScope.labels(
    meal: MealDetailsModel,
    onCategoryClick: (String) -> Unit,
    onRegionClick: (String) -> Unit,
) {
    if (meal.region.isBlank() && meal.category.isBlank()) return

    item(key = meal.category + meal.region) {
        CompositionLocalProvider(
            LocalContentColor provides AppTheme.colorScheme.T8
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier
                    .animateItem()
                    .fillMaxWidth()
                    .padding(top = 24.dp)
            ) {
                val labelDefault = LabelData(
                    textStyle = AppTheme.typography.P4,
                    textColor = AppTheme.colorScheme.T8,
                    backgroundColor = AppTheme.colorScheme.BG4,
                    paddingValues = PaddingValues(horizontal = 20.dp, vertical = 6.dp),
                )

                Label(
                    labelData = labelDefault.copy(
                        text = meal.region,
                        icon = painterResource(id = R.drawable.ic_region),
                        onClick = { onRegionClick(meal.region) })
                )

                Label(
                    labelData = labelDefault.copy(
                        text = meal.category,
                        icon = painterResource(id = R.drawable.ic_category),
                        onClick = { onCategoryClick(meal.category) })
                )
            }
        }
    }
}


private fun LazyListScope.mealName(meal: MealDetailsModel) {
    item(key = meal.name) {
        com.inconceptlabs.designsystem.components.core.Text(
            text = meal.name,
            style = AppTheme.typography.H5,
            modifier = Modifier.padding(top = 12.dp)
        )
    }
}


private fun LazyListScope.mealThumbnail(meal: MealDetailsModel) {
    if (meal.thumbnail.isBlank()) return

    var isImageLoading = true

    item(key = meal.thumbnail) {
        Image(
            painter = rememberAsyncImagePainter(meal.thumbnail),
            contentDescription = "MealImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .padding(top = 24.dp)
                .fillMaxWidth()
                .height(320.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(shimmerBrush(isImageLoading))
        )
    }
}


private fun LazyListScope.toolbar(
    meal: MealDetailsModel,
    isBookmarked: Boolean,
    onBackButtonClick: () -> Unit,
    onShareButtonClick: () -> Unit,
    onSaveButtonClick: () -> Unit,
) {


    item(key = "Toolbar") {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                icon = painterResource(id = R.drawable.ic_back),
                size = Size.S,
                cornerType = CornerType.CIRCULAR,
                onClick = onBackButtonClick
            )

            Text(
                text = stringResource(R.string.meal_details_title),
                style = AppTheme.typography.S1,
                modifier = Modifier.weight(1f)
            )

            var shouldShowShareButton by remember { mutableStateOf(false) }

// Toggle visibility when needed
            shouldShowShareButton = false  // Hide


            IconButton(
                icon = painterResource(id = R.drawable.ic_share),
                size = Size.S,
                cornerType = CornerType.CIRCULAR,
                onClick = onShareButtonClick,
                modifier = Modifier.alpha(if (shouldShowShareButton) 1f else 0f)

            )

            val iconResId =
                if (isBookmarked) R.drawable.ic_bookmark_fill else R.drawable.ic_bookmark

            CompositionLocalProvider(
                LocalContentColor provides Red900
            ) {
                IconButton(
                    icon = painterResource(id = iconResId),
                    size = Size.S,
                    cornerType = CornerType.CIRCULAR,
                    onClick = onSaveButtonClick
                )
            }
        }
    }
}

