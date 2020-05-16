package com.example.timetoeat;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ClipDescription.MIMETYPE_TEXT_PLAIN;

public class Upload_Recipe extends AppCompatActivity {


    private Button btnSelectImg;
    private Button btnUploadImg;
    private ImageView uploadedImg;
    private EditText recName, recIngredients, recDescription;
    private ClipboardManager clipboard;

    private String currentUserID;
    private FirebaseAuth mAuth;
    private DatabaseReference RootRef;
    private StorageReference userProfileImagesRef;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    public FirebaseAuth.AuthStateListener authListener;
    public FirebaseUser user;
    public String CurrentUser;


    private static final int GalleryPic = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload__recipe);

        btnSelectImg = (Button) findViewById(R.id.bnt_select_image);
        btnUploadImg = (Button) findViewById(R.id.bnt_upload_image);
        uploadedImg = (ImageView) findViewById(R.id.iv_foodImage);
        recName = (EditText) findViewById(R.id.recipe_title);
        recIngredients = (EditText) findViewById(R.id.recipe_ingredients);
        recDescription = (EditText) findViewById(R.id.recipe_description);


        mAuth = FirebaseAuth.getInstance();
        currentUserID = mAuth.getCurrentUser().getUid();
        RootRef = FirebaseDatabase.getInstance().getReference();
        userProfileImagesRef = FirebaseStorage.getInstance().getReference().child("RecipeImages");

        storage = FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        //clipboard manager for copy and paste

        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText("label", "Text to copy");
        clipboard.setPrimaryClip(clip);


        String pasteData = "";

        // If it does contain data, decide if you can handle the data.
        if (!(clipboard.hasPrimaryClip())) {

        } else if (!(clipboard.getPrimaryClipDescription().hasMimeType(MIMETYPE_TEXT_PLAIN))) {

            // since the clipboard has data but it is not plain text

        } else {

            //since the clipboard contains plain text.
            ClipData.Item item = clipboard.getPrimaryClip().getItemAt(0);

            // Gets the clipboard as text.
            pasteData = item.getText().toString();
        }


        btnSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, GalleryPic);
            }
        });

         /*btnUploadImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), PersonalRecipe.class));
            }
        });*/


        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    //User is signed in
                    CurrentUser = firebaseAuth.getUid();
                    btnUploadImg.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            startActivity(new Intent(getApplicationContext(), PersonalRecipe.class));
                        }
                    });


                } else {
                    //User is signed out
                }
            }
        };
        FirebaseAuth.getInstance().addAuthStateListener(authListener);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);


        if (requestCode == GalleryPic && resultCode == RESULT_OK
                && data != null) {


            Uri ImageUri = data.getData();


            //Storage reference
            final ProgressDialog progressDialog=new ProgressDialog(this);
            progressDialog.setMessage("Uploading Image");
            progressDialog.show();

            String image = currentUserID;
            userProfileImagesRef.putFile(ImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    progressDialog.dismiss();
                    Toast.makeText(Upload_Recipe.this, "Uploaded", Toast.LENGTH_SHORT).show();
                    userProfileImagesRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri uri)
                        {
                            Object newURL;
                            newURL=uri.toString();
                            Log.i("URIIIII",newURL.toString() );
                            //Toast.makeText(Upload_Recipe.this, ""+uri.toString(), Toast.LENGTH_SHORT).show();
                            Glide.with(Upload_Recipe.this).load(newURL).into(uploadedImg);


                            final String myCurrentDateTime = DateFormat.getDateTimeInstance()
                                    .format(Calendar.getInstance().getTime());

                            Map<String, Object> recipes = new HashMap<>();
                            recipes.put("recipeName", recName.getText().toString());
                            recipes.put("recipeIngredients", recIngredients.getText().toString());
                            recipes.put("recipeDescription",recDescription.getText().toString());
                            recipes.put("imgUrl", (String) newURL);


                            /*Map<String, PersonalRecipeInfo> recipes = new HashMap<>();


                            recipes.put("SingleItem", new PersonalRecipeInfo(
                                    recName.getText().toString(),
                                    recIngredients.getText().toString(),
                                    recTitle.getText().toString(),
                                    (String) newURL
                            ));*/

                            RootRef.child("Recipes").child(CurrentUser)
                                    //.setValue(newURL)
                                    .setValue(recipes)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                //startActivity(new Intent(getApplicationContext(), PersonalRecipe.class));
                                                Toast.makeText(Upload_Recipe.this, "Image Saved to the Database", Toast.LENGTH_SHORT).show();
                                            } else {
                                                String message = task.getException().toString();
                                                Toast.makeText(Upload_Recipe.this, "Error" + message, Toast.LENGTH_SHORT);
                                            }
                                        }
                                    });
                        }
                    });
                }

            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(Upload_Recipe.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                    double progress=(100.0 * taskSnapshot.getBytesTransferred()
                            / taskSnapshot.getTotalByteCount());
                    progressDialog.setMessage("Uploaded "+progress+" %");

                }
            });

    }
    }

}


