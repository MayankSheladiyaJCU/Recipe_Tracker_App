package com.mayank122.recipe_tracker_app.data

import com.mayank122.recipe_tracker_app.R
import com.mayank122.recipe_tracker_app.model.CategoryByCountry
import com.mayank122.recipe_tracker_app.model.CookingTip
import com.mayank122.recipe_tracker_app.model.TrendingRecipe

object utils {

    val categories = listOf(
        CategoryByCountry("Italian", R.drawable.dimsum),
        CategoryByCountry("Chinese", R.drawable.spaghetti),
        CategoryByCountry("Indian", R.drawable.samosa),
        CategoryByCountry("Canadian", R.drawable.nepali),
        CategoryByCountry("Mexican", R.drawable.mexican),
        CategoryByCountry("American", R.drawable.american),
        CategoryByCountry("Thai", R.drawable.thaifood),
        CategoryByCountry("Japanese", R.drawable.japanese)
    )

    fun getTrendingRecipes(): List<TrendingRecipe> {
        return listOf(
            TrendingRecipe(
                53065,
                "Sushi",
                "Japanese Seafood",
                "https://www.themealdb.com/images/media/meals/g046bb1663960946.jpg"
            ),
            TrendingRecipe(
                53010,
                "Lamb Tzatziki Burgers",
                "Greek Lamb",
                "https://www.themealdb.com/images/media/meals/k420tj1585565244.jpg"
            ),
            TrendingRecipe(
                53033,
                "Rice",
                "Japanese Side",
                "https://www.themealdb.com/images/media/meals/kw92t41604181871.jpg"
            )

        )
    }
    fun getCookingTips(): List<CookingTip> {
        return listOf(
            CookingTip(
                52874,
                "Beef and Mustard Pie",
                "Toss the beef and flour together in a bowl with some salt and black pepper",
                "https://www.themealdb.com/images/media/meals/sytuqu1511553755.jpg"
            ),
            CookingTip(
                52908,
                "Ratatouille",
                "Cut the aubergines in half lengthways. Place them on the board, cut side down, slice in half lengthways again and then across",
                "https://www.themealdb.com/images/media/meals/wrpwuu1511786491.jpg"
            )
        )
    }


}