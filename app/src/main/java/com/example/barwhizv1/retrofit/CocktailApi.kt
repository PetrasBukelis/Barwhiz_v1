package com.example.barwhizv1.retrofit

import com.example.barwhizv1.model.CategoryList
import com.example.barwhizv1.model.CocktailList
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CocktailApi {

    @GET("random.php")
    fun getRandomCocktail():Call<CocktailList>

    @GET("lookup.php?")
    fun getCocktailDetails(@Query("i") id:String) : Call<CocktailList>

    @GET("filter.php?")
    fun getPopularItems(@Query("c") categoryName:String) : Call<CategoryList>
}