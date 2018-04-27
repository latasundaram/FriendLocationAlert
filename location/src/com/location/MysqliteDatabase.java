package com.location;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class MysqliteDatabase extends SQLiteOpenHelper {

	static final String TABLE_USERDETAILS = "user_details";
	static final String COL_USER = "userid";
	static final int VERSION = 1;
	static final String DATABASE = "friend.db";

	public MysqliteDatabase(Context context) {
		super(context, DATABASE, null, VERSION);
		// TODO Auto-generated constructor stub

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		String Query = "CREATE TABLE "+TABLE_USERDETAILS+"("+COL_USER+" TEXT)";
		System.out.println(Query);
		db.execSQL(Query);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub

	}
	
	long insertUserDetails(String userId)
	{
		SQLiteDatabase database=getWritableDatabase();
		try {
			ContentValues values=new ContentValues();
			values.put(COL_USER, userId);
			
			return database.insert(TABLE_USERDETAILS, null, values);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		finally
		{
			database.close();
			
		}
		
		return 0;
		
		
	}
	String getUserId()
	{
		SQLiteDatabase database=getReadableDatabase();
		Cursor c=null;
		try {
			c=database.query(TABLE_USERDETAILS, new String[]{COL_USER}, null, null, null, null, null);
			if(c.moveToNext())
			{
				return  c.getString(0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		finally
		{
			database.close();
			c.close();
		}
		return null;
		
		
	}
	

}
