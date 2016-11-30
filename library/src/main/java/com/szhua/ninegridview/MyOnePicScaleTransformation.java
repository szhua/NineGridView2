package com.szhua.ninegridview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;

import com.squareup.picasso.Transformation;

/**
 * Created by 单志华 on 2016/11/30.
 */
public class MyOnePicScaleTransformation implements Transformation {
    private static  final int MINHEIGHT =250 ;
    private static final int MAXHEIGHT =150 ;

    private Context context ;
     public MyOnePicScaleTransformation(Context context){
         this.context =context ;
     }

    @Override
    public Bitmap transform(Bitmap source) {
        float scale =1 ;
        int  widthPx =source.getWidth() ;
        int heightPx =source.getHeight() ;
        int width =px2dip(context,source.getWidth());
        float height =px2dip(context,source.getHeight());
        if(width<MINHEIGHT&&height<MINHEIGHT){
            scale = (float) Arith.div(MINHEIGHT,height);
        }
        if(width>MAXHEIGHT&&height>MAXHEIGHT){
            scale = (float) Arith.div(MAXHEIGHT,height);
        }
        if(width>MAXHEIGHT&&height<MAXHEIGHT){
            scale = (float) Arith.div(MAXHEIGHT,width);
        }
        if(width<MAXHEIGHT&&height>MAXHEIGHT){
            scale = (float) Arith.div(MAXHEIGHT,height);
        }

        Bitmap bitmap = Bitmap.createBitmap((int)Arith.mul(widthPx,scale), (int)Arith.mul(heightPx,scale), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        Paint paint = new Paint();
        BitmapShader shader =
                new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP);
        if (width != 0 || height != 0) {
            // source isn't square, move viewport to center
            Matrix matrix = new Matrix();
            matrix.setScale(scale, scale);
            shader.setLocalMatrix(matrix);
        }
        paint.setShader(shader);
        paint.setAntiAlias(true);
        canvas.drawRect(0,0,widthPx*scale,heightPx*scale,paint);
        source.recycle();

        return bitmap;
    }
    @Override
    public String key() {
        return "MyOnePicScaleTransformation();";
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue
     * @param context
     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        /**
         * 获得资源比例 ；（不同的手机这个比率可能会不一样）
         */
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

}
