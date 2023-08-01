package com.example.barwhizv1.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.barwhizv1.activities.CocktailActivity
import com.example.barwhizv1.adapters.MostPopularAdapter
import com.example.barwhizv1.databinding.FragmentHomeBinding
import com.example.barwhizv1.model.CategoryDrinks
import com.example.barwhizv1.model.Drink

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel
    private lateinit var randomCocktail:Drink
    private lateinit var popularItemsAdapter:MostPopularAdapter

    companion object{
        const val COCKTAIL_ID = "com.example.barwhizv1.ui.home.idCocktail"
        const val COCKTAIL_NAME = "com.example.barwhizv1.ui.home.nameCocktail"
        const val COCKTAIL_THUMB = "com.example.barwhizv1.ui.home.thumbCocktail"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]

        popularItemsAdapter = MostPopularAdapter()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preparePopularItemsRecyclerView()
        homeMvvm.getRandomCocktail()
        observerRandomCocktail()

        onRandomCocktailClick()

        homeMvvm.getPopularItems()
        observePopularItemsLiveData()

        onPopularItemClick()
    }

    private fun onPopularItemClick() {
        popularItemsAdapter.onItemClick = { drink->
           val intent = Intent(activity,CocktailActivity::class.java)
            intent.putExtra(COCKTAIL_ID, drink.idDrink)
            intent.putExtra(COCKTAIL_NAME, drink.strDrink)
            intent.putExtra(COCKTAIL_THUMB, drink.strDrinkThumb)
            startActivity(intent)
        }
    }

    private fun preparePopularItemsRecyclerView() {
        binding.recViewDrinksPopular.apply{
            layoutManager = LinearLayoutManager(activity, LinearLayoutManager.HORIZONTAL,false)
            adapter = popularItemsAdapter
        }
    }

    private fun observePopularItemsLiveData() {
        homeMvvm.observePopularItemsLiveData().observe(viewLifecycleOwner
        ) { drinkList ->
            popularItemsAdapter.setDrinks(drinksList = drinkList as ArrayList<CategoryDrinks>)
        }
    }

    private fun onRandomCocktailClick() {
        binding.cwRandomDrink.setOnClickListener {
            val intent = Intent(activity,CocktailActivity::class.java)
            intent.putExtra(COCKTAIL_ID, randomCocktail.idDrink)
            intent.putExtra(COCKTAIL_NAME, randomCocktail.strDrink)
            intent.putExtra(COCKTAIL_THUMB, randomCocktail.strDrinkThumb)
            startActivity(intent)
        }
    }

    private fun observerRandomCocktail() {
        homeMvvm.observeRandomCocktailLivedata().observe(viewLifecycleOwner
        ) { drink ->
            Glide.with(this@HomeFragment)
                .load(drink!!.strDrinkThumb)
                .into(binding.imgRandomCocktail)

            this.randomCocktail = drink
        }

    }
}