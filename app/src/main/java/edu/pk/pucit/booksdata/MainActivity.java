package edu.pk.pucit.booksdata;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    RecyclerView rv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        String json = "";
        ArrayList<String> titles = new ArrayList<>();
        ArrayList<String> levels = new ArrayList<>();
        ArrayList<String> infos = new ArrayList<>();
        ArrayList<String> urls = new ArrayList<>();
        ArrayList<String> imgPaths = new ArrayList<>();
        try {

            InputStream is = getResources().openRawResource(R.raw.data);
            byte[] data = new byte[is.available()];

            //loop will read data into byte[] data
            while (is.read(data)!=-1){
                //empty as nothing needs to be done here
            }
            json = new String(data , "UTF-8");
            Log.i("data.json","length => "+ json.length());

            JSONObject jsonObject = new JSONObject(json);

            JSONArray jsonArray = new JSONArray(jsonObject.getString("books"));
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                titles.add(obj.getString("title"));
                levels.add(obj.getString("level"));
                infos.add(obj.getString("info"));
                urls.add(obj.getString("url"));
                imgPaths.add(obj.getString("cover"));
            }

            //Toast.makeText(this, String.valueOf(titles.size()), Toast.LENGTH_LONG).show();

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        String [] btitles = GetStringArray(titles);
        String [] bookLevels = GetStringArray(levels);
        String [] details = GetStringArray(infos);
        String [] bookUrls = GetStringArray(urls);
        String [] booksImgs = GetStringArray(imgPaths);

        RVAdapter adapter = new RVAdapter(this,btitles ,bookLevels, details, bookUrls , booksImgs);
        rv.setAdapter(adapter);

    }

    // Function to convert ArrayList<String> to String[]
    private String[] GetStringArray(ArrayList<String> arr)
    {

        // declaration and initialise String Array
        String str[] = new String[arr.size()];

        // Convert ArrayList to object array
        Object[] objArr = arr.toArray();

        // Iterating and converting to String
        int i = 0;
        for (Object obj : objArr) {
            str[i++] = (String)obj;
        }

        return str;
    }
}
