package edict.common.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import edict.R;
import edict.common.base_activities.BaseNavigationActivity;

/**
 * Created by Necra on 28-04-2018.
 */

public class BaseListActivity extends BaseNavigationActivity {

    public static final String LIST_FRAGMENT_CLASS_NAME = "ListFragmentClassName";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_fragment);

        Bundle bundle = getIntent().getExtras();

        String fragmentClassName = bundle.getString(LIST_FRAGMENT_CLASS_NAME);

        if (fragmentClassName != null) {

            Fragment fragment = Fragment.instantiate(this, fragmentClassName, bundle);

            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.mainBodyListFragment, fragment)
                    .show(fragment)
                    .commit();
        }
    }
}
