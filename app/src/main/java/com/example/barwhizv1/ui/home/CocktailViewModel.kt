package com.example.barwhizv1.ui.home

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.barwhizv1.activities.CocktailActivity
import com.example.barwhizv1.model.CocktailList
import com.example.barwhizv1.model.Drink
import com.example.barwhizv1.retrofit.RetroFitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CocktailViewModel():ViewModel() {
    private var cocktailDetailsLiveData = MutableLiveData<Drink>()

    fun getCocktailDetail(id:String){
        RetroFitInstance.api.getCocktailDetails(id).enqueue(object : Callback<CocktailList>{
            override fun onResponse(call: Call<CocktailList>, response: Response<CocktailList>) {
                if(response.body()!=null){
                    cocktailDetailsLiveData.value = response.body()!!.drinks[0]
                } else {
                    return
                }
            }

            override fun onFailure(call: Call<CocktailList>, t: Throwable) {
                Log.d("CocktailActivity", t.message.toString())
            }
        })
    }

    fun observeCocktailDetailLiveData():LiveData<Drink>{
        return cocktailDetailsLiveData
    }
}