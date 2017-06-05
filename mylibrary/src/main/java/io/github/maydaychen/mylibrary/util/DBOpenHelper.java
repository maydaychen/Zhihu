package io.github.maydaychen.mylibrary.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBOpenHelper extends SQLiteOpenHelper{

    public DBOpenHelper(Context context) {
    	super(context, "PMSG.db", null, 2);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE pushmsg ( id integer primary key autoincrement, msg_id VARCHAR (64), title VARCHAR (128), send_time VARCHAR (128), content text, create_user VARCHAR (128), commno VARCHAR (128), sendee VARCHAR (128), is_read VARCHAR (4))");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	

}
