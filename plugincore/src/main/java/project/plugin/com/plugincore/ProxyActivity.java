package project.plugin.com.plugincore;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 代理activity
 */
public class ProxyActivity extends Activity{

    private String className;
    PluginInterface pluginInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        className = intent.getStringExtra("className");
        launchActivity();
    }

    /**
     * 启动插件中的activity
     */
    private void launchActivity() {
        try {
            //通过dexclassloader加载apk
            Class<?> loadClass = PluginManager.getInstance().getDexClassLoader().loadClass(className);
            Constructor contructor = loadClass.getConstructor(new Class[]{});
             //反射得到activity
            Object instance = contructor.newInstance(new Object[]{});
            //利用标准接口 将apk里面class强转成PluginInterface
            pluginInterface = (PluginInterface) instance;
            pluginInterface.attach(this);
            Bundle bundle = new Bundle();
            pluginInterface.onCreate(bundle);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
       pluginInterface.onStart();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        pluginInterface.onDestory();
    }

    /**
     * 拿到resource  xml 图片
     */
    @Override
    public Resources getResources() {
        return PluginManager.getInstance().getResources();
    }
}
