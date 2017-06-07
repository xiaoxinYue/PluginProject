package project.plugin.com.pluginapk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import project.plugin.com.plugincore.BasePluginActivity;

public class HomeActivity extends BasePluginActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        that.setContentView(R.layout.activity_home);
    }
}
