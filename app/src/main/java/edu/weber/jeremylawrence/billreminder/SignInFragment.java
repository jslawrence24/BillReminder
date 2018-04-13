package edu.weber.jeremylawrence.billreminder;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SignInFragment extends DialogFragment
{
    private static final String TAG = "SignInFrag";
    private View rootView;
    private OnSignInClickedListener mCallback;

    public interface OnSignInClickedListener
    {
        public void OnSignInClicked();
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        try {
            mCallback = (OnSignInClickedListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement OnSignInClickedListener");
        }
    }

    public SignInFragment()
    {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_sign_in, container, false);

        Toolbar toolbar = rootView.findViewById(R.id.toolbarSignIn);
        toolbar.setTitle("Bill Reminder");


        Button btnSignIn = rootView.findViewById(R.id.btnSignIn);
        btnSignIn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                mCallback.OnSignInClicked();
                dismiss();
            }
        });

        return rootView;
    }

}
