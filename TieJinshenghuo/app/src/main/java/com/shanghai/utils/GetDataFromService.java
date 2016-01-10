package com.shanghai.utils;
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

import android.support.annotation.NonNull;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.shanghai.listener.GetDataListener;

import org.apache.http.Header;


/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/16  10:41
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.utils
 */
public class GetDataFromService {
    private AsyncHttpClient client = null;
    private RequestParams parmas = null;
    private String url;
    private GetDataListener getDataListener;

    public GetDataFromService(String url) {
        this.url = url;
        client = new AsyncHttpClient(true, 80, 443);
        parmas = new RequestParams();
    }

    public GetDataFromService(String url, int timeOut) {
        this.url = url;
        client = new AsyncHttpClient(true, 80, 443);
        client.setTimeout(timeOut);
        parmas = new RequestParams();
    }

    public void put(String key, Object v) {
        parmas.put(key, v);
    }

    public void doGet(final GetDataListener getDataListener) {
        client.get(url, parmas, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                getDataListener.onSucc(bytes);

            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                getDataListener.onError("网络异常");
            }
        });
    }

    public void doPost(final GetDataListener getDataListener) {
        client.post(url, parmas, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int i, Header[] headers, byte[] bytes) {
                getDataListener.onSucc(bytes);
            }

            @Override
            public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                getDataListener.onError("网络异常");
            }
        });
    }
}
