package com.example.barwhizv1.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.barwhizv1.model.CategoryDrinks
import com.example.barwhizv1.model.CategoryList
import com.example.barwhizv1.model.CocktailList
import com.example.barwhizv1.model.Drink
import com.example.barwhizv1.retrofit.RetroFitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private  var randomCocktailLiveData = MutableLiveData<Drink>()
    private var popularItemsLiveData = MutableLiveData<List<CategoryDrinks>>()

    fun getRandomCocktail(){
        RetroFitInstance.api.getRandomCocktail().enqueue(object : Callback<CocktailList> {
            override fun onResponse(call: Call<CocktailList>, response: Response<CocktailList>) {
                if(response.body() != null){
                    val randomCocktail: Drink = response.body()!!.drinks[0]
//                    Log.d("TEST", "drink id ${randomCocktail.idDrink} name ${randomCocktail.strDrink}")
                    randomCocktailLiveData.value = randomCocktail
                } else{
                    return
                }
            }

            override fun onFailure(call: Call<CocktailList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun getPopularItems(){
        RetroFitInstance.api.getPopularItems("Cocktail").enqueue(object : Callback<CategoryList>{
            override fun onResponse(call: Call<CategoryList>, response: Response<CategoryList>) {
                if(response.body() != null){
                    popularItemsLiveData.value = response.body()!!.drinks
                }
            }

            override fun onFailure(call: Call<CategoryList>, t: Throwable) {
                Log.d("HomeFragment", t.message.toString())
            }
        })
    }

    fun observeRandomCocktailLivedata():LiveData<Drink>{
        return randomCocktailLiveData
    }

    fun observePopularItemsLiveData():LiveData<List<CategoryDrinks>>{
        return popularItemsLiveData
    }
}