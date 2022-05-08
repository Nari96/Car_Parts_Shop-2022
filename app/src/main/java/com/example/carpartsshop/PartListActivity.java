package com.example.carpartsshop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.res.TypedArray;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

public class PartListActivity extends AppCompatActivity {
    private static final String LOG_TAG = PartListActivity.class.getName();
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private RecyclerView mReciRecyclerView;
    private ArrayList<ShoppingPart> mPartList;
    private ShoppingPartAdapter mAdapter;
    private int gridNumber = 1;
    private FrameLayout circle;
    private TextView shopcounter;

    private int cartItems = 0;

    private String carType;
    private String partType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_part_list);

         carType =  getIntent().getStringExtra("SEARCH_CAR_TYPE");
         partType = getIntent().getStringExtra("SEARCH_PART_TYPE");

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        Log.i(LOG_TAG, "firebase current user: "+ user );

        if(user == null) finish();
        mReciRecyclerView = findViewById(R.id.recyclerView);
        mReciRecyclerView.setLayoutManager(new GridLayoutManager(this, gridNumber));
        mPartList = new ArrayList<>();
        mAdapter = new ShoppingPartAdapter(this, mPartList);
        mReciRecyclerView.setAdapter(mAdapter);
        initalizeData();

    }

    private void initalizeData() {
        String[] partList;
        String[] partsInfo;
        String[] price;
        TypedArray partPicture;

        partList = getResources().getStringArray(R.array.null_name);
        partsInfo = getResources().getStringArray(R.array.null_info);
        price = getResources().getStringArray(R.array.null_price);
        partPicture = getResources().obtainTypedArray(R.array.null_pictureRes);

        switch(carType){
            case "147":
                if(partType.equals("Felfüggesztés")){
                    partList = getResources().getStringArray(R.array.Alfa_147_suspension_name);
                    partsInfo = getResources().getStringArray(R.array.Alfa_147_suspension_info);
                    price = getResources().getStringArray(R.array.Alfa_147_suspension_price);
                    partPicture = getResources().obtainTypedArray(R.array.Alfa_147_suspension_pictureRes);
                    break;
                }else if(partType.equals("Fék")){
                    partList = getResources().getStringArray(R.array.Alfa_147_brake_name);
                    partsInfo = getResources().getStringArray(R.array.Alfa_147_brake_info);
                    price = getResources().getStringArray(R.array.Alfa_147_brake_price);
                    partPicture = getResources().obtainTypedArray(R.array.Alfa_147_brake_pictureRes);
                    break;
                }else if(partType.equals("Motor")){
                    partList = getResources().getStringArray(R.array.Alfa_147_motor_name);
                    partsInfo = getResources().getStringArray(R.array.Alfa_147_motor_info);
                    price = getResources().getStringArray(R.array.Alfa_147_motor_price);
                    partPicture = getResources().obtainTypedArray(R.array.Alfa_147_motor_pictureRes);
                    break;
                }else if(partType.equals("Kipufogó")){
                    partList = getResources().getStringArray(R.array.Alfa_147_exhaust_name);
                    partsInfo = getResources().getStringArray(R.array.Alfa_147_exhaust_info);
                    price = getResources().getStringArray(R.array.Alfa_147_exhaust_price);
                    partPicture = getResources().obtainTypedArray(R.array.Alfa_147_exhaust_pictureRes);
                    break;
                }
            case "156":
                break;
            case "Giulia":
                break;
            case "Mito":
                break;
            case "E36":
                break;
            case "E39":
                break;
            case "E46":
                break;
            case "E90":
                break;


            //... itt az osszes tipust meg kene valositani de nincs ertelme hanem felhoalapuva kellene tenni....//

        }
        mPartList.clear();

        for(int i = 0; i< partList.length;i++){
            mPartList.add(new ShoppingPart(partList[i],partsInfo[i],price[i], partPicture.getResourceId(i,0)) );
            Log.i(LOG_TAG, "partlist: "+ partList[i] + "partsInfo[i]" + partsInfo[i] + "price[i]"+price[i]);
        }
        partPicture.recycle();
        mAdapter.notifyDataSetChanged();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.part_list_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.Prod_code_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem) ;
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                //do nothing
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                mAdapter.getFilter().filter(s);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.logOut:
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(PartListActivity.this, "Kijelentkezve" , Toast.LENGTH_LONG).show();
                finish();
                // TODO:  Őt meghívó Activity is finish.
                return true;
            case R.id.cart:
                //Adatok  Shared Peferences valtozba mentese
                //TODO: uj Activity ahol itemek listazasa + összeg + megrendeles gomb -> kuldes felhobe
                Toast.makeText(PartListActivity.this, "Kosár megnyitása (még nem készült el)" , Toast.LENGTH_LONG).show();
                return true;
            default:
                return  super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        final MenuItem alertMenuItem = menu.findItem(R.id.cart);
        FrameLayout rootView = (FrameLayout) alertMenuItem.getActionView();

        circle = (FrameLayout) rootView.findViewById(R.id.view_alert_red_circle);
        shopcounter = (TextView) rootView.findViewById(R.id.view_alert_count_textview);

        rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onOptionsItemSelected(alertMenuItem);
            }
        });


        return super.onPrepareOptionsMenu(menu);
    }
    public void updateAlertIcon(){
        cartItems = cartItems + 1;
        shopcounter.setText(String.valueOf(cartItems));

    }
}