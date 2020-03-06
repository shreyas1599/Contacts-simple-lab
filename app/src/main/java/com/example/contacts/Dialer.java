package com.example.contacts;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class Dialer extends MainActivity implements View.OnClickListener{
    Button buttons[][] = new Button[3][3];
    private final static int CALL_NUMBER_PERMISSION = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialer);

        for (int i=0;i<3;i++) {
            for(int j=0;j<3;j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                buttons[i][j] = findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }
        }

        Button callButton = findViewById(R.id.call);
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                TextView phone = findViewById(R.id.dialer_number);
                intent.setData(Uri.parse("tel:" + phone.getText().toString()));
                startActivity(intent);
            }
        });
    }

    @Override
    public void onClick(View view) {
        TextView phoneNumber = findViewById(R.id.dialer_number);
        phoneNumber.append(((Button) view).getText().toString());
    }

}
