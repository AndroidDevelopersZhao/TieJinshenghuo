package com.shanghai.fragment.fmt_home_drivers;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.shanghai.R;
import com.shanghai.soeasylib.util.XXHttpClient;
import com.shanghai.utils.Util;
import com.shanghai.utils.ViewHolder;
import com.shanghai.utils.util_drivers.DriversFinal;

import xinfu.com.pidanview.alerterview.alerterview.AlertView;
import xinfu.com.pidanview.alerterview.alerterview.OnItemClickListener;

/**
 * Created by Administrator on 2016/1/20.
 */
public class DriversTest_Select_Questions extends Fragment implements View.OnClickListener {
    private View view;
    private Button btn_driverstest_1, btn_driverstest_2;
    private String TAG = "NewClient";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.driverstest, null);
        initView();
        return view;
    }

    private void initView() {
        btn_driverstest_1 = (Button) view.findViewById(R.id.btn_driverstest_1);
        btn_driverstest_2 = (Button) view.findViewById(R.id.btn_driverstest_2);

        btn_driverstest_1.setOnClickListener(this);
        btn_driverstest_2.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_driverstest_1:
                showAlertView("请选择驾照类型", new OnItemClickListener() {
                    @Override
                    public void onItemClick(Object o, int position) {
                        switch (position) {
                            case 0:
                                Log.d(TAG, "A1");
                                selectType(DriversFinal.TYPE_A1);
                                break;
                            case 1:
                                Log.d(TAG, "A2");
                                selectType(DriversFinal.TYPE_A2);
                                break;
                            case 2:
                                Log.d(TAG, "B1");
                                selectType(DriversFinal.TYPE_B1);
                                break;
                            case 3:
                                Log.d(TAG, "B2");
                                selectType(DriversFinal.TYPE_B2);
                                break;
                            case 4:
                                Log.d(TAG, "C1");
                                selectType(DriversFinal.TYPE_C1);
                                break;
                            case 5:
                                Log.d(TAG, "C2");
                                selectType(DriversFinal.TYPE_C2);
                                break;
                            case -1:

                                break;

                        }
//                        Log.d(TAG, "随机测试");


                    }
                }, "取消", "A1", "A2", "B1", "B2", "C1", "C2");
                break;
            case R.id.btn_driverstest_2:

                break;

        }
    }


    private void showAlertView(String msg, OnItemClickListener onItemClickListener, String cancle, String... strings) {

        new AlertView("提示", msg, cancle, strings, null, getActivity(), AlertView.Style.ActionSheet, onItemClickListener).show();
    }

    private int finalType = -1;

    public void selectType(final int Type) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(400);
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            show(Type);
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

    private String[] getTestRequetionsType = null;

    private void show(final int Type) {
        new AlertView("提示", "请选择测试类型", "取消", new String[]{"随机测试", "顺序测试"}, null, getActivity(), AlertView.Style.Alert, new OnItemClickListener() {
            @Override
            public void onItemClick(Object o, int position) {
                switch (position) {
                    case 0:
                        Log.d(TAG, "随机测试");
                        switch (Type) {
                            case DriversFinal.TYPE_A1:
                                finalType = DriversFinal.FINAL_TYPE_A1_RANDOM;

                                break;
                            case DriversFinal.TYPE_A2:
                                finalType = DriversFinal.FINAL_TYPE_A2_RANDOM;
                                break;
                            case DriversFinal.TYPE_B1:
                                finalType = DriversFinal.FINAL_TYPE_B1_RANDOM;
                                break;
                            case DriversFinal.TYPE_B2:
                                finalType = DriversFinal.FINAL_TYPE_B2_RANDOM;
                                break;
                            case DriversFinal.TYPE_C1:
                                finalType = DriversFinal.FINAL_TYPE_C1_RANDOM;
                                break;
                            case DriversFinal.TYPE_C2:
                                finalType = DriversFinal.FINAL_TYPE_C2_RANDOM;
                                break;
                            default:
                                finalType = -1;
                                break;

                        }
                        break;

                    case 1:
                        Log.d(TAG, "顺序测试");
                        switch (Type) {
                            case DriversFinal.TYPE_A1:
                                finalType = DriversFinal.FINAL_TYPE_A1_NEXT;
                                break;
                            case DriversFinal.TYPE_A2:
                                finalType = DriversFinal.FINAL_TYPE_A2_NEXT;
                                break;
                            case DriversFinal.TYPE_B1:
                                finalType = DriversFinal.FINAL_TYPE_B1_NEXT;
                                break;
                            case DriversFinal.TYPE_B2:
                                finalType = DriversFinal.FINAL_TYPE_B2_NEXT;
                                break;
                            case DriversFinal.TYPE_C1:
                                finalType = DriversFinal.FINAL_TYPE_C1_NEXT;
                                break;
                            case DriversFinal.TYPE_C2:
                                finalType = DriversFinal.FINAL_TYPE_C2_NEXT;
                                break;

                        }
                        break;
                    case -1:

                        break;
                }
                switch (finalType) {
                    case DriversFinal.FINAL_TYPE_A1_RANDOM:
                        Log.d(TAG, "最后选择：A1随机测试");
                        getTestRequetionsType = new String[]{"1", "a1", "rand"};

                        break;
                    case DriversFinal.FINAL_TYPE_A2_RANDOM:
                        Log.d(TAG, "最后选择：A2随机测试");
                        getTestRequetionsType = new String[]{"1", "a2", "rand"};
                        break;
                    case DriversFinal.FINAL_TYPE_B1_RANDOM:
                        Log.d(TAG, "最后选择：B1随机测试");
                        getTestRequetionsType = new String[]{"1", "b1", "rand"};
                        break;
                    case DriversFinal.FINAL_TYPE_B2_RANDOM:
                        Log.d(TAG, "最后选择：B2随机测试");
                        getTestRequetionsType = new String[]{"1", "b2", "rand"};
                        break;
                    case DriversFinal.FINAL_TYPE_C1_RANDOM:
                        Log.d(TAG, "最后选择：C1随机测试");
                        getTestRequetionsType = new String[]{"1", "c1", "rand"};
                        break;
                    case DriversFinal.FINAL_TYPE_C2_RANDOM:
                        Log.d(TAG, "最后选择：C2随机测试");
                        getTestRequetionsType = new String[]{"1", "c2", "rand"};
                        break;
                    //TODO 顺序
                    case DriversFinal.FINAL_TYPE_A1_NEXT:
                        Log.d(TAG, "最后选择：A1顺序测试");
                        getTestRequetionsType = new String[]{"1", "a1", "order"};
                        break;
                    case DriversFinal.FINAL_TYPE_A2_NEXT:
                        Log.d(TAG, "最后选择：A2顺序测试");
                        getTestRequetionsType = new String[]{"1", "a2", "order"};
                        break;
                    case DriversFinal.FINAL_TYPE_B1_NEXT:
                        Log.d(TAG, "最后选择：B1顺序测试");
                        getTestRequetionsType = new String[]{"1", "b1", "order"};
                        break;
                    case DriversFinal.FINAL_TYPE_B2_NEXT:
                        Log.d(TAG, "最后选择：B2顺序测试");
                        getTestRequetionsType = new String[]{"1", "b2", "order"};
                        break;
                    case DriversFinal.FINAL_TYPE_C1_NEXT:
                        Log.d(TAG, "最后选择：C1顺序测试");
                        getTestRequetionsType = new String[]{"1", "c1", "order"};
                        break;
                    case DriversFinal.FINAL_TYPE_C2_NEXT:
                        Log.d(TAG, "最后选择：C2顺序测试");
                        getTestRequetionsType = new String[]{"1", "c2", "order"};
                        break;

                }
                repleace(new DriversTest_RandomTestView(), getTestRequetionsType);
//                getTestQuestions(1, getTestRequetionsType[0], getTestRequetionsType[1]);

            }
        }).show();
    }



    private void repleace(Fragment fragment, String... strings) {
        Bundle bundle = new Bundle();
        bundle.putInt("subject", Integer.valueOf(strings[0]));
        bundle.putString("model", strings[1]);
        bundle.putString("testType", strings[2]);

        fragment.setArguments(bundle);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.addToBackStack("as");
        transaction.replace(R.id.mainView, fragment).commit();
    }
}
