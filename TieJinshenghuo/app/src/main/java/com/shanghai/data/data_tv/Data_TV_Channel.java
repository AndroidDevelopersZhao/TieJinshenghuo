package com.shanghai.data.data_tv;

/**
 * Created by Administrator on 2015/12/14.
 */
public class Data_TV_Channel {
    private int pId=0;
    private String channelName=null;
    private String url=null;
    private String rel=null;

    public int getpId() {
        return pId;
    }

    public void setpId(int pId) {
        this.pId = pId;
    }

    public String getChannelName() {
        return channelName;
    }

    public void setChannelName(String channelName) {
        this.channelName = channelName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }
}
