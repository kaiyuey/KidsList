package kidslist.adapter;

import java.util.ArrayList;
import java.util.List;

import com.example.kidslist.R;

import kidslist.model.Todo;
import kidslist.sqlite.helper.TodoDataSource;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

// I tried two adapters in diffrent calss, this one uses BaseAdapter and viewholder
public class PointsCustomAdapter extends BaseAdapter {
	
	 private TodoDataSource mDatasource;
	 private List<Todo> todoList;
	 private LayoutInflater mInflater;
	 
	 public PointsCustomAdapter(Context context, int layoutResourceId, 
			 List<Todo> todoList, TodoDataSource datasource){
		 //super(context, layoutResourceId, todoList);
		 this.todoList = todoList;
		 mInflater = LayoutInflater.from(context);
	 }
	 
	 public View getView(int position, View convertView, ViewGroup parent) {
		  ViewHolder holder;
		  if (convertView == null) {
		   convertView = mInflater.inflate(R.layout.task_view, parent, false);
		   holder = new ViewHolder();
		   holder.txtSum = (TextView) convertView.findViewById(R.id.pointsSummary);
		   holder.txtPoints = (TextView) convertView.findViewById(R.id.points);

		   convertView.setTag(holder);
		  } else {
		   holder = (ViewHolder) convertView.getTag();
		  }
		  
		  holder.txtSum.setText(todoList.get(position).getSummary());
		  holder.txtPoints.setText(todoList.get(position).getPoints());
		  
		  return convertView;
		 }

		 static class ViewHolder {
		  TextView txtSum;
		  TextView txtPoints;
		 }

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return todoList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return todoList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		
}
