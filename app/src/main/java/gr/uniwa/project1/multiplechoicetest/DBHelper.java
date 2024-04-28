package gr.uniwa.project1.multiplechoicetest;


import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;


public class DBHelper {
    private static final String DATABASE_NAME = "test2.db";
    private SQLiteDatabase database;
    private final Context context;

    public DBHelper(Context context) {
        this.context = context;
        database = SQLiteDatabase.openDatabase(context.getDatabasePath(DATABASE_NAME).getPath(), null, SQLiteDatabase.OPEN_READWRITE);
    }

    /**
     * Retrieves a list of questions from the database.
     *
     * @return A list of Question objects.
     */
    public List<Question> getQuestionsFromDatabase() {
        List<Question> questionList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM questions";
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String questionText = cursor.getString(cursor.getColumnIndexOrThrow("text"));
                    String optionA = cursor.getString(cursor.getColumnIndexOrThrow("optionA"));
                    String optionB = cursor.getString(cursor.getColumnIndexOrThrow("optionB"));
                    String optionC = cursor.getString(cursor.getColumnIndexOrThrow("optionC"));
                    String optionD = cursor.getString(cursor.getColumnIndexOrThrow("optionD"));
                    String correctAnswer = cursor.getString(cursor.getColumnIndexOrThrow("correct_answer"));
                    String imageName = cursor.getString(cursor.getColumnIndexOrThrow("image_name"));
                    String imagePath = "file:///android_asset/" + imageName;
                    Question question = new Question(id, questionText, optionA, optionB, optionC, optionD, correctAnswer, imagePath);
                    questionList.add(question);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return questionList;
    }

    /**
     * Retrieves a list of users from the database.
     *
     * @return A list of User objects.
     */
    public List<User> getUsersFromDatabase() {
        List<User> userList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM users";
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    String lastname = cursor.getString(cursor.getColumnIndexOrThrow("lastname"));
                    String firstname = cursor.getString(cursor.getColumnIndexOrThrow("firstname"));
                    String academicID = cursor.getString(cursor.getColumnIndexOrThrow("academic_id"));
                    User user = new User(id, lastname, firstname, academicID);
                    userList.add(user);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return userList;
    }

    /**
     * Retrieves a list of scores from the database.
     *
     * @return A list of Score objects.
     */
    public List<Score> getScoresFromDatabase() {
        List<Score> scoreList = new ArrayList<>();
        try {
            String selectQuery = "SELECT * FROM scores ORDER BY id DESC";
            Cursor cursor = database.rawQuery(selectQuery, null);
            if (cursor != null && cursor.moveToFirst()) {
                do {
                    int id = cursor.getInt(cursor.getColumnIndexOrThrow("id"));
                    int scoreFinal = cursor.getInt(cursor.getColumnIndexOrThrow("score"));
                    int userID = cursor.getInt(cursor.getColumnIndexOrThrow("user_id"));
                    Score score = new Score(id, scoreFinal, userID);
                    scoreList.add(score);
                } while (cursor.moveToNext());
                cursor.close();
            }
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
        return scoreList;
    }

    /**
     * Copies the database file from assets to the application's data directory.
     *
     * @param context  The context of the application.
     * @param fileName The name of the database file in assets directory.
     */
    public static void copyDB(Context context, String fileName)
    {
        AssetManager assetManager = context.getAssets();
        InputStream input;
        OutputStream output;
        byte[] buffer;
        int byteRead;
        try
        {
            input = assetManager.open (fileName);
            File outFile = context.getDatabasePath(DATABASE_NAME);
            output = new FileOutputStream(outFile);
            buffer = new byte[1024];
            while ((byteRead = input.read (buffer)) != -1)
                output.write (buffer, 0, byteRead);
            input.close();
            output.close();
        }
        catch(IOException e)
        {
            System.out.println (e.getMessage ());
            Toast.makeText (context, "IO Error during DB copy.", Toast.LENGTH_LONG).show();
        }
    }

    /**
     * Inserts a score into the database.
     *
     * @param score  The score to be inserted.
     * @param userId The ID of the user associated with the score.
     */
    public void insertScore(int score, int userId) {
        try {
            ContentValues values = new ContentValues();
            values.put("score", score);
            values.put("user_id", userId);
            database.insert("scores", null, values);
            database.close();
        } catch (SQLiteException e) {
            e.printStackTrace();
        }
    }

    /**
     * Checks if the provided credentials exist in the database.
     *
     * @param lastname   The last name of the user.
     * @param firstname  The first name of the user.
     * @param academicID The academic ID of the user.
     * @return True if the credentials exist, otherwise false.
     */
    public boolean checkCredentials(String lastname, String firstname, String academicID) {
        try {
            String[] columns = {"lastname", "firstname", "academic_id"};
            String selection = "lastname = ? AND firstname = ? AND academic_id = ?";
            String[] selectionArgs = {lastname, firstname, academicID};
            Cursor cursor = database.query("users", columns, selection, selectionArgs, null, null, null);
            int count = cursor.getCount();
            cursor.close();
            return count > 0;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }
}
