package com.example.cityfromjson

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cityfromjson.viewmodels.CitiesViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: CitiesViewModel
    private lateinit var adapter: CityAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        viewModel = ViewModelProvider(this).get(CitiesViewModel::class.java)

        val searchEditText = findViewById<EditText>(R.id.searchEditText)
        val citiesRecyclerView = findViewById<RecyclerView>(R.id.citiesRecyclerView)
        citiesRecyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.filteredCities.observe(this) { cities ->
            adapter = CityAdapter(cities) { city ->
                val gmmIntentUri = Uri.parse("geo:${city.coord.lat},${city.coord.lon}?q=${city.name}")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                startActivity(mapIntent)
            }
            citiesRecyclerView.adapter = adapter
        }

        searchEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.filterCities(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {}
        })


    }
}