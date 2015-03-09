package kidslist.sqlite.helper;

import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import kidslist.model.Todo;

public class TodoTable {
	// Database table
	  public static final String TABLE_TODO = "todo";
	  public static final String COLUMN_ID = "_id";
	  public static final String COLUMN_CATEGORY = "category";
	  public static final String COLUMN_SUMMARY = "summary";
	  public static final String COLUMN_DESCRIPTION = "description";
	  public static final String COLUMN_STATUS = "status";
	  // Database creation SQL statement
	  private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " 
	      + TABLE_TODO
	      + " (" 
	      + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
	      + COLUMN_CATEGORY + " TEXT NOT NULL, " 
	      + COLUMN_SUMMARY + " TEXT NOT NULL, " 
	      + COLUMN_DESCRIPTION + " TEXT, " 
	      + COLUMN_STATUS + " INTEGER NOT NULL);";

	  public static void onCreate(SQLiteDatabase database) {
	    database.execSQL(DATABASE_CREATE);
	  }

	  public static void onUpgrade(SQLiteDatabase database, int oldVersion,
	      int newVersion) {
	    Log.w(TodoTable.class.getName(), "Upgrading database from version "
	        + oldVersion + " to " + newVersion
	        + ", which will destroy all old data");
	    database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
	    onCreate(database);
	  }
}
