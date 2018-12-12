package edict.common.base_activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import edict.R;
import edict.util.CityExploroViewUtils;


/**
 * Created by Necra on 10-03-2018.
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected CityExploroViewUtils viewUtils;
    private static final String TAG = "BaseActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewUtils = new CityExploroViewUtils(this);



    }

    public CityExploroViewUtils getViewUtils() {
        return viewUtils;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            finish();
            overridePendingTransition(R.anim.entry_from_left, R.anim.exit_to_right);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {

        super.onBackPressed();
        overridePendingTransition(R.anim.entry_from_left, R.anim.exit_to_right);

    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.entry_from_right, R.anim.exit_to_left);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
