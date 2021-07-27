package sg.edu.rp.myapplicationdev.android.gettingmylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActionBar;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Favourites extends AppCompatActivity {
    ListView lvFav;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourites);
        lvFav = findViewById(R.id.lvFavCoords);
        String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
        File targetFile = new File(folderLocation_I, "favourites.txt");
        if (targetFile.exists() == true){
            String data ="";
            try {
                FileReader reader = new FileReader(targetFile); BufferedReader br = new BufferedReader(reader);
                String line = br.readLine(); while (line != null){
                    data += line + "\n";
                    line = br.readLine(); }

                String [] array = data.split("\n");
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                        android.R.layout.simple_list_item_1, android.R.id.text1, array);
                lvFav.setAdapter(adapter);
                br.close();
                reader.close();
            } catch (Exception e) {

                Toast.makeText(Favourites.this, "Failed to read!", Toast.LENGTH_LONG).show();
                e.printStackTrace();
            }
            Log.d("Content", data);
        }
    }
}