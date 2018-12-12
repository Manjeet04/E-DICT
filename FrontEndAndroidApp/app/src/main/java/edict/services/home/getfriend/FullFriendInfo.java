package edict.services.home.getfriend;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.LinkedHashMap;

import edict.R;
import edict.models.AddressInfoModel;
import edict.models.WordModel;
import edict.models.FunInfoModel;
import edict.models.MainResponseModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Manjeet Singh on 7/28/2018.
 */

public class FullFriendInfo  extends AppCompatActivity implements View.OnClickListener, Callback<MainResponseModel> {


    Context f1;
    int ima = 0;

    ImageView finimage,backimage;
    TextView t1,t2,t3;

    RecyclerView recfin1;
    RecyclerView recfin2;
    RecyclerView recfin3;
    LayoutInflater layi;

    MainResponseModel res;
    LinkedHashMap<String,String> personal_info,address_info,fun_info;
    WordModel personalInfoModel;
    AddressInfoModel addressInfoModel;
    FunInfoModel funInfoModel;
    String ID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.full_info_layout);


        personal_info = new LinkedHashMap<>();
        address_info = new LinkedHashMap<>();
        fun_info = new LinkedHashMap<>();

        Bundle bundle = getIntent().getExtras();
        String text= bundle.getString("ID");




        finimage = (ImageView) findViewById(R.id.prop1);
        backimage = (ImageView) findViewById(R.id.cor1);


        recfin1 = (RecyclerView) findViewById(R.id.rec1);
        recfin2 = (RecyclerView) findViewById(R.id.rec2);
        recfin3 = (RecyclerView) findViewById(R.id.rec3);




        //nanimage.setOnClickListener(this);

        recfin1.setOnClickListener(this);
        recfin2.setOnClickListener(this);
        ID = text;
        recfin3.setOnClickListener(this);
        getResponse();


    }

    void getResponse() {

        SharedPreferences shrd  = getSharedPreferences(getResources().getString(R.string.app_name),Context.MODE_PRIVATE);
        String email = shrd.getString("email",null);

        String endPoint = "";
        String attach = ID + "&" + email;
        endPoint = endPoint.concat(attach);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://e-slam-ee.herokuapp.com/friends/get_friend_by_id/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();
        FullResponseAPI api = retrofit.create(FullResponseAPI.class);

        Call<MainResponseModel> call = api.getData(endPoint);
        call.enqueue(this);
    }



    // using recylcer view  :P +grid coder
   /* public void showdetail() {
        recnan.setHasFixedSize(true);
        GridLayoutManager detnan = new GridLayoutManager(n1, 2);

        recnan.setLayoutManager(detnan);
        LinkedHashMap<String, String> arr = new LinkedHashMap<>();
        arr.put("Name : ", "Nandini");
        arr.put("Age : ", "20");
        arr.put("Address : ", "Sector 57 Gurgaon");
        arr.put("Hobbies : ", "Reading Books, Hanging around");
        arr.put("Fav. color : ", "Mauve");
        arr.put("Education : ", "Bcom,3rd Year");
        recnan.setAdapter(new adaptnan(arr, n1));

    }*/



    @Override
    public void onClick(View view) {

        int id = view.getId();

    }


    @Override
    public void onResponse(Call<MainResponseModel> call, Response<MainResponseModel> response) {

        if(response.body()!=null) {
            res = response.body();
            personalInfoModel = res.getResponse().getPersonal_info();
            addressInfoModel = res.getResponse().getAddress_info();
            funInfoModel = res.getResponse().getFun_info();

            showPersonInfo();
            showAddressInfo();
            showFunInfo();
        }
    }

    void showPersonInfo() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:mm:ss");





        GridLayoutManager ggm = new GridLayoutManager(this,2);
        ggm.setOrientation(GridLayoutManager.VERTICAL);
        recfin1.setLayoutManager(ggm);
        recfin1.setAdapter(new Fulladapt(personal_info,this));

    }

    void showAddressInfo() {

        address_info.put("Addrress",addressInfoModel.getAddress());
        address_info.put("Landmark",addressInfoModel.getAddress());
        address_info.put("City",addressInfoModel.getCity());
        address_info.put("State",addressInfoModel.getState());
        address_info.put("Country",addressInfoModel.getCountry());
        address_info.put("Pincode",String.valueOf(addressInfoModel.getPin_code()));

        GridLayoutManager ggm = new GridLayoutManager(this,2);
        ggm.setOrientation(GridLayoutManager.VERTICAL);
        recfin2.setLayoutManager(ggm);

        recfin2.setAdapter(new Fulladapt(address_info,this));

    }

    void showFunInfo(){

        fun_info.put("Fav Food",funInfoModel.getFav_food());
        fun_info.put("Fav Song",funInfoModel.getFav_song());
        fun_info.put("Fav Movie",funInfoModel.getFav_movie());
        fun_info.put("Fav Quote",funInfoModel.getFav_quote());
        fun_info.put("Fav Sport",funInfoModel.getFav_sport());
        fun_info.put("Emabress Movement",funInfoModel.getEmbarr_moment());
        fun_info.put("Happy Movement",funInfoModel.getHappy_moment());
        fun_info.put("First Love",funInfoModel.getFirst_love());
        fun_info.put("Hard Work or Luck Reason",funInfoModel.getHw_lck_reaz());
        fun_info.put("Three Places",funInfoModel.getThree_places());
        fun_info.put("Alien Exists? ",funInfoModel.getAlien_xist());
        fun_info.put("Five Now",funInfoModel.getFiv_now());
        fun_info.put("Past Change",funInfoModel.getPast_change());
        fun_info.put("Opinion Me",funInfoModel.getOpinion_me());
        fun_info.put("Best From me",funInfoModel.getBest_mom_me());

        GridLayoutManager ggm = new GridLayoutManager(this,2);
        ggm.setOrientation(GridLayoutManager.VERTICAL);
        recfin3.setLayoutManager(ggm);

        recfin3.setAdapter(new Fulladapt(fun_info,this));
    }

    @Override
    public void onFailure(Call<MainResponseModel> call, Throwable t) {
        Toast.makeText(this,t.toString(),Toast.LENGTH_SHORT).show();
    }
}
