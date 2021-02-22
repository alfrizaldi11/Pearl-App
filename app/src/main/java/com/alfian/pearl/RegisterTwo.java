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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class RegisterTwo extends AppCompatActivity {

    EditText firstname, lastname, address;
    LinearLayout button_back;
    ImageView pic_regis;
    Button button_next, btn_add_photo;

    Uri photo_location;
    Integer photo_max = 1;

    DatabaseReference reference;
    StorageReference storage;

    String USERNAME_KEY = "usernamekey";
    String username_key = "";
    String username_key_new = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_two);

        getUsernameLocal();

        firstname = findViewById(R.id.firstname);
        lastname = findViewById(R.id.lastname);
        address = findViewById(R.id.address);
        pic_regis = findViewById(R.id.pic_regis);

        button_back = findViewById(R.id.button_back);
        button_next = findViewById(R.id.button_next);
        btn_add_photo = findViewById(R.id.btn_add_photo);

        btn_add_photo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                findphoto();
            }
        });

        button_next.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                //ubah state menjadi loading
                button_next.setEnabled(false);
                button_next.setText("Loading...");

                final String myfirstname = firstname.getText().toString();

                if (myfirstname.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Firstname kosong!", Toast.LENGTH_SHORT).show();
                    //ubah state menjadi Username kosong
                    button_next.setEnabled(true);
                    button_next.setText("Next");
                }
                else {
                        //menyimpan kepada firebase
                        reference = FirebaseDatabase.getInstance().getReference()
                                .child("customer").child(username_key_new).child("info_customer");
                        storage = FirebaseStorage.getInstance().getReference().child("Photousers").child(username_key_new);

                        //validasi untuk file(apakah ada?)
                        if (photo_location != null) {
                            final StorageReference storageReference1 =
                                    storage.child(System.currentTimeMillis() + "." +
                                            getFileExtention(photo_location));
                            storageReference1.putFile(photo_location)
                                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                        @Override
                                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                            storageReference1.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                                @Override
                                                public void onSuccess(Uri uri) {
                                                    String uri_photo = uri.toString();
                                                    reference.getRef().child("url_photo_profile").setValue(uri_photo);
                                                    reference.getRef().child("firstname").setValue(firstname.getText().toString());
                                                    reference.getRef().child("lastname").setValue(lastname.getText().toString());
                                                    reference.getRef().child("address").setValue(address.getText().toString());
                                                }
                                            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Uri> task) {
                                                    //berpindah activity
                                                    Intent gotosuccess = new Intent(RegisterTwo.this, MainMenu.class);
                                                    startActivity(gotosuccess);

                                                }
                                            });
                                        }
                                    }).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                                }
                            });
                        }
                        else {
                            Toast.makeText(getApplicationContext(), "Foto kosong!", Toast.LENGTH_SHORT).show();
                            //ubah state menjadi Username kosong
                            button_next.setEnabled(true);
                            button_next.setText("Next");
                        }
                    }
                }
        });

        button_back.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                onBackPressed();
            }
        });

    }

    String getFileExtention(Uri uri)
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
            Picasso.with(this).load(photo_location).centerCrop().fit().into(pic_regis);
        }

    }

    public void getUsernameLocal()
    {
        SharedPreferences sharedPreferences = getSharedPreferences(USERNAME_KEY, MODE_PRIVATE);
        username_key_new = sharedPreferences.getString(username_key, "");
    }

}