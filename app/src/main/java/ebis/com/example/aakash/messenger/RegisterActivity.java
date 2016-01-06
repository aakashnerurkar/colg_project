package ebis.com.example.aakash.messenger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.raweng.built.BuiltError;
import com.raweng.built.BuiltResultCallBack;
import com.raweng.built.BuiltUser;

import java.util.HashMap;


public class RegisterActivity extends Activity {

    protected EditText mUsername;
    protected EditText mUserEmail;
    protected EditText mUserPassword;
    protected EditText mUserConfirmpwd;
    protected Button mRegisterButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_register);

        //initialize
        mUsername = (EditText)findViewById(R.id.usernameRegisterEditText);
        mUserEmail = (EditText)findViewById(R.id.emailRegisterEditText);
        mUserPassword = (EditText)findViewById(R.id.passwordRegisterEditText);
        mUserConfirmpwd = (EditText)findViewById(R.id.confirmpwdRegisterEditText);
        mRegisterButton = (Button)findViewById(R.id.RegisterButton);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get username,password,email and convert to string
                String username = mUsername.getText().toString().trim();
                String email = mUserEmail.getText().toString().trim();
                String password = mUserPassword.getText().toString().trim();
                String confirmpwd = mUserConfirmpwd.getText().toString().trim();

                //register in built


                BuiltUser user = new BuiltUser();
                HashMap usrInfo = new HashMap();
                usrInfo.put("username", username);
                usrInfo.put("email", email);
                usrInfo.put("password", password);
                usrInfo.put("password_confirmation", confirmpwd);
                user.register(usrInfo, new BuiltResultCallBack() {
                    @Override
                    public void onSuccess() {
                        // object is created successfully
                        Toast.makeText(RegisterActivity.this, "Success!", Toast.LENGTH_LONG).show();
                        //take to loginpage
                        Intent taketoLogin = new Intent(RegisterActivity.this, LoginActivity.class);
                        startActivity(taketoLogin);

                    }
                    @Override
                    public void onError(BuiltError builtErrorObject) {
                        // there was an error in creating the object
                        // builtErrorObject will contain more details
                    }
                    @Override
                    public void onAlways() {
                        // write code here that you want to execute
                        // regardless of success or failure of the operation
                    }


                });


            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_register, menu);
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
