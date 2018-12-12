package edict.services.home.usersign_services;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import edict.R;
import edict.lib.network.BaseResponse;
import edict.lib.network.NetworkLayer;
import edict.lib.network.OnNetworkResponseListener;
import edict.models.SignUpModel;
import edict.models.TokenModel;

import static edict.lib.network.NetworkService.EMERGENCY_API;



public class EdictSignUpActivity extends AppCompatActivity implements View.OnClickListener {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    Button butr;
    EditText fname, pass, email;

    DatabaseReference ref;
    FirebaseDatabase database;
    TokenModel tokenizer;
    private NetworkLayer networkLayer;


    SignUpModel model;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);
        mAuth = FirebaseAuth.getInstance();
        if( networkLayer == null ) {
            networkLayer = new NetworkLayer();
        }

        model = new SignUpModel();
        tokenizer = new TokenModel();

        database  = FirebaseDatabase.getInstance();
        ref = database.getReference("Login_users");

        butr = (Button) findViewById(R.id.signUpButtonInSignUpPage);
        butr.setOnClickListener(this);

        fname = (EditText) findViewById(R.id.nameInSignUpPage);
        pass = (EditText) findViewById(R.id.passwordInSignUpPage);
        email = (EditText) findViewById(R.id.emailInSignUpPage);

    }

    @Override
    public void onClick(View view) {

        if (fname != null && !fname.getText().toString().equals("")) {


        }
        else {
            Toast.makeText(this, "Enter name", Toast.LENGTH_SHORT).show();
            return;
        }
        if (email != null && !email.getText().toString().equals("")) {


        }
        else  {
            Toast.makeText(this, "Enter email Id", Toast.LENGTH_SHORT).show();
            return;
        }
        if (pass != null && !pass.getText().toString().equals("")) {

        } else {
            Toast.makeText(this, "Enter password", Toast.LENGTH_SHORT).show();
            return;
        }


        mAuth.createUserWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        if (task.isSuccessful()) {
                            model.setEmail(email.getText().toString());
                            model.setName(fname.getText().toString());
                            model.setPassword(pass.getText().toString());

                            ref.push().setValue(model);

                            Toast.makeText(getBaseContext(), "SignUp successfully",
                                    Toast.LENGTH_SHORT).show();

                            int Min = 1;
                            int Max = 1000000;
                            final int c = Min + (int)(Math.random() * ((Max - Min) + 1));

                            tokenizer.setEmail(email.getText().toString());
                            tokenizer.setToken(c);

                            String apiEndPointQuery = getResources().getString(R.string.add_token);

                            HashMap<String, String> headers = new HashMap<>();
                            HashMap<String, String> query = new HashMap<>();

                            networkLayer.post(EMERGENCY_API, apiEndPointQuery, headers, query, tokenizer,
                                    new OnNetworkResponseListener(BaseResponse.class) {
                                        @Override
                                        public void onResponse(Object response) {
                                            SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE).edit();
                                            editor.putInt("token",c); // to keep login
                                            editor.apply();


                                        }

                                        @Override
                                        public void onError(String error, int code) {

                                            Toast.makeText(getBaseContext(),"Error Generating Token",Toast.LENGTH_SHORT).show();

                                        }
                                    });



                            // next page ka intent laga dena.
                            Bundle bundle  = new Bundle();
                            bundle.putString("email",email.getText().toString());
                            FirebaseUser user = mAuth.getCurrentUser();
                            user.sendEmailVerification()
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getBaseContext(), "Email Sent",
                                                        Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });


                            // Intent intent = new Intent("android.intent.action.MAIN");
                            // intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK); // In case of shared pref. Use this, back button handle. aage vali activity chlegi to prev vali band.
                            //intent.putExtras(bundle);
                            //startActivity(intent);

                        } else {
                            Toast.makeText(getBaseContext(), "SignUp failed", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }




}
