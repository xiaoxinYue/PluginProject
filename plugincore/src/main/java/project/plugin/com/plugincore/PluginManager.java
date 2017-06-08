package project.plugin.com.plugincore;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;

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
    private Drawable drawable;
    private View layout;
    private int color;


    private String string;

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
            packageInfo = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES);

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


    /**
     * 获取资源对应的编号
     *
     * @param res
     * @param resName
     * @param resType layout、drawable、string
     * @return
     */
    private int getId(Resources res, String resType, String resName) {
        return res.getIdentifier(resName, resType, PluginManager.getInstance().getPackageInfo().packageName);
    }

    /**
     * 获取视图
     *
     * @param context
     * @param id
     * @return
     */
    public View getView(Context context, int id) {
        return ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(id, null);
    }


    public View getLayout(String layoutName) {
        layout = getView(context, getId(resources, "layout", layoutName));
        return layout;
    }


    public int getColor(String colorName) {
        color = resources.getColor(getId(resources, "color", colorName));
        return color;
    }


    public Drawable getDrawable(String drawableName) {
        drawable = resources.getDrawable(getId(resources, "drawable", drawableName));
        return drawable;
    }

    public String getString(String stringName) {
        string = resources.getString(getId(resources, "String", stringName));
        return string;
    }


}
