package com.example.contacts;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SecondActivity extends ListActivity {

    private static final int OPEN_DIALER_PERMISSION = 5;
    private String phoneNumber;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        Bundle b = intent.getBundleExtra("BUNDLE");
        ArrayList<String> names = b.getStringArrayList("names");
        final ArrayList<String> phoneNos = b.getStringArrayList("phoneNos");
        ArrayList<String> result = new ArrayList<String>();
        result.add("Name" + "-" + "Phone Number");
        for(int i=0;i<names.size();i++) {
            result.add(names.get(i) + "-" + phoneNos.get(i));
        }
        setListAdapter(new ArrayAdapter<String>(this, R.layout.list_view,
                result));
        ListView lv = getListView();

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                phoneNumber = ((TextView) view).getText().toString();
                phoneNumber = phoneNumber.replaceAll("[^0-9]", "");
                checkPermissions();

                Toast.makeText(getApplicationContext(),
                        "You selected : "+((TextView) view).getText(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void checkPermissions() {
        if (ContextCompat.checkSelfPermission(SecondActivity.this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Permission is not granted
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(SecondActivity.this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed; request the permission
                ActivityCompat.requestPermissions(SecondActivity.this,
                        new String[]{Manifest.permission.READ_CONTACTS},
                        OPEN_DIALER_PERMISSION);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        } else {
            // Permission has already been granted
            Toast.makeText(SecondActivity.this, "Permisson granted", Toast.LENGTH_SHORT).show();
            openDialer();
        }
    }

    private void openDialer() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        startActivity(intent);
    }

    public void onRequestPermissionsResult(int requestCode,
                                           String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case OPEN_DIALER_PERMISSION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    openDialer();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    Toast.makeText(SecondActivity.this, "Please accept permissions first", Toast.LENGTH_LONG).show();
                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request.
        }
    }
}
