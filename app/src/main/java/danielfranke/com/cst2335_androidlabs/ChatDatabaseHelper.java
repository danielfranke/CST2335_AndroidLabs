package danielfranke.com.cst2335_androidlabs;

        import android.content.Context;
        import android.database.sqlite.SQLiteDatabase;
        import android.database.sqlite.SQLiteOpenHelper;
        import android.util.Log;

public class ChatDatabaseHelper extends SQLiteOpenHelper {

    // CLASS VARIABLES
    public final static String DATABASE_NAME = "Message.db";
    public final static String TABLE_NAME = "MessageTable"; // table rename won't work unless VERSION_NUM is also changed
    public final static String KEY_ID = "ID";
    public final static String KEY_MESSAGE = "MESSAGE";
    public final static int VERSION = 1;
    protected static final String ACTIVITY_NAME = "ChatDatabaseHelper";

    // CONSTRUCTOR
    public ChatDatabaseHelper(Context ctx) {
        super(ctx, DATABASE_NAME, null, VERSION);

    }

    // CREATE A NEW DATABASE
    @Override
    public void onCreate(SQLiteDatabase db) {
        //db.execSQL("CREATE TABLE " + TABLE_NAME + " (" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MESSAGE + "text" + ");"); // class example
        String CREATE_TABLE_MSG = "CREATE TABLE " + TABLE_NAME + "("
                + KEY_ID  + " INTEGER PRIMARY KEY AUTOINCREMENT ,"
                + KEY_MESSAGE + " TEXT )";
        db.execSQL(CREATE_TABLE_MSG);
        Log.i(ACTIVITY_NAME, "Calling onCreate");
    }

    // UPGRADE AN EXISTING DATABASE
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //delete what was there previously
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        Log.i("ChatDatabaseHelper", "Calling onUpgrade, oldVersion=" + oldVersion + "newVersion=" + newVersion);

    }

    // DOWNGRADE DATABASE TO OLD VERSION
    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME); //delete what was there previously
        onCreate(db);
        Log.i("ChatDatabaseHelper", "Calling onCreate");
        Log.i("ChatDatabaseHelper", "Calling onDowngrade, newVersion=" + newVersion + "oldVersion=" + oldVersion);

    }

    // OPEN DATABASE
    @Override
    public void onOpen(SQLiteDatabase db)
    {
        Log.i("Database ", "Calling onOpen");
    }
}