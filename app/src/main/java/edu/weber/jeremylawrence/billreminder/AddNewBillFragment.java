package edu.weber.jeremylawrence.billreminder;


import android.app.Activity;
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
import android.widget.EditText;

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

            EditText edtBillName = rootView.findViewById(R.id.edtBillName);
            EditText edtDueDate = rootView.findViewById(R.id.edtDate);
            EditText edtAmount = rootView.findViewById(R.id.edtAmount);

            String billName = edtBillName.getText().toString();
            String dueDate = edtDueDate.getText().toString();
            String amount = edtAmount.getText().toString();


            //TODO PUT ALL THIS INSIDE THE BILL MODEL, GET RID OF DAYS TO TO FIELD AND MAKE IT A METHOD THAT MUST BE CALLED IN THE ADAPTER TO GET ACCURATE VALE IN REAL TIME
            String[] sDate = dueDate.split("/");

            int year = Integer.parseInt(sDate[2]);
            int month = Integer.parseInt(sDate[0]) - 1;
            int day = Integer.parseInt(sDate[1]);

            Calendar calendar = Calendar.getInstance();
            Calendar now = Calendar.getInstance();
            calendar.set(year, month, day);
//            now.set(now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DATE));

            Date date = now.getTime();
            Date date2 = calendar.getTime();

            long mi1liDD = calendar.getTimeInMillis();
            long milliNow = now.getTimeInMillis();

            long milliDiff = mi1liDD - milliNow;

            long days = (milliDiff / (1000 * 60 * 60 * 24)) + 1;

//            int daysToDue = (int)((Calendar.getInstance().getTimeInMillis() - calendar.getTimeInMillis()) / (24*60*60*1000));

            if (days > 2){
                System.out.print(days);
            }

            Bill bill = new Bill(billName, dueDate, "", amount);
            bill.setDaysToDue(String.valueOf(days));

            mCallback.onSaveClicked(bill);

            dismiss();

        }else if (id == android.R.id.home){
            dismiss();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
}
