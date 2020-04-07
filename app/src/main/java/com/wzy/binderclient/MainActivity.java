package com.wzy.binderclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wzy.binderservice.IPerson;
import com.wzy.binderservice.Person;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private TextView mTextView;

    private IPerson iPerson;

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            iPerson = IPerson.Stub.asInterface(service);
            showToast("连接服务器成功");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            showToast("断开连接服务器");
        }
    };

    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTextView = findViewById(R.id.tv_result);
        Intent intent = new Intent();
        intent.setPackage("com.wzy.binderservice");
        bindService(intent, connection, Context.BIND_AUTO_CREATE);
    }

    Random random = new Random();

    public void addPerson(View view) {
        Person person = new Person();
        person.setName("person");
        person.setAge(random.nextInt(100));
        try {
            iPerson.addPerson(person);
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    public void getPersonList(View view) {
        try {
            List<Person> personList = iPerson.getList();
            StringBuilder result = new StringBuilder();
            for (Person person : personList) {
                result.append(person.toString());
            }
            mTextView.setText(result.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
