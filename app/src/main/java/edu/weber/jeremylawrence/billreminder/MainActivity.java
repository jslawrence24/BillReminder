package edu.weber.jeremylawrence.billreminder;

import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.math.BigDecimal;
import java.util.Date;

import edu.weber.jeremylawrence.billreminder.Model.Bill;

public class MainActivity extends AppCompatActivity
{
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        fragmentManager = getSupportFragmentManager();


        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Toast.makeText(MainActivity.this, "You pressed the add FAB", Toast.LENGTH_SHORT).show();
            }
        });

        // Set up recycler view to push data into
        Bill bill = new Bill("Discover Card", "4/18/2018", "0", "$120");
    }
}
