package iot.accenture.com.iotapplication;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.iot.fragments.LoginScreenFragment;
import iot.accenture.com.warehouseapplication.R;

public class MainActivity extends AppCompatActivity {
    public static String ipAddress,port,threshodValue="25.0";
    public static int timerValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.add(R.id.fragment,new LoginScreenFragment());
        ft.commit();
    }
}