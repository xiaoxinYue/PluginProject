package project.plugin.com.mypluginproject;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import dalvik.system.DexClassLoader;
import project.plugin.com.plugincore.PluginManager;
import project.plugin.com.plugincore.ProxyActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PluginManager.getInstance(this);
    }


    public void loadApk(View v) {
        //File file = new File(Environment.getExternalStorageDirectory(), "plugin0.apk");
        String filePath = this.getCacheDir()+File.separator+"plugin.apk";
        InputStream is = null;
        try {
            is = this.getAssets().open("plugin.apk");
            FileOutputStream os = new FileOutputStream(filePath);
            int len = 0;
            byte[] buffer = new byte[1024];
            while ((len = is.read(buffer)) != -1) {
                os.write(buffer, 0, len);
            }
            os.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        File file = new File(filePath);
        Toast.makeText(this, "下载成功", Toast.LENGTH_SHORT).show();
       PluginManager.getInstance().loadPath(file.getAbsolutePath());
    }

    public void jumpPluginApk(View v) {
        Intent intent = new Intent(this, ProxyActivity.class);
        intent.putExtra("className", PluginManager.getInstance().getPackageInfo().activities[0].name);
        startActivity(intent);
        Toast.makeText(this, "jumpPluginApk", Toast.LENGTH_SHORT).show();
    }

}
