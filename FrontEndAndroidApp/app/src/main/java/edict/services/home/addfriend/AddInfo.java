package edict.services.home.addfriend;

import edict.R;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

import edict.lib.network.BaseResponse;
import edict.lib.network.NetworkLayer;
import edict.lib.network.OnNetworkResponseListener;
import edict.models.AddressInfoModel;
import edict.models.WordModel;
import edict.models.FunInfoModel;
import edict.models.TokenResponseModel;
import edict.models.TokenResultModel;
import edict.models.WordModel;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static edict.lib.network.NetworkService.EMERGENCY_API;

/**
 * Created by Manjeet Singh on 7/26/2018.
 */
public class AddInfo extends Fragment implements View.OnClickListener {

    WordModel model;

    LinearLayout slamlayout;

    private Context context;
    private NetworkLayer networkLayer;
    EditText eword, emeaning, esynonym, eantonym, eword_origin, eexample;
    Button butu, tokenbutu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        context = inflater.getContext();

        final View view = inflater.inflate(R.layout.add_friend, container, false);

        model = new WordModel();

        if (networkLayer == null) {
            networkLayer = new NetworkLayer();
        }

        eword = (EditText) view.findViewById(R.id.word);
        eword.setOnClickListener(this);

        emeaning = (EditText) view.findViewById(R.id.meaning);
        emeaning.setOnClickListener(this);
        esynonym = (EditText) view.findViewById(R.id.synonym);
        esynonym.setOnClickListener(this);
        eexample = (EditText) view.findViewById(R.id.example);
        eexample.setOnClickListener(this);

        eantonym = (EditText) view.findViewById(R.id.antonym);
        eantonym.setOnClickListener(this);
        eword_origin = (EditText) view.findViewById(R.id.wordorigin);
        eword_origin.setOnClickListener(this);
        slamlayout = (LinearLayout) view.findViewById(R.id.slamlayout);


//--------------------------------------------------------------
        // Mandates.
        // favfood = (TextView) findViewById(R.id.favFoodT);
        // favfood.setOnClickListener(this);
        // favmovies = (TextView) findViewById(R.id.favMoviesT);
        //favmovies.setOnClickListener(this);
        //favsong = (TextView) findViewById(R.id.favSongT);
        // favsong.setOnClickListener(this);
        // sport = (TextView) findViewById(R.id.sportT);
        // sport.setOnClickListener(this);
        // momen = (TextView) findViewById(R.id.momenT);
        // momen.setOnClickListener(this);
        // email = (TextView) findViewById(R.id.emailT);
        // email.setOnClickListener(this);
        // bday = (TextView) findViewById(R.id.BdayT);
        // bday.setOnClickListener(this);
        // name = (TextView) findViewById(R.id.NameT);
        // name.setOnClickListener(this);
        butu = (Button) view.findViewById(R.id.filledbutton);
        butu.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.filledbutton) {
//            model.setBday(ebday.getText().toString());
//            model.setEmail(eemail.getText().toString());
//            model.setFav_food(efavfood.getText().toString());
//            model.setFav_song(efavsong.getText().toString());
//            model.setHappy_moment(emomen.getText().toString());
//            model.setName(ename.getText().toString());
//            model.setFav_movie(efavmovies.getText().toString());
//            model.setFav_sport(esport.getText().toString());

            if (eword != null && !eword.getText().toString().equals("")) {


            } else {
                Toast.makeText(context, "Word is Mandatory", Toast.LENGTH_SHORT).show();
                return;
            }
            if (emeaning != null && !emeaning.getText().toString().equals("")) {


            } else {
                Toast.makeText(context, "Meaning is Mandatory", Toast.LENGTH_SHORT).show();
                return;
            }
            if (esynonym != null && !esynonym.getText().toString().equals("")) {


            } else {
                Toast.makeText(context, "Synonym is Mandatory", Toast.LENGTH_SHORT).show();
                return;
            }
            if (eantonym != null && !eantonym.getText().toString().equals("")) {


            } else {
                Toast.makeText(context, "Antonym is Mandatory", Toast.LENGTH_SHORT).show();
                return;
            }
            if (eexample != null && !eexample.getText().toString().equals("")) {


            } else {
                Toast.makeText(context, "Example is Mandatory", Toast.LENGTH_SHORT).show();
                return;
            }
            if (eword_origin != null && !eword_origin.getText().toString().equals("")) {


            } else {
                Toast.makeText(context, "Word Origin is Mandatory", Toast.LENGTH_SHORT).show();
                return;
            }


            model.setWord(eword.getText().toString());
            model.setSynonym(esynonym.getText().toString());
            model.setAntonym(eantonym.getText().toString());
            model.setMeaning(emeaning.getText().toString());
            model.setWord_origin(eword_origin.getText().toString());
            model.setExample(eexample.getText().toString());


            String apiEndPointQuery = getResources().getString(R.string.url_add_word);

            HashMap<String, String> headers = new HashMap<>();
            HashMap<String, String> query = new HashMap<>();

            networkLayer.post(EMERGENCY_API, apiEndPointQuery, headers, query, model,
                    new OnNetworkResponseListener(BaseResponse.class) {
                        @Override
                        public void onResponse(Object response) {


                            Toast.makeText(context, "Success Posting", Toast.LENGTH_SHORT).show();
                            butu.setEnabled(false);
                            String resp = "Thank You So Much :)";
                            butu.setText(resp);


                        }

                        @Override
                        public void onError(String error, int code) {

                            Toast.makeText(context, "Error Posting", Toast.LENGTH_SHORT).show();

                        }
                    });

        }
    }
}
