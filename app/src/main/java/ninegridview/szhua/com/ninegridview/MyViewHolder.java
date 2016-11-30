package ninegridview.szhua.com.ninegridview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import com.szhua.ninegridview.NineGridView;
import com.szhua.ninegridview.NineGridViewImageControl;

/**
 * Created by 单志华 on 2016/11/28.
 */
public class MyViewHolder extends RecyclerView.ViewHolder {
    public NineGridView nineGridView ;
    public ImageView imageView ;
    public MyViewHolder(View itemView) {
        super(itemView);
        nineGridView = (NineGridView) itemView.findViewById(R.id.nine);
        imageView = (ImageView) itemView.findViewById(R.id.imageView);
    }


}
