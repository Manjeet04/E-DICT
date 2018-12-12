package edict.lib.image_upload;

import android.graphics.Bitmap;

/**
 * Created by Necra on 23-12-2017.
 */

public interface OnImageUploadListener {
    public void onImagePicked(Bitmap bitmap);
    public void onError(String error, int code);
    public void onImageUploaded(String imageUrl);
}
