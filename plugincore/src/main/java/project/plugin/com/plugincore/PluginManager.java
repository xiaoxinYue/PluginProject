package project.plugin.com.plugincore;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import dalvik.system.DexClassLoader;

/**
 * Created by landy on 2017/6/4.
 */

public class PluginManager {

    private DexClassLoader dexClassLoader;
    private Context context;
    private Resources resources;
    private PackageInfo packageInfo;

    private static PluginManager pluginManager;

    public PackageInfo getPackageInfo() {
        return packageInfo;
    }

    public static PluginManager instance;

    public PluginManager(Context context) {
        this.context = context;
    }

    public static PluginManager getInstance(Context context) {
        if (instance == null) {
            instance = new PluginManager(context);
        }
        return instance;
    }

    public static PluginManager getInstance() {
        if (instance == null) {
            throw new RuntimeException("PluginManager必须实例化");
        }
        return instance;
    }


    public DexClassLoader getDexClassLoader() {
        return dexClassLoader;
    }

    public Resources getResources() {
        return resources;
    }


    /**
     * 加载apk路径
     *
     * @Param path
     */
    public void loadPath(String path) {
        File dexOutFile = context.getDir("dex", Context.MODE_PRIVATE);
        //实例化dexclassLoad 加载文件
        dexClassLoader = new DexClassLoader(path, dexOutFile.getAbsolutePath(), null, context.getClassLoader());
        //实例化resource
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, path);
            Resources superResource = context.getResources();
            resources = new Resources(assetManager, superResource.getDisplayMetrics(), superResource.getConfiguration());
            PackageManager packageManager = context.getPackageManager();
            packageInfo = packageManager.getPackageArchiveInfo(path,PackageManager.GET_ACTIVITIES);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }


}
