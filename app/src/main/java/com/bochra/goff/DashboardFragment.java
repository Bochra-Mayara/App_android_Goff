package com.bochra.goff;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


public class DashboardFragment extends Fragment {
    DatabaseReference ref;
    FirebaseDatabase database;
    TextView Valuedash1,Valuedash2;
    String valeur1,valeur2;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_dashboard, container, false);
        database = FirebaseDatabase.getInstance ();
        Valuedash1 = root.findViewById ( R.id.VAL1);
        Valuedash2 = root. findViewById ( R.id.VAL2);
        ref =FirebaseDatabase.getInstance().getReference();

        ref.addValueEventListener ( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot ) {
                valeur1= dataSnapshot.child( "valeur/ultrason" ).getValue ().toString ();
                Valuedash1.setText (valeur1);
                Valuedash1.setTextColor(Color.BLUE);

                valeur2= dataSnapshot.child( "valeur/infrarouge" ).getValue ().toString ();
                Valuedash2.setText (valeur2);
                Valuedash2.setTextColor(Color.BLUE);

            }
            @Override
            public void onCancelled( DatabaseError error) {
                // Failed to read value
                Toast.makeText (getActivity(), "FAILED TO READ DATABASE", Toast.LENGTH_SHORT ).show ();
                ;
            }
        } );

        return  root;
    }
}