# CustomErrorViewLayout

这是一个可以快速给你的布局添加错误视图的库,而且非常轻量级,使用简单.


![](http://upload-images.jianshu.io/upload_images/9352581-868540e78e76fc82.gif?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

#### 添加的gradle
第一步：  
Add it in your root build.gradle at the end of repositories:

	repositories {
	    maven {
			url  'https://bintray.com/diosamolee/CustomView' 
	    }
	}
第二步：(也可以直接使用下面依赖,如果使用不行,就按步骤来)  
Step 2. Add the dependency

		dependencies {
	       		 compile 'com.custom_errorview_layou:CustomErrorViewLayout:1.0'
		}

### 写入xml

1.直接通过写入xml使用,然后给控件添加id

```xml
 <com.CustomErrorViewLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
            <View
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"/>
    </com.layout.CustomErrorViewLayout>
```


2. 使用下面Activity中的代码

```java
 public class MainActivity extends AppCompatActivity implements CustomErrorViewLayout.CustomErrorViewLayoutClickListenler {

    private CustomErrorViewLayout mCustomErrorViewLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //查找控件
        mCustomErrorViewLayout = findViewById(R.id.cevl);
        //设置控件的点击事件回调到 下面的customErrorClick方法
        mCustomErrorViewLayout.setCustomErrorViewLayoutClickListenler(this);
        //设置Loading超时错误显示的视图,和提示
        mCustomErrorViewLayout.setTimeoutRes(R.drawable.errorpage,"请求错误,点击刷新");

                //使用默认的CELMannger使用库中原生的状态样式
					//空试图
                // CELMannager.exceptionDetail(CEL.EMPTY,mCustomErrorViewLayout);
					//错误视图
                // CELMannager.exceptionDetail(CEL.ERROR,mCustomErrorViewLayout);
					//加载中
                // CELMannager.exceptionDetail(CEL.LOADING,mCustomErrorViewLayout);
					//网络错误
                // CELMannager.exceptionDetail(CEL.NO_NETWORK,mCustomErrorViewLayout);
					//隐藏视图
                // CELMannager.exceptionDetail(CEL.GONE,mCustomErrorViewLayout);
    }


// 错误控件的回调
    @Override
    public void customErrorClick(View view) {
        Toast.makeText(this, "点击了错误的控件", Toast.LENGTH_SHORT).show();
    }
}


```


### Api介绍:

	showError() //改变视图中的图片和提示,主要通过这个方法去设置layout 中的改变
	hide() //隐藏所有布局显示子布局中的内容
	setTimeoutRes() //设置点击超时内容
	setCustomErrorViewLayoutClickListenler() //设置视图点击事件,不设置点击没有效果
	getImage() 获取视图中ImageView对象可以自己改变大小和位置,图片
	getText()	获取视图中TextView对象可以自己改变大小和位置,颜色
	setDelayMillis() 设置超时时间默认是10秒
	CELMannager.exceptionDetail();    //默认库中资源控制者,
	可以传入CEL.EMPTY  ,CEL.ERROR  ,CEL.LOADING  ,CEL.NO_NETWORK  ,CEL.GONE  ,这几种状态加CustomErrorViewLayout显示不同视图



### 如果有疑问,或者遇见问题,可以发送到我的邮箱diosamolee2014@gmail.com
