package com.szhua.ninegridview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.IllegalFormatException;

/**
 * Created by szhua  on 2016/11/28.
 * 仿微信九宫格;
 */
public class NineGridView<T> extends ViewGroup {

    /**
     * 默认的margin;
     */
    private final float DEFAULT_MARGIN_SIZE  = 10;
    /**
     * 默认的一个图片style ;
     */
    private final int DEFAULT_ONE_PIC_STYE =0 ;
    /*
     默认的四个图片style;
     */
    private final int DEFAULT_FOUR_PIC_STYLE = 0;
    /**
     * 一个图片默认的图片大小
     */
    private final int DEFAULT_ONE_PIC_FIXED_SIZE =200 ;

    /**
     * 图片间距
     */
    private float imageMargin  ;
    /**
     * one_pic_syle ;
     */
    private int one_pic_style ;
    /**
     * four_pic_style ;
     */
    private int four_pic_style ;
    /**
     * 行数 ；
     */
    private  int row_count  ;
    /**
     * 列数 ；
     */
    private  int coluum_count ;
    /**
     * 当个imageView的size ；width =height ;
     */
    private int  singleImageViewSize;

    /**
     * 一个图片固定的大小 ；
     */
    private int onePicFixedSize;


    /**
     * 需要填充的数据 ；
     */
    private ArrayList<T> imageDatas ;

    /**
     * 图片显示创建操作控制类；
     */
    private   NineGridViewImageControl<T> nineGridViewImageControl ;

    /**
     * 图片缓存类;节省内存；(创建Imageview控件是一个特别浪费内存的事情)
     */
    private ArrayList<ImageView> imageViewCache  =new ArrayList<ImageView>();



    public NineGridView(Context context) {
        this(context,null);
    }

    public NineGridView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public NineGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initRes(context,attrs) ;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width =MeasureSpec.getSize(widthMeasureSpec) ;

        int widthForNineGrid =width-getPaddingLeft()-getPaddingRight();
        int height =0 ;
        /**
         * 只有一张图片的时候；
         */
        if(imageDatas!=null&&imageDatas.size()==1){
            /**
             * 图片固定大小的模式 ；
             */
            if(one_pic_style==Constant.ONE_PIC_STYLE_FIXED){
                height =onePicFixedSize+getPaddingLeft()*2;
            }
        }else{
            /**
             * 每个imageView的size ==
             */
            singleImageViewSize = (int) ((widthForNineGrid-imageMargin*2)/3);
            height = (int) (coluum_count*singleImageViewSize+(coluum_count-1)*imageMargin)+getPaddingLeft()*2;
        }
        setMeasuredDimension(width,height);

    }


    /**
     *
     * @param nineGridViewImageControl
     */
    public void setNineGridViewImageControl(NineGridViewImageControl<T> nineGridViewImageControl) {
        this.nineGridViewImageControl = nineGridViewImageControl;
    }


    /**
     * 初始化属性 ；
     * @param context
     * @param  attrs
     *
     */
    private void initRes(Context context ,AttributeSet attrs) {


        TypedArray typeArray =context.obtainStyledAttributes(attrs,R.styleable.NineGridView);

        imageMargin =typeArray.getDimension(R.styleable.NineGridView_imgmargin,DEFAULT_MARGIN_SIZE) ;

        one_pic_style =typeArray.getInteger(R.styleable.NineGridView_one_picture_style,DEFAULT_ONE_PIC_STYE) ;

        four_pic_style =typeArray.getInteger(R.styleable.NineGridView_four_picture_style,DEFAULT_FOUR_PIC_STYLE) ;

        onePicFixedSize = (int) typeArray.getDimension(R.styleable.NineGridView_one_picture_fixed_size,DEFAULT_ONE_PIC_FIXED_SIZE);

        typeArray.recycle();

    }



    /**
     * 设置图片路径;
     * @param imageDatas
     * 这里添加缓存的处理 ；
     */
    public void setImagePaht(ArrayList<T> imageDatas){

        /**
         * 必须进行设置nineGridViewImageControl
         */
           if(nineGridViewImageControl==null){
               throw  new  IllegalArgumentException("Please set your nineGridViewImageControl firstly , NineGridView needs it firstly");
           }

        /**
         * 若是没有数据的话，不显示本控件；
         */
            if(imageDatas==null||imageDatas.isEmpty()){
                this.setVisibility(GONE);
            }else{


                this.setVisibility(VISIBLE);

                //若是图片的数量大于9的话，只取前9个数据 ；
                if(imageDatas.size()>9){
                    imageDatas.subList(0,9);
                }

                 // 计算行列数 ；
                 calculateGridNumbers(imageDatas.size());

                 // 若是第一次添加数据
                if(imageDatas==null){
                    for (int i=0;i<imageDatas.size(); i++) {

                        //生成新的imageView；
                        ImageView imageView =generateImageView(i);
                        addView(imageView ,generateDefaultLayoutParams());
                        imageViewCache.add(imageView);
                    }
                }else{
                    int oldImageSize =imageDatas.size();
                    int newImageSize =imageDatas.size();
                    /**
                     * 图片少的话 ;
                     */
                    if(oldImageSize>newImageSize){
                        /**
                         * 得到减少的view，跟新数据，从viewGroup中去除 ；
                         */
                       removeViews(newImageSize,oldImageSize-newImageSize);

                        //使用removeview时候，当remove的时候子view的postion也发生了相应的改变，所以导致失败
                        // TODO: 2016/11/28   对于数组的操作，遍历不能删除，对其构造产生影响；
                    }

                    if(oldImageSize<newImageSize){
                        /**
                         * 得到增加的view，跟新数据，从viewGroup中添加 ；
                         */
                        for (int i =oldImageSize;i<newImageSize;i++){
                            ImageView imageView =generateImageView(i);
                            addView(imageView,generateDefaultLayoutParams());
                        }

                    }
                }
            }
        /**
         * 重新赋值；
         */
        this.imageDatas =imageDatas ;
        /**
         * 请求重新布局;
         */
        requestLayout();
    }


    /**
     * 创建ImageView；
     */
    public ImageView generateImageView(int position){
        ImageView imageView =null ;

        /*
         缓存有的话从缓存中获得;
         */
        if(imageViewCache.size()>position){
             imageView = imageViewCache.get(position) ;
             return  imageView ;
        }
         imageView =nineGridViewImageControl.generateImageView(getContext()) ;

         imageViewCache.add(imageView) ;

         return  imageView ;
    }




    @Override
    protected void onLayout(boolean b, int x, int i1, int i2, int i3) {

               if(imageDatas==null||imageDatas.isEmpty()){
                   return;
               }

                if(imageDatas.size()==1&&one_pic_style==Constant.ONE_PIC_STYLE_FIXED){
                    ImageView imageView = (ImageView) getChildAt(0);
                    int left =getPaddingLeft();
                    int right =onePicFixedSize +left;
                    int top = getPaddingLeft() ;
                    int bottom =top +onePicFixedSize ;
                    imageView.layout(left,right,top,bottom);
                    return;
                }


            //遍历整个数据库，设置ImageView的位置；
            for (int i =0;i<imageDatas.size(); i++) {

            ImageView imageview = (ImageView) getChildAt(i);

              //为imageView设置数据 ;
              nineGridViewImageControl.displayImaView(imageview, imageDatas.get(i));
              //设置点击事件；
              nineGridViewImageControl.onImageViewClick(i, imageDatas.get(i));


              //行
              int rowIndex;
              //列；
              int columnIndex;

              if (imageDatas.size() == 4 && four_pic_style == Constant.FOUR_PIC_STYLE_2_2_STYLE) {
                  rowIndex = i / 2 + 1;
                  columnIndex = i % 2 + 1;
              } else {
                  rowIndex = i / 3 + 1;
                  columnIndex = i % 3 + 1;
              }


              int left = (int) ((columnIndex - 1) * singleImageViewSize + (columnIndex - 1) * imageMargin) + getPaddingLeft();
              int right = left + singleImageViewSize;
              int top = (int) ((rowIndex - 1) * singleImageViewSize + (rowIndex - 1) * imageMargin) + getPaddingLeft();
              int bottom = top + singleImageViewSize;

              imageview.layout(left, top, right, bottom);
          }
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


    /**
     * 根据图片的数量计算行数和列数；
     * @param picsNum
     */
    public void  calculateGridNumbers( int  picsNum ){
        row_count =3;
        coluum_count = (picsNum-1)/3+1;
    }

}
