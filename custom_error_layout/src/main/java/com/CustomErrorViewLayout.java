package com;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.customerrorlayout.R;

import java.util.ArrayList;
import java.util.List;

/**
 * @author LiHongCheng
 * @version V1.0
 * @Description: 当页面加载数据失败，数据为空此布局可以显示对应的视图给用户
 * @mail diosamolee2014@gmail.com
 * @date 2017/12/19 11:45
 */
public class CustomErrorViewLayout extends FrameLayout implements View.OnClickListener {
    private final Context mContext;
    private ImageView mImage;
    private TextView mText;
    private View mErrorView;

    private Handler mHandler=new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            showError(R.drawable.nowifi,"网络连接失败,请确保网络正常\n点击屏幕刷新");
        }
    };
    private ProgressBar mProgressBar;
    private int timeoutId;
    private String timeoutText;


    /**
     * 设置视图超时时间
     * @param delayMillis
     */
    public void setDelayMillis(int delayMillis) {
        mDelayMillis = delayMillis;
    }

    /**
     * 网络请求超时
     */
    private int mDelayMillis;

    /**
     * 暴露ImageView 方便实列设置
     * @return
     */
    public ImageView getImage() {
        return mImage;
    }

    /**
     * 暴露TextView 方便实列设置
     * @return
     */
    public TextView getText() {
        return mText;
    }


    public CustomErrorViewLayout(Context context) {
        this(context,null);
    }

    public CustomErrorViewLayout(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomErrorViewLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext=context;
        init();
    }

    /**
     * 超时的图片和时间
     * @param resId
     */
    public void setTimeoutRes(int resId,String text){
        timeoutId =resId;
        timeoutText =text;
    }

    /**
     * The succeed state
     */
    public final static int TYPE_GONE = 0;
    /**
     * The loading state
     */
    private int TYPE_LOADING=1;
    /**
     * The error state
     */
    public final static int TYPE_CUSTOM = 2;
    /**
     * 记录当前视图的状态
     * 默认是0
     */
    public int currentState;
    /**
     * 存放子类视图
     */
    private List<View> childViews;
    /**
     * 回调出视图的点击事件
     */
    public CustomErrorViewLayoutClickListenler mCustomErrorViewLayoutClickListenler;
    public void setCustomErrorViewLayoutClickListenler(CustomErrorViewLayoutClickListenler customErrorViewLayoutClickListenler) {
        mCustomErrorViewLayoutClickListenler = customErrorViewLayoutClickListenler;
    }
    public interface CustomErrorViewLayoutClickListenler {
        void customErrorClick(View view);
    }


    /**
     *  初始化
     */
    private void init() {
        childViews=new ArrayList<>();
        mErrorView = View.inflate(mContext, R.layout.empty_error_layout, null);
        mImage = (ImageView) mErrorView.findViewById(R.id.image);
        mText = (TextView) mErrorView.findViewById(R.id.text);
        mProgressBar = (ProgressBar) mErrorView.findViewById(R.id.progressbar);
        mErrorView.setVisibility(GONE);
        mErrorView.setOnClickListener(this);
        addView(mErrorView);
        mDelayMillis=10000;
    }


    /**
     * 展示错误信息
     * @param resId 图片资源id
     * @param text
     */
    public void showError(int resId,String text){
        showAbnormalView(resId,text);
        currentState= TYPE_CUSTOM;
    }

    public void showLoading(){
        mErrorView.setVisibility(VISIBLE);
        mProgressBar.setVisibility(VISIBLE);
        mImage.setVisibility(GONE);
        mText.setText("加载中");
        currentState= TYPE_LOADING;
    }



    /**
     * 显示异常视图
     */
    private void showAbnormalView(int resId, String text) {
        if (currentState==TYPE_LOADING){
            mProgressBar.setVisibility(GONE);
        }
        mImage.setImageResource(resId);
        mText.setText(text);
        mErrorView.setVisibility(VISIBLE);
        mImage.setVisibility(VISIBLE);
        getChildViews();
        hideChildView();
        mHandler.removeMessages(0);
    }
    /**
     *隐藏加入异常的视图,显示正常的子View
     * currentState默认是0的时候，会不执行内容，所以对性能无影响
     */
    public void hide(){
        if (TYPE_GONE !=currentState){
            mErrorView.setVisibility(GONE);
            showChildView();
            currentState=TYPE_GONE;
            mHandler.removeMessages(0);
        }
    }


    /**
     * 判断view 对象是否是EmptyOrErrorView
     * @param view
     * @return
     */
    private boolean isEmptyView(View view){
        if ((view == null|| mErrorView== view)){
            return true;
        }
        return false;
    }


    /**
     * 隐藏所有的子View
     */
    private void hideChildView(){
        for (View view: childViews ) {
            if (isEmptyView(view)){
                continue;
            }
            view.setVisibility(GONE);
        }
    }

    /**
     * 获取除了对象是否是EmptyOrErrorView 的子View
     */
    private void getChildViews(){
        int childCount = getChildCount();
        View view;
        for (int i=0;i<childCount;i++){
            view = getChildAt(i);
            if (isEmptyView(view)){
                continue;
            }
            childViews.add(view);
        }
    }
    /**
     * 显示除了EmptyOrErrorView 的子View
     */
    private void showChildView(){
        for (View view: childViews ) {
            if (isEmptyView(view)){
                continue;
            }
            view.setVisibility(VISIBLE);
        }
    }


    /**
     * 视图的点击事件
     * @param view
     *
     *
     *
     */
    @Override
    public void onClick(View view) {
        if (mCustomErrorViewLayoutClickListenler!=null && currentState != TYPE_LOADING ){
            mHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (timeoutText!=null){
                        showError(timeoutId,timeoutText);
                    }else {
                        CELMannager.exceptionDetail(CEL.ERROR,CustomErrorViewLayout.this);
                    }
                }
            },mDelayMillis);
            showLoading();
            mCustomErrorViewLayoutClickListenler.customErrorClick(view);
        }
    }
}
