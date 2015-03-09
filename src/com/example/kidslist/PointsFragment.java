package com.example.kidslist;

import java.util.ArrayList;
import java.util.List;

import kidslist.adapter.MyCustomAdapter;
import kidslist.adapter.PointsCustomAdapter;
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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.AdapterContextMenuInfo;

public class PointsFragment extends ListFragment {
	
	private TodoDataSource datasource;
	private Todo item;
	List<Todo> pointslist;
	PointsCustomAdapter adapt;
	//ListAdapter adapt;
	ListView listPoints;
	TextView totalPoints;
	
	public PointsFragment(){}
	
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);         
           
        	datasource = new TodoDataSource(getActivity());
    	    datasource.open();
    	    
    	    pointslist = datasource.getAllDones();
    	    
    	    adapt = new PointsCustomAdapter(getActivity(), R.layout.task_view, pointslist, datasource);
            //ListView listTask =(ListView) rootView.findViewById(android.R.id.list);
            listPoints.setAdapter(adapt);
            totalPoints.setText(String.valueOf(datasource.getPoints()));

    }
    
    
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.fragment_points, container, false);
        listPoints = (ListView) rootView.findViewById(android.R.id.list);
        totalPoints = (TextView) rootView.findViewById(R.id.points);
        registerForContextMenu (listPoints);

        return rootView;
    }
	
	@Override
	public void onListItemClick(ListView l, View v, int position, long id){
		super.onListItemClick(l,v,position,id);
		 Toast.makeText(getActivity(),"Text!",Toast.LENGTH_SHORT).show();
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
	        	return true;
	        case R.id.delete:
	        	Todo deleteTask = (Todo) listPoints.getAdapter().getItem(info.position);
	        	datasource.deleteTodo(deleteTask);
	        	return true;
	        default:
	            return super.onContextItemSelected(item);
	    }
	}
	
	public long points(){
		return this.datasource.getPoints();
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
