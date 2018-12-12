package edict.services.home.usersign_services;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import edict.R;

/**
 * Created by Manjeet Singh on 11/26/2017.
 */

public class EdictLoginActivity extends AppCompatActivity implements View.OnClickListener {

    EditText email, pass;
    Button button;
    TextView newuser;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_in);
        email = (EditText) findViewById(R.id.emailInLoginPage);
        pass = (EditText) findViewById(R.id.passwordInLoginPage);
        newuser = (TextView) findViewById(R.id.newuser);
        FirebaseApp.initializeApp(getApplicationContext());
        newuser.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent("android.intent.action.SIGNUPACTIVITY");
                // intent.putExtras(bundle);
                startActivity(intent);

                // your handler code here
            }
        });




        button = (Button) findViewById(R.id.signInButtonInLoginPage);
        button.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onClick(View view) {


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


        mAuth.signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(getBaseContext(), "Register first", Toast.LENGTH_SHORT).show();
                        } else {
                            FirebaseUser user = mAuth.getCurrentUser();
                            String name = user.getEmail();
                            if(!user.isEmailVerified()) {
                                Toast.makeText(getBaseContext(), "Verify Your Email First", Toast.LENGTH_SHORT).show(); return;
                            }
                            Toast.makeText(getBaseContext(),"Successfully logged In",Toast.LENGTH_SHORT).show();
                            Bundle bundle  = new Bundle();
                            bundle.putString("email",email.getText().toString());
                            SharedPreferences pref = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE);


                           /// int token  = pref.getInt("token",10000000);
                            //Toast.makeText(getBaseContext(),"Your Secret token is " + Integer.toString(token) + ", Save it somewhere." ,Toast.LENGTH_LONG).show();
                            SharedPreferences.Editor editor1 = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE).edit();
                            editor1.putString("email",email.getText().toString()); // to keep login
                            editor1.apply();
                            SharedPreferences.Editor editor = getSharedPreferences(getResources().getString(R.string.app_name),MODE_PRIVATE).edit();
                            editor.putBoolean("isLogin",true);
                            // to keep login
                            editor.apply();
                            Intent intent = new Intent("android.intent.action.EdictMainActivity");
                             startActivity(intent);
                        }

                    }
                });
    }
}