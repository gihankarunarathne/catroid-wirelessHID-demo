package com.example.catroid_bt_app.ui.test;

import android.test.ActivityInstrumentationTestCase2;

import com.example.catroid_bt_app.ui.KeyBoardActivity;
import com.jayway.android.robotium.solo.Solo;

public class KeyBoardActivityTest extends
        ActivityInstrumentationTestCase2<KeyBoardActivity> {
    private Solo solo;
    private String SERVER_MAC_ADDRESS = "00:16:41:86:AA:4A";

    public KeyBoardActivityTest() {
        super(KeyBoardActivity.class);
    }

    public void setUp() throws Exception {
        solo = new Solo(getInstrumentation(), getActivity());
        super.setUp();
        
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
        getActivity().finish();
        super.tearDown();
    }
    
    public void testOnClick(){
        
    }

}
