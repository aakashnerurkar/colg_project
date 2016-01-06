package ebis.com.example.aakash.messenger;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.raweng.built.BuiltObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by aakash on 3/14/2015.
 */
public class commentadapter extends ArrayAdapter<BuiltObject> {

    protected Context mContext;
    protected List<BuiltObject> mComment;
    //int layoutResourceId;

    public commentadapter(Context context ,List<BuiltObject> comment) {
        super(context, R.layout.commentrowlayout, comment);
        //this.layoutResourceId= layoutResourceId;
        mContext=context;
        mComment=comment;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //View rowView = convertView;
        ViewHolder holder;
        // reuse views
        if (convertView == null) {

            convertView=LayoutInflater.from(mContext).inflate(R.layout.commentrowlayout, null);
            holder=new ViewHolder();
            //LayoutInflater inflater = (LayoutInflater) mContext.getSystemService( Context.LAYOUT_INFLATER_SERVICE );
            //rowView = inflater.inflate(R.layout.commentrowlayout, parent,false);
            // configure view holder
            //ViewHolder viewHolder = new ViewHolder();
            holder.commentusername=(TextView) convertView.findViewById(R.id.commentusername);
            holder.commenttime=(TextView) convertView.findViewById(R.id.commenttime);
            holder.comment=(TextView) convertView.findViewById(R.id.comment);
            convertView.setTag(holder);
        }else{
            //fill data
            //ViewHolder holder = (ViewHolder) rowView.getTag();
            holder= (ViewHolder) convertView.getTag();
        }



        BuiltObject commentObject = mComment.get(position);

        //username
        String username = commentObject.getString("username");
        holder.commentusername.setText(username);

        //commenttime
        String commenttime = commentObject.getString("time_comm");
        holder.commenttime.setText(commenttime);

        //comment
        String comment = commentObject.getString("comment");
        holder.comment.setText(comment);

        return convertView;



    }


    public static class ViewHolder{
        TextView comment;
        TextView commentusername;
        TextView commenttime;

    }

}
