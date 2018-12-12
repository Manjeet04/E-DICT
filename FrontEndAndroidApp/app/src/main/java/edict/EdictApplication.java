package edict;

import android.app.Application;

import edict.lib.network.NetworkService;
//import edict.sharedActivities.SharedPreferencesManager;


/**
 * Created by cteegarden on 1/26/16.
 */
public class EdictApplication extends Application {

    public EdictApplication() {

    }


    @Override
    public void onCreate() {
        super.onCreate();

        NetworkService.createInstance(getApplicationContext());

      //  SharedPreferencesManager.createInstance(getApplicationContext());

    }

}