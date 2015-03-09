package kidslist.sqlite.helper;

import java.util.ArrayList;

import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import kidslist.model.Todo;


public class TodoDataSource {

	  // Database fields
	  private SQLiteDatabase database;
	  private DatabaseHelper dbHelper;
	  private long points;

	  private String[] allColumns = { 
				DatabaseHelper.COLUMN_ID, 
				DatabaseHelper.COLUMN_CATEGORY, 
				DatabaseHelper.COLUMN_SUMMARY, 
			    DatabaseHelper.COLUMN_DESCRIPTION, 
				DatabaseHelper.COLUMN_STATUS,
				DatabaseHelper.COLUMN_POINTS,
				DatabaseHelper.COLUMN_LOCATION };
	  
	  public TodoDataSource(Context context) {
		dbHelper = new DatabaseHelper(context);
	 }
	  
	  // something need to be added
	  public void open() throws SQLException {
		  database = dbHelper.getWritableDatabase();
			
		}
		
	  public void close() {
		  dbHelper.close();
	  }
		  
	  public Todo createTodo(String category, String summary, String description,
			  String points, String location){
		  ContentValues values = new ContentValues();
		  values.put(DatabaseHelper.COLUMN_CATEGORY, category);
		  values.put(DatabaseHelper.COLUMN_SUMMARY, summary);
		  values.put(DatabaseHelper.COLUMN_DESCRIPTION, description);
	      values.put(DatabaseHelper.COLUMN_STATUS, 0);
	      values.put(DatabaseHelper.COLUMN_POINTS, points);
	      values.put(DatabaseHelper.COLUMN_LOCATION, location);
	      
		  long insertId=database.insert(DatabaseHelper.TABLE_TODO, null,
			        values);
		  Cursor cursor = database.query(DatabaseHelper.TABLE_TODO,
					allColumns, DatabaseHelper.COLUMN_ID + " = " + insertId, null,
					null, null, null);
		  cursor.moveToFirst();
		  Todo todo = cursorToTodo(cursor);
		  cursor.close();
		  return todo;
	  }
	  
	  public void deleteTodo(Todo todo) {
		    long id = todo.getId();
		    System.out.println("Comment deleted with id: " + id);
		    database.delete(DatabaseHelper.TABLE_TODO, DatabaseHelper.COLUMN_ID
		        + " = " + id, null);
	  }
	  //save tasks
	  public void saveTodo(Todo todo) {
			long id = todo.getId();
			
			ContentValues values = new ContentValues();
			values.put(DatabaseHelper.COLUMN_SUMMARY, todo.getSummary());
			values.put(DatabaseHelper.COLUMN_CATEGORY, todo.getCategory());
			values.put(DatabaseHelper.COLUMN_DESCRIPTION, todo.getDescription());
			values.put(DatabaseHelper.COLUMN_STATUS, todo.getStatus() ? 1 : 0);
			values.put(DatabaseHelper.COLUMN_POINTS, todo.getPoints());
			values.put(DatabaseHelper.COLUMN_LOCATION, todo.getLocation());
			
			System.out.println("Task saved with id: " + id);
			database.update(DatabaseHelper.TABLE_TODO, values,
					DatabaseHelper.COLUMN_ID + " = " + id, null);
		}
	  
		public Todo find(long id) {
			for (Todo todo : getAllTodos()) {
				if (todo.getId() == id) {
					return todo;
				}
			}
			return null;
		}
		//all tasks
		public List<Todo> getAllTodos() {
			List<Todo> todos = new ArrayList<Todo>();

			Cursor cursor = database.query(DatabaseHelper.TABLE_TODO, allColumns, null, null, null, null, null);

			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Todo todo = cursorToTodo(cursor);
				todos.add(todo);
				cursor.moveToNext();
			}
			// Make sure to close the cursor
			cursor.close();
			return todos;
		}
		//all tasks wait to be do
		public List<Todo> getAllwaits() {
			List<Todo> todos = new ArrayList<Todo>();
			Cursor cursor = database.query(DatabaseHelper.TABLE_TODO, allColumns, null,null,null,null,null);
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Todo todo = cursorToTodo(cursor);
				if(todo.getStatus() == false){
					todos.add(todo);
				}
				cursor.moveToNext();
			}
			return todos;
		}
		
		//all the done task
		public List<Todo> getAllDones() {
			List<Todo> todos = new ArrayList<Todo>();
			Cursor cursor = database.query(DatabaseHelper.TABLE_TODO, allColumns, null,null,null,null,null);
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Todo todo = cursorToTodo(cursor);
				if(todo.getStatus() == true){
					todos.add(todo);
				}
				cursor.moveToNext();
			}
			return todos;
		}
	  
		public long getPoints(){
			Cursor cursor = database.query(DatabaseHelper.TABLE_TODO, allColumns, null,null,null,null,null);
			
			cursor.moveToFirst();
			while (!cursor.isAfterLast()) {
				Todo todo = cursorToTodo(cursor);
				if(todo.getStatus() == true){
					points = points + Long.parseLong(todo.getPoints());
				}
				cursor.moveToNext();
			}
			return this.points;
		}
		
	  private Todo cursorToTodo(Cursor cursor) {
		    Todo todo = new Todo();
		    todo.setId(cursor.getLong(0));
		    todo.setCategory(cursor.getString(1));
		    todo.setSummary(cursor.getString(2));
		    todo.setDescription(cursor.getString(3));
		    todo.setStatus(cursor.getInt(4) == 0 ? false : true);
		    todo.setPoints(cursor.getString(5));
		    todo.setLocation(cursor.getString(6));
		    return todo;
		  }
}
