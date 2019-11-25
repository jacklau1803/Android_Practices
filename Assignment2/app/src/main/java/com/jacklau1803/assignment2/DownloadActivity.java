package com.jacklau1803.assignment2;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class DownloadActivity extends AppCompatActivity {
    EditText url;
    EditText title;
    ProgressDialog pd;
    DownloadTask task;
    DBHandler db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download);
        url = findViewById(R.id.et_url);
        title = findViewById(R.id.et_title);
        Button download = findViewById(R.id.b_down);
        db = new DBHandler(this);
        pd = new ProgressDialog(DownloadActivity.this);
        pd.setIndeterminate(true);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("Connecting");
        pd.setMessage("Downloading the image file...");
        download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkNetwork()){
                    finish();
                }else {
                    if (url.getText().toString().trim().length() == 0) {
                        toastMessage("Please enter image url");
                    } else if (title.getText().toString().trim().length() == 0) {
                        toastMessage("Please enter image title");
                    } else {

                        task = (DownloadTask) new DownloadTask()
                                .execute(stringToURL(
                                        url.getText().toString()
                                ));
                    }
                    finish();
                }
            }
        });
    }

    protected boolean checkNetwork(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        if(ni != null && ni.isConnected()){
            return true;
        }else{
            toastMessage("No connection");
            return false;
        }
    }

    private class DownloadTask extends AsyncTask<URL, Void, Bitmap> {
        protected void onPreExecute() {
            pd.show();
        }

        protected Bitmap doInBackground(URL... urls) {
            URL url = urls[0];
            HttpURLConnection connection = null;
            try {
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();
                InputStream inputStream = connection.getInputStream();
                BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
                Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
                return bmp;

            } catch (IOException e) {
                e.printStackTrace();
                toastMessage("Failed to connect");
            } finally {
                connection.disconnect();
            }
            return null;
        }

        protected void onPostExecute(Bitmap result) {
            pd.dismiss();

            if (result != null) {
                Uri imageInternalUri = saveImageToInternalStorage(result);
                Log.d("MyApp", String.valueOf(imageInternalUri));
                db.addData(title.getText().toString(), String.valueOf(imageInternalUri));
                finish();
            } else {
            }
        }
    }

    // Custom method to convert string to url
    protected URL stringToURL(String urlString) {
        try {
            URL url = new URL(urlString);
            return url;
        } catch (MalformedURLException e) {
            e.printStackTrace();
            toastMessage("Invalid url string");
        }
        return null;
    }

    protected Uri saveImageToInternalStorage(Bitmap bitmap) {
        ContextWrapper wrapper = new ContextWrapper(getApplicationContext());
        File file = wrapper.getDir("Images", MODE_PRIVATE);
        file = new File(file, title.getText().toString() + ".jpg");
        try {
            OutputStream stream = null;
            stream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            stream.flush();
            stream.close();


        } catch (IOException e) // Catch the exception
        {
            e.printStackTrace();
        }
        Uri savedImageURI = Uri.parse(file.getAbsolutePath());
        return savedImageURI;
    }
    public void saveData(String title, String uri) {
        boolean insertData = db.addData(title, uri);

        if (insertData) {
            toastMessage("Data Successfully Inserted!");
        } else {
            toastMessage("Something went wrong");
        }
    }

    private void toastMessage(String message){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show();
    }
}

