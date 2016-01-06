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

import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltUser;
import com.raweng.built.*;


public class NewcommentActivity extends Activity {

    protected EditText mNewcomment;
    protected Button mPost;
    String title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcomment);

        mNewcomment= (EditText)findViewById(R.id.commentEdittext);
        mPost = (Button)findViewById(R.id.postbutton);

        //get title
        Intent intent=getIntent();
        title=intent.getStringExtra("title");
        System.out.println(""+title);



        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get comment and username

                BuiltUser currentUser = BuiltUser.getCurrentUser();
                String currentUsername=currentUser.getUserName();
                String newcomment=mNewcomment.getText().toString();


                if(newcomment.isEmpty()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewcommentActivity.this);
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
                }else{

                    //post comment on built
                    BuiltObject object=new BuiltObject("comments");
                    object.set("comment",newcomment);
                    object.set("username",currentUsername);
                    object.set("commentonthread",title);
                    object.save(new BuiltResultCallBack() {
                        @Override
                        public void onSuccess() {
                            //comment created successfully
                            Toast.makeText(NewcommentActivity.this, "Posted Successfully!", Toast.LENGTH_LONG).show();
                            //take to homepage
                            Intent taketothread = new Intent(NewcommentActivity.this, ForumActivity.class);
                            startActivity(taketothread);

                        }

                        @Override
                        public void onError(BuiltError builtError) {
                            //create object failed
                            Log.i("error: ", "" + builtError.getErrorMessage());
                            Log.i("error: ", "" + builtError.getErrorCode());
                            Log.i("error: ", "" + builtError.getErrors());
                            AlertDialog.Builder builder = new AlertDialog.Builder(NewcommentActivity.this);
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
        getMenuInflater().inflate(R.menu.menu_newcomment, menu);
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
