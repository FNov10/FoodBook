/*EditFoodFragment.java class
 *Version 1.0
 * 26/8/2022
 * This class creates a custom dialog fragment to edit a food object using multiple editTexts
 */



package com.example.foodbook;

import static com.example.foodbook.R.id.bestBefore_editText;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import java.text.DecimalFormat;
import java.util.Calendar;


public class EditFoodFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    private EditText Description;
    private EditText Count;
    private EditText unitcost;
    private Food food;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private TextView displayTotal;
    private String bestbeforee;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Toast t;


    /*Constructor to pass through the current foodObject for editing*/
    public EditFoodFragment(Food food) {
        this.food = food;

    }


    public EditFoodFragment() {
    }

    private OnFragmentInteractionListener listener;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /*Once the dialog box is closed, this function edits the current food objects*/
    public interface OnFragmentInteractionListener{
        void onOkkPressed(Food newFood,String Description, String location, int count, int unitcosr);

    }

    @Override public void onAttach(Context context){
        super.onAttach(context);
        if(context instanceof OnFragmentInteractionListener){
            listener = (OnFragmentInteractionListener) context;

        }else{
            throw new RuntimeException(context.toString()
                    + "Must implement onfragmentinteractionlistener");
        }

    }
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState){
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.add_food_fragment_layout,null);
        Description = view.findViewById(R.id.FoodDescription_editText);
        Count = view.findViewById(R.id.FoodCount_editText);
        unitcost = view.findViewById(R.id.UnitCost_editText);

        /*Setting up the spinner to edit*/
        spinner = view.findViewById(R.id.Location_editText);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.loctions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
        setCancelable(false);

        /*Making sure the editText fiels have the current food Object details typed in already*/
        Description.setText(food.getDescription());
        Count.setText(String.valueOf(food.getCount()));
        unitcost.setText(String.valueOf(food.getUnitCost()));
        displayTotal = (TextView) getActivity().findViewById(R.id.Total_cost);


        /*Setting up the datePickerDialog*/
        mDisplayDate=  view.findViewById(bestBefore_editText);

        mDisplayDate.setText(food.getBestbefore());
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);

                int month = cal.get(Calendar.MONTH);

                ///month = Integer.parseInt(monthFormatted);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year,month,day);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();

            }

        });

        /*Setting up formatting for the Date*/
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                DecimalFormat formatter = new DecimalFormat("00");
                String dayFormatted = formatter.format(day);

                month = month +1;
                String monthFormatted = formatter.format(month);

                String date = year+"-"+ monthFormatted + "-" + dayFormatted  ;
                bestbeforee = date;
                mDisplayDate.setText(date);
                food.setBestbefore(date);

            }
        };

        /*Making sure our spinner remembers the current food fragment object details*/
        int pantry;
        int freezer;
        int fridge;
        int pos = 0;
        if (food.getLocation().toString().equals("Pantry")){
            pos =0;
        }
        if (food.getLocation().toString().equals("Freezer")){
            pos =1;
        }
        if (food.getLocation().toString().equals("Fridge")){
            pos =2;
        }
        spinner.setSelection(pos);

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Edit Food")

                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        food.CalculateTotal();
                        displayTotal.setText(String.valueOf(food.getTotalCost()));

                        displayTotal.setText(String.valueOf(food.getTotalCost()));


                    }
                })

                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        String location =spinner.getSelectedItem().toString();

                        //int bestbefore = Integer.parseInt(bestBefore.getText().toString());

                        /*Validating the editTexts*/
                        if (TextUtils.isEmpty(Description.getText().toString())|| Description.length()>30  ){
                            Description.setError(null);
                            food.CalculateTotal();
                            displayTotal.setText(String.valueOf(food.getTotalCost()));
                            makeToast("ERROR: Description is empty");
                            displayTotal.setText(String.valueOf(food.getTotalCost()));
                            //food.CalculateTotal();


                            displayTotal.setText(String.valueOf(food.getTotalCost()));

                        }

                        else if (TextUtils.isEmpty(Count.getText().toString())){
                            Count.setError(null);
                            makeToast("ERROR: Count is empty");
                            food.CalculateTotal();


                            displayTotal.setText(String.valueOf(food.getTotalCost()));
                        }

                        else if (TextUtils.isEmpty(unitcost.getText().toString())){
                            unitcost.setError(null);
                            makeToast("ERROR: UnitCost is empty");
                            food.CalculateTotal();


                            displayTotal.setText(String.valueOf(food.getTotalCost()));
                        }

                        else{
                            String descroption = Description.getText().toString();
                            //ensuring cost always rounded up
                            double costD = Double.parseDouble(unitcost.getText().toString());
                            int count = Integer.parseInt(Count.getText().toString());
                            int unitcount = (int) Math.ceil(costD);
                            Food newFood = new Food(descroption, location,count,unitcount);
                            listener.onOkkPressed( food, descroption, location,count,unitcount);
                            food.CalculateTotal();
                            int total = newFood.getTotalCost();

                            displayTotal.setText(String.valueOf(food.getTotalCost()));
                        }


                    }
                }).create();



    }


    private void makeToast(String s){
        if (t != null) t.cancel();
        t = Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_SHORT);
        t.show();

    };
}