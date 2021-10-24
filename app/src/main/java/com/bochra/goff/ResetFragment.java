package com.bochra.goff;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;


public class ResetFragment extends Fragment {
    private EditText mail;
    private Button ResetPassword;
    FirebaseAuth auth;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_reset, container, false);
        mail = root.findViewById ( R.id.email );
        auth = FirebaseAuth.getInstance ();
        ResetPassword = root.findViewById ( R.id.ResetPassword );
        ResetPassword.setOnClickListener ( new View.OnClickListener () {
            @Override
            public void onClick (View view) {
                String email = mail.getText ().toString ().trim ();
                if (email.isEmpty ()) {
                    mail.setError ( "Email is required! " );
                    mail.requestFocus ();
                    return;
                }
                if (!Patterns.EMAIL_ADDRESS.matcher ( email ).matches ()) {
                    mail.setError ( "Please proved valid email" );
                    return;
                }
                auth.sendPasswordResetEmail ( email ).addOnCompleteListener ( new OnCompleteListener<Void> () {
                    @Override
                    public void onComplete (@NonNull Task<Void> task) {
                        if (task.isSuccessful ()) {
                            Toast.makeText(getActivity(),"heck your Email to reset your password!",Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getActivity(),"tray again! Something wrong happened!",Toast.LENGTH_SHORT).show();
                        }
                    }

                } );
            }
        } );

        return root;
    }
    }
