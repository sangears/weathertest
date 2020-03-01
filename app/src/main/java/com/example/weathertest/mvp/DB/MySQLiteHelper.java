package com.example.weathertest.mvp.DB;

import java.util.LinkedList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.example.weathertest.mvp.Adapter.Cities;

public class MySQLiteHelper extends SQLiteOpenHelper {
    private static final String TAG2 = "myLogs";
    // Database Version
    private static final int DATABASE_VERSION = 1;
    // Database Name
    private static final String DATABASE_NAME = "CitiesDB";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // SQL statement to create book table
        String CREATE_CITY_TABLE = "CREATE TABLE city ( " +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "city TEXT, "+
                "temp1 TEXT, "+
                "description TEXT, "+
                "pressure TEXT, " +
                "humidity TEXT, " +
                "wind TEXT, " +
                "temp2 TEXT, " +
                "icon BLOB, " +
                "id2 INTEGER )";

        // create books table
        db.execSQL(CREATE_CITY_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older books table if existed
        db.execSQL("DROP TABLE IF EXISTS city");

        // create fresh books table
        this.onCreate(db);
    }
    //---------------------------------------------------------------------

    /**
     * CRUD operations (create "add", read "get", update, delete) book + get all books + delete all books
     */

    // Books table name
    private static final String TABLE_CITY = "city";

    // Books Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_CITY = "city";
    private static final String KEY_TEMP1 = "temp1";
    private static final String KEY_DESC = "description";
    private static final String KEY_ICON = "icon";
    private static final String KEY_TEMP2 = "temp2";
    private static final String KEY_PRES = "pressure";
    private static final String KEY_HUM = "humidity";
    private static final String KEY_WIND = "wind";
    private static final String KEY_ID2 = "id2";
    private static final String[] COLUMNS = {KEY_ID,KEY_CITY,KEY_TEMP1,KEY_DESC,KEY_ICON,KEY_TEMP2,KEY_PRES,KEY_HUM,KEY_WIND,KEY_ID2};

    public void addCities(Cities city){
        //Log.d("addBook", city.toString());
        // 1. get reference to writable DB
        SQLiteDatabase db = this.getWritableDatabase();

        // 2. create ContentValues to add key "column"/value
        ContentValues values = new ContentValues();
        values.put(KEY_CITY, city.getCity()); // get title
        values.put(KEY_TEMP1, city.getTemp()); // get author
        values.put(KEY_DESC, city.getDesc());
        values.put(KEY_ICON, city.getLogo());
        values.put(KEY_TEMP2, city.getTemp2());
        values.put(KEY_PRES, city.getPressure());
        values.put(KEY_HUM, city.getHumidity());
        values.put(KEY_WIND, city.getWind());
        values.put(KEY_ID2, city.getId());



        // 3. insert
        db.insert(TABLE_CITY, // table
                null, //nullColumnHack
                values); // key/value -> keys = column names/ values = column values

        // 4. close

    }

    public Cities getCities(int id){
        Cities city = new Cities();
        // 1. get reference to readable DB
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
        // 2. build query
        cursor =
                db.query(TABLE_CITY, // a. table
                        COLUMNS, // b. column names
                        " id2 = ?", // c. selections
                        new String[] { String.valueOf(id) }, // d. selections args
                        null, // e. group by
                        null, // f. having
                        null, // g. order by
                        null); // h. limit

        // 3. if we got results get the first one
        if (cursor != null)
            cursor.moveToFirst();

        // 4. build book object
        
        city.setId(Integer.parseInt(cursor.getString(0)));
        city.setCity(cursor.getString(1));
        city.setTemp(cursor.getString(2));
        city.setDesc(cursor.getString(3));
        city.setLogo(cursor.getString(4));
        city.setTemp2(cursor.getString(5));
        city.setPressure(cursor.getString(6));
        city.setHumidity(cursor.getString(7));
        city.setWind(cursor.getString(8));
      //  Log.d("getBook("+id+")", city.toString());
        } finally {
            // this gets called even if there is an exception somewhere above
            if(cursor != null)
                cursor.close();
        }
        // 5. return book

        return city;
    }

  public int getAllCount() {


      // 1. build the query
      String query = "SELECT  * FROM " + TABLE_CITY;

      // 2. get reference to writable DB
      SQLiteDatabase db = this.getWritableDatabase();
      Cursor cursor = db.rawQuery(query, null);

      // 3. go over each row, build book and add it to list

      try {
          if (cursor.moveToFirst()) {
              do {

                  Cities city = new Cities();
                  city.setId(Integer.parseInt(cursor.getString(0)));
                  city.setCity(cursor.getString(1));
                  city.setTemp(cursor.getString(2));
                  city.setDesc(cursor.getString(3));
                  city.setLogo(cursor.getString(4));
                  city.setTemp2(cursor.getString(5));
                  city.setPressure(cursor.getString(6));
                  city.setHumidity(cursor.getString(7));
                  city.setWind(cursor.getString(8));
                  }
              while (cursor.moveToNext());
          }
      }

      finally {
          if (cursor != null && !cursor.isClosed())
              cursor.close();
          db.close();
      }

     // Log.d("getAllBooks()", books.toString());
      Log.d(TAG2, "count"+String.valueOf(cursor.getCount()));

      // return books
      return cursor.getCount();
  }

    public void deleteTable() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CITY, null, null);

    }
}
