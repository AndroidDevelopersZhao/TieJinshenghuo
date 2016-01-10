package com.shanghai.data;
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

import android.graphics.Bitmap;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/16  13:14
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.data
 */
public class Data_Video_Home {
    private Bitmap images = null;//图片
    private String tvTitle = null;//标题
    private String grade = null;//评分
    private String starring_1 = null;//主演1
    private String starring_2 = null;//主演2
    private String starring_3 = null;//主演3
    private String starring_4 = null;//主演4
    private String type_1 = null;
    private String type_2 = null;
    private String date = null;

    public Bitmap getImages() {
        return images;
    }

    public void setImages(Bitmap images) {
        this.images = images;
    }

    public String getTvTitle() {
        return tvTitle;
    }

    public void setTvTitle(String tvTitle) {
        this.tvTitle = tvTitle;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getStarring_1() {
        return starring_1;
    }

    public void setStarring_1(String starring_1) {
        this.starring_1 = starring_1;
    }

    public String getStarring_2() {
        return starring_2;
    }

    public void setStarring_2(String starring_2) {
        this.starring_2 = starring_2;
    }

    public String getStarring_3() {
        return starring_3;
    }

    public void setStarring_3(String starring_3) {
        this.starring_3 = starring_3;
    }

    public String getStarring_4() {
        return starring_4;
    }

    public void setStarring_4(String starring_4) {
        this.starring_4 = starring_4;
    }

    public String getType_1() {
        return type_1;
    }

    public void setType_1(String type_1) {
        this.type_1 = type_1;
    }

    public String getType_2() {
        return type_2;
    }

    public void setType_2(String type_2) {
        this.type_2 = type_2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
