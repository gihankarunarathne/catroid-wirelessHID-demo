package com.example.catroid_bt_app;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.catroid_bt_app.bluetooth.DeviceListActivity;
import com.example.catroid_bt_app.ui.KeyBoardActivity;
import com.example.catroid_bt_app.ui.MouseActivity;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button keyboard = (Button) findViewById(R.id.button_keyboard);
        keyboard.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,
                        KeyBoardActivity.class));
            }
        });

        Button mouse = (Button) findViewById(R.id.button_mouse);
        mouse.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MouseActivity.class));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}
