package com.example.digitrafficinspectorv0;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class testfile {
}

//
//package com.example.digitrafficinspectorv0;
//
//        import androidx.annotation.NonNull;
//        import androidx.annotation.Nullable;
//        import androidx.appcompat.app.AppCompatActivity;
//        import androidx.core.app.ActivityCompat;
//        import androidx.core.content.ContextCompat;
//        import androidx.core.content.FileProvider;
//
//        import android.Manifest;
//        import android.app.Activity;
//        import android.content.ContentResolver;
//        import android.content.Context;
//        import android.content.Intent;
//        import android.content.pm.PackageManager;
//        import android.graphics.Bitmap;
//        import android.net.Uri;
//        import android.os.Bundle;
//        import android.os.Environment;
//        import android.provider.MediaStore;
//        import android.util.Log;
//        import android.view.View;
//        import android.webkit.MimeTypeMap;
//        import android.widget.Button;
//        import android.widget.ImageView;
//        import android.widget.Toast;
//
//        import com.google.android.gms.tasks.OnFailureListener;
//        import com.google.android.gms.tasks.OnSuccessListener;
//        import com.google.firebase.storage.FirebaseStorage;
//        import com.google.firebase.storage.StorageReference;
//        import com.google.firebase.storage.UploadTask;
//
//        import java.io.File;
//        import java.io.IOException;
//        import java.text.SimpleDateFormat;
//        import java.util.Date;
//
//
//public class CapturePicture extends AppCompatActivity {
//    public static final int CAMERA_PERM_CODE = 101;
//    public static final int CAMERA_REQUEST_CODE = 102;
//    ImageView selectedImage;
//    Button cameraBtn,submitBtn;
//    String currentPhotoPath;
//    StorageReference storageReference;
//
//    String pic1,pic2,pic3,pic4,turn;
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_capture_picture);
//        pic1 = getIntent().getStringExtra("pic1");
//        pic2 = getIntent().getStringExtra("pic2");
//        pic3 = getIntent().getStringExtra("pic3");
//        pic4 = getIntent().getStringExtra("pic4");
//        turn = getIntent().getStringExtra("turn");
//
//        selectedImage = findViewById(R.id.displayImageView);
//        cameraBtn = findViewById(R.id.cameraBtn);
//        submitBtn = findViewById(R.id.submit_btn);
//
//        storageReference = FirebaseStorage.getInstance().getReference();
//
//        cameraBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                askCameraPermissions();
//            }
//        });
//
//
//
//        submitBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                Intent intent = new Intent(CapturePicture.this, registerComplaintPage.class);
//                intent.putExtra("pic1",pic1);
//                intent.putExtra("pic2",pic2);
//                intent.putExtra("pic3",pic3);
//                intent.putExtra("pic4",pic4);
//                startActivity(intent);
//            }
//        });
//    }
//
//    private void askCameraPermissions() {
//        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
//            ActivityCompat.requestPermissions(this,new String[] {Manifest.permission.CAMERA}, CAMERA_PERM_CODE);
//        }else {
//            dispatchTakePictureIntent();
//        }
//
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        if(requestCode == CAMERA_PERM_CODE){
//            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
//                dispatchTakePictureIntent();
//            }else {
//                Toast.makeText(this, "Camera Permission is Required to Use camera.", Toast.LENGTH_SHORT).show();
//            }
//        }
//    }
//
//
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == CAMERA_REQUEST_CODE) {
//            if (resultCode == Activity.RESULT_OK) {
//                File f = new File(currentPhotoPath);
//                selectedImage.setImageURI(Uri.fromFile(f));
//                if(turn.equals("1"))
//                {
//                    pic1 = currentPhotoPath;
//                }
//                if(turn.equals("2"))
//                {
//                    pic2 = currentPhotoPath;
//                }
//                if(turn.equals("3"))
//                {
//                    pic3 = currentPhotoPath;
//                }
//                if(turn.equals("4"))
//                {
//                    pic4 = currentPhotoPath;
//                }
//
//
//            }
//
//        }
//
//    }
//
//
//    private File createImageFile() throws IOException {
//        // Create an image file name
//        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
//        String imageFileName = "JPEG_" + timeStamp + "_";
////        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
//        File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
//        File image = File.createTempFile(
//                imageFileName,  /* prefix */
//                ".jpg",         /* suffix */
//                storageDir      /* directory */
//        );
//
//        // Save a file: path for use with ACTION_VIEW intents
//        currentPhotoPath = image.getAbsolutePath();
//        return image;
//    }
//
//
//    private void dispatchTakePictureIntent() {
//        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // Ensure that there's a camera activity to handle the intent
//        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            // Create the File where the photo should go
//            File photoFile = null;
//            try {
//                photoFile = createImageFile();
//            } catch (IOException ex) {
//
//            }
//            // Continue only if the File was successfully created
//            if (photoFile != null) {
//                Uri photoURI = FileProvider.getUriForFile(this,
//                        "com.example.digitrafficinspectorv0",
//                        photoFile);
//                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
//                startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE);
//            }
//        }
//    }
//
//
//}