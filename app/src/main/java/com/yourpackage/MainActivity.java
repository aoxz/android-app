// MainActivity.java

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    EditText editTextName, editTextAge;
    Button buttonAdd, buttonDisplay;
    TextView textViewToads;

    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextName = findViewById(R.id.editTextName);
        editTextAge = findViewById(R.id.editTextAge);
        buttonAdd = findViewById(R.id.buttonAdd);
        buttonDisplay = findViewById(R.id.buttonDisplay);
        textViewToads = findViewById(R.id.textViewToads);

        sqLiteDatabase = openOrCreateDatabase("ToadDB", MODE_PRIVATE, null);
        sqLiteDatabase.execSQL("CREATE TABLE IF NOT EXISTS Toads(name VARCHAR, age INT(3))");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String ageStr = editTextAge.getText().toString().trim();

                if (name.isEmpty() || ageStr.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                    return;
                }

                int age = Integer.parseInt(ageStr);

                sqLiteDatabase.execSQL("INSERT INTO Toads VALUES('" + name + "'," + age + ")");
                Toast.makeText(MainActivity.this, "Toad added successfully", Toast.LENGTH_SHORT).show();

                editTextName.getText().clear();
                editTextAge.getText().clear();
            }
        });

        buttonDisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor resultSet = sqLiteDatabase.rawQuery("SELECT * FROM Toads", null);
                StringBuilder stringBuilder = new StringBuilder();

                if (resultSet.moveToFirst()) {
                    do {
                        String name = resultSet.getString(0);
                        int age = resultSet.getInt(1);
                        stringBuilder.append("Name: ").append(name).append(", Age: ").append(age).append("\n");
                    } while (resultSet.moveToNext());
                }

                textViewToads.setText(stringBuilder.toString());
            }
        });
    }
}
