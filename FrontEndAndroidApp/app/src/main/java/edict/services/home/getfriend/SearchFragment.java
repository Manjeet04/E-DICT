package edict.services.home.getfriend;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import edict.R;
import edict.lib.network.NetworkLayer;


/**
 * Created by Manjeet Singh on 7/27/2018.
 */

public class SearchFragment extends Fragment implements View.OnClickListener  {

    LinearLayout phraselayout;
    LinearLayout wordlayout;

    RadioButton wordSearchRadio, phraseSearchRadio;

    private Context context;
    private NetworkLayer networkLayer;
    EditText wordsearch,meaningsearch;
    Button select, wordbutu, phrasebutu;

    private RadioButton selectedRadio = null;

    int a = 0, b = 0, c = 0, d = 0, e = 0, f = 0;

    private CheckBox wordCheckBox, meaningCheckBox, synonymCheckBox, antonymCheckBox,wordOriginCheckBox, exampleCheckBox;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        context = inflater.getContext();

        final View view = inflater.inflate(R.layout.searchlayout, container, false);

        if (networkLayer == null) {
            networkLayer = new NetworkLayer();
        }

        wordCheckBox = (CheckBox) view.findViewById(R.id.wordcheck);
        wordCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                a = (a == 0)?1:0;
            }
        });
        meaningCheckBox = (CheckBox) view.findViewById(R.id.meaningcheck);
        meaningCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                b = (b == 0)?1:0;
            }
        });
        synonymCheckBox= (CheckBox) view.findViewById(R.id.synonymcheck);
        synonymCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                c = (c == 0)?1:0;
            }
        });
        antonymCheckBox = (CheckBox) view.findViewById(R.id.antonymcheck);
        antonymCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                d = (d == 0)?1:0;
            }
        });
        wordOriginCheckBox = (CheckBox) view.findViewById(R.id.wordorigincheck);
        wordOriginCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                e = (e == 0)?1:0;
            }
        });
        exampleCheckBox = (CheckBox) view.findViewById(R.id.examplecheck);
        exampleCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                f = (f == 0)?1:0;
            }
        });



        wordlayout = (LinearLayout) view.findViewById(R.id.searchword);
        phraselayout = (LinearLayout) view.findViewById(R.id.searchmeaning);

        wordsearch = (EditText) view.findViewById(R.id.token);
        wordsearch.setOnClickListener(this);



        meaningsearch = (EditText) view.findViewById(R.id.searchmeanin);
        wordSearchRadio= (RadioButton) view.findViewById(R.id.radio_pirates);
        phraseSearchRadio = (RadioButton) view.findViewById(R.id.radio_ninjas);
        wordSearchRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedRadio == null) selectedRadio = wordSearchRadio;
                else {
                    selectedRadio = wordSearchRadio;
                    phraseSearchRadio.setChecked(false);
                }
            }
        });

        phraseSearchRadio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(selectedRadio == null) selectedRadio = phraseSearchRadio;
                else {
                    selectedRadio = phraseSearchRadio;
                    wordSearchRadio.setChecked(false);
                }
            }
        });

        wordbutu = (Button) view.findViewById(R.id.wordbutton);
        wordbutu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(wordsearch.getText().toString().equals("")) {
                    Toast.makeText(getContext(),"Please enter a word",Toast.LENGTH_SHORT).show();
                    return;
                }


            Intent intent = new Intent(getActivity(),WordSearchActivity.class);
                intent.putExtra("search",wordsearch.getText().toString());
                context.startActivity(intent);

            }
        });



        phrasebutu = (Button) view.findViewById(R.id.meaningbutton);
        phrasebutu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(meaningsearch.getText().toString().equals("")) {
                    Toast.makeText(context,"Please enter something",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(a == 0 && b == 0 && c == 0 && d == 0 && e == 0) {
                    Toast.makeText(context,"Please select atleast one option",Toast.LENGTH_SHORT).show();
                    return;
                }

                // phrase api
                Intent intent  = new Intent(getActivity(),SearchPhraseActivity.class);
                String query = "";
                if(a == 1) query = query + "word,";
                if(b == 1) query = query + "meaning,";
                if(c == 1) query = query + "synonym,";
                if(d == 1) query = query + "antonym,";
                if(e == 1) query = query + "word_origin,";
                if(f == 1) query = query + "example,";
                intent.putExtra("queryString",query);
                intent.putExtra("search",meaningsearch.getText().toString());
                context.startActivity(intent);

            }
        });
        select = (Button) view.findViewById(R.id.tokenbutton);


        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedRadio == null) {
                    Toast.makeText(context,"Please select one option",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(selectedRadio == wordSearchRadio) {
                    wordlayout.setVisibility(View.VISIBLE);
                    phraselayout.setVisibility(View.GONE);
                }
                else {
                    wordlayout.setVisibility(View.GONE);
                    phraselayout.setVisibility(View.VISIBLE);
                }

//                String endpoint = getResources().getString(R.string.url_check_token) + "/" + etoken.getText().toString();
//                HashMap<String, String> headers = new HashMap<String, String>();
//                HashMap<String, String> query = new HashMap<String, String>();
//
//                networkLayer.get(EMERGENCY_API, endpoint, headers, query, false, false, new OnNetworkResponseListener(TokenResultModel.class) {
//                    @Override
//                    public void onResponse(Object response) {
//
//                        tokenresult = (TokenResultModel) response;
//                        if (tokenresult.getToken().size() != 0) {
//                            tokenres = tokenresult.getToken();
//                            TokenResponseModel tokenresponse = tokenres.get(0);
//                            tokenmail = tokenresponse.getEmail();
//                            finaltoken = tokenresponse.getToken();
//                            Toast.makeText(context, "Token Successfully Authenticated", Toast.LENGTH_SHORT).show();
//
//                            tokenlayout.setVisibility(View.GONE);
//                            model = new BasicInfoModel();
//                            modeladdress = new AddressInfoModel();
//                            modelfun = new FunInfoModel();
//                            eaddress.getText().clear();
//                            ecity.getText().clear();
//                            esport.getText().clear();
//                            efavfood.getText().clear();
//                            etoken.getText().clear();
//                            efirlv.getText().clear();
//                            eaims.getText().clear();
//                            ealien.getText().clear();
//                            ebestme.getText().clear();
//                            ecountry.getText().clear();
//                            edesc.getText().clear();
//                            eemail.getText().clear();
//                            efavmovies.getText().clear();
//                            efavsong.getText().clear();
//                            efive.getText().clear();
//                            ehappy.getText().clear();
//                            eembarr.getText().clear();
//                            elandmark.getText().clear();
//                            emob.getText().clear();
//                            ehobby.getText().clear();
//                            ezodiac.getText().clear();
//                            estate.getText().clear();
//                            esport.getText().clear();
//                            equote.getText().clear();
//                            eplac.getText().clear();
//                            epin.getText().clear();
//                            eplac.getText().clear();
//                            emomen.getText().clear();
//                            emotto.getText().clear();
//                            ename.getText().clear();
//                            eopinme.getText().clear();
//                            eopin.getText().clear();
//                            epapa.getText().clear();
//                            emum.getText().clear();
//
//                            slamlayout.setVisibility(View.VISIBLE);
//
//
//                        } else {
//                            Toast.makeText(context, "Invalid Token, Try Again", Toast.LENGTH_SHORT).show();
//
//                        }
//
//
//                    }
//
//                    @Override
//                    public void onError(String error, int code) {
//
//                        Toast.makeText(context, "Something went wrong", Toast.LENGTH_SHORT).show();
//
//                    }
//                });


                // your handler code here
            }
        });


        return view;
    }

//    @Override
//    public void onActivityResult(int requestCode, int resultCode, Intent data) { //
//        super.onActivityResult(requestCode, resultCode, data);
//        if (resultCode == RESULT_OK) {
//            if (requestCode == IMAGE) {
//                if (data != null) {
//                    Uri imageUri = data.getData();
//                    if (imageUri != null) {
//                        Toast.makeText(context, " Image mil gai", Toast.LENGTH_SHORT).show();
//                        get(imageUri);
//                    }
//                }
//            }
//        }
//    }

//    @SuppressWarnings("VisibleForTests")
//    private void get(Uri imageUri) {
//        StorageReference mStorageRef;
//        mStorageRef = FirebaseStorage.getInstance().getReference();
//
//        //Uri file = Uri.fromFile(new File(imageUri.toString()));
//        int Min = 1;
//        int Max = 1000000;
//        int c = Min + (int) (Math.random() * ((Max - Min) + 1));
//        StorageReference riversRef = mStorageRef.child("images" + c);
//
//        riversRef.putFile(imageUri)
//                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//                    @Override
//                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                        // Get a URL to the uploaded content
//                        final Uri downloadUrl = taskSnapshot.getDownloadUrl();
//                        String urll = downloadUrl.toString();
//                        model.setPic(urll);
//                        //Toast.makeText(getBaseContext(), downloadUrl.toString(), Toast.LENGTH_SHORT).show();
//                    }
//                })
//                .addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception exception) {
//                        Toast.makeText(context, "Bakchodi ho gai", Toast.LENGTH_SHORT).show();
//                    }
//                });
//    }

    @Override
    public void onClick(View view) {
//        if (view.getId() == R.id.filledbutton) {
////            model.setBday(ebday.getText().toString());
////            model.setEmail(eemail.getText().toString());
////            model.setFav_food(efavfood.getText().toString());
////            model.setFav_song(efavsong.getText().toString());
////            model.setHappy_moment(emomen.getText().toString());
////            model.setName(ename.getText().toString());
////            model.setFav_movie(efavmovies.getText().toString());
////            model.setFav_sport(esport.getText().toString());
//
//            if (ename != null && !ename.getText().toString().equals("")) {
//
//
//            } else {
//                Toast.makeText(context, "Name is Mandatory", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (eemail != null && !eemail.getText().toString().equals("")) {
//
//
//            } else {
//                Toast.makeText(context, "E-Mail is Mandatory", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (efavfood != null && !efavfood.getText().toString().equals("")) {
//
//
//            } else {
//                Toast.makeText(context, "Favourite Food is Mandatory", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (efavmovies != null && !efavmovies.getText().toString().equals("")) {
//
//
//            } else {
//                Toast.makeText(context, "Favourite Movie is Mandatory", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (efavsong != null && !efavsong.getText().toString().equals("")) {
//
//
//            } else {
//                Toast.makeText(context, "Favourite Song is Mandatory", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (esport != null && !esport.getText().toString().equals("")) {
//
//
//            } else {
//                Toast.makeText(context, "Favourite Sport is Mandatory", Toast.LENGTH_SHORT).show();
//                return;
//            }
//            if (ehappy != null && !ehappy.getText().toString().equals("")) {
//
//
//            } else {
//                Toast.makeText(context, "Happiest Moment is Mandatory", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//
//            model.setName(ename.getText().toString());
//            model.setSun_sign(ezodiac.getText().toString());
//            model.setFathers_name(epapa.getText().toString());
//            model.setMothers_name(emum.getText().toString());
//            model.setHobbies(ehobby.getText().toString());
//            model.setAim_in_life(eaims.getText().toString());
//            model.setMotto(emotto.getText().toString());
//            model.setDescribe_urslf(edesc.getText().toString());
//            model.setContact(Integer.parseInt(emob.getText().toString()));
//            modeladdress.setAddress(eaddress.getText().toString());
//            modeladdress.setCity(ecity.getText().toString());
//            modeladdress.setLandmark(elandmark.getText().toString());
//            modeladdress.setCountry(ecountry.getText().toString());
//            modeladdress.setState(estate.getText().toString());
//            modeladdress.setPin_code(Integer.parseInt(epin.getText().toString()));
//            model.setMail_id(eemail.getText().toString());
//            modelfun.setFav_movie(efavmovies.getText().toString());
//            modelfun.setFav_sport(esport.getText().toString());
//            modelfun.setFav_food(efavfood.getText().toString());
//            modelfun.setFav_song(efavsong.getText().toString());
//            modelfun.setFav_quote(equote.getText().toString());
//            modelfun.setThree_places(eplac.getText().toString());
//            modelfun.setAlien_xist(ealien.getText().toString());
//            modelfun.setBest_mom_me(ebestme.getText().toString());
//            modelfun.setHappy_moment(ehappy.getText().toString());
//            modelfun.setHw_lck_reaz(eopin.getText().toString());
//            modelfun.setFiv_now(efive.getText().toString());
//            modelfun.setPast_change(emomen.getText().toString());
//            modelfun.setOpinion_me(eopinme.getText().toString());
//            modelfun.setEmbarr_moment(eembarr.getText().toString());
//            modelfun.setFirst_love(efirlv.getText().toString());
//            SharedPreferences pref = this.getActivity().getSharedPreferences(getResources().getString(R.string.app_name), MODE_PRIVATE);
//            model.setUser_id(tokenmail);
//            model.setFun_info(modelfun);
//            model.setAddress(modeladdress);
//            String day = "Day = " + simpleDatePicker.getDayOfMonth();
//            String month = "Month = " + (simpleDatePicker.getMonth() + 1);
//            String year = "Year = " + simpleDatePicker.getYear();
//            String final_date = Integer.toString(simpleDatePicker.getDayOfMonth()) + "/" + Integer.toString((simpleDatePicker.getMonth() + 1)) + "/" + Integer.toString(simpleDatePicker.getYear());
//            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
//
//            Date date1 = null;
//            try {
//                date1 = dateFormat.parse(final_date);
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//            model.setDob(date1);
//            String apiEndPointQuery = getResources().getString(R.string.url_add_friend);
//
//            HashMap<String, String> headers = new HashMap<>();
//            HashMap<String, String> query = new HashMap<>();
//
//            networkLayer.post(EMERGENCY_API, apiEndPointQuery, headers, query, model,
//                    new OnNetworkResponseListener(BaseResponse.class) {
//                        @Override
//                        public void onResponse(Object response) {
//
//
//                            Toast.makeText(context, "Success Posting", Toast.LENGTH_SHORT).show();
//                            butu.setEnabled(false);
//                            String resp = "Thank You So Much :)";
//                            butu.setText(resp);
//
//                            slamlayout.setVisibility(View.GONE);
//                            tokenlayout.setVisibility(View.VISIBLE);
//
//
//                        }
//
//                        @Override
//                        public void onError(String error, int code) {
//
//                            Toast.makeText(context, "Error Posting", Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
//
//        }
    }
}
