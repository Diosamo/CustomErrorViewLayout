package com;


import com.customerrorlayout.R;

/**
 * @author LiHongCheng
 * @version V1.0
 * @Description: 对对应视图进行处理,显示不同的提示
 * @mail diosamolee2014@gmail.com
 * @date 2017/12/20 08:45
 */

public class CELMannager {

    public static void exceptionDetail(int code, CustomErrorViewLayout eoe) {
        if (eoe==null) return;
        switch (code) {
            //表示成功将布局隐藏
            case 0:
                eoe.hide();
                break;
            //错误视图
            case 110:
                eoe.showError(R.drawable.errorpage,"系统繁忙,点击屏幕刷新");
                break;
            //视图为空
            case 111:
                eoe.showError(R.drawable.nullpage,"没有内容了");
                break;
            //没有网络
            case 112:
                eoe.showError(R.drawable.nowifi,"网络连接失败,请确保网络正常\n点击屏幕刷新");
                break;
            //加载中
            case 113:
                eoe.showLoading();
                break;

            default:
                break;
        }
    }


}
