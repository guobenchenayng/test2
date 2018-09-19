package com.yfy.tv.ui;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Color;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.yfy.tv.aidl.IMyAidl;
import com.yfy.tv.base.BaseActivity;
import com.yfy.tv.base.BasePresenter;
import com.yfy.tv.model.bean.Person;
import com.yfy.tv.mytencent.R;
import com.yfy.tv.service.BindService;
import com.yfy.tv.service.StartService;

import java.util.ArrayList;
import java.util.List;

public class ActivityTest extends BaseActivity<BasePresenter> implements View.OnClickListener {

    private TextView mStartServiceText;
    private TextView mStopServiceText;
    private TextView mBindServiceText;
    private TextView mUnbindServiceText;
    private Intent   mStartIntent;
    private Intent   bindIntent;
    private TextView mAIDLServiceText;
    private TextView mRemoteDataText;
    private IMyAidl  myAidl;
    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intentAIDL = new Intent("com.tencent.tv.service.ServerService");
        intentAIDL.setPackage("com.tencent.tv.service");
        bindService(intentAIDL, connAIDL, Context.BIND_AUTO_CREATE);
//        RecyclerView
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected int contentView() {
        return R.layout.activity_test;
    }

    @Override
    protected void initView() {
        mStartServiceText = findViewById(R.id.tv_start_service);
        mStopServiceText = findViewById(R.id.tv_stop_service);
        mBindServiceText = findViewById(R.id.tv_bind_service);
        mUnbindServiceText = findViewById(R.id.tv_unbind_service);
        mAIDLServiceText = findViewById(R.id.tv_aidl_service);
        mRemoteDataText = findViewById(R.id.tv_romote_data);
        mListView = findViewById(R.id.lv_touch_test);
        setAdapter();
    }

    private void setAdapter() {
        ArrayList arrayList = new ArrayList();
        for (int i = 0; i <= 50; i++){
            arrayList.add(i);
        }
        TestAdapter adapter = new TestAdapter(this,arrayList);
        mListView.setAdapter(adapter);
    }

    @Override
    protected void setListener() {
        mStartServiceText.setOnClickListener(this);
        mStopServiceText.setOnClickListener(this);
        mBindServiceText.setOnClickListener(this);
        mUnbindServiceText.setOnClickListener(this);
        mAIDLServiceText.setOnClickListener(this);
        mRemoteDataText.setOnClickListener(this);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("-------------------onServiceConnected");
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("-------------------onServiceDisconnected");
        }
    };

    private ServiceConnection connAIDL = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("-------------------onServiceConnected");
            myAidl = IMyAidl.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("-------------------onServiceDisconnected");
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_start_service:
                mStartIntent = new Intent(this, StartService.class);
                startService(mStartIntent);
                break;
            case R.id.tv_stop_service:
                stopService(mStartIntent);
                break;
            case R.id.tv_bind_service:
                bindIntent = new Intent(this, BindService.class);
                bindService(bindIntent,conn, Context.BIND_AUTO_CREATE);
                break;
            case R.id.tv_unbind_service:
                unbindService(conn);
                break;
            case R.id.tv_aidl_service:
                Intent intentAIDL = new Intent();
                intentAIDL.setAction("com.tencent.tv.aidl");
                intentAIDL.setPackage("com.tencent.tv");
                bindService(intentAIDL, connAIDL, Context.BIND_AUTO_CREATE);
                break;

            case R.id.tv_romote_data:
                try {
                    myAidl.addPerson(new Person("GB"));
                    List<Person> list = myAidl.getPersonList();
                    for (Person item : list){
                        System.out.println("-------" + item.getmName());
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private static class TestAdapter extends BaseAdapter{
        private Context mContext;
        private ArrayList mArrayList;

        public TestAdapter(Context context,ArrayList list){
            this.mContext = context;
            this.mArrayList = list;
        }

        @Override
        public int getCount() {
            return mArrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return mArrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null){
                viewHolder = new ViewHolder();
                convertView = LayoutInflater.from(mContext).inflate(R.layout.item_list, null);
                viewHolder.mConstraintLayout = convertView.findViewById(R.id.cl_layout);
                convertView.setTag(viewHolder);
            }else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            if (position % 2 == 0)
                viewHolder.mConstraintLayout.setBackgroundColor(Color.RED);
            else
                viewHolder.mConstraintLayout.setBackgroundColor(Color.YELLOW);
            return convertView;
        }

        static class ViewHolder{
            public ConstraintLayout mConstraintLayout;
        }
    }
}
