package com.shanghai.utils;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

/**
 * Created by Administrator on 2016/1/18.
 */
public class ViewHolder {
    public static TextView textView;
    public static Button button;
    public static ImageView imageView;
    public static ListView listView;
    public static ScrollView scrollView;

    public static TextView getTextView() {
        return textView;
    }

    public static void setTextView(TextView textView) {
        ViewHolder.textView = textView;
    }

    public static Button getButton() {
        return button;
    }

    public static void setButton(Button button) {
        ViewHolder.button = button;
    }

    public static ImageView getImageView() {
        return imageView;
    }

    public static void setImageView(ImageView imageView) {
        ViewHolder.imageView = imageView;
    }

    public static ListView getListView() {
        return listView;
    }

    public static void setListView(ListView listView) {
        ViewHolder.listView = listView;
    }

    public static ScrollView getScrollView() {
        return scrollView;
    }

    public static void setScrollView(ScrollView scrollView) {
        ViewHolder.scrollView = scrollView;
    }
}
