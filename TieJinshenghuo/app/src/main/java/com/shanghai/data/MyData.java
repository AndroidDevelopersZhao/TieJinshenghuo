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

/**
 *  项目名称： NewsClient
 *  创建日期： 2015/12/28  11:28
 *  项目作者： 赵文贇
 *  项目包名： xinfu.com.newsclient.data
 */
public class MyData {
    private String username = null;
    private String city = null;
    private String netWorkState=null;
    private String IMEI=null;
    private String IESI=null;
    private String mtype=null;
    private String mtyb=null;
    private String numer=null;

    public MyData(String username, String city, String netWorkState, String IMEI, String IESI, String mtype, String mtyb, String numer) {
        this.username = username;
        this.city = city;
        this.netWorkState = netWorkState;
        this.IMEI = IMEI;
        this.IESI = IESI;
        this.mtype = mtype;
        this.mtyb = mtyb;
        this.numer = numer;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getNetWorkState() {
        return netWorkState;
    }

    public void setNetWorkState(String netWorkState) {
        this.netWorkState = netWorkState;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getIESI() {
        return IESI;
    }

    public void setIESI(String IESI) {
        this.IESI = IESI;
    }

    public String getMtype() {
        return mtype;
    }

    public void setMtype(String mtype) {
        this.mtype = mtype;
    }

    public String getMtyb() {
        return mtyb;
    }

    public void setMtyb(String mtyb) {
        this.mtyb = mtyb;
    }

    public String getNumer() {
        return numer;
    }

    public void setNumer(String numer) {
        this.numer = numer;
    }
}
