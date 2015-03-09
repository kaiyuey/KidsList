package kidslist.sqlite.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;


public class DatabaseHelper extends SQLiteOpenHelper {
	
	// Database table
		  public static final String TABLE_TODO = "todo";
		  public static final String COLUMN_ID = "_id";
		  public static final String COLUMN_CATEGORY = "category";
		  public static final String COLUMN_SUMMARY = "summary";
		  public static final String COLUMN_DESCRIPTION = "description";
		  public static final String COLUMN_STATUS = "status";
		  public static final String COLUMN_POINTS = "points";
		  public static final String COLUMN_LOCATION = "location";
		  
	  private static final String DATABASE_NAME = "todolist.db";
	  private static final int DATABASE_VERSION = 1;
	  
	// Database creation SQL statement
		  private static final String DATABASE_CREATE = "CREATE TABLE IF NOT EXISTS " 
		      + TABLE_TODO
		      + " (" 
		      + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " 
		      + COLUMN_CATEGORY + " TEXT NOT NULL, " 
		      + COLUMN_SUMMARY + " TEXT NOT NULL, " 
		      + COLUMN_DESCRIPTION + " TEXT, " 
		      + COLUMN_STATUS + " INTEGER NOT NULL, "
		      + COLUMN_POINTS + " TEXT, "
		      + COLUMN_LOCATION + " TEXT);";


	  public DatabaseHelper(Context context) {
	    super(context, DATABASE_NAME, null, DATABASE_VERSION);
	  }

	  // Method is called during creation of the database
	  @Override
	  public void onCreate(SQLiteDatabase database) {
		  database.execSQL(DATABASE_CREATE);
	  }

	  // Method is called during an upgrade of the database,
	  // e.g. if you increase the database version
	  @Override
	  public void onUpgrade(SQLiteDatabase database, int oldVersion,
	      int newVersion) {
	    //TodoTable.onUpgrade(database, oldVersion, newVersion);
		  Log.w(DatabaseHelper.class.getName(),
					"Upgrading database from version " + oldVersion + " to "
							+ newVersion + ", which will destroy all old data");
			database.execSQL("DROP TABLE IF EXISTS " + TABLE_TODO);
			onCreate(database);
	  }

}

