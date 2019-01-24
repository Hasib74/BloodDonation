package com.securesoftbd.cco.bloodbonorlist.AddActivity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.securesoftbd.cco.bloodbonorlist.DatabaseCheck;
import com.securesoftbd.cco.bloodbonorlist.Database.Quary;
import com.securesoftbd.cco.bloodbonorlist.Model.Donor;
import com.securesoftbd.cco.bloodbonorlist.R;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;


public class AddDonorActivity extends AppCompatActivity
{

    private EditText nameET,ageET,contractNumberET,addressET;
    private TextView lastDonationDateTV;
    private Spinner bloodGroupSPN;
    private Button doneBTN;
    private ImageView donorIV;

    private DatePickerDialog datePickerDialog;
    private Bitmap donorImage = null;
    private int CAMERA_REQUEAT_CODE = 10;
    private int GALLERY_REQUEST_CODE = 20;
    private String name,age,contractNumber,address,lastDonationDate,bloodGroup;

    private Quary quary;
    private String donorImageString = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_donor);
        init();
        onClick();

    }


    public String getEncoded64ImageStringFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 70, stream);
        byte[] byteFormat = stream.toByteArray();
        String imgString = Base64.encodeToString(byteFormat, Base64.NO_WRAP);
        return imgString;
    }

    private void onClick() {
        doneBTN.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v)
            {

              boolean verified = verifyFild();

              if(verified)
                {
                    ProgressDialog progressDialog = new ProgressDialog(AddDonorActivity.this);
                    progressDialog.setMessage("Waiting....");
                    progressDialog.show();
                    progressDialog.setCancelable(false);

                  //  String  image = getEncoded64ImageStringFromBitmap(donorImage);
                    progressDialog.dismiss();

                    Donor donor = new Donor(name, age, donorImageString, bloodGroup, contractNumber, lastDonationDate, address);

                    quary.InsertData(donor);
                    clearAll();
                    Alertdialog("Message","Insert Data Succesfully");

                    if(isNetworkAvailable())
                    {

                        String id =  FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
                        DatabaseReference firebaseDatabase = FirebaseDatabase.getInstance().getReference().child("User");

                        String unicId = firebaseDatabase.push().getKey();
                        String sqliteId = quary.getUserId();
                        donor = new Donor(sqliteId, name, age, donorImageString, bloodGroup, contractNumber, lastDonationDate, address);
                        firebaseDatabase.child(id).child(unicId).setValue(donor);

                    }

                    }
                }

        });

        donorIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CameraGalleryImageCapture();
            }
        });

        lastDonationDateTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar =Calendar.getInstance();
                int Year = calendar.get(Calendar.YEAR);
                int Month= calendar.get(Calendar.MONTH);
                int Day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(AddDonorActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth)
                    {

                        lastDonationDateTV.setText(dayOfMonth+"-"+(month+1)+"-"+year);

                    }
                },Year,Month,Day);

                datePickerDialog.show();
            }
        });

    }
    public boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
    private void clearAll() {
        //Clear All Add Attribute

        nameET.setText(null);
        ageET.setText(null);
        contractNumberET.setText(null);
        addressET.setText(null);
        lastDonationDateTV.setText(null);
        bloodGroupSPN.setId(0);

        Drawable drawable = getResources().getDrawable(R.drawable.profile_add_icon);
        donorImage =null ;
        donorIV.setImageBitmap(((BitmapDrawable)drawable).getBitmap());
        //donorIV.setImageBitmap();

    }

    private boolean verifyFild() {

        name = nameET.getText().toString();
        age = ageET.getText().toString();
        contractNumber = contractNumberET.getText().toString();
        address = addressET.getText().toString();
        lastDonationDate = lastDonationDateTV.getText().toString();
        bloodGroup = bloodGroupSPN.getSelectedItem().toString();

        if(name.isEmpty() || age.isEmpty() || contractNumber.isEmpty() || address.isEmpty() ||
                lastDonationDate.equals("dd-mm-yyyy") || bloodGroup.isEmpty() )
        {
            Alertdialog("Error !! ","Filup the all fild..");

        }
        else if(donorImage == null)
        {
                AlertDialog.Builder builder = new AlertDialog.Builder(AddDonorActivity.this);
                builder.setTitle("Are you sure ?");
                builder.setMessage("Donor profile set Default!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        defaultImage();
                        dialog.dismiss();
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        CameraGalleryImageCapture();
                        dialog.dismiss();
                    }
                });
            builder.show();

         //   return true;
        }
        else
        {
            return true;
        }

        return false;
        //return true;
    }

    private void defaultImage() {
        Drawable drawable = getResources().getDrawable(R.drawable.profile_icon);
        donorImage = ((BitmapDrawable)drawable).getBitmap();
        donorImageString =  getEncoded64ImageStringFromBitmap(donorImage);
        donorIV.setImageBitmap(donorImage);
    }

    private void CameraGalleryImageCapture() {
      /*  AlertDialog.Builder imageCaptureDialog = new AlertDialog.Builder(AddDonorActivity.this);
        final AlertDialog imageDialog = imageCaptureDialog.create();*/

      //  LinearLayout layoutBottomSheet;
       // final TextView btnBottomSheet = view.findViewById(R.id.btnBottomSheet);
        //BottomSheetBehavior sheetBehavior;

        //View view1 = getLayoutInflater().inflate(R.layout., null);
        View imageCaptureDialogView = getLayoutInflater().inflate(R.layout.view_alertdialog_camera_gallary_design,null,false);

        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(imageCaptureDialogView);
        dialog.show();

        //imageCaptureDialog.setView(imageCaptureDialogView);




        //imageDialog.show();

        ImageView camera,gallery;

        camera = imageCaptureDialogView.findViewById(R.id.getCameraImageId);
        gallery = imageCaptureDialogView.findViewById(R.id.getGalleryImageId);

        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent,CAMERA_REQUEAT_CODE);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                Intent galleryIntent = new Intent(Intent.ACTION_PICK);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent,GALLERY_REQUEST_CODE);
            }
        });
    }

    private void Alertdialog(String title, String message) {

        AlertDialog.Builder dialog = new AlertDialog.Builder(AddDonorActivity.this);
        dialog.setTitle(title);
        dialog.setMessage(message);

        //dialog.show();

        final AlertDialog alertDialog = dialog.create();
        alertDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                alertDialog.dismiss();
            }
        },1500);
    }

    private void init() {
        quary = new Quary(this);
        nameET = findViewById(R.id.nameETId);
        ageET = findViewById(R.id.ageETId);
        contractNumberET = findViewById(R.id.contractETId);
        addressET = findViewById(R.id.addressETId);
        lastDonationDateTV = findViewById(R.id.lastDonatedateTVId);
        doneBTN = findViewById(R.id.doneBTNId);
        bloodGroupSPN = findViewById(R.id.spinnerId);
        donorIV = findViewById(R.id.donorImageId);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_REQUEAT_CODE && resultCode == Activity.RESULT_OK) {
            donorImage = (Bitmap) data.getExtras().get("data");
           donorIV.setImageBitmap(donorImage);
           donorImageString =  getEncoded64ImageStringFromBitmap(donorImage);
        }
        else if(requestCode == GALLERY_REQUEST_CODE && resultCode == Activity.RESULT_OK)
        {

            Uri imgaeUri = data.getData();

            try {
                InputStream imageStream = getContentResolver().openInputStream(imgaeUri);
                donorImage  = BitmapFactory.decodeStream(imageStream);
                donorIV.setImageBitmap(donorImage);
                donorImageString =  getEncoded64ImageStringFromBitmap(donorImage);
                //setImageVar.setImageBitmap(bitmap);
            } catch (FileNotFoundException e)
            {
                e.printStackTrace();
            }
        }
        else if(donorImage == null)
        {
            defaultImage();
        }
    }
}
