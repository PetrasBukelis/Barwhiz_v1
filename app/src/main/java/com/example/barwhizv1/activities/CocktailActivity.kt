package com.example.barwhizv1.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.barwhizv1.R
import com.example.barwhizv1.databinding.ActivityCocktailBinding
import com.example.barwhizv1.model.Drink
import com.example.barwhizv1.ui.home.CocktailViewModel
import com.example.barwhizv1.ui.home.HomeFragment
import com.example.barwhizv1.ui.home.HomeViewModel

class CocktailActivity : AppCompatActivity() {
    private lateinit var cocktailId:String
    private lateinit var cocktailName:String
    private lateinit var cocktailThumb:String
    private lateinit var binding:ActivityCocktailBinding
    private lateinit var cocktailMvvm:CocktailViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCocktailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cocktailMvvm = ViewModelProvider(this)[CocktailViewModel::class.java]

        getCocktailInformationFromIntent()

        setInformationInViews()
        loadingCase()

        cocktailMvvm.getCocktailDetail(cocktailId)
        observeCocktailDetailsLiveData()
    }

    private fun observeCocktailDetailsLiveData() {
        cocktailMvvm.observeCocktailDetailLiveData().observe(this, object : Observer<Drink>{
            override fun onChanged(value: Drink) {
                onResponseCase()
                val cocktail = value

                binding.tvCategory.text = "Category : ${cocktail!!.strCategory}"
                binding.tvType.text = "Type : ${cocktail.strAlcoholic}"
                binding.tvInstructionsSteps.text = "Ingredients: " + getIngredientsWithMeasurements(cocktail) + "\nHow to make it: " + cocktail.strInstructions
            }
        })
    }

    private fun getIngredientsWithMeasurements(cocktail: Drink): String {
        val ingredientsWithMeasurements = mutableListOf<String>()

        val drinkClass = cocktail.javaClass
        for (i in 1..15) {
            val ingredientField = drinkClass.getDeclaredField("strIngredient$i")
            ingredientField.isAccessible = true
            val ingredient = ingredientField.get(cocktail) as? String

            val measurementField = drinkClass.getDeclaredField("strMeasure$i")
            measurementField.isAccessible = true
            val measurement = measurementField.get(cocktail) as? String

            if (!ingredient.isNullOrBlank() && !measurement.isNullOrBlank()) {
                ingredientsWithMeasurements.add("$ingredient - $measurement")
            }
        }

        return ingredientsWithMeasurements.joinToString(", ")
    }

    private fun setInformationInViews() {
        Glide.with(applicationContext)
            .load(cocktailThumb)
            .into(binding.imgCocktailDetail)

        binding.collapsingToolbar.title = cocktailName
        binding.collapsingToolbar.setCollapsedTitleTextColor(ContextCompat.getColor(this,R.color.white))
        binding.collapsingToolbar.setExpandedTitleColor(ContextCompat.getColor(this,R.color.white))
    }

    private fun getCocktailInformationFromIntent() {
        val intent = intent
        cocktailId = intent.getStringExtra(HomeFragment.COCKTAIL_ID)!!
        cocktailName = intent.getStringExtra(HomeFragment.COCKTAIL_NAME)!!
        cocktailThumb = intent.getStringExtra(HomeFragment.COCKTAIL_THUMB)!!
    }

    private fun loadingCase(){
        binding.progressBar.visibility = View.VISIBLE
        binding.btnAddToFav.visibility = View.INVISIBLE
        binding.tvInstructions.visibility = View.INVISIBLE
        binding.tvCategory.visibility = View.INVISIBLE
        binding.tvType.visibility = View.INVISIBLE
    }

    private fun onResponseCase(){
        binding.progressBar.visibility = View.INVISIBLE
        binding.btnAddToFav.visibility = View.VISIBLE
        binding.tvInstructions.visibility = View.VISIBLE
        binding.tvCategory.visibility = View.VISIBLE
        binding.tvType.visibility = View.VISIBLE
    }
}  