package com.szhua.ninegridview;

import android.content.Context;
import android.widget.ImageView;

/**
 * Created by szhua on 2016/11/29.
 * nineGridview imageView controlUtil ;
 */
public abstract class NineGridViewImageControl<T> {


      protected abstract void displayImaView(ImageView imageView, T data);

      protected abstract void onImageViewClick(int positon, T data);
    /**
     * 生成ImageView；
     * @param context
     * @return
     */
    public ImageView generateImageView(Context context){
        ImageView imageView =new ImageView(context) ;
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return  imageView;
    }


}
