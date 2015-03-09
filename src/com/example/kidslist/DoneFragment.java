package com.example.kidslist;

import java.util.List;

import kidslist.adapter.MyCustomAdapter;
import kidslist.model.Todo;
import kidslist.sqlite.helper.TodoDataSource;
import android.app.Fragment;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ContextMenu.ContextMenuInfo;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class DoneFragment extends ListFragment {
	
	private TodoDataSource datasource;

	List<Todo> todoList;
	//MyCustomAdapter adapt;
	ListAdapter adapt;
	ListView listTask;
	
	public DoneFragment(){}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);         

    	datasource = new TodoDataSource(getActivity());
	    datasource.open();
	    todoList = datasource.getAllDones();
	    adapt = new MyCustomAdapter(getActivity(), R.layout.todo_list, todoList, datasource);
        //ListView listTask =(ListView) rootView.findViewById(android.R.id.list);
        listTask.setAdapter(adapt);
	}
	
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_done, container, false);
        listTask = (ListView) rootView.findViewById(android.R.id.list);
        registerForContextMenu (listTask);

        return rootView;
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l,v,position,id);
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
	        	//makeToast("edit");
	        	return true;
	        case R.id.delete:
	        	//makeToast("delete");
	        	Todo deleteTask = (Todo) listTask.getAdapter().getItem(info.position);
	        	datasource.deleteTodo(deleteTask);
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
    @Override
	public void onResume() {
      datasource.open();
      super.onResume();
      //adapt.notifyDataSetChanged();
      Log.d("MyActivity", "List Frag Resumed");

    }

    @Override
	public void onPause() {
      datasource.close();
      super.onPause();
    }
}
