package com.example.kidslist;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import kidslist.model.Todo;
//import kidslist.todos.contentprovider.MyTodoContentProvider;
import kidslist.sqlite.helper.TodoDataSource;

/*
 * TodoDetailActivity allows to enter a new todo item 
 * or to change an existing
 */
public class TodoDetailActivity extends Activity {
  private Spinner mCategory;
  private EditText mTitleText;
  private EditText mBodyText;
  private EditText mPoints;
  private TextView mLocation;

  private TodoDataSource datasource;

  @Override
  protected void onCreate(Bundle bundle) {
    super.onCreate(bundle);
    setContentView(R.layout.todo_edit);

    datasource = new TodoDataSource(getApplicationContext());
    datasource.open();
    
    mCategory = (Spinner) findViewById(R.id.category);
    mTitleText = (EditText) findViewById(R.id.todo_edit_summary);
    mBodyText = (EditText) findViewById(R.id.todo_edit_description);
    mPoints = (EditText) findViewById(R.id.points);
    mLocation = (TextView) findViewById(R.id.location);
    
    Button confirmButton = (Button) findViewById(R.id.todo_edit_button);
    
    Bundle extras = getIntent().getExtras();
	long id = getIntent().getLongExtra("ID",0);
	final Todo editTodo = datasource.find(id);
    getGps(extras);
    getCamera(extras);
    // Or passed from the other activity
    if (extras != null) {

    	//mCategory.setText(editTodo.getCategory());
    	mTitleText.setText(editTodo.getSummary());
    	mBodyText.setText(editTodo.getDescription());
    	mPoints.setText(editTodo.getPoints());
    	mLocation.setText(editTodo.getLocation());
    	makeToast(editTodo.getLocation());
    	
    	// you can edit your todo task
    	confirmButton.setOnClickListener(new View.OnClickListener() {
    		public void onClick(View view) {
    	        if (TextUtils.isEmpty(mTitleText.getText().toString())) {
    	          makeToast("Please maintain a Summary");
    	        }else{
    	        	editTodo.setSummary(mTitleText.getText().toString());
    	        	editTodo.setDescription(mBodyText.getText().toString());
    	        	editTodo.setPoints(mPoints.getText().toString());
    	        	//editTodo.setLocation(mLocation.getText().toString());
    	        	datasource.saveTodo(editTodo);
    	            Intent i = new Intent(TodoDetailActivity.this, MainActivity.class);
    				startActivity(i);
    	        }
    		}
    	});
    }
    //new todo task
    else{
    confirmButton.setOnClickListener(new View.OnClickListener() {
      public void onClick(View view) {
        if (TextUtils.isEmpty(mTitleText.getText().toString())) {
          makeToast("Please maintain a Summary");
        } else {
        	mPoints.setText("0");
        	final String category = mCategory.getSelectedItem().toString();
           	final String summary = mTitleText.getText().toString();
            final String description = mBodyText.getText().toString();
            final String points= mPoints.getText().toString();
            final String location = mLocation.getText().toString();
            datasource.createTodo(category, summary, description, points, location);
            Intent i = new Intent(TodoDetailActivity.this, MainActivity.class);
			startActivity(i);
        }
      }

    });
    }
  }
  
  private void getGps(final Bundle bundle){
	    Button getGps = (Button) findViewById(R.id.gps);
	    getGps.setOnClickListener(new View.OnClickListener(){	    	
    		public void onClick(View v){
    			//Toast.makeText(TodoDetailActivity.this, "You click on it", Toast.LENGTH_LONG).show();
    			Intent i = new Intent(TodoDetailActivity.this, GPSActivity.class);
    			if (bundle == null){
    				 makeToast("Location only available with confirmed task");
    			}
    			else{
    				i.putExtras(bundle);
    				startActivity(i);
    			}
    		}
    	});	  
  }
  
  private void getCamera(final Bundle bundle){
	    Button getCamera = (Button) findViewById(R.id.camera);
	    getCamera.setOnClickListener(new View.OnClickListener(){
	    	public void onClick(View v){
	    		Intent i = new Intent(TodoDetailActivity.this, CameraActivity.class);
	    		if (bundle == null){
   				 makeToast("Location only available with confirmed task");
   			}
   			else{
   				i.putExtras(bundle);
   				startActivity(i);
   			}
	    	}
	    });

  }
  
  @Override
	public void onResume() {
    datasource.open();
    super.onResume();
    //adapt.notifyDataSetChanged();

  }

  @Override
	public void onPause() {
    datasource.close();
    super.onPause();
  }

  private void makeToast(String text) {
    Toast.makeText(TodoDetailActivity.this, text,
        Toast.LENGTH_LONG).show();
  }
} 
