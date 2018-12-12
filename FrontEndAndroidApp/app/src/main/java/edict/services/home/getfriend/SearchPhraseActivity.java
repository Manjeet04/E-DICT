package edict.services.home.getfriend;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import edict.R;
import edict.models.Response_alpha;
import edict.models.WordListResponse;
import edict.models.WordModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

/**
 * Created by Manjeet Singh on 12/11/2018.
 */

public class SearchPhraseActivity extends AppCompatActivity implements Callback<WordListResponse> {
    private RecyclerView suggestedWordRecycler;

    private WordListResponse res;

    private WordModel model;

    private List<WordModel> wordModelList;

    TextView word;
    TextView meaning;
    TextView synonym;
    TextView antonym;
    TextView wordorigin;
    TextView example;
    TextView hitscore,t_hitscore;
    String queryString;
    String searchString;
    @Override
    public void onCreate(Bundle savedInstance) {
        super.onCreate(savedInstance);
        setContentView(R.layout.phrase_search_layout);

        Bundle bundle = getIntent().getExtras();

        wordModelList = new ArrayList<>();

        queryString = bundle.getString("queryString","");
        searchString = bundle.getString("search","");


        word = (TextView) findViewById(R.id.word);
        meaning = (TextView) findViewById(R.id.meaning);
        synonym = (TextView) findViewById(R.id.synonym);
        antonym = (TextView) findViewById(R.id.antonym);
        wordorigin = (TextView) findViewById(R.id.wordorigin);
        example = (TextView) findViewById(R.id.example);
        hitscore = (TextView) findViewById(R.id.hitscore);

        suggestedWordRecycler = (RecyclerView) findViewById(R.id.suggestedWord);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        llm.setOrientation(LinearLayoutManager.VERTICAL);

        suggestedWordRecycler.setLayoutManager(llm);

        getData();


    }

    public void getData() {
        String endPoint = "dict/search_query";
        Retrofit retrofit = new Retrofit.Builder()// R hits the link.. and converts the raw to iss type. widout R fit. no converter.
                .baseUrl("https://ee-dict.herokuapp.com/dict/")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        PhraseSearchAPI api = retrofit.create(PhraseSearchAPI.class);
        Call<WordListResponse> call = api.getWordList(endPoint,searchString,queryString);
        call.enqueue(this);
    }

    @Override
    public void onResponse(Call<WordListResponse> call, Response<WordListResponse> response) {
        if(response==null) {
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
        } else {
            try {
                res = response.body();
                wordModelList = res.getRespose();
                showData();
            }
            catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void showData() {
        if(wordModelList.size() == 0) {
            Toast.makeText(this,"No words available",Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        model = wordModelList.get(0);
        wordModelList.remove(0);
        suggestedWordRecycler.setAdapter(new PhraseSearchAdapter(getBaseContext(),wordModelList));

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
        t_hitscore = (TextView) findViewById(R.id.khitscore);
        t_hitscore.setVisibility(View.GONE);
        hitscore.setVisibility(View.GONE);
        if(model.getHit_points()!=0) {
            hitscore.setText("" + model.getHit_points());
        }

    }

    @Override
    public void onFailure(Call<WordListResponse> call, Throwable t) {
        Toast.makeText(this,"Something went wrong",Toast.LENGTH_SHORT).show();
    }
}
