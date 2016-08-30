package com.example.cat.zajav;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.example.cat.zajav.dummy.DummyContent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a {@link ItemListActivity}
 * in two-pane mode (on tablets) or a {@link ItemDetailActivity}
 * on handsets.
 */
public class ItemDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_BUNDLE = "z_bundle";
    public static final String ARG_ID = "z_id";
    public static final String ARG_STRCODE = "z_strcode";
    public static final String ARG_DATE = "z_date";
    public static final String ARG_NOTE = "z_note";
    private int id;
    private View rootView;

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.DummyItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ItemDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ID));

            Activity activity = this.getActivity();
/*
            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.content);
            }
*/
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.item_detail, container, false);

        // Show the dummy content as text in a TextView.
/*
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.item_detail)).setText(mItem.details);
        }
*/
        EditText et_strcode= (EditText)rootView.findViewById(R.id.zh_strcode);
        String s=getArguments().getString(ARG_STRCODE);
        if (s==null)
            s=new String("id="+id);
        et_strcode.setText(s);

        EditText et_note= (EditText)rootView.findViewById(R.id.zh_note);
        s=getArguments().getString(ARG_NOTE);
        if (s==null)
            s=new String(" ");
        if (s.length() < 10)
            s=new String(s+"                    ");
        et_note.setText(s);

        DatePicker dp = (DatePicker) rootView.findViewById(R.id.zh_date);
        s=getArguments().getString(ARG_DATE);
        if (s==null)
          s=new String("01.01.2016");

        SimpleDateFormat format1 = new SimpleDateFormat();
        format1.applyPattern("dd.MM.yyyy");
        try {
            Date d = format1.parse(s);
//            dp.init(2016, 12, 12, null);
            Calendar c = Calendar.getInstance();
            c.setTime (d); // Date date            dp.init(d. null);
            dp.init(c.get(Calendar.YEAR),c.get(Calendar.MONTH),c.get(Calendar.DAY_OF_MONTH),null );
        } catch (ParseException e) {
            e.printStackTrace();
        }


        id= Integer.valueOf(getArguments().getString(ARG_ID));


        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
/*
        EditText et_strcode= (EditText)rootView.findViewById(R.id.zh_strcode);
        et_strcode.setText(arguments.getString(ARG_STRCODE));

        EditText et_note= (EditText)rootView.findViewById(R.id.zh_note);
        et_note.setText(arguments.getString(ARG_NOTE));

        Date d = new Date(arguments.getString(ARG_NOTE));
        ((DatePicker) rootView.findViewById(R.id.zh_date)).updateDate   (d.getYear(), d.getMonth(), d.getDay());
        id= Integer.valueOf(arguments.getString(ARG_ID));
*/
    }

    public void onClickOK(View view){
        EditText et_strcode= (EditText)rootView.findViewById(R.id.zh_strcode);
        String strcode = et_strcode.getText().toString();

        EditText et_note= (EditText)rootView.findViewById(R.id.zh_note);
        String note = et_note.getText().toString().trim();

        DatePicker dp = (DatePicker) rootView.findViewById(R.id.zh_date);
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, dp.getYear());
        c.set(Calendar.MONTH, dp.getMonth());
        c.set(Calendar.DAY_OF_MONTH, dp.getDayOfMonth());

        ZajavDBHelper.saveZajav(id, strcode, c.getTime(), note);
        getActivity().finish();
    }

    public void onClickCancel(View view){
        getActivity().finish();
    }

}
