package com.jacklau1803.assignment2;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {
    ListView listView;
    DBHandler db;
    ArrayList<String> ids = new ArrayList<>();
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> images = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        listView = findViewById(R.id.listview);
        db = new DBHandler(this);
        Cursor cursor = db.getData();
        if(cursor.getCount()== 0){
            Toast.makeText(this, "Empty database", Toast.LENGTH_SHORT);
        }else{
            while(cursor.moveToNext()){

                titles.add(cursor.getString(1));
                ids.add(cursor.getString(0));
                images.add(cursor.getString(2));
            }
        }
        Log.e("cursor", String.valueOf(ids.size()));
        ListAdaptor adaptor = new ListAdaptor();
        listView.setAdapter((ListAdapter) adaptor);
    }

    class ListAdaptor extends BaseAdapter{
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = getLayoutInflater().inflate(R.layout.list, null);
            ImageView imageView = view.findViewById(R.id.imageView);
            TextView title = view.findViewById(R.id.tv_title);
            TextView id =  view.findViewById(R.id.tv_id);
            title.setText("TITLE: " + titles.get(position));
            title.setText("TITLE: " + titles.get(position));
            title.setText("TITLE: " + titles.get(position));
            id.setText("ID: " + ids.get(position));

            imageView.setImageURI(Uri.parse(images.get(position)));
            return view;
        }
    }
}
