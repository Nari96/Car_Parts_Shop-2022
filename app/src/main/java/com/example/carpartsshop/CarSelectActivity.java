package com.example.carpartsshop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CarSelectActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner carBrandSpinner;
    Spinner carTypeSpinner;
    Spinner partTypeSpinner;
    private static final int SECRET_KEY = 8765;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_select);

        int secret_key = getIntent().getIntExtra("SECRET_KEY",0);
        if(secret_key != SECRET_KEY) finish();

        carBrandSpinner = findViewById(R.id.carBrandSpinner);
        carTypeSpinner = findViewById(R.id.carTypeSpinner);
        partTypeSpinner = findViewById(R.id.partTypeSpinner);

        carBrandSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> brandAdapter = ArrayAdapter.createFromResource(this, R.array.carBrand, android.R.layout.simple_spinner_item);
        brandAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carBrandSpinner.setAdapter(brandAdapter);

        carTypeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.bmwType, android.R.layout.simple_spinner_item);
        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        carTypeSpinner.setAdapter(typeAdapter);

        partTypeSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<CharSequence> partTypeAdapter = ArrayAdapter.createFromResource(this, R.array.partType, android.R.layout.simple_spinner_item);
        partTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        partTypeSpinner.setAdapter(partTypeAdapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            switch (adapterView.getId()){
                case R.id.carBrandSpinner:{
                    // kiolvasom a beállított spinner értéket
                    // beállítom a type spinnernek az új listát
                    ArrayAdapter<CharSequence> typeAdapter = ArrayAdapter.createFromResource(this, R.array.alfaRomeoType, android.R.layout.simple_spinner_item);
                     String selected_item  = carBrandSpinner.getSelectedItem().toString();
                     if(selected_item.equals("Alfa Romeo"))  typeAdapter = ArrayAdapter.createFromResource(this, R.array.alfaRomeoType, android.R.layout.simple_spinner_item);
                     if(selected_item.equals("Bmw"))  typeAdapter = ArrayAdapter.createFromResource(this, R.array.bmwType, android.R.layout.simple_spinner_item);
                     if(selected_item.equals("Ford"))  typeAdapter = ArrayAdapter.createFromResource(this, R.array.fordType, android.R.layout.simple_spinner_item);
                     if(selected_item.equals("Audi"))  typeAdapter = ArrayAdapter.createFromResource(this, R.array.audiType, android.R.layout.simple_spinner_item);
                     if(selected_item.equals("Dacia"))  typeAdapter = ArrayAdapter.createFromResource(this, R.array.daciaType, android.R.layout.simple_spinner_item);
                     if(selected_item.equals("Honda"))  typeAdapter = ArrayAdapter.createFromResource(this, R.array.hondaType, android.R.layout.simple_spinner_item);
                    typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    carTypeSpinner.setAdapter(typeAdapter);
                    break;
                }
                case R.id.carTypeSpinner:{
                    //do nothing
                }

            }

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void searchPart(View view) {
        //belerakom az intetbe a spinnerek ertekeit.
        String searchCarType  = carTypeSpinner.getSelectedItem().toString();
        String searchPartType = partTypeSpinner.getSelectedItem().toString();
        Intent intent = new Intent(this, PartListActivity.class);
        intent.putExtra("SECRET_KEY", SECRET_KEY);
        intent.putExtra("SEARCH_CAR_TYPE", searchCarType );
        intent.putExtra("SEARCH_PART_TYPE", searchPartType);
        startActivity(intent);
    }

    public void cancel(View view) {
        finish();
    }
}