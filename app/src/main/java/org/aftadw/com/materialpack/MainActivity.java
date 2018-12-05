package org.aftadw.com.materialpack;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    Fragment Home = new Home();
    Fragment Result = new Result();

    Button homeButton;
    Button resultButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
                        .setFontAttrId(R.attr.fontPath)
                        .build());

        homeButton = findViewById(R.id.homeButton);
        resultButton = findViewById(R.id.resultButton);
        homeButton.setEnabled(false);
        resultButton.setEnabled(true);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.add(R.id.fragment_contain,Home).add(R.id.fragment_contain,Result).show(Home).hide(Result).commit();


        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeButton.setEnabled(false);
                resultButton.setEnabled(true);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //transaction.remove(Result);
                transaction.show(Home).hide(Result).commit();

            }
        });
        resultButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                homeButton.setEnabled(true);
                resultButton.setEnabled(false);
                FragmentTransaction transaction = getFragmentManager().beginTransaction();
                //transaction.remove(Home);
                transaction.show(Result).hide(Home).commit();
            }
        });
    }

    @Override
    public void onPause(){
        super.onPause();
        Toast.makeText(getApplication().getBaseContext(),"Pause",Toast.LENGTH_SHORT);
    }
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

    }








}
