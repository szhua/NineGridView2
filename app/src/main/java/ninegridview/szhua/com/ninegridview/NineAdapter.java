package ninegridview.szhua.com.ninegridview;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * Created by szhua on 2016/11/28.
 */
public class NineAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private ArrayList<ArrayList<String>> lists =new ArrayList<>() ;

    public void setLists(ArrayList<ArrayList<String>> lists) {
        this.lists = lists;
        notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view =View.inflate(parent.getContext(),R.layout.item,null) ;
        MyViewHolder myViewHolder =new MyViewHolder(view) ;
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
         holder.nineGridView.setImagePaht(lists.get(position));
    }

    @Override
    public int getItemCount() {
        return lists.size();
    }

}
