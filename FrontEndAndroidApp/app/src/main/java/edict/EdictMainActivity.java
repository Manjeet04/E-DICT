package edict;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import edict.common.base_activities.BaseHomeActivity;

import edict.lib.network.NetworkLayer;
import edict.lib.network.OnNetworkResponseListener;
import edict.services.home.HomeFragment;
import edict.services.home.addfriend.AddInfo;
import edict.services.home.getfriend.SearchFragment;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import static edict.lib.network.NetworkService.EMERGENCY_API;


public class EdictMainActivity extends BaseHomeActivity {


    private NetworkLayer networkLayer;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        if( networkLayer == null ) {
            networkLayer = new NetworkLayer();
        }

        loadData();

    }


    private void loadData() {

        String endpoint = getResources().getString(R.string.url_dict_home);
        HashMap<String, String> headers = new HashMap<String, String>();
        HashMap<String, String> query = new HashMap<String, String>();

        networkLayer.get(EMERGENCY_API, endpoint, headers, query, false, false, new OnNetworkResponseListener(EdictHomeResponse.class) {
            @Override
            public void onResponse(Object response) {

                handleResponse((EdictHomeResponse)response);

            }

            @Override
            public void onError(String error, int code) {

                viewUtils.showToast(error);

            }
        });

    }



    private void handleResponse(EdictHomeResponse eslameeHomeResponse) {

        super.updateCarouselView(eslameeHomeResponse.getHome_image_list());
        List<String> tabl = new ArrayList<>();
        List<Fragment> tabFragmentList = getTabFragmentList(eslameeHomeResponse.getTab_list());

        List<String> temp  = new ArrayList<>();
        temp.add("HOME");
        temp.add("SMART_SEARCH");
        temp.add("ADD_WORD");

        super.updateViewPager(temp,tabFragmentList);

    }

    private List<Fragment> getTabFragmentList(List<String> tabList) {



        List<Fragment> list = new LinkedList<>();

        for(String tab : tabList) {
            list.add(getFragmentForTab(tab));
        }

        return list;

    }

    private Fragment getFragmentForTab(String tab) {

        Fragment fragment = null;

        switch(tab) {
            case "HOME":
                fragment = new HomeFragment();
                break;

            case "SMART_SEARCH":
                fragment = new SearchFragment();
                break;

            case "ADD_WORD":
                fragment = new AddInfo();
                break;

            /*case "events":
                fragment = new RecyclerActivityForEventList();
                break;*/

        }

        return fragment;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    }

}
