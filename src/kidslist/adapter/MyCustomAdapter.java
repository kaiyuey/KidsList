package kidslist.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.kidslist.R;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.Toast;
import kidslist.model.Todo;
import kidslist.sqlite.helper.TodoDataSource;

public class MyCustomAdapter extends ArrayAdapter<Todo>  {
	
	private TodoDataSource mDatasource;
	private List<Todo> todoList = new ArrayList<Todo>();
	//int layoutResourceId;

	public MyCustomAdapter (Context context, int layoutResourceId, 
		   List<Todo> todoList, TodoDataSource datasource){
		   super(context, layoutResourceId, todoList);
		   this.todoList = todoList;
		   mDatasource = datasource;
	}
	
	/**This method will DEFINe what the view inside the list view will finally look like
	* Here we are going to code that the checkbox state is the status of task and
	* check box text is the task name
	*/
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	
    CheckBox chk = null;
	if(convertView == null){
	LayoutInflater inflater = (LayoutInflater) getContext()
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	convertView = inflater.inflate(R.layout.todo_list, parent, false);
	chk=(CheckBox) convertView.findViewById(R.id.checkBox1);
	convertView.setTag(chk);

	chk.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				CheckBox cb = (CheckBox) v;
				Todo changeTask = (Todo) cb.getTag();
				changeTask.setStatus(cb.isChecked() == true);
				Toast.makeText(getContext(),
					       "Clicked on Checkbox: " + changeTask.getSummary() +
					       " is " + changeTask.getStatus(), 
					       Toast.LENGTH_LONG).show();
				mDatasource.saveTodo(changeTask);
			}
		});
	}
	else {
		chk = (CheckBox) convertView.getTag();
	}
	
	Todo current=todoList.get(position);
	chk.setText(current.getSummary());
	chk.setChecked(current.getStatus());

	chk.setTag(current);
	Log.d("listener", String.valueOf(current.getId()));

	return convertView;
	}
	
	
}
