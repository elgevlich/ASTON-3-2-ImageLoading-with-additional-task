package com.example.funimages;

import static com.example.funimages.R.drawable.ic_baseline_image_24;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

	ImageView image;
	EditText urlEdit;
	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		image = findViewById(R.id.image_view);
		Button button = findViewById(R.id.button);
		urlEdit = findViewById(R.id.picture_url);

		button.setOnClickListener(this);
	}

	@Override public void onClick(View view) {
		url = urlEdit.getText().toString();
		new DownloadImageTask().execute(url);
	}

	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {

		HttpURLConnection httpURLConnection;

		protected Bitmap doInBackground(String... urls) {
			try {
				URL url = new URL(urls[0]);
				httpURLConnection = (HttpURLConnection)url.openConnection();
				InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
				return BitmapFactory.decodeStream(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		protected void onPostExecute(Bitmap result) {
			if (result != null) {
				image.setImageBitmap(result);
			} else {
				Toast.makeText(getApplicationContext(), "Cannot download the image:(", Toast.LENGTH_SHORT).show();
				image.setImageDrawable(getDrawable(ic_baseline_image_24));
			}
		}

	}

}


