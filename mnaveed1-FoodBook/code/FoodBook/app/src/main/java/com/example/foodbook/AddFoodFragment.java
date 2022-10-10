/*addFoodFragment.java class
 *Version 1.0
 * 26/8/2022
 * This class creates a custom dialog fragment to add a food object using multiple editTexts
 *
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


public class AddFoodFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
    public MainActivity test;

    private EditText foodDescription;
    private EditText foodCount;
    private EditText unitCost;
    private String bestbeforee;
    private TextView bestBefore;
    private Food food;
    private Context context;
    private Spinner spinner;
    private ArrayAdapter<CharSequence> adapter;
    private TextView displayTotal;
    public static int total;
    private TextView mDisplayDate;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    Toast t;





    public AddFoodFragment(){}

    public AddFoodFragment(Context context){
        this.context = context.getApplicationContext();
    }

    private OnFragmentInteractionListener listener;

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println(2);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    /*Executes onOKPressed once dialog fragment OK is pressed*/
    public interface  OnFragmentInteractionListener{
        void onOkPressed(Food newFood);
    }
    //public AddFoodFragment(Context context){}

    @Override public void onAttach(Context context) {

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
        //new AddFoodFragment(context);
        //final AddFoodFragment[] test = {new AddFoodFragment()};
        foodDescription = view.findViewById(R.id.FoodDescription_editText);
        foodCount = view.findViewById(R.id.FoodCount_editText);
        unitCost = view.findViewById(R.id.UnitCost_editText);
        bestBefore = view.findViewById(R.id.bestBefore_editText);

        /*Initializing the spinner to have the three possible locations*/
        spinner = view.findViewById(R.id.Location_editText);
        adapter = ArrayAdapter.createFromResource(getActivity(), R.array.loctions, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        //Layoutnflater lf = getActivity().getLayoutInflater();
        //View view2 = LayoutInflater.from(getActivity()).inflate(R.layout.activity_main,null);


        displayTotal = (TextView) getActivity().findViewById(R.id.Total_cost);

        /*Initializing the datepicker dialog to set the bestbeforeDate*/
        mDisplayDate=  view.findViewById(bestBefore_editText);
        mDisplayDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);

                int month = cal.get(Calendar.MONTH);

                ///month = Integer.parseInt(monthFormatted);
                int day = cal.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog dialog = new DatePickerDialog(getActivity(), android.R.style.Theme_Holo_Dialog_MinWidth, mDateSetListener, year,month,day);
                //dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();



            }

        });

        /*Setting up the format for the BestbeforeDate as specified by question*/
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                DecimalFormat formatter = new DecimalFormat("00");
                String dayFormatted = formatter.format(day);

                month = month +1;
                String monthFormatted = formatter.format(month);

                String date = year+"-"+ monthFormatted + "-" + dayFormatted  ;


                mDisplayDate.setText(date);

                bestbeforee = date;

            }
        };


        //Our custom Alertdialog
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        return builder
                .setView(view)
                .setTitle("Add Food")
                .setNegativeButton("Cancel",null)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String Description = foodDescription.getText().toString();


                        String location =spinner.getSelectedItem().toString();
                        //int bestbefore = Integer.parseInt(bestBefore.getText().toString());

                        /*Validating the text fields to make sure theyre not empty and design specifics*/
                        if (TextUtils.isEmpty(foodDescription.getText().toString())|| Description.length()>30  ){
                            foodDescription.setError(null);
                            makeToast("ERROR: Description is empty");

                        }

                        else if (TextUtils.isEmpty(foodCount.getText().toString())){
                            foodCount.setError(null);
                            makeToast("ERROR: Count is empty");
                        }

                        else if (TextUtils.isEmpty(unitCost.getText().toString())){
                            unitCost.setError(null);
                            makeToast("ERROR: UnitCost is empty");
                        }




                        /*If no validation error, then create the alert dialog*/
                        else{
                            double costD = Double.parseDouble(unitCost.getText().toString());
                            int count = Integer.parseInt(foodCount.getText().toString());
                            int unitcount = (int) Math.ceil(costD);
                            Food newFood = new Food(Description,bestbeforee, location,count,unitcount);//populating our food object
                            listener.onOkPressed(newFood);
                            newFood.CalculateTotal();//total cost
                            total = newFood.getTotalCost();
                            Food food = new Food();
                            //Toast toast=Toast.makeText(getActivity().getApplicationContext(),String.valueOf(food.getTotalCost()),Toast.LENGTH_SHORT);


                            displayTotal.setText(String.valueOf(food.getTotalCost()));







                        }

                    }
                }).create();



    }


    // simple make Toast function \
    private void makeToast(String s){
        if (t != null) t.cancel();
        t = Toast.makeText(getActivity().getApplicationContext(),s,Toast.LENGTH_SHORT);
        t.show();

    };






    ;
}
