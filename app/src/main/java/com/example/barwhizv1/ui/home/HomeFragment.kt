package com.example.barwhizv1.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.barwhizv1.databinding.FragmentHomeBinding
import com.example.barwhizv1.model.Drink

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeMvvm:HomeViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        homeMvvm = ViewModelProviders.of(this)[HomeViewModel::class.java]
        homeMvvm = ViewModelProvider(this)[HomeViewModel::class.java]
        //Cia kasekas gali but
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

        homeMvvm.getRandomCocktail()
        observerRandomCocktail()

    }

    private fun observerRandomCocktail() {
        homeMvvm.observeRandomCocktailLivedata().observe(viewLifecycleOwner,object :
            Observer<Drink> {
            override fun onChanged(value: Drink) {
                Glide.with(this@HomeFragment)
                    .load(value!!.strDrinkThumb)
                    .into(binding.imgRandomCocktail)
            }
        })

    }
}