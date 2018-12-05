package org.aftadw.com.materialpack;

import android.app.Activity;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;

/**
 * Created by Afta Dizca Wahana on 03/28/2018.
 */

public class SpinnerInteractionListener implements AdapterView.OnItemSelectedListener,View.OnTouchListener {
    boolean userSelect = false;
    int position;
    Karton[] karton;
    myDbAdapter db;
    Activity activity;
    int prevSelectedItem;


    public SpinnerInteractionListener(Activity activity, Karton[] karton, int prevSelectedItem, int position, myDbAdapter db){
        this.position = position;
        this.karton = karton;
        this.db = db;
        this.activity =  activity;
        this.prevSelectedItem = prevSelectedItem;
    }
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        userSelect = true;
        return false;
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(userSelect){
            userSelect=false;
            String a = (String) adapterView.getItemAtPosition(i);
            if (karton[position].produk.getSelectedItem().toString().equals("")) {
                karton[position].line.setEnabled(false);
                karton[position].saldoAwal.setEnabled(false);
                karton[position].saldoAwal.setFocusable(false);
                karton[position].terima.setEnabled(false);
                karton[position].terima.setFocusable(false);
            } else {
                karton[position].line.setEnabled(true);
                karton[position].saldoAwal.setEnabled(true);
                karton[position].saldoAwal.setEnabled(true);
                karton[position].terima.setEnabled(true);
                karton[position].terima.setFocusable(true);
            }

            if(this.prevSelectedItem!=i || !(a.equals("")) ) {

                db.saveDataKarton(activity,db, karton);
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
