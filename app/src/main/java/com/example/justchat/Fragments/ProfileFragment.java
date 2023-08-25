package com.example.justchat.Fragments;

import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.justchat.Model.Users;
import com.example.justchat.R;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileFragment extends Fragment {


    DatabaseReference reference;
    FirebaseUser user;
    CircleImageView imageView;
    TextView username;
    public static final int CAMERA_CODE = 200;
    public static final int GALLERY_CODE = 100;
    Uri imageUri = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_profile, container, false);

        Permissions(); // for permissions

        imageView = view.findViewById(R.id.profile_frag_image);
        username = view.findViewById(R.id.username_profile_frag);

        user = FirebaseAuth.getInstance().getCurrentUser();

        reference = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("RestrictedApi")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Users users = snapshot.getValue(Users.class);

                assert users != null;
                username.setText(users.getUsername());

                if (users.getImageURL().equals("default")) {

                    imageView.setImageResource(R.drawable.user);
                } else {

                    // change this to
                   // Glide.with(getActivity().getApplicationContext()).load(users.getImageURL()).into(imageView);

                    Glide.with(requireContext()).load(users.getImageURL()).into(imageView);



                }



            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        imageView.setOnClickListener(v -> changeImage());







        return view;
    }

    private void changeImage() {

        String[] options = {"Camera", "Gallery"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Choose an Option");

        builder.setItems(options, (dialog, i) -> {


            if (i==0) {

                openCamera();

            }

            if (i==1) {

                openGallery();
            }
        });

        builder.create().show();


    }

    private void openGallery() {


        Intent intent = new Intent (Intent.ACTION_PICK);
        intent.setType("image/*"); // all kinds of images
        startActivityForResult(intent, GALLERY_CODE);



    }

    private void openCamera() {

        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "Temp Pick");
        values.put(MediaStore.Images.Media.TITLE, "Temp Desc");
        imageUri = requireContext().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);


        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        startActivityForResult(intent, CAMERA_CODE);



    }



    private void Permissions() {


        Dexter.withContext(getContext())
                .withPermissions(
                        Manifest.permission.CAMERA,
                        Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).withListener(new MultiplePermissionsListener() {
            @Override public void onPermissionsChecked(MultiplePermissionsReport report) {
            }
            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {

            }

        }).check();



    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK) {

            imageUri = data.getData();

            String filepath = "Photos/" + "userprofile_" + user.getUid();

            StorageReference reference = FirebaseStorage.getInstance().getReference(filepath);
            reference.putFile(imageUri).addOnSuccessListener(taskSnapshot -> {

                Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();

                task.addOnSuccessListener(uri -> {

                    String imageURL = uri.toString();

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("imageURL", imageURL);
                    reference1.updateChildren(hashMap);


                });


            });


        }



        if (requestCode == CAMERA_CODE && resultCode == RESULT_OK) {

            Uri uri = imageUri;

            String filepath = "Photos/" + "userprofile_" + user.getUid();

            StorageReference reference = FirebaseStorage.getInstance().getReference(filepath);
            reference.putFile(uri).addOnSuccessListener(taskSnapshot -> {

                Task<Uri> task = Objects.requireNonNull(Objects.requireNonNull(taskSnapshot.getMetadata()).getReference()).getDownloadUrl();

                task.addOnSuccessListener(uri1 -> {

                    String imageURL = uri1.toString();

                    DatabaseReference reference1 = FirebaseDatabase.getInstance().getReference("Users").child(user.getUid());

                    HashMap<String, Object> hashMap = new HashMap<>();
                    hashMap.put("imageURL", imageURL);
                    reference1.updateChildren(hashMap);


                });


            });


    }




    }
}