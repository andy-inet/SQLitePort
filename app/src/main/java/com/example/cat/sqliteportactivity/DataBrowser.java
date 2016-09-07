package com.example.cat.sqliteportactivity;

import android.content.CursorLoader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.example.cat.sqliteport.PortDBHelper;
import com.example.cat.sqliteport.R;

public class DataBrowser extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    static final Uri ZAJAV_URI = Uri.parse("content://com.example.cat.sqliteport/zajav");
    private boolean mTwoPane;
    Cursor semCursor, semSpecCur;
    private SimpleCursorAdapter ZajavAdapter;
    private String zajavID;
    private Cursor zCursor;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        listView = (ListView) findViewById(R.id.zajav_list);

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

    private void displayListView() {

        // The desired columns to be bound
        String[] columns = new String[]{
                PortDBHelper.Z_ID,
                PortDBHelper.Z_STRCODE,
                PortDBHelper.Z_DATE,
                PortDBHelper.Z_NOTE
        };

//        Cursor cursor = PortDBHelper.fetchAllZajavs();
        if (zCursor != null)
            zCursor.close();

        zCursor = getContentResolver().query(ZAJAV_URI,
                columns, null, null, null);


        // the XML defined views which the data will be bound to
        int[] to = new int[]{
                R.id.z_id,
                R.id.z_strcode,
                R.id.z_date,
                R.id.z_note
        };

        // create the adapter using the cursor pointing to the desired data
        //as well as the layout information
        ZajavAdapter = new SimpleCursorAdapter(
                this, R.layout.item_list_content,
                zCursor,
                columns,
                to,
                0);

        // Assign adapter to ListView


        LayoutInflater inflater = getLayoutInflater();

        listView.setAdapter(ZajavAdapter);

        TextView tv = (TextView) findViewById(R.id.textView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View view,
                                    int position, long id) {
                // Get the cursor, positioned to the corresponding row in the result set
//                zCursor = (Cursor) listView.getItemAtPosition(position);
//                listView.setSelection(position);

                // Get the state's capital from this row in the database.
                zajavID =
                        zCursor.getString(zCursor.getColumnIndexOrThrow("_id"));
//                Toast.makeText(getApplicationContext(),
//                        zajavID, Toast.LENGTH_SHORT).show();

            }
        });


    }







}
