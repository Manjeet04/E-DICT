package edict.lib.image_upload;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import edict.R;
import edict.lib.network.NetworkAPI;
import edict.lib.network.NetworkAPICreator;
import edict.lib.network.NetworkLayer;
import edict.lib.network.OnNetworkResponseListener;
import edict.util.ImageUploadUtils;
import com.myhexaville.smartimagepicker.ImagePicker;
import com.myhexaville.smartimagepicker.OnImagePickedListener;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static edict.lib.network.NetworkService.DATA_ENTRY_API;
import static edict.lib.network.OnNetworkResponseListener.DEFAULT;

/**
 * Created by Necra on 23-12-2017.
 */

public class ImageUploadHelper {

    private NetworkLayer networkLayer;
    private Activity activity;

    private ImagePicker imagePicker;

    private String imagePath;
    private Uri imageURI;
    private UploadImageResponse uploadImageResponse;

    public ImageUploadHelper(Activity activity, NetworkLayer networkLayer) {
        this.networkLayer = networkLayer;
        this.activity = activity;
        initialise();
    }


    private void initialise() {
        imagePicker = new ImagePicker(activity, null, new OnImagePickedListener() {

            @Override
            public void onImagePicked(Uri imageUri) {

                imagePath = imageUri.getPath();

                imageURI = ImageUploadUtils.getImageContentUri(activity.getApplicationContext(), imagePath);

                BitmapFactory.Options bmOptions = new BitmapFactory.Options();
                Bitmap bitmap = BitmapFactory.decodeFile(imagePath,bmOptions);

                ((OnImageUploadListener)activity).onImagePicked(bitmap);

            }
        }).setWithImageCrop(
                1,/*aspect ratio x*/
                1 /*aspect ratio y*/);    }


    public void handlePermissions(int requestCode, String[] permissions, int[] grantResults) {

        imagePicker.handlePermission(requestCode, grantResults);

    }


    public void handleActivityResult(int requestCode, int resultCode, Intent data) {

        haveStoragePermissions();

        imagePicker.handleActivityResult(resultCode,requestCode, data);

    }

    public  boolean haveStoragePermissions() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {

                ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }




    public void selectPicture() {

        imagePicker.choosePicture(true);

    }


    public void startUpload(String bucketLocation) {

        String file_type = ImageUploadUtils.getMimeType(activity, imageURI);

        String uniqueId = UUID.randomUUID().toString();

        String file_path = bucketLocation + uniqueId;

        String apiEndPoint = activity.getResources().getString(R.string.url_upload_image);
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, String> query = new HashMap<String, String>();

        query.put("file_name", file_path);
        query.put("file_type", file_type);

        networkLayer.get(DATA_ENTRY_API, apiEndPoint, headers, query, false, false, new OnNetworkResponseListener(UploadImageResponse.class) {

            @Override
            public void onResponse(Object response) {
                uploadImageResponse = (UploadImageResponse) response;
                uploadImage();
            }

            @Override
            public void onError(String error, int code) {
                ((OnImageUploadListener)activity).onError(error, code);
            }

        });

    }


    public void uploadImage() {

        Set<String> keys = uploadImageResponse.getData().getFields().keySet();
        HashMap<String, RequestBody> map = new HashMap<>();
        for(String k : keys) {
            RequestBody requestBody = ImageUploadUtils.createPartFromString(uploadImageResponse.getData().getFields().get(k));
            map.put(k, requestBody);
        }

        MultipartBody.Part body = null;
        try
        {
            body = ImageUploadUtils.prepareFilePart(activity, imagePath, "file", imageURI);
        }
        catch (Exception e)
        {

            ((OnImageUploadListener)activity).onError("Error in uploading image", DEFAULT);

        }


        String URL = uploadImageResponse.getData().getUrl();

        // assuming base URL and endpoint both are received for upload
        int index = URL.lastIndexOf("/");

        String baseUrl = URL.substring(0, index) + "/";
        String apiEndpoint = "/" + URL.substring(index + 1);


        // if length is 1, only base URL is received for upload
        if(apiEndpoint.length() <= 1) {
            apiEndpoint = "";
        }


        NetworkAPI imageUploadAPI = NetworkAPICreator.create(baseUrl);

        networkLayer.postMultipart(imageUploadAPI, apiEndpoint, map, body, new OnNetworkResponseListener(String.class) {
            @Override
            public void onResponse(Object response) {
                String responseString = (String) response;
                ((OnImageUploadListener)activity).onImageUploaded(uploadImageResponse.getUrl());

            }

            @Override
            public void onError(String error, int code) {
                ((OnImageUploadListener)activity).onError(error, code);
            }
        });

    }


}
