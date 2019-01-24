package com.securesoftbd.cco.bloodbonorlist.AvailableActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import com.securesoftbd.cco.bloodbonorlist.Adaptar.AvailableBloodGroupAdapter;
import com.securesoftbd.cco.bloodbonorlist.Database.Quary;
import com.securesoftbd.cco.bloodbonorlist.Model.Donor;
import com.securesoftbd.cco.bloodbonorlist.R;
import java.util.ArrayList;
import java.util.List;

public class AvailableDonorActivity extends AppCompatActivity {

    private Button viewAvailableBTN;
    private RecyclerView recyclerView;
    private List<Donor> donorList;
    private AvailableBloodGroupAdapter adapter;
    private Quary quary;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_donor);

        init();

    }


    private void init()
    {
        recyclerView = findViewById(R.id.availableBloodGroupRecyclerViewId);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        quary = new Quary(this);

        donorList = new ArrayList<>();
        donorList = quary.getAvalableBloodGroup();

        adapter = new AvailableBloodGroupAdapter(donorList,this);
        recyclerView.setAdapter(adapter);

     //   viewAvailableBTN = findViewById(R.id.viewAvailableBTNId);
    }
}