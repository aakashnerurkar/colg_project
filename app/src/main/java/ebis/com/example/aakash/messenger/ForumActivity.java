package ebis.com.example.aakash.messenger;

import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ListView;

import com.raweng.built.BuiltError;
import com.raweng.built.BuiltObject;
import com.raweng.built.BuiltQuery;
import com.raweng.built.BuiltResultCallBack;
import com.raweng.built.BuiltUser;
import com.raweng.built.QueryResult;
import com.raweng.built.QueryResultsCallBack;
import com.raweng.built.utilities.BuiltConstant;

import java.util.HashMap;
import java.util.List;



public class ForumActivity extends ListActivity {

    protected List<BuiltObject> mThread;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        BuiltUser currentuser = BuiltUser.getCurrentUser();
        if(currentuser != null){
            //take user to homepage

            BuiltQuery query = new BuiltQuery("thread_id");
            query.descending("time");
            query.exec(new QueryResultsCallBack() {
                @Override
                public void onSuccess(QueryResult queryResult) {
                    mThread= queryResult.getResultObjects();

                    forumadapter adapter=new forumadapter(getListView().getContext(), mThread);
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
        else{
            //show login screen
            Intent takeUsertoLogin = new Intent(ForumActivity.this, LoginActivity.class);
            startActivity(takeUsertoLogin);
        }

    }



    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        BuiltObject threadObject = mThread.get(position);
        String objectId = threadObject.getUid();
        Intent goToThreadDetail = new Intent(ForumActivity.this, ThreaddetailActivity.class);
        goToThreadDetail.putExtra("thread_id", objectId);
        startActivity(goToThreadDetail);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_forum, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case R.id.new_topic:
                //take user to newpost
                Intent intent = new Intent(this, NewpostActivity.class);
                startActivity(intent);
                break;
            case R.id.logout:
                //logout user
                BuiltUser user = new BuiltUser();
                user.logout(new BuiltResultCallBack() {
                    @Override
                    public void onSuccess() {
                        Log.i("BuiltUser", "Logged-out");
                        Intent takeUsertoLogin = new Intent(ForumActivity.this, LoginActivity.class);
                        startActivity(takeUsertoLogin);
                    }
                    @Override
                    public void onError(BuiltError error) {
                        // TODO Auto-generated method stub
                        // login failed
                        // the message, code and details of the error

                    }
                    @Override
                    public void onAlways() {
                        // TODO Auto-generated method stub
                    }


                });


                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
