package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
public class MainActivity2 extends AppCompatActivity {
    private static final int REQUEST_CODE_METRIC = 1;
    private static final int REQUEST_CODE_STANDARD = 2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent intent = getIntent();
        String calc = intent.getStringExtra("calc");
        TextView pastBmiTextView = findViewById(R.id.pastbmi);
        RelativeLayout layout = findViewById(R.id.layout);
        RelativeLayout layout2 = findViewById(R.id.layout2);
        Button back = findViewById(R.id.back);

        // Open the SQLite database
        SQLiteDatabase database = openOrCreateDatabase("bmi_database", MODE_PRIVATE, null);

        // Retrieve the last BMI record from the database
        Cursor cursor = database.rawQuery("SELECT bmi FROM bmi_records ORDER BY _id DESC LIMIT 1 OFFSET 1", null);

        if (cursor.moveToFirst()) {
            int bmiColumnIndex = cursor.getColumnIndex("bmi");
            String bmiResult = cursor.getString(bmiColumnIndex);
            pastBmiTextView.setText("Last bmi result: "+bmiResult);
        }

        cursor.close();
        database.close();

        if("metric".equals(calc)) {
            float bmi = getIntent().getFloatExtra("bmi", 0);
            TextView bmiTextView = findViewById(R.id.bmioutput);
            bmiTextView.setText(String.format("%.2f", bmi));

            String BMICat;
            if (bmi < 18.5) {
                BMICat = "Underweight";
                layout.setBackgroundResource(R.drawable.underweight);
                layout2.setBackgroundResource(R.drawable.underweight);
                back.setBackgroundResource(R.drawable.uwbtn);
            } else if (bmi < 25) {
                BMICat = "Normal weight";
                layout.setBackgroundResource(R.drawable.normalweight);
                layout2.setBackgroundResource(R.drawable.normalweight);
                back.setBackgroundResource(R.drawable.nwbtn);
            } else if (bmi < 30) {
                BMICat = "Overweight";
                layout.setBackgroundResource(R.drawable.overweight);
                layout2.setBackgroundResource(R.drawable.overweight);
                back.setBackgroundResource(R.drawable.owbtn);
            } else {
                BMICat = "Obese";
                layout.setBackgroundResource(R.drawable.obese);
                layout2.setBackgroundResource(R.drawable.obese);
                back.setBackgroundResource(R.drawable.obsbtn);
            }
            TextView catTextView = findViewById(R.id.bmicategory);
            catTextView.setText(String.format(BMICat));

        } else if ("standard".equals(calc)) {
            float bmi2 = getIntent().getFloatExtra("bmi2", 0);
            TextView bmiTextView2 = findViewById(R.id.bmioutput);
            bmiTextView2.setText(String.format("%.2f", bmi2));

            String BMICat;
            if (bmi2 < 18.5) {
                BMICat = "Underweight";
                layout.setBackgroundResource(R.drawable.underweight);
                layout2.setBackgroundResource(R.drawable.underweight);
                back.setBackgroundResource(R.drawable.uwbtn);
            } else if (bmi2 < 25) {
                BMICat = "Normal weight";
                layout.setBackgroundResource(R.drawable.normalweight);
                layout2.setBackgroundResource(R.drawable.normalweight);
                back.setBackgroundResource(R.drawable.nwbtn);
            } else if (bmi2 < 30) {
                BMICat = "Overweight";
                layout.setBackgroundResource(R.drawable.overweight);
                layout2.setBackgroundResource(R.drawable.overweight);
                back.setBackgroundResource(R.drawable.owbtn);
            } else {
                BMICat = "Obese";
                layout.setBackgroundResource(R.drawable.obese);
                layout2.setBackgroundResource(R.drawable.obese);
                back.setBackgroundResource(R.drawable.obsbtn);
            }
            TextView catTextView = findViewById(R.id.bmicategory);
            catTextView.setText(String.format(BMICat));

        }
        else {}

        button = (Button) findViewById(R.id.back);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                setResult(Activity.RESULT_OK, resultIntent);
                finish();
            }
        });
    }
}