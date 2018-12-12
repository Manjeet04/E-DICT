package edict.services.home;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

import edict.R;
import edict.models.MostSearchModel;
import edict.models.MostSearchModel;
import edict.models.Response_alpha;
import edict.services.home.getfriend.API_alpha;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Manjeet Singh on 7/27/2018.
 */

public class HomeFragment extends Fragment implements Callback<Response_alpha> {
    RecyclerView alpha;
   // ImageView imgvi;
    Response_alpha res;
    List<MostSearchModel> list;
    Context context;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);




        alpha = (RecyclerView) view.findViewById(R.id.homerec);
        context  = getContext();

        // or just use getApplication context
        getdata();
        //imgvi = (ImageView) view.findViewById(R.id.food);
        return view;

    }
    public void getdata() {
        // Bundle bundle1 = getArguments();
        //String email = bundle1.getString("email",null);
        SharedPreferences shrd  = context.getSharedPreferences(getResources().getString(R.string.app_name),Context.MODE_PRIVATE);



        String endPoint = "most_searched";

        //endPoint = endPoint.concat("/" + email);


        //   loading = ProgressDialog.show(context,"Data Loading","Please wait .. ");

        try {
            Retrofit retrofit = new Retrofit.Builder()// R hits the link.. and converts the raw to iss type. widout R fit. no converter.
                    .baseUrl("https://ee-dict.herokuapp.com/dict/")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build();

            API_alpha api = retrofit.create(API_alpha.class);
            Call<Response_alpha> call = api.getRes(endPoint);
            call.enqueue(this);
        } catch (Exception e) {
            System.out.println(e.toString());
        }

    }

    @Override
    public void onResponse(Call<Response_alpha> call, Response<Response_alpha> response) { // call response functions.

        if(response==null) {
            Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();
        } else {
            try {
                res = response.body();
                list = res.getResponse();
                showData();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showData() {
        alpha.setHasFixedSize(true);
        LinearLayoutManager llm = new LinearLayoutManager(context); // manager which will help in making adapter.
        llm.setOrientation(LinearLayoutManager.VERTICAL);  // orientation
        alpha.setLayoutManager(llm);
        alpha.setAdapter(new HomeListAdapter(list,context)); // call addapter.
    }

    @Override
    public void onFailure(Call<Response_alpha> call, Throwable t) {

        Toast.makeText(context,"Something went wrong",Toast.LENGTH_SHORT).show();

    }
}
