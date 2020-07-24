package com.example.ning.assignmnet3;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mapquest.mapping.MapQuest;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText usernameEditText = (EditText) findViewById(R.id.username);
        final EditText passwordEditText = (EditText) findViewById(R.id.password);

        Button logInBtn = (Button) findViewById(R.id.logInBtn);
        logInBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                logInAsyncTask login = new logInAsyncTask();
                String recordPassword = "";
                try{
                recordPassword = login.execute(username).get();
                } catch (InterruptedException e) {
                e.printStackTrace(); //handle it the way you like
                } catch (ExecutionException e) {
                e.printStackTrace();//handle it the way you like
                }
                MD5 MDHash = new MD5();
                String hashedPassword = MDHash.getMd5(password);
                if (hashedPassword.equals(recordPassword)){
                    Intent intent = new Intent(MainActivity.this, HomeScreen.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("username", username);
                    bundle.putString("password", recordPassword);
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
                else {
                    passwordEditText.setText("");
                    EditText usernameEditText = (EditText) findViewById(R.id.username);
                    usernameEditText.setText("");
                    Toast.makeText(getApplicationContext(),
                            "Invalid Username or Password", Toast.LENGTH_SHORT).show();
                }
            }
        });

       Button signUpBtn = (Button) findViewById(R.id.signUpBtn);
       signUpBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SignUpPage.class);
                startActivity(intent);
            }

        });
    }

    private class logInAsyncTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground (String...params){
            return RestClient.findByUsername(params[0]); }
        @Override
        protected void onPostExecute (String recordPassword){
        }
    }


}
