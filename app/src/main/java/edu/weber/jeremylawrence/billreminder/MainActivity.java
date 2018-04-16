package edu.weber.jeremylawrence.billreminder;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import edu.weber.jeremylawrence.billreminder.adapters.BillListRecyclerAdapter;
import edu.weber.jeremylawrence.billreminder.model.Bill;
import edu.weber.jeremylawrence.billreminder.model.SelectedBill;

public class MainActivity extends AppCompatActivity
        implements BillListRecyclerAdapter.OnBillClickedListener,
                   SignInFragment.OnSignInClickedListener,
                   AddNewBillFragment.OnSaveClickedListener,
                   EditBillFragment.OnEditSaveClickedListener
{
    private static final int RC_SIGN_IN = 123;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String[] userName;
    private DatabaseReference mDatabase;
    private BillListFragment billListFragment;
    private Bill bill;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        billListFragment = (BillListFragment) fragmentManager.findFragmentById(R.id.billListFrag);
        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                addBill();
            }
        });
    }

    private void addBill()
    {
        AddNewBillFragment addNewBillFragment = new AddNewBillFragment();
        fragmentManager.beginTransaction()
                .add(android.R.id.content, addNewBillFragment)
                .addToBackStack("addNew")
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            case R.id.action_logout:
                AuthUI.getInstance()
                        .signOut(this).addOnCompleteListener(new OnCompleteListener<Void>()
                {
                    @Override
                    public void onComplete(@NonNull Task<Void> task)
                    {
                        currentUser = null;
                        if(billListFragment != null) {
                            billListFragment.clearList();
                        }
                        updateUI(currentUser);

                    }
                });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser); //fm get frag??

    }

    private void updateUI(FirebaseUser currentUser)
    {
        if (currentUser == null) {
            SignInFragment signInFragment = new SignInFragment();
            fragmentManager.beginTransaction()
                    .add(android.R.id.content, signInFragment)
                    .commit();
        } else {
            this.currentUser = currentUser;
            userName = this.currentUser.getDisplayName().split(" ");
            String userFirstName = userName[0];

            setTitle(userFirstName + "'s Bills");
        }
    }

    private void signIn()
    {
        setTitle("Bill Reminder");
        // Choose authentication providers
        List<AuthUI.IdpConfig> providers = Arrays.asList(
                new AuthUI.IdpConfig.EmailBuilder().build(),
                new AuthUI.IdpConfig.GoogleBuilder().build());

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN);
    }

    @Override
    public void onBillClicked(Bill bill)
    {
        SelectedBill.bill = bill;

        ViewBillDetailsFragment viewBillDetailsFragment = new ViewBillDetailsFragment();
        fragmentManager.beginTransaction()
                .setTransition(FragmentTransaction.TRANSIT_ENTER_MASK)
                .add(android.R.id.content, viewBillDetailsFragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void OnSignInClicked()
    {
        signIn();
    }

    @Override
    public void onSaveClicked(Bill bill)
    {
        mDatabase.child(currentUser.getUid()).child(bill.toString()).setValue(bill);
    }

    @Override
    public void onEditSaveClicked(Bill bill)
    {
        Toast.makeText(this, "Cannot update " + bill.toString() + ", Edit under maintenance",
                Toast.LENGTH_SHORT).show();
    }
}
