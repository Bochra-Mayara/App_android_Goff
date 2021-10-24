package com.bochra.goff.ui.register;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.bochra.goff.R;
import com.bochra.goff.ResetFragment;
import com.bochra.goff.ui.login.LoginFragment;
import com.bochra.goff.user;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class RegisterFragment extends Fragment {
    private EditText Name, mail,Pass , phon;
    private Button RegisterBtn;
    private TextView LoginBtn;
    private FirebaseAuth mAuth;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_register, container, false);
        Name = root.findViewById( R.id.fullName );
        mail = root.findViewById ( R.id.email );
        Pass = root.findViewById ( R.id.password );
        phon = root.findViewById ( R.id.phone );
        RegisterBtn = root.findViewById ( R.id.Register_btn );
        LoginBtn = root.findViewById ( R.id.return_login );

        //
        mAuth = FirebaseAuth.getInstance ();

        ////Reggister
        RegisterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register();
            }
        });
        ///Return Login
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.relativeLayout7,new LoginFragment());
                fr.commit();
            }
        });




        return root;
    }

    private void startActivity(Class<? extends View.OnClickListener> aClass) {
        getActivity();
    }

    private void register() {

            // Take the value of 4 edit texts in Strings
            final String fullName = Name.getText ().toString ().trim ();
            final String email = mail.getText ().toString ().trim ();
            final String password = Pass.getText ().toString ().trim ();
            final String phone = phon.getText ().toString ().trim ();
            // Validations for input email -password -fullName-phone number
            if (fullName.isEmpty ()) {
                Name.setError ( "FullName is required" );
               Name.requestFocus ();
                return;
            }
            if (fullName.length () < 8) {
                Name.setError ( "Min FullName length is 8 characters" );
                Name.requestFocus ();
                return;
            }
            if (email.isEmpty ()) {
                mail.setError ( "Email is required" );
                mail.requestFocus ();
                return;
            }
            if (!Patterns.EMAIL_ADDRESS.matcher ( email ).matches ()) {
                mail.setError ( "Please proved valid email" );
                return;
            }
            if (password.isEmpty ()) {
                Pass.setError ( "Password is required" );
                Pass.requestFocus ();
                return;
            }
            if (password.length () < 8) {
                Pass.setError ( "Min Password length is 8 characters" );
               Pass.requestFocus ();
                return;
            }
            if (phone.isEmpty ()) {
                phon.setError ( "Phone is required" );
                phon.requestFocus ();
                return;
            }
            if (phone.length () < 8) {
                phon.setError ( "Min Phone length is 8 numbers" );
                phon.requestFocus ();
                return;
            }

            //REGISTER USER IN FIREBASE
            mAuth.createUserWithEmailAndPassword ( email, password ).addOnCompleteListener ( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful ()) {    //REGISTER VALUES IN FIREBASE DATABASE IN "USERS"
                        user User = new user( fullName, phone,email);
                        FirebaseDatabase.getInstance ().getReference ( "Users" ).child ( FirebaseAuth.getInstance ().getCurrentUser ().getUid () )
                                .setValue ( User ).addOnCompleteListener ( new OnCompleteListener<Void> () {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful ()) {
                                    Toast.makeText(getActivity(),"user has been registered successfully",Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(getActivity(),"Failed to register! Try again",Toast.LENGTH_SHORT).show();// Registration failed


                                }
                            }
                        });
                    } else {     // Registration failed

                        Toast.makeText(getActivity(),"Failed to register!",Toast.LENGTH_SHORT).show();
                    }
                }
            } );
        }
    }


