package com.example.barwhizv1.retrofit

import com.example.barwhizv1.model.CocktailList
import retrofit2.Call
import retrofit2.http.GET

interface CocktailApi {

    @GET("random.php")
    fun getRandomCocktail():Call<CocktailList>
}