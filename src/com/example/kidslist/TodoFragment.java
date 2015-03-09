package com.example.kidslist;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.app.ListFragment;
import android.content.Intent;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import kidslist.adapter.MyCustomAdapter;
import kidslist.sqlite.helper.DatabaseHelper;
import kidslist.sqlite.helper.TodoDataSource;
import kidslist.model.Todo;
import kidslist.model.Tag;

public class TodoFragment extends ListFragment {
	
	private TodoDataSource datasource;
	private Todo item;
	List<Todo> todoList;
	MyCustomAdapter adapt;
	//ListAdapter adapt;
	ListView listTask;
	EditText t;
	
	public TodoFragment(){	
	}	
	
	/*@Override
	public void onCreate(Bundle savedInstanceState){
		super.onCreate(savedInstanceState);
		//setHasOptionsMenu(true);
		
		datasource = new TodoDataSource(getActivity());
	    datasource.open();
	    
	    todoList = datasource.getAllTodos();
	    adapt= new MyCustomAdapter(getActivity(), R.layout.todo_list, todoList);
        //ListView listTask =(ListView) rootView.findViewById(android.R.id.list);
        listTask.setAdapter(adapt);
	}*/
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);         
           
        	datasource = new TodoDataSource(getActivity());
    	    datasource.open();
    	    
    	    todoList = datasource.getAllwaits();
    	    adapt = new MyCustomAdapter(getActivity(), R.layout.todo_list, todoList, datasource);
            //ListView listTask =(ListView) rootView.findViewById(android.R.id.list);
            listTask.setAdapter(adapt);
            
            Button button = (Button) getActivity().findViewById(R.id.simpleadd);
            button.setOnClickListener(new OnClickListener() {
           @Override
            public void onClick(View v) {
                //String summary = t.getText().toString();                
                Intent i = new Intent(getActivity(), TodoDetailActivity.class);
    		    
    		    //Bundle bundle = new Bundle();
    		   // bundle.putString("Summary", summary);
    		    //i.putExtras(bundle);
    		    startActivity(i);
            }
        });        
    }
        
    @Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
	}
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_todo, container, false);
        
        listTask = (ListView) rootView.findViewById(android.R.id.list);
        listTask.setAdapter(adapt);

        registerForContextMenu (listTask);

        return rootView;
    }
    
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l,v,position,id);
		 //Toast.makeText(getActivity(),"Click!",Toast.LENGTH_SHORT).show();
		 Todo selectTask = (Todo) l.getAdapter().getItem(position);
         Intent editDetail = new Intent(getActivity(), TodoDetailActivity.class);
         editDetail.putExtra("ID", selectTask.getId());
		 startActivity(editDetail);

	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v,
	                                ContextMenuInfo menuInfo) {
	    MenuInflater inflater = getActivity().getMenuInflater();
	    inflater.inflate(R.menu.context_menu, menu);
	}
	
	@Override
	public boolean onContextItemSelected(MenuItem item) {
	    AdapterContextMenuInfo info = (AdapterContextMenuInfo) item.getMenuInfo();
	    switch (item.getItemId()) {
	        case R.id.edit:
	        	makeToast("edit");
	        	return true;
	        case R.id.delete:
	        	makeToast("delete");
	        	Todo deleteTask = (Todo) listTask.getAdapter().getItem(info.position);
	        	datasource.deleteTodo(deleteTask);
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
    @Override
	public void onResume() {
      //registerForContextMenu (listTask);
      datasource.open();
      super.onResume();
      adapt.notifyDataSetChanged();
      Log.d("MyActivity", "List Frag Resumed");

    }

    @Override
	public void onPause() {
      datasource.close();
      super.onPause();
    }
    
    private void makeToast(String text) {
        Toast.makeText(getActivity(), text,
            Toast.LENGTH_LONG).show();
      }
}