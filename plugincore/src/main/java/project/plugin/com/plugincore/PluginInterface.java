package project.plugin.com.plugincore;

import android.app.Activity;
import android.os.Bundle;

/**
 * Created by landy on 2017/6/5.
 * 插件化开发标准
 * 所有通过插件化加载的activity必须实现一下接口
 */

public interface PluginInterface {

    public void attach(Activity proxyActivity);

    public void onCreate(Bundle savedInstanceState);

    public void onResume();

    public void onStart();

    public void onPause();

    public void onStop();

    public void onDestory();

    public void onSaveInstanceState(Bundle outState);

    public void onBackPressed();

}
