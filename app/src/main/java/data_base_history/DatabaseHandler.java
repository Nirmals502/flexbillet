package data_base_history;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "Flexbillet";

    // Contacts table name
    private static final String TABLE_History = "History";

    // Contacts Table Columns names
    private static final String KEY_ID = "id";
    private static final String STATUS = "status";
    private static final String KEY_PH_NO = "phone_number";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_History + "("
                + STATUS + " TEXT)";
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_History);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new historylist
    public void add_history_items(History_list historylist) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(STATUS, historylist.getStatus()); // History_list Name
        // History_list Phone

        // Inserting Row
        db.insert(TABLE_History, null, values);
        db.close(); // Closing database connection
    }


    // Getting All Contacts
    public List<History_list> getAll_history_status() {
        List<History_list> historylistList = new ArrayList<History_list>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + TABLE_History;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                History_list historylist = new History_list();
                historylist.setStatus(cursor.getString(0));

                // Adding historylist to list
                historylistList.add(historylist);
            } while (cursor.moveToNext());
        }

        // return contact list
        return historylistList;
    }


    // Getting contacts Count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_History;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();

        // return count
        return cursor.getCount();
    }

    public void delete() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_History);

        // Create tables again
        onCreate(db);

    }
}
