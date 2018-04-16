package edu.weber.jeremylawrence.billreminder;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.weber.jeremylawrence.billreminder.adapters.BillListRecyclerAdapter;
import edu.weber.jeremylawrence.billreminder.model.Bill;


/**
 * A simple {@link Fragment} subclass.
 */
public class BillListFragment extends Fragment
{
    public static final String TAG = "BillListFrag";
    private View root;
    private RecyclerView rvBillList;
    private BillListRecyclerAdapter adapter;
    private DatabaseReference mDatabase;
    private List<Bill> allBills;
    private FirebaseUser currentUser;
    private OnBillListReady mCallback;

    private ValueEventListener billValueListener = new ValueEventListener()
    {
        @Override
        public void onDataChange(DataSnapshot dataSnapshot) { getAllBills(dataSnapshot); }

        @Override
        public void onCancelled(DatabaseError databaseError) { }
    };




    public interface OnBillListReady
    {
        public FirebaseUser getCurrentUser();
    }

    public BillListFragment()
    {
        // Required empty public constructor
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try{
            mCallback = (OnBillListReady)activity;
        } catch (ClassCastException e){
            throw new ClassCastException(activity.toString() + " must implement OnBillListReady");
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_bill_list, container, false);

        rvBillList = root.findViewById(R.id.rvBillList);

        adapter = new BillListRecyclerAdapter(new ArrayList<Bill>(),
                (BillListRecyclerAdapter.OnBillClickedListener)getActivity());

        rvBillList.setAdapter(adapter);
        rvBillList.setHasFixedSize(true);

        allBills = new ArrayList<>();

        return root;
    }

    @Override
    public void onResume()
    {
        super.onResume();
        currentUser = mCallback.getCurrentUser();
         setListener(currentUser);

    }

    public void setListener(FirebaseUser currentUser)
    {
        if (currentUser != null){
            mDatabase = FirebaseDatabase.getInstance().getReference(currentUser.getUid());

            mDatabase.addValueEventListener(new ValueEventListener()
            {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot)
                {
                    getAllBills(dataSnapshot);
                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            });
        }
    }

    public void getAllBills(DataSnapshot dataSnapshot)
    {
        allBills.clear();
        for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
            Bill bill = singleSnapshot.getValue(Bill.class);
            bill.setKey(dataSnapshot.getKey());
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
        adapter.notifyDataSetChanged();
    }

    public int getBillCount()
    {
        return adapter.getItemCount();
    }
}
