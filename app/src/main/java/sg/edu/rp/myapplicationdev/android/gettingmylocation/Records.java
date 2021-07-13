package sg.edu.rp.myapplicationdev.android.gettingmylocation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Records extends AppCompatActivity {
    TextView tvRecords;
    Button btnRefresh;
    ListView lvCoords;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_records);

        tvRecords = findViewById(R.id.tvRecords);
        lvCoords = findViewById(R.id.lvCoords);
        btnRefresh = findViewById(R.id.btnRefresh);

        String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
        File targetFile = new File(folderLocation_I, "location.txt");
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
                lvCoords.setAdapter(adapter);

                tvRecords.setText("Number of Records: "+array.length);
                br.close();
                reader.close();
            } catch (Exception e) {

                Toast.makeText(Records.this, "Failed to read!", Toast.LENGTH_LONG).show();
                e.printStackTrace(); }
            Log.d("Content", data); }

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String folderLocation_I = getFilesDir().getAbsolutePath() + "/Folder";
                File targetFile = new File(folderLocation_I, "location.txt");
                if (targetFile.exists() == true){
                    String data ="";
                    try {
                        FileReader reader = new FileReader(targetFile); BufferedReader br = new BufferedReader(reader);
                        String line = br.readLine(); while (line != null){
                            data += line + "\n";
                            line = br.readLine(); }

                        String [] array = data.split("\n");
                        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, android.R.id.text1, array);
                        lvCoords.setAdapter(adapter);
                        tvRecords.setText("Number of Records: "+array.length);
                        br.close();
                        reader.close();
                    } catch (Exception e) {
                        Toast.makeText(Records.this, "Failed to read!", Toast.LENGTH_LONG).show();
                        e.printStackTrace(); }
                    Log.d("Content", data); }
            }
        });
    }
}