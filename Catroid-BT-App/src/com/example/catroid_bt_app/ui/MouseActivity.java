package com.example.catroid_bt_app.ui;

import com.example.catroid_bt_app.R;
import com.example.catroid_bt_app.R.layout;
import com.example.catroid_bt_app.R.menu;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class MouseActivity extends Activity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_mouse);
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.mouse, menu);
    return true;
  }

}
