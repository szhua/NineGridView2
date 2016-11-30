package com.szhua.ninegridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;


/**
 * Created by szhua on 2016/11/29.
 * nineGridview imageView controlUtil ;
 */
public abstract class NineGridViewImageControl<T> {


      protected abstract void displayImaView(ImageView imageView, T data ,int dataSize);

      protected abstract void onImageViewClick(int positon, T data);


    /**
     * 生成ImageView；
     * @param context
     * @return
     */
    public ImageView generateImageView(Context context){
        ImageView imageView =new ImageView(context);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        return  imageView;
    }


}
