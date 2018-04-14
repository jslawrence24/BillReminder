package edu.weber.jeremylawrence.billreminder;


import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toolbar;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddNewBillFragment extends DialogFragment
{
    private static final String TAG = "AddNewBillFrg";
    private View rootView;


    public AddNewBillFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_add_new_bill, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbar);
        toolbar.setTitle("Add New Bill");

        return  rootView;
    }

}
