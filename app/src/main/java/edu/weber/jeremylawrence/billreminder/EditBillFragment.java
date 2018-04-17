package edu.weber.jeremylawrence.billreminder;


import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import edu.weber.jeremylawrence.billreminder.model.Bill;
import edu.weber.jeremylawrence.billreminder.model.SelectedBill;


/**
 * A simple {@link Fragment} subclass.
 */
public class EditBillFragment extends DialogFragment
{
    private static final String TAG = "EditBillFrag";
    private View rootView;
    private EditText edtBillName;
    private static TextInputEditText edtDate;
    private EditText edtAmount;
    private static Date dueDate;
    private OnEditSaveClickedListener mCallback;

    private static SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy",
            Locale.getDefault());
    private Toolbar toolbar;

    public interface OnEditSaveClickedListener
    {
        void onEditSaveClicked(Bill bill);
    }


    public EditBillFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            mCallback = (OnEditSaveClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() +
                    " must implement OnEditSaveClickedListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        rootView = inflater.inflate(R.layout.fragment_add_new_bill, container, false);

        toolbar = rootView.findViewById(R.id.toolbarAdd);
        toolbar.setTitle("");

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.ic_close_white_24dp);
        }
        setHasOptionsMenu(true);

        edtBillName = rootView.findViewById(R.id.edtBillName);
        edtAmount = rootView.findViewById(R.id.edtAmount);

        edtDate = rootView.findViewById(R.id.edtDate);

        edtDate.setOnClickListener(new TextInputLayout.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                showDatePickerDialog(v);
            }
        });

        return rootView;
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
        Bill bill = SelectedBill.bill;

        if (id == R.id.action_save) {
            String billName = edtBillName.getText().toString();
            String amount = edtAmount.getText().toString();

            // Update bill
            bill.setName(billName);
            bill.setAmount(amount);

            if (dueDate != null) {
                bill.setDue_date(dueDate);
            } else {
                try {
                    Date date = dateFormat.parse(edtDate.getText().toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

            if (billName.equals("")) {
                Toast.makeText(getContext(), "Bill Name Required", Toast.LENGTH_LONG).show();
            }
            mCallback.onEditSaveClicked(bill);

            dismiss();
            getFragmentManager().popBackStack();
            return true;

        } else if (id == android.R.id.home) {
            dismiss();
            getFragmentManager().popBackStack();

            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        setFields();
    }

    private void setFields()
    {
        Bill bill = SelectedBill.bill;

        edtBillName.setText(bill.toString());
        edtAmount.setText(bill.getAmount());
        edtDate.setText(dateFormat.format(bill.getDue_date()));
    }

    private void showDatePickerDialog(View v)
    {
        DialogFragment newFragment = new EditBillFragment.DatePickerFragment();
        newFragment.show(getFragmentManager(), "datePicker");
    }


    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener
    {
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState)
        {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day)
        {
            dueDate = new Date(year - 1900, month, day);
            String date = dateFormat.format(dueDate);
            edtDate.setText(date);
        }
    }
}
