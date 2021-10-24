package com.bochra.goff.ui.login;

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
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bochra.goff.DashboardFragment;
import com.bochra.goff.R;
import com.bochra.goff.ResetFragment;
import com.bochra.goff.ui.register.RegisterFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class LoginFragment extends Fragment {
    EditText mail, Pass;
    Button LoginBtn;
    TextView ret_register, ForgotPassword;
    FirebaseAuth mAuth;




    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_login, container, false);
        mail = root.findViewById ( R.id.email );
        Pass = root.findViewById ( R.id.password );
        mAuth = FirebaseAuth.getInstance ();
        LoginBtn = root.findViewById ( R.id.login_btn );// page profile
        ret_register = root.findViewById ( R.id.return_register );// page d'inscription
        ForgotPassword = root.findViewById ( R.id.forgotPassword );
        ////////Login
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login();
            }
        });
        ///Return Register
        ret_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.relativeLayout5,new RegisterFragment());
                fr.commit();

            }

        });
        ///////////Forget Password
        ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.relativeLayout5,new ResetFragment());
                fr.commit();
            }
        });



        return root;

    }

    private void login() {

            String email = mail.getText ().toString ().trim ();
            String password = Pass.getText ().toString ().trim ();
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
            }
            if (password.length () < 8) {
                Pass.setError ( "Min Password length is 8 characters" );
                Pass.requestFocus ();
                return;
            }
            // authenticate the user
            mAuth.signInWithEmailAndPassword ( email, password ).addOnCompleteListener ( new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful ()) {
                        FirebaseUser user=FirebaseAuth.getInstance ().getCurrentUser ();

                        if(user.isEmailVerified()){
                            FragmentTransaction fr = getFragmentManager().beginTransaction();
                            fr.replace(R.id.relativeLayout5,new DashboardFragment());
                            fr.commit();
                        }
                        else{
                            user.sendEmailVerification ();
                            Toast.makeText(getActivity(),"check your email",Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(getActivity(),"Failed to login !Please check your credentials",Toast.LENGTH_SHORT).show();
                    }
                }
            } );
        }
    }
