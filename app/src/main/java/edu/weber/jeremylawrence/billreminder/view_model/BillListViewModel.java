/*
package edu.weber.jeremylawrence.billreminder.view_model;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.util.Log;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import edu.weber.jeremylawrence.billreminder.model.Bill;


public class BillListViewModel extends ViewModel
{
    private DatabaseReference mDatabase;
    private List<Bill> bills;

    public List<Bill> getBills()
    {
        if (bills == null){
            mDatabase = FirebaseDatabase.getInstance().getReference();



            ChildEventListener childEventListener = new ChildEventListener()
            {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String s)
                {
                    Bill bill = dataSnapshot.getValue(Bill.class);
                    bill.setKey(dataSnapshot.getKey());
                    bills.add(bill);
                }

                @Override
                public void onChildChanged(DataSnapshot dataSnapshot, String s)
                {

                }

                @Override
                public void onChildRemoved(DataSnapshot dataSnapshot)
                {

                }

                @Override
                public void onChildMoved(DataSnapshot dataSnapshot, String s)
                {

                }

                @Override
                public void onCancelled(DatabaseError databaseError)
                {

                }
            };

            mDatabase.addChildEventListener(childEventListener);
        }

        return bills;
    }
}
*/
