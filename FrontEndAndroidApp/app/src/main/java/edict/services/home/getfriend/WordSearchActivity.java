package edict.services.home.getfriend;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import edict.R;
import edict.models.WordListResponse;
import edict.models.WordModel;
import edict.models.WordSearchModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Manjeet Singh on 12/11/2018.
 */

public class WordSearchActivity extends AppCompatActivity implements Callback<WordSearchModel> {

    List<WordModel> list;
    WordSearchModel res;
    String query;

    WordModel model;

    TextView word;
    TextView meaning;
    TextView synonym;
    TextView antonym;
    TextView wordorigin;
    TextView example;
    TextView hitscore,t_hitscore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_layout);

        word = (TextView) findViewById(R.id.word);
        meaning = (TextView) findViewById(R.id.meaning);
        synonym = (TextView) findViewById(R.id.synonym);
        antonym = (TextView) findViewById(R.id.antonym);
        wordorigin = (TextView) findViewById(R.id.wordorigin);
        example = (TextView) findViewById(R.id.example);
        hitscore = (TextView) findViewById(R.id.hitscore);
        t_hitscore = (TextView) findViewById(R.id.khitscore);
        t_hitscore.setVisibility(View.GONE);

        Bundle bundle = getIntent().getExtras();
        query = bundle.getString("search","");

        getData();

    }

    public void getData() {
        String endPoint = "dict/word_search";
        Retrofit retrofit = new Retrofit.Builder()// R hits the link.. and converts the raw to iss type. widout R fit. no converter.
                .baseUrl("https://ee-dict.herokuapp.com/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        WordSearchAPI api = retrofit.create(WordSearchAPI.class);
        Call<WordSearchModel> call = api.getResponse(endPoint,query);
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<WordSearchModel> call, Response<WordSearchModel> response) {
        if(response == null) {
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                res = response.body();
                list = res.getResult();
                if(list!=null && list.size()>0) model = list.get(0);
                showData();
            }
            catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showData() {
        if(model == null) {
            Toast.makeText(this,"No words found!!",Toast.LENGTH_SHORT).show();
            finish();
            return ;
        }
        if(model.getWord()!=null) {
            word.setText("" + model.getWord());

        }
        if(model.getMeaning()!=null) { // correct here.
           meaning.setText(""+ model.getMeaning());
        }
        if(model.getSynonym()!=null) {
            synonym.setText("" + model.getSynonym());
        }
        if(model.getAntonym()!=null) {
            antonym.setText("" + model.getAntonym());
        }
        if(model.getWord_origin()!=null) {
            wordorigin.setText("" + model.getWord_origin());
        }
        if(model.getExample()!=null) {
            example.setText("" + model.getExample());
        }
        hitscore.setVisibility(View.GONE);
        if(model.getHit_points()!=null && model.getHit_points()!=0) {
            hitscore.setText("" + model.getHit_points());
        }

    }
    @Override
    public void onFailure(Call<WordSearchModel> call, Throwable t) {
        Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
    }
}
