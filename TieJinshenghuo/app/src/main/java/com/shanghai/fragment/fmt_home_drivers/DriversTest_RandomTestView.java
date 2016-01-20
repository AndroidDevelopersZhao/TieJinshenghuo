package com.shanghai.fragment.fmt_home_drivers;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.shanghai.R;
import com.shanghai.data.data_drivers.All_Data;
import com.shanghai.data.data_drivers.Result;
import com.shanghai.listener.listener_getimages.OnGetImagesListener;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;

import java.util.Random;

import xinfu.com.pidanview.alerterview.progress.SVProgressHUD;

/**
 * Created by Administrator on 2016/1/20.
 */
public class DriversTest_RandomTestView extends Fragment implements View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private View view;
    private String TAG = "NewClient";
    private int subject = -1;
    private String model = null;
    private String testType = null;
    private ImageView im_drivers;
    private RadioButton rb_drivers_item1, rb_drivers_item2, rb_drivers_item3, rb_drivers_item4;
    private Button btn_drivers;
    private TextView tv_drivers;
    private RadioGroup group ;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fmt_driverstest_randomtest, null);
        try {
            subject = getArguments().getInt("subject");
            model = getArguments().getString("model");
            testType = getArguments().getString("testType");
            initView();
        } catch (Throwable throwable) {
            Toast.makeText(getActivity(), "传入参数接收失败", Toast.LENGTH_LONG).show();
        }


        return view;
    }

    private Handler handler_getRequestions = null;
    private String answer = null;

    private void initView() {
        im_drivers = (ImageView) view.findViewById(R.id.im_drivers);
        rb_drivers_item1 = (RadioButton) view.findViewById(R.id.rb_drivers_item1);
        rb_drivers_item2 = (RadioButton) view.findViewById(R.id.rb_drivers_item2);
        rb_drivers_item3 = (RadioButton) view.findViewById(R.id.rb_drivers_item3);
        rb_drivers_item4 = (RadioButton) view.findViewById(R.id.rb_drivers_item4);
        btn_drivers = (Button) view.findViewById(R.id.btn_drivers);
        btn_drivers.setOnClickListener(this);
        tv_drivers = (TextView) view.findViewById(R.id.tv_drivers);
        rb_drivers_item1.setTag("item1");
        rb_drivers_item2.setTag("item2");
        rb_drivers_item3.setTag("item3");
        rb_drivers_item4.setTag("item4");
        group= (RadioGroup) view.findViewById(R.id.group);
        group.setOnCheckedChangeListener(this);
        Log.d(TAG, "接收到参数---科目：" + subject + ",驾照类型：" + model + ",测试类型：" + testType);
        //接收的handler
        SVProgressHUD.showWithStatus(getActivity(), "正在获取题目...");
        handler_getRequestions = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (SVProgressHUD.isShowing(getActivity())) {
                    SVProgressHUD.dismiss(getActivity());
                }
                switch (msg.what) {
                    case 1:
                        Result result = (Result) msg.getData().getSerializable("data");
                        Log.d(TAG, "请求成功，接收到单一对象成功，问题为：" + result.getQuestion());
                        answer = result.getAnswer();
                        if (result.getQuestion() != null && !result.getQuestion().equals("")) {
                            tv_drivers.setText(result.getQuestion());
                        }
                        if (result.getItem1() != null && !result.getItem1().equals("")) {
                            rb_drivers_item1.setVisibility(View.VISIBLE);
                            rb_drivers_item1.setText(result.getItem1());
                        }
                        if (result.getItem2() != null && !result.getItem2().equals("")) {
                            rb_drivers_item2.setVisibility(View.VISIBLE);
                            rb_drivers_item2.setText(result.getItem2());
                        }
                        if (result.getItem3() != null && !result.getItem3().equals("")) {
                            rb_drivers_item3.setVisibility(View.VISIBLE);
                            rb_drivers_item3.setText(result.getItem3());
                        }
                        if (result.getItem4() != null && !result.getItem4().equals("")) {
                            rb_drivers_item4.setVisibility(View.VISIBLE);
                            rb_drivers_item4.setText(result.getItem4());
                        }
                        if (result.getUrl() != null && !result.getUrl().equals("")) {
                            Util.getImagesFromInternet(result.getUrl(), new OnGetImagesListener() {
                                @Override
                                public void onSucc(final Bitmap bitmap) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            im_drivers.setImageBitmap(bitmap);
                                        }
                                    });
                                }

                                @Override
                                public void onError(String errorMsg) {

                                }
                            });

                        }
                        break;
                    case -1:
                        String errormsg = msg.getData().getString("data");
                        Log.d(TAG, "请求失败，" + errormsg);
                        break;
                }
            }
        };
        getTestQuestions(subject, model, testType);
    }

    private void getTestQuestions(int subject, String model, String testType) {
        Log.d(TAG, "开始请求,请求参数,科目：" + subject + ",驾照类型：" + model + ",测试类型：" + testType);
        XXHttpClient client = new XXHttpClient(Util.url_GETTESTREQUESTIONS, true, new XXHttpClient.XXHttpResponseListener() {
            @Override
            public void onSuccess(int i, byte[] bytes) {
                Log.d(TAG, "返回：" + new String(bytes));
                All_Data all_data = new Gson().fromJson(new String(bytes), All_Data.class);
                if (all_data != null && all_data.getError_code() == 0) {
                    Random random = new Random();
                    int r = random.nextInt(all_data.getResult().length);
                    Util.sendMsgToHandler(handler_getRequestions, all_data.getResult()[r], true);
                } else {
                    Util.sendMsgToHandler(handler_getRequestions, all_data.getReason(), false);
                }
            }

            @Override
            public void onError(int i, Throwable throwable) {
                Log.d(TAG, "网络异常");
                Util.sendMsgToHandler(handler_getRequestions, "网络异常", false);
            }

            @Override
            public void onProgress(long l, long l1) {

            }
        });
        client.put("key", "d8cc59e235e37fcb817e2f28e6d5e7ca");
        client.put("subject", subject);
        client.put("model", model);
        client.put("testType", testType);
        client.doPost(15000);

    }

    @Override
    public void onClick(View v) {
        if (aswer == -1) {
            Toast.makeText(getActivity(), "请选择后再点击提交按钮", Toast.LENGTH_LONG).show();
            return;
        }
        if (String.valueOf(aswer).equals(this.answer)) {
            SVProgressHUD.showSuccessWithStatus(getActivity(), "回答正确");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    repleace(new DriversTest_RandomTestView(), String.valueOf(subject), model, testType);
                    onDestroy();
                }
            }).start();


        }else {
            SVProgressHUD.showErrorWithStatus(getActivity(), "错误");
        }

    }

    private void repleace(Fragment fragment, String... strings) {
        Bundle bundle = new Bundle();
        bundle.putInt("subject", Integer.valueOf(strings[0]));
        bundle.putString("model", strings[1]);
        bundle.putString("testType", strings[2]);

        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
//        transaction.addToBackStack("as");
        transaction.replace(R.id.mainView, fragment).commit();
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "当前页面销毁");
        super.onDestroy();
    }
    private int aswer=-1;
    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        if (checkedId==rb_drivers_item1.getId()){
            aswer=1;
        }

        if (checkedId==rb_drivers_item2.getId()){
            aswer=2;
        }
        if (checkedId==rb_drivers_item3.getId()){
            aswer=3;
        }
        if (checkedId==rb_drivers_item4.getId()){
            aswer=4;
        }
    }

//    @Override
//    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//        Log.d(TAG, "点击了：" + buttonView.getTag() + ",是否选中？：" + isChecked);
////        buttonView.setChecked(isChecked ? false : true);
////        switch (String.valueOf(buttonView.getTag())) {
////            case "item1":
//////                if (rb_drivers_item1.isChecked()) {
//////                    rb_drivers_item1.setChecked(false);
//////                } else {
//////                    rb_drivers_item1.setChecked(true);
//////                }
////                if (rb_drivers_item1.isSelected()) {
////                    rb_drivers_item1.setSelected(false);
////                } else {
////                    rb_drivers_item1.setSelected(true);
////                }
//////                rb_drivers_item1.setSelected(rb_drivers_item1.isSelected() ? false : true);
////
////                break;
////            case "item2":
//////                rb_drivers_item2.setChecked(rb_drivers_item2.isChecked() ? false : true);
////                rb_drivers_item1.setSelected(rb_drivers_item1.isSelected() ? false : true);
////                break;
////            case "item3":
////                rb_drivers_item3.setChecked(rb_drivers_item3.isChecked() ? false : true);
////
////                break;
////            case "item4":
////                rb_drivers_item4.setChecked(rb_drivers_item4.isChecked() ? false : true);
////
////                break;
////        }
//    }
}
