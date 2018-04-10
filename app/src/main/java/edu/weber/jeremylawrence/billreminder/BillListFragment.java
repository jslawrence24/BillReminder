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

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
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
    private DatabaseReference mDatabase;
    private List<Bill> allBills;


    public BillListFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_bill_list, container, false);

        allBills = new ArrayList<>();
        mDatabase = FirebaseDatabase.getInstance().getReference();
        rvBillList = (RecyclerView) root.findViewById(R.id.rvBillList);

        adapter = new BillListRecyclerAdapter(new ArrayList<Bill>(),
                (BillListRecyclerAdapter.OnBillClickedListener)getActivity());

        rvBillList.setAdapter(adapter);
        rvBillList.setHasFixedSize(true);

        mDatabase.addChildEventListener(new ChildEventListener()
        {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s)
            {
                getAllBills(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s)
            {
                getAllBills(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot)
            {
                deleteBill(dataSnapshot);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s)
            {
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
            }
        });


//
//        BillListViewModel billListViewModel = new BillListViewModel();
//
////        adapter.setBillList(billListViewModel.getBills());

        return root;
    }

    private void getAllBills(DataSnapshot dataSnapshot)
    {
        allBills.clear();
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            Bill bill = singleSnapshot.getValue(Bill.class);
            allBills.add(bill);
        }
//            adapter = new BillListRecyclerAdapter(allBills,
//                    (BillListRecyclerAdapter.OnBillClickedListener)getActivity() );
//            rvBillList.setAdapter(adapter);
        adapter.setBillList(allBills);
    }

    private void deleteBill(DataSnapshot dataSnapshot)
    {
        for (DataSnapshot ds : dataSnapshot.getChildren()) {
            Bill bill = ds.getValue(Bill.class);
            for (int i = 0; i < allBills.size(); i++) {
                if (allBills.get(i).getName().equals(bill)) {
                    allBills.remove(i);
                }
            }
            adapter.notifyDataSetChanged();
        }
    }

    public void clearList()
    {
        adapter.setBillList(new ArrayList<Bill>());
    }
}
