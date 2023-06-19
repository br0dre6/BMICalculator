package com.example.bmicalculator;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private dbresult dbResult;
    private Button button;
    private Button button2;
    private static final int REQUEST_CODE_METRIC = 1;
    private static final String DB_NAME = "bmi_database";
    private static final String TABLE_NAME = "bmi_records";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_BMI = "bmi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbResult = new dbresult(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EditText heightEditText = findViewById(R.id.textHeight1);
        EditText weightEditText = findViewById(R.id.textWeight2);
        EditText ageEditText = findViewById(R.id.textAge);

        weightEditText.setSingleLine(true);
        heightEditText.setSingleLine(true);
        ageEditText.setSingleLine(true);

        button = findViewById(R.id.calculatebmi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String calc = "metric";

                float height = Float.parseFloat(heightEditText.getText().toString());
                float weight = Float.parseFloat(weightEditText.getText().toString());

                float bmi = weight / ((height / 100) * (height / 100));

                // Save BMI record to the database
                saveBMIRecord(bmi);

                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                intent.putExtra("bmi", bmi);
                intent.putExtra("calc", calc);
                startActivityForResult(intent, REQUEST_CODE_METRIC);
            }
        });

        RelativeLayout dropdownLayout = findViewById(R.id.dropdownLayout);
        TextView checkclass = findViewById(R.id.checkclass);
        checkclass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dropdownLayout.getVisibility() == View.VISIBLE) {
                    dropdownLayout.setVisibility(View.GONE);
                } else {
                    dropdownLayout.setVisibility(View.VISIBLE);
                }
            }
        });


        heightEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String text = heightEditText.getText().toString();
                    if (text.equals("0")) {
                        heightEditText.setText("");
                    }
                } else {
                    String text = heightEditText.getText().toString();
                    if (text.isEmpty()) {
                        heightEditText.setText("0");
                    }
                }
            }
        });
        weightEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String text = weightEditText.getText().toString();
                    if (text.equals("0")) {
                        weightEditText.setText("");
                    }
                } else {
                    String text = weightEditText.getText().toString();
                    if (text.isEmpty()) {
                        weightEditText.setText("0");
                    }
                }
            }
        });
        ageEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String text = ageEditText.getText().toString();
                    if (text.equals("0")) {
                        ageEditText.setText("");
                    }
                } else {
                    String text = ageEditText.getText().toString();
                    if (text.isEmpty()) {
                        ageEditText.setText("0");
                    }
                }
            }
        });

        button2 = (Button) findViewById(R.id.standard);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, MainActivity3.class);
                startActivity(intent);
                finish();
            }
        });
    }

    // Helper method to save BMI record to the database
    private void saveBMIRecord(float bmi) {
        SQLiteDatabase db = dbResult.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BMI, bmi);

        long result = db.insert(TABLE_NAME, null, values);

        db.close();
    }

    // Helper class for managing database creation and version management
    static class dbresult extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;

        dbresult(MainActivity context) {
            super(context, DB_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String createTableQuery = "CREATE TABLE " + TABLE_NAME + " ("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + COLUMN_BMI + " REAL)";

            db.execSQL(createTableQuery);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_CODE_METRIC) {
            }
        }
    }
}