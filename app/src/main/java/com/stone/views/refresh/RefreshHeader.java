package com.stone.views.refresh;

import android.view.View;

/**
 * Created by Jerry on 2017/10/6
 */

public interface RefreshHeader {

    /**
     * 下拉刷新时调用
     */
    void onRefresh();

    /**
     * @return 返回View示例
     */

    View getView();

    int getState();

    void notifyStateChange(int state);
}
