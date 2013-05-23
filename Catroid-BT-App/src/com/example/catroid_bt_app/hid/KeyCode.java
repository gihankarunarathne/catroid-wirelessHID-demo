/**
 *  Catroid: An on-device graphical programming language for Android devices
 *  Copyright (C) 2010  Catroid development team 
 *  (<http://code.google.com/p/catroid/wiki/Credits>)
 *
 *  This program is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.example.catroid_bt_app.hid;

public class KeyCode {

    private boolean isModifier;
    private int keyCode;
    private int eventType;

    public KeyCode() {
        this.eventType = 1;
    }

    public KeyCode(boolean isModifier, int keyCode) {
        this.isModifier = isModifier;
        this.keyCode = keyCode;
        this.eventType = 1;
    }

    public KeyCode(int eventType, int keyCode) {
        this.isModifier = false;
        this.eventType = eventType;
        this.keyCode = keyCode;
    }

    public KeyCode(int eventType, int dx, int dy) {
        this.isModifier = false;
        this.eventType = eventType;
        this.keyCode = 0;
    }

    public boolean isModifier() {
        return isModifier;
    }

    public void setModifier(boolean isModifier) {
        this.isModifier = isModifier;
    }

    public int getKeyCode() {
        return keyCode;
    }

    public void setKeyCode(int keyCode) {
        this.keyCode = keyCode;
    }

    public int getEventType() {
        return this.eventType;
    }

    public void setEventType(int eventType) {
        this.eventType = eventType;
    }

}
