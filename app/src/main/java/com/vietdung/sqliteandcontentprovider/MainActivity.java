package com.vietdung.sqliteandcontentprovider;

import android.Manifest;
import android.content.ContentResolver;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int SREQUEST_CODE_PERMISSION = 0;
    private List<Contact> mContacts;
    private ContactAdapter mContactAdapter;
    private RecyclerView mRecyclerViewMain;
    private String[] mPermissions = {Manifest.permission.READ_CONTACTS};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!checkPermissions()) {
            return;
        }
        initView();
        addEvents();
    }

    private void addEvents() {
        getListContacts();
    }

    private void getListContacts() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                null, null, null, null);

        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String phoneNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            mContacts.add(new Contact(name, phoneNumber));
        }
        cursor.close();
        mContactAdapter.notifyDataSetChanged();
    }

    private void initView() {
        mContacts = new ArrayList<>();
        mContactAdapter = new ContactAdapter(MainActivity.this, mContacts);
        mRecyclerViewMain = findViewById(R.id.recycler_main);
        mRecyclerViewMain.setHasFixedSize(true);
        mRecyclerViewMain.setAdapter(mContactAdapter);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerViewMain.setLayoutManager(layoutManager);
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            for (String check : mPermissions) {
                int status = checkSelfPermission(check);
                if (status == PackageManager.PERMISSION_DENIED) {
                    requestPermissions(mPermissions, SREQUEST_CODE_PERMISSION);
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (checkPermissions()) {
            initView();
            addEvents();
        } else {
            finish();
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

}
