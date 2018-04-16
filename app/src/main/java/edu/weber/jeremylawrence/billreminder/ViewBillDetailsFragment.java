package edu.weber.jeremylawrence.billreminder;


import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;

import edu.weber.jeremylawrence.billreminder.model.Bill;
import edu.weber.jeremylawrence.billreminder.model.SelectedBill;


/**
 * A simple {@link Fragment} subclass.
 */
public class ViewBillDetailsFragment extends DialogFragment
{
    private static final String TAG = "ViewBillDetailsFrag";
    private Toolbar toolbar;
    private FloatingActionButton fabedit;
    private TextView txvDate, txvRepeat, txvAmount, txvNotification;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE, MMMM dd");
    private View rootView;



    public ViewBillDetailsFragment()
    {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_view_bill_details, container, false);

        toolbar = rootView.findViewById(R.id.toolbarDetails);

        txvDate = rootView.findViewById(R.id.txv_details_date);
        txvRepeat = rootView.findViewById(R.id.txv_details_repeat);
        txvAmount = rootView.findViewById(R.id.txv_details_amount);
        txvNotification = rootView.findViewById(R.id.txv_details_notification);

        fabedit = rootView.findViewById(R.id.fabEdit);
        fabedit.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                EditBillFragment editBillFragment = new EditBillFragment();
                getFragmentManager().beginTransaction()
                        .replace(android.R.id.content, editBillFragment)
                        .addToBackStack("editBill")
                        .commit();
            }
        });

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);

        ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }
        setHasOptionsMenu(true);

        return rootView;
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
        toolbar.setTitle(bill.toString());
        txvDate.setText(dateFormat.format(bill.getDue_date()));
        txvRepeat.setText("Repeats Monthly (Sample)");      //TODO vIEW DETAILS REPEAT

        LinearLayout amntLayout = rootView.findViewById(R.id.layout_detils_amount);

        String amnt = bill.getAmount();
        if (amnt == null) {
            amntLayout.setVisibility(LinearLayout.GONE);
        } else {
            txvAmount.setText(bill.getAmount());
        }

        txvNotification.setText("1 Week Before (Sample)");      //TODO VIEW DETAILS NOTIFICATION
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        return dialog;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        menu.clear();
        getActivity().getMenuInflater().inflate(R.menu.menu_view_bill_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch (id){
            case R.id.action_delete:
                Toast.makeText(getActivity(), "Delete is under maintenance",
                        Toast.LENGTH_SHORT).show();
                dismiss();
                getFragmentManager().popBackStack();
                return true;
            case android.R.id.home:
                dismiss();
                getFragmentManager().popBackStack();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
