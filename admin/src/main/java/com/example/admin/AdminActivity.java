package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.common.MedCentre;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;


import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fa;
    MedCentre[] arr = {new MedCentre("Capital Hospital",5),
            new MedCentre("SUM Hospital",7)};
    ArrayList<MedCentre> medCentreArrayList = new ArrayList<MedCentre>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        //Adding elements
        medCentreArrayList.add(new MedCentre("Capital Hospital",3));
        medCentreArrayList.add(new MedCentre("SUM Hospital",4));

        //RecyclerView & CustomAdapter in use
        recyclerView =findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter ad = new CustomAdapter(medCentreArrayList);
        recyclerView.setAdapter(ad);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        //Floating Action Button to add
        fa = findViewById(R.id.add_alarm_fab);
        fa.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("Add slots");

                View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_layout,null);
                builder.setView(customLayout);
                EditText editText1 = findViewById(R.id.edCentre);
                EditText editText2 = findViewById(R.id.edSlots);
                final String[] centreName = new String[1];
                final String[] slots = new String[1];
                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        centreName[0] = charSequence.toString();
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });
                editText2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                        slots[0] = charSequence.toString();
                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                    }
                });

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int slotsNum = Integer.parseInt(slots[0]);
                        if (centreName[0]!=null && slots[0]!=null)
                            medCentreArrayList.add(new MedCentre(centreName[0],slotsNum));
                        ad.notifyDataSetChanged();
                    }
                });
                builder.create().show();
            }
        });
    }
}