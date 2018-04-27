package com.location;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class FriendCustomizedAdapter extends ArrayAdapter<String> {
	ArrayList<String> name,number,status;
	Context context;
	TextView nameTextView;
	TextView numberTextView;
	Button accecptButton,rejectButton;
	public FriendCustomizedAdapter(Context context, int textViewResourceId, ArrayList<String> name, ArrayList<String> number) {
		super(context, textViewResourceId);
		// TODO Auto-generated constructor stub
		this.name=name;
		this.context=context;
		this.number=number;
		
	}
	public int getCount() {
		// TODO Auto-generated method stub
		return name.size();
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View v=convertView;
		LayoutInflater inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		v=inflater.inflate(R.layout.friendcustomview, null);

		nameTextView=(TextView) v.findViewById(R.id.allfname);
		numberTextView=(TextView) v.findViewById(R.id.allnumber);
		accecptButton=(Button) v.findViewById(R.id.accecptbtn);
		rejectButton=(Button) v.findViewById(R.id.rejectbtn);
		
		
		
		
		nameTextView.setText(name.get(position));
		numberTextView.setText(number.get(position));
		accecptButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String urlString=ConnectionUtility.url+"FriendServelt?usecase=accecpt&fnumber="+number.get(position)+"&status=off&userid="+LoginActivity.userid;
				System.out.println(urlString);
				String response=ConnectionUtility.send(urlString);
				if(response.trim().equalsIgnoreCase("success"))
				{
					Toast.makeText(context, "Adding friend Success", 3000).show();
				}
				else if(response.trim().equalsIgnoreCase("add"))
				{
					Toast.makeText(context, "Alread added to Friend List", 3000).show();
				}
			}
		});
		rejectButton.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String urlString=ConnectionUtility.url+"FriendServelt?usecase=reject&fnumber="+number.get(position)+"&number="+LoginActivity.userid;
				System.out.println(urlString);
				String response=ConnectionUtility.send(urlString);
				if(response.trim().equalsIgnoreCase("success"))
				{
					Toast.makeText(context, "Deleting Friend Success", 3000).show();
				}
				else
				{
					Toast.makeText(context, "Alread Deleted to Friend List", 3000).show();
				}
				
			}
		});
		return v;
	}
}
