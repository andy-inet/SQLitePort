package com.example.cat.sqliteportactivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cat.sqliteport.R;

public class DataBrowser extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    static final Uri ZAJAV_URI = Uri.parse("content://com.example.cat.sqliteport/zajav");
    static final Uri META_URI = Uri.parse("content://com.example.cat.sqliteport/meta");
    private boolean mTwoPane;
    Cursor semCursor, semSpecCur;
    private SimpleCursorAdapter ZajavAdapter;
    private String zajavID;
    private Cursor cursor;
    private ListView listView;
    private TextView tvSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        listView = (ListView) findViewById(R.id.zajav_list);
        tvSelected = (TextView) findViewById(R.id.tv_Selected);

        LayoutInflater inflater = getLayoutInflater();
        ViewGroup header = (ViewGroup) inflater.inflate(R.layout.z_header, listView, false);
        listView.addHeaderView(header, null, true);


        // открываем подключение
//        PortDBHelper.sqlHelper = new PortDBHelper(this);


        displayListView();



/*
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
*/

//        View recyclerView = findViewById(R.id.item_list);
//        assert recyclerView != null;

//        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
//        ZajavAdapter.notifyDataSetChanged();
//        listView.refreshDrawableState();
        displayListView();
    }

    public void onClick(View view) {
        EditText et = (EditText) findViewById(R.id.etID);
        String s = et.getText().toString();
        EditText etMode = (EditText) findViewById(R.id.etMode);
        String sMode = etMode.getText().toString();
        String su;
        if (s.length() > 0)
            su = new String("content://com.example.cat.sqliteport/" + cursor.getString(1) + "/" + s);
        else
            su = new String("content://com.example.cat.sqliteport/" + cursor.getString(1));

        if (sMode.equals("Q")) {
            Context context = view.getContext();
            Intent intent = new Intent(context, TblDataActivity.class);
            intent.putExtra("URI", su);
            intent.putExtra("ID", s);
            context.startActivity(intent);
        }else        if (sMode.equals("D")) {
            getContentResolver().delete(Uri.parse(su), "_id", new String[]{s});
        }

    }

    private void displayListView() {

        // The desired columns to be bound
        String[] columns = new String[]{"_id", "tbl"};

//        Cursor cursor = PortDBHelper.fetchAllZajavs();
        if (cursor != null)
            cursor.close();

        cursor = getContentResolver().query(META_URI,
                columns, null, null, null);
        if (cursor != null) {
            if (cursor.moveToNext()) {

            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Форма ресурса: Ошибка при чтении данных.", Toast.LENGTH_SHORT).show();
        }


        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.z_id,
                R.id.z_tbl
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        ZajavAdapter = new SimpleCursorAdapter(
                this, R.layout.item_list_content,
                cursor,
                columns,
                to,
                0);

        // Assign adapter to ListView


        LayoutInflater inflater = getLayoutInflater();

        listView.setAdapter(ZajavAdapter);

//        TextView tv = (TextView) findViewById(R.id.textView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
//                cursor = (Cursor) listView.getItemAtPosition(position);
//                listView.setSelected(true);
                //listView.setSelection(position);
//                SimpleCursorAdapter a = (SimpleCursorAdapter) listView.getAdapter();

                // Get the state's capital from this row in the database.
//                zajavID =
//                        cursor.getString(cursor.getColumnIndexOrThrow("_id"));
//                Toast.makeText(getApplicationContext(),
//                        zajavID, Toast.LENGTH_SHORT).show();
                tvSelected.setText(cursor.getString(1));

            }
        });


    }


}
