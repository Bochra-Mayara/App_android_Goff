package com.bochra.goff.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bochra.goff.R;
import com.bochra.goff.ui.login.LoginFragment;
import com.bochra.goff.ui.register.RegisterFragment;
import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {
    ProgressBar progressBar;
    Button bt_home;
    private FirebaseAuth auth;



    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_home, container, false);
        bt_home = root.findViewById ( R.id.btn_home);
        progressBar = root.findViewById(R.id.progressbar);
        progressBar.setVisibility(View.VISIBLE);
        auth = FirebaseAuth.getInstance ();

        bt_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fr = getFragmentManager().beginTransaction();
                fr.replace(R.id.home1,new RegisterFragment());
                fr.commit();
            }
        });


        return root;
    }
}