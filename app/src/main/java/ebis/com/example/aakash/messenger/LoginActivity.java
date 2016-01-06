package ebis.com.example.aakash.messenger;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raweng.built.*;
import com.raweng.built.utilities.BuiltConstant;


public class LoginActivity extends Activity {

    protected EditText mLoginEmail;
    protected EditText mLoginpassword;
    protected Button mloginButton;
    protected Button mregisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            Built.initializeWithApiKey(getApplicationContext(), "bltfd6973cd542eb269", "messenger");

        }
        catch(Exception e){
            e.printStackTrace();
        }
        setContentView(R.layout.activity_login);

        //initialize
        mLoginEmail = (EditText)findViewById(R.id.emailLoginEditText);
        mLoginEmail.setText("test@gmail.com");
        mLoginpassword = (EditText)findViewById(R.id.passwordLoginEditText);
        mLoginpassword.setText("test");
        mloginButton = (Button)findViewById(R.id.loginButton);
        mregisterButton = (Button)findViewById(R.id.registerLoginButton);

        mloginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get username and password
                String email = mLoginEmail.getText().toString().trim();
                String password = mLoginpassword.getText().toString().trim();

                final BuiltUser builtUserObject = new BuiltUser();
                builtUserObject.login(email , password , new BuiltResultCallBack() {
                    // here "john@malkovich.com" is a valid email id of your user
                    // and "password", the corresponding password
                    @Override
                    public void onSuccess() {
                        // user has logged in successfully
                        // builtUserObject.authtoken contains the session authtoken
                        Toast.makeText(LoginActivity.this,"Welcome Back! "+builtUserObject.getUserName(), Toast.LENGTH_LONG).show();
                        //take to homepage
                        Intent takeuserhome = new Intent(LoginActivity.this, ForumActivity.class);
                        startActivity(takeuserhome);
                    }
                    @Override
                    public void onError(BuiltError builtErrorObject) {
                        // login failed
                        // the message, code and details of the error
                        Log.i("error: ", "" + builtErrorObject.getErrorMessage());
                        Log.i("error: ", "" + builtErrorObject.getErrorCode());
                        Log.i("error: ", "" + builtErrorObject.getErrors());
                        AlertDialog.Builder builder = new AlertDialog.Builder(LoginActivity.this);
                        builder.setTitle("Sorry!");
                        builder.setMessage("error: " + builtErrorObject.getErrorMessage());
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int which) {
                                //close dialog
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog dialog = builder.create();
                        dialog.show();

                    }
                    @Override
                    public void onAlways() {
                        // write code here that you want to execute
                        // regardless of success or failure of the operation
                    }


                });


            }
        });

        mregisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takeUsertoRegister = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(takeUsertoRegister);
            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
