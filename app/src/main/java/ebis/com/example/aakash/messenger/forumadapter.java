package ebis.com.example.aakash.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.raweng.built.BuiltObject;

import java.util.List;

/**
 * Created by aakash on 3/4/2015.
 */
public class forumadapter extends ArrayAdapter<BuiltObject> {

    protected Context mContext;
    protected List<BuiltObject> mThread;

    public forumadapter(Context context,List<BuiltObject> thread) {
        super(context, R.layout.forumrowlayout, thread);
        mContext=context;
        mThread=thread;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        ViewHolder holder;

        if(convertView==null){

            convertView= LayoutInflater.from(mContext).inflate(R.layout.forumrowlayout, null);
            holder=new ViewHolder();
            holder.threadlikecount=(TextView) convertView.findViewById(R.id.likecount);
            holder.threadtitle=(TextView) convertView.findViewById(R.id.threadtitle);
            holder.threadtime=(TextView) convertView.findViewById(R.id.timeposted);
            holder.threadusername=(TextView) convertView.findViewById(R.id.byusername);

            convertView.setTag(holder);
        }else{
            holder= (ViewHolder) convertView.getTag();
        }

        BuiltObject threadObject = mThread.get(position);

        //title
        String title = threadObject.getString("title");
        holder.threadtitle.setText(title);

        //time
        String time = threadObject.getString("time");
        holder.threadtime.setText(time);

        //username
        String username = threadObject.getString("username");
        holder.threadusername.setText(username);

        //likecount
        int likecount1 = threadObject.getInt("likes");
        holder.threadlikecount.setText(likecount1+" likes");

        return convertView;

    }

    public static class ViewHolder{
        TextView threadlikecount;
        TextView threadtitle;
        TextView threadtime;
        TextView threadusername;

    }

}
