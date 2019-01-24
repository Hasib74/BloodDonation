package com.securesoftbd.cco.bloodbonorlist.ViewActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.securesoftbd.cco.bloodbonorlist.Adaptar.DonorViewAdepter;
import com.securesoftbd.cco.bloodbonorlist.Database.Quary;
import com.securesoftbd.cco.bloodbonorlist.Model.Donor;
import com.securesoftbd.cco.bloodbonorlist.R;

import java.util.ArrayList;
import java.util.List;

public class ViewActivity extends AppCompatActivity {

    private Spinner spinner;
    private EditText editText;
    private TextView availableDonorTV;
    private Button viewProfileBTN;
    private RecyclerView recyclerView;
    private List<Donor> donorList;
    private DonorViewAdepter donorViewAdepter;
    private Quary quary;
    private String blood = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        inti();
        blood = getIntent().getStringExtra("BLOODGROUP");
        DataShow();

        onClick();

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        DataShow();
    }

    private void DataShow() {

        try {

            availableDonorTV.setBackgroundResource(R.drawable.available_donor_background_design);

            if (blood == null) {


                donorList = quary.getDonorDBdata();

            } else {
                spinner.setVisibility(View.GONE);
                availableDonorTV.setText("Available Donor List");
                donorList = quary.getAvalableBloodGroupDetails(blood);
            }

        } catch (Exception e) {

        }
        donorViewAdepter = new DonorViewAdepter(donorList, this);
        recyclerView.setAdapter(donorViewAdepter);
    }

    private void searchDataShow(String search, boolean blood) {

        try {


            if (blood) {

                donorList = quary.searchByBlood(search);

            } else {
                //spinner.setVisibility(View.GONE);
                donorList = quary.searchByName(search);
            }

        } catch (Exception e) {

        }
        donorViewAdepter = new DonorViewAdepter(donorList, this);
        recyclerView.setAdapter(donorViewAdepter);
    }

    private void onClick() {
       /* viewProfileBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewActivity.this,ProfileViewActivity.class));
            }
        });*/

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    String blood = spinner.getSelectedItem().toString();
                    searchDataShow(blood, true);
                } else {
                    DataShow();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count != 0) {

                    searchDataShow(s.toString(), false);
                } else {

                    DataShow();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


    }

    private void inti() {
        availableDonorTV = findViewById(R.id.availableDonorTVId);
        donorList = new ArrayList<>();
        quary = new Quary(this);
        spinner = findViewById(R.id.search_by_blood_spinner_id);
        editText = findViewById(R.id.search_by_name_editText_id);
        recyclerView = findViewById(R.id.viewRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //   viewProfileBTN = findViewById(R.id.viewProfileBTNId);
    }
}
