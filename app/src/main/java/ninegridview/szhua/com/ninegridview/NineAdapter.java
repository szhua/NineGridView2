package ninegridview.szhua.com.ninegridview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.szhua.ninegridview.MyOnePicScaleTransformation;
import com.szhua.ninegridview.NineGridViewImageControl;

import java.util.ArrayList;

/**
 * Created by szhua on 2016/11/28.
 */
public class NineAdapter extends RecyclerView.Adapter<MyViewHolder> {



    private Context context ;

    private ArrayList<ArrayList<String>> lists =new ArrayList<>() ;

    public void setLists(ArrayList<ArrayList<String>> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    public NineAdapter(Context context){
        this.context =context ;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =View.inflate(parent.getContext(),R.layout.item,null) ;
        MyViewHolder myViewHolder =new MyViewHolder(view) ;
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
         ArrayList<String> strings =lists.get(position) ;
         holder.nineGridView.setNineGridViewImageControl(new NineControl());
         holder.nineGridView.setImagePaht(lists.get(position));
        if(strings.size()==1){
            holder.nineGridView.setVisibility(View.GONE);
            holder.imageView.setVisibility(View.VISIBLE);
            Picasso.with(context).load("https://gss0.bdstatic.com/7051cy89RcgCncy6lo7D0j9wexYrbOWh7c50/zhidaoribao/2016/1130/yq.jpg")
                    .transform(new MyOnePicScaleTransformation(context)).placeholder(R.drawable.ic).into(holder.imageView);
        }else{
            holder.nineGridView.setVisibility(View.VISIBLE);
            holder.imageView.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }


    class NineControl extends NineGridViewImageControl {
        @Override
        protected void displayImaView( final ImageView imageView, Object data , final int dataSize) {
            Picasso.with(context).load("http://img4.duitang.com/uploads/item/201509/28/20150928193800_Z4Jdv.jpeg")
                    .placeholder(com.szhua.ninegridview.R.drawable.ic).into(imageView);
        }
        @Override
        protected void onImageViewClick(int positon, Object data) {
        }

    }


}
