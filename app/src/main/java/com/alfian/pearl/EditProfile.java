package com.alfian.pearl;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class EditProfile extends AppCompatActivity {
    ImageView xpic_profile;
    EditText xpassword, xemail, xphone, xfirstname, xlastname, xaddress;
    TextView xusername;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    Button button_simpan, btn_add_new_photo;
    LinearLayout button_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        xpic_profile = findViewById(R.id.xpic_profile);
        btn_add_new_photo  = findViewById(R.id.btn_add_new_photo);

        button_simpan = findViewById(R.id.button_simpan);
        button_back = findViewById(R.id.button_back);

        xusername = findViewById(R.id.xusername);
        xpassword = findViewById(R.id.xpassword);
        xemail = findViewById(R.id.xemail);
        xphone = findViewById(R.id.xphone);
        xfirstname = findViewById(R.id.xfirstname);
        xlastname = findViewById(R.id.xlastname);
        xaddress = findViewById(R.id.xaddress);

        getUsernameLocal();

        reference = FirebaseDatabase.getInstance().getReference()
                .child("customer").child(username_key_new).child("info_customer");
        storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);

        reference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot)
            {
                xusername.setText(dataSnapshot.child("username").getValue().toString());
                xpassword.setText(dataSnapshot.child("password").getValue().toString());
                xemail.setText(dataSnapshot.child("email").getValue().toString());
                xphone.setText(dataSnapshot.child("phone").getValue().toString());
                xfirstname.setText(dataSnapshot.child("firstname").getValue().toString());
                xlastname.setText(dataSnapshot.child("lastname").getValue().toString());
                xaddress.setText(dataSnapshot.child("address").getValue().toString());
                Picasso.with(EditProfile.this)
                        .load(dataSnapshot.child("url_photo_profile")
                                .getValue().toString()).centerCrop().fit()
                        .into(xpic_profile);
            }

            @Override
            public void onCancelled(DatabaseError databaseError)
            {
                //kosong
            }
        });


        button_simpan.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {

                //ubah state menjadi loading
                button_simpan.setEnabled(false);
                button_simpan.setText("Loading...");

                reference.addListenerForSingleValueEvent(new ValueEventListener()
                {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot)
                    {
                        dataSnapshot.getRef().child("username").setValue(xusername.getText().toString());
                        dataSnapshot.getRef().child("password").setValue(xpassword.getText().toString());
                        dataSnapshot.getRef().child("email").setValue(xemail.getText().toString());
                        dataSnapshot.getRef().child("phone").setValue(xphone.getText().toString());
                        dataSnapshot.getRef().child("firstname").setValue(xfirstname.getText().toString());
                        dataSnapshot.getRef().child("lastname").setValue(xlastname.getText().toString());
                        dataSnapshot.getRef().child("address").setValue(xaddress.getText().toString());

                        Intent gotobackprofile = new Intent(EditProfile.this, Profile.class);
                        startActivity(gotobackprofile);
                        Toast.makeText(getApplicationContext(), "Profile berhasil di update", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError)
                    {
                        button_simpan.setEnabled(true);
                        button_simpan.setText("Simpan");
                    }
                });

                //----
                //validasi untuk file(apakah ada?)
                if (photo_location != null)
                {
                    final StorageReference storageReference1 =
                            storage.child(System.currentTimeMillis() + "." +
                                    getFileExtension(photo_location));
                    storageReference1.putFile(photo_location)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                            {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                                {

                                    storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                                    {
                                        @Override
                                        public void onSuccess(Uri uri)
                                        {
                                            String uri_photo = uri.toString();
                                            reference.getRef().child("url_photo_profile").setValue(uri_photo);

                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<Uri>()
                                    {
                                        @Override
                                        public void onComplete(@NonNull Task<Uri> task)
                                        {
                                            //berpindah activity
                                            Intent gotobackprofile = new Intent(EditProfile.this, Profile.class);
                                            startActivity(gotobackprofile);
                                            Toast.makeText(getApplicationContext(), "Profile berhasil di update", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>()
                    {
                        @Override
                        public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task)
                        {
                            //---
                        }
                    });
                }
                else{
                    button_simpan.setEnabled(true);
                    button_simpan.setText("Simpan");
                }
                //----

            }
        });

        btn_add_new_photo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                findphoto();
            }

        });
    }

    String getFileExtension(Uri uri)
    {
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
    }

    public void findphoto()
    {
        Intent pic = new Intent();
        pic.setType("image/*");
        pic.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(pic, photo_max);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == photo_max && resultCode == RESULT_OK && data !=null && data.getData() != null)
        {
            photo_location = data.getData();
            Picasso.with(this).load(photo_location).centerCrop().fit().into(xpic_profile);
        }

    }


    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }
}