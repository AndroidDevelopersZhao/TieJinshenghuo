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
 * 创建日期： 2015/12/14  11:19
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.data
 */
public class Data_News {
    private Bitmap bitmap=null;
    private String title = null;
    private String content = null;
    private String img_width = null;
    private String full_title = null;
    private String pdate = null;
    private String src = null;
    private String img_length = null;
    private String img = null;
    private String url = null;
    private String pdate_src = null;

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setImg_width(String img_width) {
        this.img_width = img_width;
    }

    public void setFull_title(String full_title) {
        this.full_title = full_title;
    }

    public void setPdate(String pdate) {
        this.pdate = pdate;
    }

    public void setSrc(String src) {
        this.src = src;
    }

    public void setImg_length(String img_length) {
        this.img_length = img_length;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setPdate_src(String pdate_src) {
        this.pdate_src = pdate_src;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getImg_width() {
        return img_width;
    }

    public String getFull_title() {
        return full_title;
    }

    public String getPdate() {
        return pdate;
    }

    public String getSrc() {
        return src;
    }

    public String getImg_length() {
        return img_length;
    }

    public String getImg() {
        return img;
    }

    public String getUrl() {
        return url;
    }

    public String getPdate_src() {
        return pdate_src;
    }
}
