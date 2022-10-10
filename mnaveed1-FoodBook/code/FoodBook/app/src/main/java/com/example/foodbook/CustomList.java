/*CustomList.java class
 *Version 1.0
 * 26/8/2022
 * This is our custom ArrayAdapter so that we can hold a food object inside the arrayadapter
 */

package com.example.foodbook;

import static com.example.foodbook.R.id.bestBefore_editText;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CustomList extends ArrayAdapter<Food> {
    private static final String TAG = "CustomList";
   // private TextView mDisplayDate;
    //private DatePickerDialog.OnDateSetListener mDateSetListener;
    private ArrayList<Food> foods;
    private Context context;

    public CustomList( Context context, ArrayList<Food> foods) {
        super(context, 0,foods);
        this.foods = foods;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        //return super.getView(position, convertView, parent);
        View view = convertView;
        if(view == null){
            view = LayoutInflater.from(context).inflate(R.layout.content,parent,false);

        }
        Food food = foods.get(position);

        TextView FoodName = view.findViewById(R.id.FoodName);
        TextView FoodCount = view.findViewById(R.id.FoodCount);
        TextView unitCost = view.findViewById(R.id.UnitCost);

        /*Ensuring the listview updates with the current food objects details*/
        FoodName.setText(food.getDescription());
        FoodCount.setText(String.valueOf("Qty: " + food.getCount()));
        unitCost.setText("$"+String.valueOf(food.getUnitCost()));



        return view;


    }
}
