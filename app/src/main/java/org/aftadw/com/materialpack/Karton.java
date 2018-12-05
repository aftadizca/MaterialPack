package org.aftadw.com.materialpack;

import android.widget.EditText;
import android.widget.Spinner;

/**
 * Created by Afta Dizca Wahana on 03/25/2018.
 */

public class Karton {

    public Spinner produk;
    public Spinner line;
    public EditText saldoAwal;
    public EditText terima;

    public Karton(){

    }

    public void add(Spinner produk, Spinner line, EditText saldoAwal, EditText terima){
        this.produk = produk;
        this.line = line;
        this.saldoAwal = saldoAwal;
        this.terima = terima;
    }
}
