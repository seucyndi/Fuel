package android.fuel;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase.CursorFactory;

public class FuelSQLHelper extends SQLiteOpenHelper {
	
	public static final String tableName = "fuel";
	
	public String sWhere;
	public int iOilType;
	public double iOilPrice;
	public int iItemMetre;
	public int iItemPrice;
	
	public int iLastTotalMetre;
	public double iFreqOilPrice;
	public int iFreqItemPrice;
	public String sFreqItemWhere;

	public FuelSQLHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS " + tableName + " (id INTEGER PRIMARY KEY, [where] VARCHAR, [type] int, perprice double, price int, metre int)");  
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		
	}

}
