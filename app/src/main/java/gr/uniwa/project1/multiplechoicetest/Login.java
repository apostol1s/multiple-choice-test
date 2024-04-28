package gr.uniwa.project1.multiplechoicetest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.util.List;

public class Login extends AppCompatActivity implements View.OnClickListener {
    EditText firstname;
    EditText lastname;
    EditText academicId;
    Button loginButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        loginButton = findViewById(R.id.loginButton);
        firstname = findViewById(R.id.firstnameEditText);
        lastname = findViewById(R.id.lastnameEditText);
        academicId = findViewById(R.id.academicIdEditText);
        loginButton.setOnClickListener(this);//
        if (!fileExists("test2.db")) {
            DBHelper.copyDB(this, "test2.db");
        }
    }

    /**
     * Checks if a file exists in the application's internal storage.
     *
     * @param fileName The name of the file to check.
     * @return True if the file exists, otherwise false.
     */
    boolean fileExists(String fileName) {
        File file = new File(getApplicationContext().getFilesDir(), fileName);
        return file.exists();
    }

    @Override
    public void onClick(View view) {
        String firstNameText = firstname.getText().toString().trim();
        String lastNameText = lastname.getText().toString().trim();
        String academicIdText = academicId.getText().toString().trim();
        if (firstNameText.isEmpty() || lastNameText.isEmpty() || academicIdText.isEmpty()) {
            Toast.makeText(Login.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
        } else {
            DBHelper dbHelper = new DBHelper(this);
            List<User> users = dbHelper.getUsersFromDatabase();
            boolean isValid = dbHelper.checkCredentials(lastNameText, firstNameText, academicIdText);
            int userId = -1;
            if (isValid) {
                User.getInstance().setFirstname(firstNameText);
                User.getInstance().setLastname(lastNameText);
                User.getInstance().setAcademicID(academicIdText);
                for (User user : users) {
                    if (user.getLastname().equals(lastNameText) && user.getFirstname().equals(firstNameText) && user.getAcademicID().equals(academicIdText)) {
                        userId = user.getId();
                        break;
                    }
                }
                if (userId != -1) {
                    User.getInstance().setId(userId);
                    Intent intent = new Intent(this, FirstPage.class);
                    intent.putExtra("userId", userId);
                    startActivity(intent);
                }
            } else {
                Toast.makeText(Login.this, "Invalid User", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
