package ebis.com.example.aakash.messenger;

/**
 * Created by aakash on 3/25/2015.
 */
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

/**
 * Created by HOME on 2/21/2015.
 */
public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
    private LayoutInflater inflater;
    List<Information> data = Collections.emptyList();
    private Context context;
    private ClickListener clickListener;
    public Adapter(Context context, List<Information> data) {
        this.context = context;
        inflater = LayoutInflater.from(context);
        this.data = data;

    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.custom__row, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Information current = data.get(position);
        holder.title.setText(current.title);
        holder.icon.setImageResource(current.iconId);

    }
    public void setClickListener(ClickListener clickListener){
        this.clickListener=clickListener;
    }





    @Override
    public int getItemCount() {
        return data.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title;
        ImageView icon;

        public MyViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            title = (TextView) itemView.findViewById(R.id.listText);
            icon = (ImageView) itemView.findViewById(R.id.listIcon);
        }

        @Override
        public void onClick(View v) {
            context.startActivity(new Intent(context, ChatActivity.class));
            context.startActivity(new Intent(context,ForumActivity.class));
            if(clickListener!=null)
            {
                clickListener.itemClicked(v,getPosition());
            }
        }


    }


    public interface ClickListener {
        public void itemClicked(View view, int position);
    }


}


