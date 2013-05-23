package org.catrobat.catroid.server;

import java.awt.MouseInfo;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

import javax.microedition.io.StreamConnection;

import org.catrobat.catroid.server.robots.RobotWrapper;
import org.catrobat.catroid.server.robots.RobotMapper;

/**
 * 
 * @author Pointner
 * @author Gihan Karunarathne
 */
class ReceiveBytesThread implements Runnable {

    private StreamConnection stream_connection;
    private int EXIT_CODE = -1;
    private boolean D;

    private int mouseX;
    private int mouseY;

    public ReceiveBytesThread(StreamConnection connection, boolean testMode) {
        stream_connection = connection;
        this.D = testMode;
        this.mouseX = 0; // MouseInfo.getPointerInfo().getLocation().x;
        this.mouseY = 0; // MouseInfo.getPointerInfo().getLocation().y;
    }

    public void run() {

        InputStream input_stream = null;
        OutputStream output_stream = null;

        try {
            input_stream = stream_connection.openInputStream();

            if (D)
                output_stream = stream_connection.openDataOutputStream();

            // System.out.println("waiting for input...");

            while (true) {

                byte[] buffer = new byte[10];
                input_stream.read(buffer);

                if (D) {
                    output_stream.write(buffer);
                    output_stream.flush();
                } else {
                    if (lookupCMD(buffer) == EXIT_CODE) {
                        break;
                    }
                }
            }

            if (input_stream != null)
                input_stream.close();
            if (output_stream != null)
                output_stream.close();

        } catch (IOException e) {
            // e.printStackTrace();
        }
    }

    private int lookupCMD(byte[] input) {
        RobotMapper rm = null;
        RobotWrapper rw = null;
        byte[] exit_byte = { -1 };

        if (input == exit_byte) {
            return -1;
        }

        try {
            rm = new RobotMapper();
            rw = new RobotWrapper();
        } catch (Exception e) {
            return -1;
        }

        if (D) {
            int i = 0;
            for (byte b : input) {
                System.out.print("Index " + i + ":" + b + " ");
                i++;
            }
            System.out.println("");
        }

        if (input[1] > 1) {

            switch (input[1]) {
            case 2:
                rw.mouseClick(rm.extractMouseValue(input[1], input[3]));
                this.mouseX = 500;
                this.mouseY = 500;
                rw.mouseMove(mouseX, mouseY);
                break;
            case 3:
                rw.mouseWheel(rm.extractMouseValue(input[1], input[3]));
                break;
            case 4:
                rw.mouseWheel(rm.extractMouseValue(input[1], input[3]));
                break;
            case 5:
                this.mouseY += rm.extractMouseValue(input[1], input[3]);
                rw.mouseMove(mouseX, mouseY);
                break;
            case 6:
                this.mouseY += rm.extractMouseValue(input[1], input[3]);
                rw.mouseMove(mouseX, mouseY);
                break;
            case 7:
                this.mouseX += rm.extractMouseValue(input[1], input[3]);
                rw.mouseMove(mouseX, mouseY);
                break;
            case 8:
                this.mouseX += rm.extractMouseValue(input[1], input[3]);
                rw.mouseMove(mouseX, mouseY);
                break;
            }
        }

        ArrayList<Integer> list = rm.getKeyList(input);
        try {
            rw.keyPressAndRelease(list);
        } catch (Exception e) {
            return -1;
        }

        return 0;
    }
}
