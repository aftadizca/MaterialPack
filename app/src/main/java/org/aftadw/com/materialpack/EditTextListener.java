package org.aftadw.com.materialpack;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

/**
 * Created by Afta Dizca Wahana on 03/30/2018.
 */

public class EditTextListener implements View.OnFocusChangeListener , TextWatcher {

    boolean changed = true;
    Activity context;
    myDbAdapter db;
    Karton[] karton;

    public EditTextListener(Activity context, myDbAdapter db, Karton[] karton){
        this.context = context;
        this.db =  db;
        this.karton = karton;
    }

    @Override
    public void onFocusChange(View view, boolean b) {
        if(!b && changed){
            db.saveDataKarton(context,db, karton);
            this.changed = false;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        this.changed = true;
    }
}
