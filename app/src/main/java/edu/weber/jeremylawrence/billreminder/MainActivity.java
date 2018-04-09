package edu.weber.jeremylawrence.billreminder;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;

import edu.weber.jeremylawrence.billreminder.adapters.BillListRecyclerAdapter;
import edu.weber.jeremylawrence.billreminder.model.Bill;

public class MainActivity extends AppCompatActivity
        implements BillListRecyclerAdapter.OnBillClickedListener
{
    private static final int RC_SIGN_IN = 123;
    private FragmentManager fragmentManager;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String[] userName;
    private DatabaseReference mDatabase;
    int counter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fragmentManager = getSupportFragmentManager();
        mDatabase = FirebaseDatabase.getInstance().getReference();

        mAuth = FirebaseAuth.getInstance();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                counter++;
                String billName = "Bill " + counter;
                Bill bill = new Bill(billName, "Date", "length", "$$$");
                mDatabase.child(currentUser.getUid()).child(billName).setValue(bill);
//                Toast.makeText(MainActivity.this, "Add new bill, coming soon!", Toast.LENGTH_LONG).show();
            }
        });
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
                mAuth.signOut();
                currentUser = null;
                updateUI(currentUser);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void updateUI(FirebaseUser currentUser)
    {
        if (currentUser == null) {
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

            //TODO GET SIGN IN RESULT, APP DOESN'T EXIT IF USER BACKS OUT OF LOGIN
        } else {
            this.currentUser = currentUser;
            userName = this.currentUser.getDisplayName().split(" ");
            String userFirstName = userName[0];

            setTitle(userFirstName + "'s Bills");
        }
    }

    @Override
    public void onBillClicked(Bill bill)
    {
        //TODO BILL CLICKED EVENT
        Toast.makeText(this, "You clicked a bill!", Toast.LENGTH_LONG).show();
    }
}
