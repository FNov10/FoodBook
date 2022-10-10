/*Foodbook Mainactivity.java class
 *Version 1.0
 * 26/8/2022
 * Initializes all the activies, list views, dialog fragment and arrayadapters
 */

package com.example.foodbook;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements AddFoodFragment.OnFragmentInteractionListener,EditFoodFragment.OnFragmentInteractionListener {
    private static final String TAG = "MainActivitty";
    private TextView mDisplayDate;

    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private int total;
    ListView foodList;
    ArrayAdapter<Food> foodAdapter;
    ArrayList<Food>  dataList;
    private TextView displayTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        foodList = findViewById(R.id.food_list);
        final AlertDialog.Builder builder;
        displayTotal = (TextView) findViewById(R.id.Total_cost);

        /*Creating empty foodAdapter to initialize*/
        builder = new AlertDialog.Builder(this);
        dataList = new ArrayList<>();
        foodAdapter = new CustomList(this,dataList);
        foodList.setAdapter(foodAdapter);



        /*Once the add button is clicked, our new food Fragment is added based on input details*/
        final FloatingActionButton addFoodButton = findViewById(R.id.add_food_button);
        addFoodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new AddFoodFragment().show(getSupportFragmentManager(),"Add_food");



            }

        });

        final ListView foodList = findViewById(R.id.food_list);
        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //makeToast(dataList.get(i).getBestbefore());
            }
        });

        /*Clicking on any item of list view allowing it to edit the details of the current food object*/
        foodList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                int index = i;
                Food selectedfood = dataList.get(i);
                //makeToast(String.valueOf(selectedfood.CalculateTotal()));
                //makeToast(selectedfood.getLocation())

                //Recalculating total
                int oldTotal = selectedfood.getCount()*selectedfood.getUnitCost();
                selectedfood.editTotalCost(oldTotal);
                new EditFoodFragment(selectedfood).show(getSupportFragmentManager(),"Edit food");

            }
        });

        /*Long click to remove a food*/
        foodList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                builder.setMessage("Do you want to Delete "+ foodAdapter.getItem(i).getDescription() + "?")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                Food removal = foodAdapter.getItem(i);

                                //Recalculating total
                                int oldTotal = removal.getCount()*removal.getUnitCost();
                                removal.editTotalCost(oldTotal);
                                DeleteFood(removal);
                                int totalbefore = Integer.parseInt( displayTotal.getText().toString());
                                int newtotal = totalbefore-oldTotal;
                                displayTotal.setText(String.valueOf(newtotal));

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();

                            }
                        });
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Delete Confirmation");
                alert.show();

                return true;
            }
        });

    }

    /*Adds the passed food object to the arrayAdapter*/
    @Override
    public void onOkPressed(Food newFood){
        foodAdapter.add(newFood);


    }

    /*Edits the current food Object*/
    public void onOkkPressed(Food newFood,String description, String location, int count, int unitCost){

        newFood.setDescription(description);
        newFood.setLocation(location);
        newFood.setCount(count);
        newFood.setUnitCost(unitCost);



    }
    Toast t;
    private void makeToast(String s){
        if (t != null) t.cancel();
        t = Toast.makeText(getApplicationContext(),s,Toast.LENGTH_SHORT);
        t.show();

    };

    //Deletes food from arrayadapter
    public void DeleteFood(Food food){
        foodAdapter.remove(food);
    }


}