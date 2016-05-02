package com.example.zhengsuren.olddriver;

import android.content.Context;
import android.content.Intent;
import android.app.AlertDialog;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button bt1;
    private TextView regist;
    private Context mContext;
    private EditText username;
    private EditText password;
    private Handler handler = new Handler();
    //private static boolean check = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bt1 = (Button) findViewById(R.id.button);
        regist = (TextView) findViewById(R.id.regist);
        mContext = this;
        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String url = "http://139.129.132.60/api/login";
                if ( (username.getText().toString()).isEmpty() || (password.getText().toString()).isEmpty())
                {
                    new  AlertDialog.Builder(mContext).setTitle("提示").setMessage("用户名和密码不能为空！")
                            .setPositiveButton("确定",null).show();
                }
                else
                {
                    handler = new Handler()
                    {
                        @Override
                        public void handleMessage(Message msg)
                        {
                            super.handleMessage(msg);
                            switch (msg.what)
                            {
                                case 0:
                                {
                                    UserInfo data = (UserInfo) msg.obj;
                                    if (data != null)
                                    {
                                        if (data.getError() == 0)
                                        {
                                            //check = true;
                                            Intent intent = new Intent(mContext,LoginSuccess.class);
                                            //用Bundle携带数据
                                            Bundle bundle=new Bundle();

                                            //传递name参数为tinyphp
                                            bundle.putString("url", url);
                                            bundle.putString("email",username.getText().toString());
                                            bundle.putString("pwd", password.getText().toString());
                                            intent.putExtras(bundle);

                                            startActivity(intent);
                                            break;
                                        }
                                    }

                                    new  AlertDialog.Builder(mContext).setTitle("提示").setMessage("用户名或密码错误！")
                                            .setPositiveButton("确定",null).show();
                                    break;
                                }

                                default:
                                    break;
                            }
                        }
                    };

                    HttpThread thread = new HttpThread(url,username.getText().toString(),
                                        password.getText().toString(), handler,mContext);

                    thread.start();

                    //public StackTraceElement[] getStackTrace()
                    //　返回一个堆栈轨迹元素的数组，代表了这个线程的堆栈情况。
                    //　如果：1.这个线程没有被开启；
                    //       2.这个线程被开启了但是没有被系统运行过（因为线程运行是需要根据一定规律轮换的）；
                    //       3.这个线程结束了。
                    //　这三种情况下getStackTrace()返回的数组长度为0。
                    //StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();

                }

            }
        });

        regist.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,RegistPage.class);
                startActivity(intent);
            }
        });
    }
}