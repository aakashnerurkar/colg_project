package ebis.com.example.aakash.messenger;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.raweng.built.BuiltError;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltQuery;
import com.raweng.built.BuiltResultCallBack;
import com.raweng.built.QueryResult;
import com.raweng.built.QueryResultsCallBack;

import java.util.ArrayList;
import java.util.List;


public class ThreaddetailActivity extends ListActivity {
    String threadId;
    String threadTitle;
    protected TextView mThreadtitle;
    protected TextView mThreaddescription;
    protected TextView mThreaduser;
    protected List<BuiltObject> mComment;
    protected Button mlikeButton;
    protected Button mcommentButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_threaddetail);


        mThreadtitle = (TextView)findViewById(R.id.threadtitle);
        mThreaddescription = (TextView)findViewById(R.id.threaddescription);
        mThreaduser = (TextView)findViewById(R.id.threaduser);
        mcommentButton = (Button)findViewById(R.id.commentbutton);
        mlikeButton = (Button)findViewById(R.id.likebutton);
        Intent intent=getIntent();
        threadId = intent.getStringExtra("thread_id");

        final BuiltObject obj = new BuiltObject("thread_id");
        obj.setUid(threadId);
        obj.fetch(new BuiltResultCallBack() {
            @Override
            public void onSuccess() {
                //System.out.println("---------thf----------"+obj.get("description"));
                String threadtitle = obj.getString("title");
                mThreadtitle.setText(threadtitle);
                String threaddescription = obj.getString("description");
                mThreaddescription.setText(threaddescription);
                String threaduser = obj.getString("username");
                mThreaduser.setText(threaduser);
                //Log.d("done","success");


                mcommentButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        String threadtitle = obj.getString("title");
                        Intent taketocomment = new Intent(ThreaddetailActivity.this, NewcommentActivity.class);
                        taketocomment.putExtra("title", threadtitle);
                        startActivity(taketocomment);

                    }
                });
            }

            @Override
            public void onError(BuiltError builtError) {
                Log.d("done", "error");
            }

            @Override
            public void onAlways() {
                Log.d("done", "always");
            }
        });

        mlikeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                obj.increment("likes", 1);
                obj.save(new BuiltResultCallBack() {
                    @Override
                    public void onSuccess() {
                        Toast.makeText(ThreaddetailActivity.this, "Thread Liked! ", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(BuiltError builtError) {
                        // the message, code and details of the error
                        Log.i("error: ", "" + builtError.getErrorMessage());
                        Log.i("error: ", "" + builtError.getErrorCode());
                        Log.i("error: ", "" + builtError.getErrors());
                    }

                    @Override
                    public void onAlways() {

                    }
                });
            }
        });



        /*mcommentButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                BuiltObject object = new BuiltObject("thread_id");
                String objectid = object.getUid();
                Intent taketocomment = new Intent(ThreaddetailActivity.this,NewcommentActivity.class);
                taketocomment.putExtra("thread_id",objectid);
                startActivity(taketocomment);

            }
        });*/


        //final commentadapter adapter = new commentadapter(this,R.layout.commentrowlayout,mComment);
        //final ListView commentlist = (ListView)findViewById(R.id.commentlistView);

        BuiltQuery query = new BuiltQuery("comments");
        //query.where("commentonthread ", mThreadtitle);
        query.ascending("time_comm");
        query.exec(new QueryResultsCallBack() {
            @Override
            public void onSuccess(QueryResult queryResult) {
                mComment= queryResult.getResultObjects();

                commentadapter adapter = new commentadapter(getListView().getContext(),mComment);
                //ListView commentlist = (ListView)findViewById(R.id.commentlistView);

                //commentlist.setAdapter(adapter);
                setListAdapter(adapter);



            }

            @Override
            public void onError(BuiltError builtError) {

            }

            @Override
            public void onAlways() {

            }
        });



    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_threaddetail, menu);
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
