package com.example.catroid_bt_app.ui;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.example.catroid_bt_app.hid.Communicator;
import com.example.catroid_bt_app.hid.KeyCode;

@SuppressLint("ViewConstructor")
public class MultiTouchPad extends View {
    // Debug
    private static final String TAG = "Catroid-BT";
    private static final boolean D = true;

    private static final int INVALID_POINTER_ID = -1;
    private static final int pixelScale = 4;
    private static final float buffer = 4;

    private float mdx = 0;
    private float mdy = 0;

    private float mLastTouchX;
    private float mLastTouchY;
    private int mActivePointerId = INVALID_POINTER_ID;
    private ArrayList<KeyCode> actionList = null;
    private boolean isMove = false;

    private Communicator com = null;

    public MultiTouchPad(Context context, Communicator com) {
        this(context, null, 0);
        this.com = com;
        this.actionList = new ArrayList<KeyCode>();
    }

    public MultiTouchPad(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MultiTouchPad(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        final int action = ev.getAction();

        switch (action & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN: {
            final float x = ev.getX();
            final float y = ev.getY();

            mLastTouchX = x;
            mLastTouchY = y;
            mActivePointerId = ev.getPointerId(0);
            Log.i(TAG, "ACTION_DOWN");
            break;
        }

        case MotionEvent.ACTION_MOVE: {
            final int pointerIndex = ev.findPointerIndex(mActivePointerId);
            final float x = ev.getX(pointerIndex);
            final float y = ev.getY(pointerIndex);

            final float dx = Math.round(x - mLastTouchX);
            final float dy = Math.round(y - mLastTouchY);

            mdx += dx;
            mdy += dy;

            if (mdy > buffer) {
                actionList.add(new KeyCode(5, Math.round(mdy / pixelScale)));
                mdy = 0;
                isMove = true;
            }
            if (mdy < -buffer) {
                actionList.add(new KeyCode(6, Math.round(mdy / pixelScale)));
                mdy = 0;
                isMove = true;
            }

            if (mdx > buffer) {
                actionList.add(new KeyCode(7, Math.round(mdx / pixelScale)));
                mdx = 0;
                isMove = true;
            }
            if (mdx < -buffer) {
                actionList.add(new KeyCode(8, Math.round(mdx / pixelScale)));
                mdx = 0;
                isMove = true;
            }

            if (isMove)
                invalidate();

            mLastTouchX = x;
            mLastTouchY = y;

            break;
        }

        case MotionEvent.ACTION_UP: {
            mActivePointerId = INVALID_POINTER_ID;
            if (D)
                Log.i(TAG, "ACTION_UP");
            break;
        }

        case MotionEvent.ACTION_CANCEL: {
            mActivePointerId = INVALID_POINTER_ID;
            if (D)
                Log.i(TAG, "ACTION_CANCEL");
            break;
        }

        case MotionEvent.ACTION_POINTER_UP: {
            final int pointerIndex = (ev.getAction() & MotionEvent.ACTION_POINTER_INDEX_MASK) >> MotionEvent.ACTION_POINTER_INDEX_SHIFT;
            final int pointerId = ev.getPointerId(pointerIndex);
            if (pointerId == mActivePointerId) {
                // This was our active pointer going up. Choose a new
                // active pointer and adjust accordingly.
                final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                mLastTouchX = ev.getX(newPointerIndex);
                mLastTouchY = ev.getY(newPointerIndex);
                mActivePointerId = ev.getPointerId(newPointerIndex);
            }
            Log.i(TAG, "ACTION_POINTER_UP");
            break;
        }

        }

        return true;
    }

    @Override
    public void onDraw(Canvas canvas) {
        if (D)
            Log.i(TAG, "out >> dx:" + mdx + " dy:" + mdy);
        if (actionList.size() > 0) {
            this.com.send(actionList);
            isMove = false;
            actionList = new ArrayList<KeyCode>();
        }
    }
}