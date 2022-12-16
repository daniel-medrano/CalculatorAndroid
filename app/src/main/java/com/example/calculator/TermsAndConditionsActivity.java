package com.example.calculator;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

public class TermsAndConditionsActivity extends AppCompatActivity {
    Button btnAgree;
    CheckBox chkAccept;
    TextView lblContent;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_and_conditions);
        initViews();
    }

     void initViews() {
        btnAgree = findViewById(R.id.btnAgree);
        chkAccept = findViewById(R.id.chkAccept);
        lblContent = findViewById(R.id.lblContent);
        sharedPreferences = getSharedPreferences("terms and conditions", this.MODE_PRIVATE);

        lblContent.setText("Yo, Esteban de Jesús Martínez Morales, acepto con pleno uso de mis facultades mentales que Saprissa es el mejor equipo en la historia de Costa Rica y que la Liga Deportiva Alajualense es un eterno segundón.");

        btnAgree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TermsAndConditionsActivity.this, MainActivity.class);
                editor = sharedPreferences.edit();
                editor.putBoolean("agreed", true);
                editor.commit();
                startActivity(intent);
                finish();
            }
        });
        chkAccept.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean checked) {
                if (checked) {
                    btnAgree.setEnabled(true);
                } else {
                    btnAgree.setEnabled(false);
                }
            }
        });
    }
}