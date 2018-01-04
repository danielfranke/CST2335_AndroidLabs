package danielfranke.com.cst2335_androidlabs;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static danielfranke.com.cst2335_androidlabs.ChatDatabaseHelper.TABLE_NAME;


public class ChatWindow extends Activity {

    protected static final String ACTIVITY_NAME = "ChatWindowActivity";
    ListView listViewMessages;
    EditText editTextMessage;
    Button buttonSendMessage;
    ArrayList<String> userList = new ArrayList<>();
    String input;
    ChatAdapter messageAdapter;
    SQLiteDatabase sqldb;
    Cursor cursor;

    private Boolean isLandscapeLayout;
    private FrameLayout landscapeFrameLayout;
    private int requestCode = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_window);

        Log.i(ACTIVITY_NAME, "In onCreate()");
        Log.d("DEBUG", getResources().getConfiguration().orientation + "");

        listViewMessages = findViewById(R.id.viewChatMessages);
        editTextMessage = findViewById(R.id.typeChatMessages);
        buttonSendMessage = findViewById(R.id.sendChatMessage);

        messageAdapter=new ChatAdapter(this);
        listViewMessages.setAdapter(messageAdapter);

        landscapeFrameLayout = findViewById(R.id.landscapeFrameLayoutChatWindow);

        if(landscapeFrameLayout == null) {
            isLandscapeLayout = false;
            Log.i(ACTIVITY_NAME, "--- Layout is portrait");
        }
        else
        {
            isLandscapeLayout = true;
            Log.i(ACTIVITY_NAME, "--- Layout is landscape");
        }



        ChatDatabaseHelper aHelperObject = new ChatDatabaseHelper(this);
        sqldb = aHelperObject.getWritableDatabase();

        cursor= sqldb.rawQuery("select * from "+ChatDatabaseHelper.TABLE_NAME,null);

        int messageIndex = cursor.getColumnIndex(ChatDatabaseHelper.KEY_MESSAGE);
        if(cursor.moveToFirst()) {

            while (!cursor.isAfterLast()){
                Log.i(ACTIVITY_NAME, "SQL MESSAGE:" + cursor.getString(messageIndex));
                userList.add(cursor.getString(messageIndex));
                cursor.moveToNext();
            }
        }

        Log.i(ACTIVITY_NAME, "Cursor’s  column count =" + cursor.getColumnCount());

        buttonSendMessage.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                input = editTextMessage.getText().toString();
                userList.add(input);
                messageAdapter.notifyDataSetChanged();
                editTextMessage.setText("");

                ContentValues newData = new ContentValues();
                newData.put(ChatDatabaseHelper.KEY_MESSAGE, input);
                sqldb.insert(TABLE_NAME, "" , newData);
                refreshActivity();

            }
        });

        final Intent intent = new Intent(this, MessageDetails.class);

        listViewMessages.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String message = messageAdapter.getItem(position);
                long idInDb =  messageAdapter.getItemId(position);

                Bundle bundle = new Bundle();
                bundle.putLong("id",idInDb);
                bundle.putString("message", message);
                bundle.putBoolean("isLandscape", isLandscapeLayout);

                if(isLandscapeLayout){
                    MessageFragment messageFragment = new MessageFragment();
                    messageFragment.setArguments(bundle);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.landscapeFrameLayoutChatWindow,messageFragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                }
                else{
                    intent.putExtra("bundle", bundle);
                    startActivityForResult(intent, requestCode);
                }
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (this.requestCode == requestCode && data != null) {
            Long id = data.getLongExtra("id", -1);
            sqldb.delete(TABLE_NAME, ChatDatabaseHelper.KEY_ID + "=" + id, null);
            refreshActivity();
        }
    }

    private class ChatAdapter extends ArrayAdapter<String> {
         ChatAdapter(Context ctx){
            super(ctx, 0);
        }

        public int getCount() {
            return userList.size();
        }

        public String getItem(int position) {
            return userList.get(position);
        }

        public View getView(int position, View convertView, ViewGroup parent) {
            LayoutInflater inflater = ChatWindow.this.getLayoutInflater();
            View result;
            if (position % 2 == 0)
                result = inflater.inflate(R.layout.chat_row_incoming, null);
            else
                result = inflater.inflate(R.layout.chat_row_outgoing, null);

            TextView message = result.findViewById(R.id.message_text);
            message.setText(getItem(position));
            return result;
        }

        public long getItemId(int position){
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndex(ChatDatabaseHelper.KEY_ID));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.i(ACTIVITY_NAME, "In onResume()");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(ACTIVITY_NAME, "In onStart()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.i(ACTIVITY_NAME, "In onPause()");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.i(ACTIVITY_NAME, "In onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sqldb.close();
        cursor.close();
        Log.i(ACTIVITY_NAME, "In onDestroy()");
    }

    public void refreshActivity(){
        finish();
        Intent intent = getIntent();
        startActivity(intent);
    }
}