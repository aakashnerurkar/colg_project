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

import com.raweng.built.BuiltError;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltResultCallBack;
import com.raweng.built.BuiltUser;
import com.raweng.built.*;
import com.raweng.built.utilities.BuiltConstant;

import java.sql.Timestamp;
import java.util.Date;


public class NewpostActivity extends Activity {

    protected EditText mTitlenewpost;
    protected EditText mDescriptionnewpost;
    protected Button mSubmitbutton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        mTitlenewpost=(EditText)findViewById(R.id.titlenewposteditText);
        mDescriptionnewpost=(EditText)findViewById(R.id.descriptionnewposteditText);
        mSubmitbutton=(Button)findViewById(R.id.submitbutton);

        mSubmitbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get title and description and submit to built.io
                String title = mTitlenewpost.getText().toString();
                String description = mDescriptionnewpost.getText().toString();
                BuiltUser currentUser = BuiltUser.getCurrentUser();
                String currentUsername=currentUser.getUserName();



                    if(title.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewpostActivity.this);
                    builder.setTitle("Oops!");
                    builder.setMessage("Title should not be empty");
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

                else {

                    //save post in built.io
                    BuiltObject object = new BuiltObject("thread_id");
                    object.set("title", title);
                    object.set("description", description);
                    BuiltObject object1 =
                    object.setReference("username ", currentUsername);
                    int time = (int) (System.currentTimeMillis());
                    Timestamp tsTemp = new Timestamp(time);
                    String ts =  tsTemp.toString();
                    object.set("time", tsTemp);

                    object.save(new BuiltResultCallBack() {
                        @Override
                        public void onSuccess() {
                            //object created successfully
                            Toast.makeText(NewpostActivity.this, "Posted Successfully!", Toast.LENGTH_LONG).show();
                            //take to homepage
                            Intent taketoForum = new Intent(NewpostActivity.this, ForumActivity.class);
                            startActivity(taketoForum);

                        }

                        @Override
                        public void onError(BuiltError builtError) {
                            //create object failed
                            Log.i("error: ", "" + builtError.getErrorMessage());
                            Log.i("error: ", "" + builtError.getErrorCode());
                            Log.i("error: ", "" + builtError.getErrors());
                            AlertDialog.Builder builder = new AlertDialog.Builder(NewpostActivity.this);
                            builder.setTitle("Sorry!");
                            builder.setMessage("error: " + builtError.getErrorMessage());
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

                        }


                    });
                }

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_newpost, menu);
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
