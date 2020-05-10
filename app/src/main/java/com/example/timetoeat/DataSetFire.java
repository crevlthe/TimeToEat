package com.example.timetoeat;


import android.net.Uri;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class DataSetFire {
    private String recName;
    private String imgUrl;

    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private StorageReference storageReference = storage.getReference();

    /*private void getUrl(String imgUrl){
        storageReference.child(imgUrl + ".png").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri downloadUrl)
            {
                String url = downloadUrl.toString();
                Log.i("FUCKING URL",url);
                return url;

            }
        });
    }*/



    public DataSetFire(String recName, String imgUrl){
        recName = recName;
        imgUrl = imgUrl;
    }

    public DataSetFire(){

    }

    public String getRecName() {
        return recName;
    }

    public String getImgUrl() {

       return imgUrl;
    }

    public void setRecName(String recName) {
        recName = recName;
    }

    public void setImgUrl(String imgUrl) {
        imgUrl = imgUrl;
    }


}
