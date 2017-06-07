package project.plugin.com.pluginapk;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.File;

import project.plugin.com.plugincore.BasePluginActivity;
import project.plugin.com.plugincore.PluginManager;
import project.plugin.com.plugincore.ProxyActivity;

public class MainActivity extends BasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.activity_main);
        that.findViewById(R.id.plugin_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(that, "plugin 中 de 吐司", Toast.LENGTH_SHORT).show();
            }
        });
        that.findViewById(R.id.plugin_jump_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(that,HomeActivity.class);
                that.startActivity(intent);
            }
        });
    }

}
