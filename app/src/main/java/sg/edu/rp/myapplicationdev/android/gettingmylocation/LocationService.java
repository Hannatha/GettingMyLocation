package sg.edu.rp.myapplicationdev.android.gettingmylocation;

import android.Manifest;
import android.app.Service;
import android.content.Intent;
import android.location.Location;
import android.os.FileObserver;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.PermissionChecker;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.Marker;

import java.io.File;
import java.io.FileWriter;

public class LocationService extends Service {
    FusedLocationProviderClient client;
    LocationRequest request;
    LocationCallback callback;
    Marker central;
    public LocationService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        client = LocationServices.getFusedLocationProviderClient(this);
        File folder = new File(getFilesDir().getAbsolutePath() + "/Folder");
        if (!folder.exists()) {
            if (folder.mkdir()){
                Log.d("Create folder", "Success");
            } else {
                Log.d("Create folder", "Failure");
            }
        } else {
            Log.d("Create folder", "Folder exists.");
        }


        if (checkPermission()){
            request = LocationRequest.create();
            request.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
            request.setInterval(30000);
            request.setFastestInterval(5000);
            request.setSmallestDisplacement(500);
            callback = new LocationCallback() {
                @Override
                public void onLocationResult(LocationResult locationResult) {
                    if (locationResult != null) {
                        Location location = locationResult.getLastLocation();

                        try {
                            File file = new File(folder, "location.txt");
                            FileWriter writer = new FileWriter(file, true);
                            writer.write(location.getLatitude() + ", " + location.getLongitude() + "\n");
                            writer.flush();
                            writer.close();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            client.requestLocationUpdates(request, callback, null);
            Toast.makeText(this, "Detection started.", Toast.LENGTH_SHORT).show();
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (client != null) {
            client.removeLocationUpdates(callback);
            Toast.makeText(this, "Stopped detecting.", Toast.LENGTH_SHORT).show();
        }
    }

    private boolean checkPermission() {
        int permissionCheck_Coarse = ContextCompat.checkSelfPermission(
                LocationService.this, Manifest.permission.ACCESS_COARSE_LOCATION);
        int permissionCheck_Fine = ContextCompat.checkSelfPermission(
                LocationService.this, Manifest.permission.ACCESS_FINE_LOCATION);
        return permissionCheck_Coarse == PermissionChecker.PERMISSION_GRANTED || permissionCheck_Fine == PermissionChecker.PERMISSION_GRANTED;
    }
}