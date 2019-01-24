package com.securesoftbd.cco.bloodbonorlist;

import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.securesoftbd.cco.bloodbonorlist.Database.Quary;
import com.securesoftbd.cco.bloodbonorlist.Model.Donor;
import com.securesoftbd.cco.bloodbonorlist.Model.DonorOnline;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class DatabaseCheck extends JobService {

    //int firebasesSize = 0 , sqliteSize = 0;
    private List<String> firebaseId = new ArrayList<>();
    //private List<String> firebaseUnicKey = new ArrayList<>();
    private List<String> sqliteId = new ArrayList<>();
    //Context context = this;
    //Context mContext = DatabaseCheck.this;  //[] only specify the class name
    Quary quary=null;

    // = new Query(context);// = new Query(this);
    @Override
    public boolean onStartJob(JobParameters params) {
        // Intent service = new Intent(getApplicationContext(), LocalWordService.class);
        //getApplicationContext().startService(service);
        //Toast.makeText(this, "test", Toast.LENGTH_SHORT).show();


        Log.d("stop","save 1 ");
        String d= "have";
        FirebaseAuth  auth = FirebaseAuth.getInstance();//.getCurrentUser();
        String id = auth.getCurrentUser().getPhoneNumber();
        quary = new Quary(this);


        if(id != null)
        {
            Log.d("stop","save ");

            final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("User").child(id);

            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot)
                {


                    /*                 firebaseId.clear();
                   for(DataSnapshot dataSnapshot1 : dataSnapshot.getChildren())
                   {
                       Log.d("stop","firebase list test ");

                       String  donorOnline = dataSnapshot1.getValue(String.class);

                       firebaseId.add(donorOnline);
                       //  firebaseUnicKey.add(dataSnapshot1.getValue().toString());
                       //firebasesSize = firebasesSize++;
                       Log.d("stop","firebase list "+ donorOnline);
                   }


                    sqliteId = quary.getUserSize();

                    int update = 0;

                    Log.d("stop","size of list "+ firebaseId.size()+" "+ sqliteId.size());

                    for(int i = 0 ; i<sqliteId.size();i++)
                    {

                        for(int j = 0 ; j< firebaseId.size(); j++)
                        {
                            if(!sqliteId.get(i).equals( firebaseId.get(j)))
                            {
                                update = 1;
                            }

                        }

                        Log.d("stop"," update "+update);

                        if(update == 1)
                        {

                            List<Donor> allInfo = quary.getUser(sqliteId.get(i));
                            String unicId = databaseReference.getKey();

                            databaseReference.child(unicId).setValue(allInfo).addOnSuccessListener(new OnSuccessListener<Void>()
                            {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    //Toast.makeText(getApplicationContext(), "Added to the Firebase database", Toast.LENGTH_SHORT).show();

                                }
                            });//.child("df").setValue();
                            update = 0;
                        }

                    }

*/

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });




            }



      //      Log.d("stop","save 2 ");



        Log.d("stop","last");
     //   jobFinished(params,false);

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params)
    {
        Log.d("stop","stop");

        return false;
    }
}
