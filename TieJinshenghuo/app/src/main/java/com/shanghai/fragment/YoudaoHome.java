package com.shanghai.fragment;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import com.shanghai.R;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/14  9:59
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.fragment
 */
public class YoudaoHome extends Fragment implements View.OnClickListener {
    private View view;
    private EditText et_youdao = null;
    private Button btn_youdao;
    private TextView tv_youdao; //

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.youdaohome, null);
        et_youdao = (EditText) view.findViewById(R.id.et_youdao);
        btn_youdao = (Button) view.findViewById(R.id.btn_youdao);
        btn_youdao.setOnClickListener(this);
        tv_youdao = (TextView) view.findViewById(R.id.tv_youdao);
        return view;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_youdao:
                String data = et_youdao.getText().toString().trim();
                AsyncHttpClient client = new AsyncHttpClient(true, 80, 443);

                RequestParams params = new RequestParams();

                params.put("key", "aef63bbe769fae375e86238ec11b84eb");
                params.put("word", data);
                client.get("http://japi.juhe.cn/youdao/dictionary.from", params, new AsyncHttpResponseHandler() {
                    @Override
                    public void onSuccess(int i, Header[] headers, byte[] bytes) {
                        xLog(new String(bytes));
                        try {
                            JSONObject jsonObject = new JSONObject(new String(bytes));
                            String code = jsonObject.getString("error_code");
                            if (code.equals("0")) {
                                JSONObject jsonObject1 = new JSONObject(jsonObject.getJSONObject("result").toString());
                                final JSONObject jsonObject2 = new JSONObject(jsonObject1.getJSONObject("data").toString());
                                xLog("============" + jsonObject2.getJSONArray("translation").get(0));
                                getActivity().runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            for (int i = 0; i < jsonObject2.getJSONArray("translation").length(); i++) {
                                                tv_youdao.append("\n" + jsonObject2.getJSONArray("translation").get(i));
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int i, Header[] headers, byte[] bytes, Throwable throwable) {
                        xLog("error超时");

                    }
                });
                break;
        }
    }


    public void xLog(String msg) {

        Log.w("NewsClient------->>>>>>", msg);
    }
}
