package edict.util;

import android.content.Context;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

/**
 * Created by Necra on 23-02-2018.
 */

public class CityExploroViewUtils {

    private Picasso picasso;
    private Context context;

    public CityExploroViewUtils(Context context) {

        this.context = context;
        picasso = Picasso.with(context);

    }

    public void showToast(String message) {

        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

    }

    public void loadImage(ImageView imageView, String imageUrl) {

        picasso.load(imageUrl)
                .fit()
                .into(imageView);
    }


    public void loadImage(ImageView imageView, String imageUrl, boolean centerCrop) {

        picasso.load(imageUrl)
                .fit()
                .centerInside()
                .into(imageView);
    }


}
