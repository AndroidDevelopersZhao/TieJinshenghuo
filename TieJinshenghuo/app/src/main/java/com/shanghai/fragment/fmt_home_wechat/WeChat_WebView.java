package com.shanghai.fragment.fmt_home_wechat;
/**
 * .   如果你认为你败了，那你就一败涂地；
 * .   如果你认为你不敢，那你就会退缩；
 * .   如果你想赢但是认为你不能；
 * .   那么毫无疑问你就会失利；
 * .   如果你认为你输了，你就输了；
 * .   我们发现成功是从一个人的意志开始的；
 * .   成功是一种心态。
 * .   生活之战中，
 * .   胜利并不属于那些更强和更快的人，
 * .   胜利者终究是认为自己能行的人。
 * .
 * .   If you think you are beaten,you are;
 * .   If you think you dare not,you don't;
 * .   If you can to win but think you can't;
 * .   It's almost a cinch you won't.
 * .   If you think you'll lose,you're lost;
 * .   For out of the world we find Success begins with a fellow's will;
 * .   It's all in a state of mind.
 * .   Life's battles don't always go to the stronger and faster man,But sooner or later the man who wins Is the man who thinks he can.
 * .
 * .   You can you do.  No can no bb.
 * .
 */

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.*;

import com.shanghai.R;


/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/18  10:14
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.fragment
 */
public class WeChat_WebView extends Fragment {
    private View view;
    private android.webkit.WebView wv_wechat;

    @Nullable //
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.wechatwebview, null);
        initView();
        return view;
    }

    private void initView() {
        String url = getArguments().getString("url");
        wv_wechat = (android.webkit.WebView) view.findViewById(R.id.wv_wechat);
        wv_wechat.loadUrl(url);
        wv_wechat.getSettings().setJavaScriptEnabled(true);
        wv_wechat.getSettings().setPluginState(WebSettings.PluginState.ON);
//        wv_wechat.getSettings().setPluginsEnabled(true);//可以使用插件
        wv_wechat.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        wv_wechat.getSettings().setAllowFileAccess(true);
        wv_wechat.getSettings().setDefaultTextEncodingName("UTF-8");
        wv_wechat.getSettings().setLoadWithOverviewMode(true);
        wv_wechat.getSettings().setUseWideViewPort(true);
        wv_wechat.setVisibility(View.VISIBLE);
        wv_wechat.getSettings().setJavaScriptEnabled(true);
        wv_wechat.getSettings().setMediaPlaybackRequiresUserGesture(true);
        wv_wechat.setWebViewClient(new WebViewClient());
//        wv_wechat.setSaveEnabled(true);
    }

    @Override
    public void onDestroy() {
        wv_wechat.destroy();
        super.onDestroy();
    }
}
