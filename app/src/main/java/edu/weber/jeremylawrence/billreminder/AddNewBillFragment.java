package edu.weber.jeremylawrence.billreminder;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

import edu.weber.jeremylawrence.billreminder.model.Bill;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewBillFragment extends DialogFragment
{
    private static final String TAG = "AddNewBillFrg";
    private View rootView;
    private OnSaveClickedListener mCallback;
    private EditText edtBillName;
    private static EditText edtDueDate;
    private EditText edtAmount;

    public interface OnSaveClickedListener
    {
        public void onSaveClicked(Bill bill);
    }


    public AddNewBillFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            mCallback = (OnSaveClickedListener)activity;
        }catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnSaveClickedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_new_bill, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Add New Bill");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(android.R.drawable.ic_menu_close_clear_cancel);
        }
        setHasOptionsMenu(true);

        edtBillName = rootView.findViewById(R.id.edtBillName);
        edtDueDate  = rootView.findViewById(R.id.edtDate);
        edtAmount   = rootView.findViewById(R.id.edtAmount);

        Button btnDatePick = rootView.findViewById(R.id.btnDatePick);

        btnDatePick.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDatePickerDialog(v);
            }
        });

        return  rootView;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_add_bill, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        if (id == R.id.action_save){
//            Toast.makeText(getActivity(), "Aw, Snap! Unable to save right now  :(", Toast.LENGTH_SHORT).show();



            String billName = edtBillName.getText().toString();
            String dueDate = edtDueDate.getText().toString();
            String amount = edtAmount.getText().toString();

            Bill bill = new Bill(billName, dueDate, amount);

            mCallback.onSaveClicked(bill);

            dismiss();

        }else if (id == android.R.id.home){
            dismiss();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }

    public static void setEdtDueDate(String dueDate)
    {

    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {



        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            String dueDate = (month + 1) + "/" + day + "/" + year;
            edtDueDate.setText(dueDate);
        }
    }
}
