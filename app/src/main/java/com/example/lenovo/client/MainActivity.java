package com.example.lenovo.secondapp;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends ActionBarActivity {

    private static final int portNumber = 8043;
    private Socket s = null;
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private Button connect_button;
    private EditText host;
    final String FORWARD = "forward";
    final String BACKWARD = "backward";
    final String RIGHT = "right";
    final String LEFT = "left";
    final String STOP = "stop";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button forward_button = (Button) findViewById(R.id.forward_button);
        final Button backward_button = (Button) findViewById(R.id.backward_button);
        final Button right_button = (Button) findViewById(R.id.right_button);
        final Button left_button = (Button) findViewById(R.id.left_button);
        connect_button = (Button) findViewById(R.id.connect_button);
        host = (EditText) findViewById(R.id.editText);

        Button.OnTouchListener forward_button_listener = new Button.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    try{dos.writeUTF(FORWARD);} catch(Exception e){e.printStackTrace();};

                } else if (action == MotionEvent.ACTION_UP) {
                    try{dos.writeUTF(STOP);} catch(Exception e){e.printStackTrace();};

                }
                return false;
            }
        };

        Button.OnTouchListener backward_button_listener = new Button.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    try{dos.writeUTF(BACKWARD);} catch(Exception e){e.printStackTrace();};

                } else if (action == MotionEvent.ACTION_UP) {
                    try{dos.writeUTF(STOP);} catch(Exception e){e.printStackTrace();};
                }
                return false;
            }
        };

        Button.OnTouchListener right_button_listener = new Button.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    try{dos.writeUTF(RIGHT);} catch(Exception e){e.printStackTrace();};

                } else if (action == MotionEvent.ACTION_UP) {
                    try{dos.writeUTF(STOP);} catch(Exception e){e.printStackTrace();};
                }
                return false;
            }
        };

        Button.OnTouchListener left_button_listener = new Button.OnTouchListener() {
            public boolean onTouch(View v, MotionEvent event) {

                int action = event.getAction();
                if (action == MotionEvent.ACTION_DOWN) {
                    try{dos.writeUTF(LEFT);} catch(Exception e){e.printStackTrace();};

                } else if (action == MotionEvent.ACTION_UP) {
                    try{dos.writeUTF(STOP);} catch(Exception e){e.printStackTrace();};
                }
                return false;
            }
        };

        Button.OnClickListener connect_button_listener = new Button.OnClickListener() {
            public void onClick(View v) {
                try {
                    if (connect_button.getText().equals("Connect")) {
                        new ClientSocket().execute(host.getText().toString());
                        connect_button.setText("Disconnect");
                    } else {
                        dos.writeUTF("close");
                        dos.close();
                        s.close();
                        connect_button.setText("Connect");
                    }
                } catch (Exception exception) {
                    exception.printStackTrace();
                }
            }
        };

        forward_button.setOnTouchListener(forward_button_listener);
        backward_button.setOnTouchListener(backward_button_listener);
        right_button.setOnTouchListener(right_button_listener);
        left_button.setOnTouchListener(left_button_listener);
        connect_button.setOnClickListener(connect_button_listener);

    }

    private class ClientSocket extends AsyncTask<String, Void, Boolean> {

        private int portNumber = 8043;

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                s = new Socket(params[0], portNumber);
                dos = new DataOutputStream(s.getOutputStream());
            } catch (Exception exception) {
                exception.printStackTrace();
                return false;
            }
            return true;
        }

//        @Override
//        protected void onPostExecute(Boolean result) {
//            connectedToRobotNetworkFlag = result;
//            System.out.println("Hi3"+connectedToRobotNetworkFlag);
//        }
    }
}

