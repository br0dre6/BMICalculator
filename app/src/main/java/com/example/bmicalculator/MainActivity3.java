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


public class MainActivity3 extends AppCompatActivity {
    private dbresult1 dbResult1;
    private Button button;
    private Button button2;
    private static final int REQUEST_CODE_STANDARD = 2;
    // SQLite Database Constants
    private static final String DB_NAME = "bmi_database";
    private static final String TABLE_NAME = "bmi_records";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_BMI = "bmi";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        dbResult1 = new dbresult1(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        EditText weightEditText = findViewById(R.id.textWeight2);
        EditText feetEditText = findViewById(R.id.textHeight1);
        EditText inchesEditText = findViewById(R.id.textHeight3);
        EditText ageEditText = findViewById(R.id.textAge);

        weightEditText.setSingleLine(true);
        feetEditText.setSingleLine(true);
        inchesEditText.setSingleLine(true);
        ageEditText.setSingleLine(true);

        button2 = (Button) findViewById(R.id.metric);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity3.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        button = (Button) findViewById(R.id.calculatebmi);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String calc = "standard";

                float weight = Float.parseFloat(weightEditText.getText().toString());
                float feet = Float.parseFloat(feetEditText.getText().toString());
                float inches = Float.parseFloat(inchesEditText.getText().toString());

                float heightInInches = (feet * 12) + inches;

                float bmi = (weight / (heightInInches * heightInInches)) * 703;

                // Save BMI record to the database
                saveBMIRecord(bmi);

                Intent intent = new Intent(MainActivity3.this, MainActivity2.class);
                intent.putExtra("bmi2", bmi);
                intent.putExtra("calc", calc);
                startActivityForResult(intent, REQUEST_CODE_STANDARD);
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
        feetEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String text = feetEditText.getText().toString();
                    if (text.equals("0")) {
                        feetEditText.setText("");
                    }
                } else {
                    String text = feetEditText.getText().toString();
                    if (text.isEmpty()) {
                        feetEditText.setText("0");
                    }
                }
            }
        });
        inchesEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    String text = inchesEditText.getText().toString();
                    if (text.equals("0")) {
                        inchesEditText.setText("");
                    }
                } else {
                    String text = inchesEditText.getText().toString();
                    if (text.isEmpty()) {
                        inchesEditText.setText("0");
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
    }

    // Helper method to save BMI record to the database
    private void saveBMIRecord(float bmi) {
        SQLiteDatabase db = dbResult1.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_BMI, bmi);

        long result = db.insert(TABLE_NAME, null, values);

        db.close();
    }

    // Helper class for managing database creation and version management
    static class dbresult1 extends SQLiteOpenHelper {
        private static final int DATABASE_VERSION = 1;

        dbresult1(MainActivity3 context) {
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
            if (requestCode == REQUEST_CODE_STANDARD) {
            }
        }
    }
}