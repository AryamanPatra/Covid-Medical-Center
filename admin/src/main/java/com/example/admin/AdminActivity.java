package com.example.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.common.MedCentre;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import java.util.ArrayList;

public class AdminActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton fa1,fa2;
    ImageButton imb;
    MedCentre[] arr = {new MedCentre("Capital Hospital",5),
            new MedCentre("SUM Hospital",7)};
    ArrayList<MedCentre> medCentreArrayList = new ArrayList<MedCentre>();
    int size;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);

        //Storing data using GSon
        SharedPreferences sp = getSharedPreferences("MyPref",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        Gson gson = new Gson();


        //RecyclerView & CustomAdapter in use
        recyclerView =findViewById(R.id.recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        CustomAdapter ad = new CustomAdapter(medCentreArrayList);
        recyclerView.setAdapter(ad);
        recyclerView.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL));

        //Floating Action Button to add
        fa1 = findViewById(R.id.add_element);
        fa1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){

                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("Add slots");

                View customLayout = getLayoutInflater().inflate(R.layout.alert_dialog_layout,null);
                builder.setView(customLayout);
                EditText editText1 = customLayout.findViewById(R.id.edCentre);
                EditText editText2 = customLayout.findViewById(R.id.edSlots);
                final String[] centreName = new String[1];
                final String[] slots = new String[1];

                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        centreName[0] = editable.toString();
                    }
                });
                editText2.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        slots[0] = editable.toString();
                    }
                });

                builder.setPositiveButton("Add", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        int slotsNum = Integer.parseInt(slots[0]);
                        if (centreName[0]!=null && slots[0]!=null){
                            medCentreArrayList.add(new MedCentre(centreName[0],slotsNum));
                            size = medCentreArrayList.size();
                            String json = gson.toJson(medCentreArrayList.get(size-1));
                            ed.putString((size-1)+"",json);
                            ed.putInt("length",size);
                            ed.commit();
                        }
                        ad.notifyDataSetChanged();
                    }
                });
                builder.create().show();
            }
        });

        //Floating Action Button to del
        fa2 = findViewById(R.id.del_element);
        fa2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(AdminActivity.this);
                builder.setTitle("Delete Slots");

                View customLayout = getLayoutInflater().inflate(R.layout.del_alert_layout,null);
                builder.setView(customLayout);
                EditText editText1 = customLayout.findViewById(R.id.edDelCentre);
                final String[] centreName = new String[1];

                editText1.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {
                        centreName[0] = editable.toString();
                    }
                });

                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        size = medCentreArrayList.size();
                        if (centreName[0]!=null){
                            for(int j=0; j< size;j++){
                                String mcName = medCentreArrayList.get(j).getCentreName();
                                if (mcName.equals(centreName[0])){
                                    medCentreArrayList.remove(j);
                                    sp.edit().remove(j+"").commit();
                                    int retrievedSize = sp.getInt("length",0);
                                    for (int k=j; k<retrievedSize-1; k++){
                                        String json = sp.getString((k+1)+"","");
                                        ed.putString(k+"",json);
                                        ed.commit();
                                    }
                                    ed.putInt("length",--retrievedSize);
                                    ed.commit();
                                    ad.notifyDataSetChanged();
                                    break;
                                }
                            }
                        }
                    }
                });
                builder.create().show();
            }
        });

        //Deleting Data using Image Button
        View temp = getLayoutInflater().inflate(R.layout.data_row,null);
        imb = temp.findViewById(R.id.delData);
        imb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                imb.setTag(0);
                Toast.makeText(AdminActivity.this,"Clicked",Toast.LENGTH_SHORT).show();
            }
        });

        //Retrieving data when app launched
        int retrievedSize = sp.getInt("length",0);
        for (int i=0; i<retrievedSize; i++){
            String json = sp.getString(i+"","");
            MedCentre mc = gson.fromJson(json,MedCentre.class);
            if (mc==null)
                Log.d("f","It's null");
            medCentreArrayList.add(mc);
            ad.notifyDataSetChanged();
        }
    }
}