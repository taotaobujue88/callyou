package com.example.callyou;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.callyou.R;
public class MainActivity extends Activity {
    EditText editNum;
    Button btnCall;  //打电话
    EditText editSms;
    Button btnSms;   //发短信
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addPermissions();
        editNum = (EditText) findViewById(R.id.editNum);
        btnCall = (Button) findViewById(R.id.btnCall);
        editSms = (EditText) findViewById(R.id.editSms);
        btnSms = (Button) findViewById(R.id.btnSms);
        //打电话
        btnCall.setOnClickListener(listenerCall);
        btnSms.setOnClickListener(listenerSms);
    }
    public void addPermissions() {
        String[] permissionsList = {Manifest.permission.CALL_PHONE ,Manifest.permission.SEND_SMS};
        ArrayList<String> mPermissionList = new ArrayList<>();
        for (int i = 0; i < permissionsList.length; i++) {
            if (ContextCompat.checkSelfPermission(MainActivity.this, permissionsList[i])
                    != PackageManager.PERMISSION_GRANTED) {
                mPermissionList.add(permissionsList[i]);
            }
        }
        //非初次进入App且已授权
        if (!mPermissionList.isEmpty()) {
            //请求权限方法
            //将List转为数组
            String[] permissionsNew = mPermissionList.toArray(new String[mPermissionList.size()]);
            //这个触发下面onRequestPermissionsResult这个回调
            ActivityCompat.requestPermissions(MainActivity.this, permissionsNew, 1);
        }
    }
    View.OnClickListener listenerCall = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String phone =editNum.getText().toString();
            if("".equals(phone)){
                Toast.makeText(MainActivity.this, "电话号码不能为空", Toast.LENGTH_SHORT)
                        .show();
                return;
            }
            //使用Intent调用打电话的功能
            Intent intent = new Intent();
            //设置Action和Uri
            intent.setAction(Intent.ACTION_CALL);
            //设置数据
            intent.setData(Uri.parse("tel:"+phone));
            startActivity(intent);
            //电话号码清空
            editNum.setText("");
        }
    };
    View.OnClickListener listenerSms = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String phone = editNum.getText().toString();
            if("".equals(phone)){
                Toast.makeText(MainActivity.this, "电话号码不能为空", Toast.LENGTH_LONG)
                        .show();
                return;
            }
            String smsContent = editSms.getText().toString();
            if("".equals(smsContent)){
                Toast.makeText(MainActivity.this, "短信内容不能为空", Toast.LENGTH_LONG)
                        .show();
                return;
            }
            //使用Intent调用打电话的功能
            Intent intent = new Intent();
            //设置Action和Uri
            intent.setAction(Intent.ACTION_SENDTO);
            //设置数据
            intent.setData(Uri.parse("smsto:"+phone));
            //指定短信的内容
            intent.putExtra("sms_body", smsContent);
            startActivity(intent);
            //电话号码清空
            editNum.setText("");
            editSms.setText("");
        }
    };
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add("电话拨号").setIcon(android.R.drawable.ic_menu_call);
        menu.add("回到Home").setIcon(android.R.drawable.ic_menu_view);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String title = item.getTitle().toString();
        //调用系统拨号界面
        if(title.equals("电话拨号")){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_CALL);
            intent.setData(Uri.parse("tel:"+editNum.getText().toString()));
            startActivity(intent);
        }else if(title.equals("回到Home")){
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_MAIN);
//添加category属性
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        return true;
    }
}