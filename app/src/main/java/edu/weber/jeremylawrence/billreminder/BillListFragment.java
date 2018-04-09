package edu.weber.jeremylawrence.billreminder;


import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.weber.jeremylawrence.billreminder.adapters.BillListRecyclerAdapter;
import edu.weber.jeremylawrence.billreminder.model.Bill;
import edu.weber.jeremylawrence.billreminder.view_model.BillListViewModel;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillListFragment extends Fragment
{
    private View root;
    private RecyclerView rvBillList;
    private BillListRecyclerAdapter adapter;


    public BillListFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        root =  inflater.inflate(R.layout.fragment_bill_list, container, false);
        rvBillList = (RecyclerView)root.findViewById(R.id.rvBillList);

        adapter = new BillListRecyclerAdapter(new ArrayList<Bill>(),
                (BillListRecyclerAdapter.OnBillClickedListener)getActivity());

        rvBillList.setAdapter(adapter);
        rvBillList.setHasFixedSize(true);

        BillListViewModel billListViewModel = new BillListViewModel();

//        adapter.setBillList(billListViewModel.getBills());

        return root;
    }

}
