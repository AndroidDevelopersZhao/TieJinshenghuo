package com.shanghai.anim;
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

import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;

/**
 * 项目名称： NewsClient
 * 创建日期： 2015/12/20  17:39
 * 项目作者： 赵文贇
 * 项目包名： xinfu.com.newsclient.anim
 */
public class MyAnimation {
    /**
     * 获取一个从左往右平移的动画
     *
     * @return
     */
    public static AnimationSet LeftToRight() {
        AnimationSet set = new AnimationSet(false);
        TranslateAnimation t = new TranslateAnimation(-300, 0, 0, 0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);

        alphaAnimation.setDuration(1000);
        t.setDuration(1000);

        set.addAnimation(alphaAnimation);
        set.addAnimation(t);
        return set;
    }

    /**
     * 获取一个从右往左平移的动画
     *
     * @return
     */
    public static AnimationSet RightToLeft() {
        AnimationSet set = new AnimationSet(false);
        TranslateAnimation t = new TranslateAnimation(300, 0, 0, 0);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
        set.addAnimation(alphaAnimation);
        set.addAnimation(t);

        alphaAnimation.setDuration(1000);
        t.setDuration(1000);
        return set;
    }


}
