package com.example.tp3;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerCountries;
    ListView listViewCities;

    Map<String, List<String>> countryCityMap;
    ArrayAdapter<String> citiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        spinnerCountries = findViewById(R.id.spinner_countries);
        listViewCities = findViewById(R.id.listview_cities);

        // Données
        countryCityMap = new HashMap<>();
        countryCityMap.put("Maroc", Arrays.asList("Casablanca", "Rabat", "Marrakech"));
        countryCityMap.put("France", Arrays.asList("Paris", "Lyon", "Marseille"));
        countryCityMap.put("USA", Arrays.asList("New York", "Los Angeles", "Chicago"));

        // Spinner
        List<String> countries = new ArrayList<>(countryCityMap.keySet());
        ArrayAdapter<String> countryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, countries);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCountries.setAdapter(countryAdapter);

        // Listener Spinner
        spinnerCountries.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                String selectedCountry = countries.get(position);
                List<String> cities = countryCityMap.get(selectedCountry);
                citiesAdapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, cities);
                listViewCities.setAdapter(citiesAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {}
        });

        // Click sur une ville
        listViewCities.setOnItemClickListener((adapterView, view, position, id) -> {
            String city = (String) adapterView.getItemAtPosition(position);
            Intent intent = new Intent(MainActivity.this, CityDetailActivity.class);
            intent.putExtra("city_name", city);
            startActivity(intent);
        });
    }
}