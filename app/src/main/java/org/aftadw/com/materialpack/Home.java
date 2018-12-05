package org.aftadw.com.materialpack;

import android.content.Context;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.support.v7.widget.Toolbar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;
import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link Home.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link Home#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Home extends Fragment{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private ArrayList<String> produkList = new ArrayList<String>();


    Karton[] karton = new Karton[8];
    RelativeLayout touchInterceptor;
    private myDbAdapter db;

    private OnFragmentInteractionListener mListener;

    public Home() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Home.
     */
    // TODO: Rename and change types and number of parameters
    public static Home newInstance(String param1, String param2) {
        Home fragment = new Home();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        Toast.makeText(this.getContext(),"SavedInstance Home",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        setHasOptionsMenu(true);
        Toast.makeText(this.getContext(),"Create Home",Toast.LENGTH_SHORT).show();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_home, container, false);
        AppCompatActivity activity = (AppCompatActivity) getActivity();

        db = new myDbAdapter(getContext());


        Toast.makeText(this.getContext(),"CreateView Home",Toast.LENGTH_SHORT).show();
        //Spinner Adapter
        ArrayAdapter<CharSequence> adapter1 = ArrayAdapter.createFromResource(getActivity(),R.array.produk,R.layout.spinner_item);
        ArrayAdapter<CharSequence> adapter2 = ArrayAdapter.createFromResource(getActivity(),R.array.line,R.layout.spinner_item);
        adapter1.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
        adapter2.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);

        //Deklarasi Komponen
        int[] lineid = new int[]{R.id.line1,R.id.line2,R.id.line3,R.id.line4,R.id.line5,R.id.line6,R.id.line7,R.id.line8};
        int[] produkid = new int[]{R.id.produk1,R.id.produk2,R.id.produk3,R.id.produk4,R.id.produk5,R.id.produk6,R.id.produk7,R.id.produk8};
        int[] saldoawalid = new int[]{R.id.saldoAwal1,R.id.saldoAwal2,R.id.saldoAwal3,R.id.saldoAwal4,R.id.saldoAwal5,R.id.saldoAwal6,R.id.saldoAwal7,R.id.saldoAwal8};
        int[] terimaid = new int[]{R.id.terima1,R.id.terima2,R.id.terima3,R.id.terima4,R.id.terima5,R.id.terima6,R.id.terima7,R.id.terima8};

        for(int i = 0; i<karton.length; i++){
            karton[i] = new Karton();
            karton[i].add((Spinner) view.findViewById(produkid[i]),(Spinner)view.findViewById(lineid[i]),(EditText) view.findViewById(saldoawalid[i]),(EditText) view.findViewById(terimaid[i]));
            karton[i].produk.setAdapter(adapter1);
            karton[i].line.setAdapter(adapter2);

            SpinnerInteractionListener produkSpinnerListener = new SpinnerInteractionListener(getActivity(),karton,karton[i].produk.getSelectedItemPosition(),i,db);
            SpinnerInteractionListener lineSpinnerListener = new SpinnerInteractionListener(getActivity(),karton,karton[i].line.getSelectedItemPosition(),i,db);
            EditTextListener saldoAwalListener = new EditTextListener(getActivity(),db,karton);
            EditTextListener terimaListener = new EditTextListener(getActivity(),db,karton);

            //autosave
            karton[i].produk.setOnTouchListener(produkSpinnerListener);
            karton[i].produk.setOnItemSelectedListener(produkSpinnerListener);
            karton[i].line.setOnTouchListener(lineSpinnerListener);
            karton[i].line.setOnItemSelectedListener(lineSpinnerListener);
            karton[i].saldoAwal.setOnFocusChangeListener(saldoAwalListener);
            karton[i].saldoAwal.addTextChangedListener(saldoAwalListener);
            karton[i].terima.setOnFocusChangeListener(saldoAwalListener);
            karton[i].terima.addTextChangedListener(saldoAwalListener);




        }

        //Toolbar
        Toolbar myToolbar = (Toolbar) view.findViewById(R.id.my_toolbar);
        activity.setSupportActionBar(myToolbar);


        myToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch(item.getItemId()) {
                    case R.id.navigation_save:
                        db.saveDataKarton(getActivity(),db , karton);
                        Toast.makeText(getActivity(), "Save "+db.getDbCount("id","savekarton"), Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_undo:
                        if(db.getDbCount("id", "savekarton")>1){
                            db.deleteSaveData(db.getLastId("id","savekarton"),"savekarton","id");
                            karton = db.getDataKarton(karton,db.getLastId("id","savekarton"));
                            tableDisable();
                        }
                        getActivity().invalidateOptionsMenu();
                        break;
                    case R.id.navigation_archive:
                        Toast.makeText(getActivity(), "Archive", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_setting:
                        Toast.makeText(getActivity(), "Setting", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.navigation_clear:
                        Toast.makeText(getActivity(), "Clear", Toast.LENGTH_SHORT).show();
                        db.deleteTableKarton();
                        karton = db.getDataKarton(karton,db.getLastId("id","savekarton"));
                        tableDisable();
                        break;
                }
                return false;
            }
        });
        //End The Toolbar

        Toast.makeText(getActivity(), "id="+db.getLastId("id","savekarton"), Toast.LENGTH_SHORT).show();

        karton = db.getDataKarton(karton,db.getLastId("id","savekarton"));
        tableDisable();


        //disable focus edittext when tap
        touchInterceptor = view.findViewById(R.id.touchDetector);

        touchInterceptor.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    for(int i = 0; i<karton.length; i++){
                        if (karton[i].saldoAwal.isFocused()) {
                            Rect outRect = new Rect();
                            karton[i].saldoAwal.getGlobalVisibleRect(outRect);
                            if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                                karton[i].saldoAwal.clearFocus();
                                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }
                        if (karton[i].terima.isFocused()) {
                            Rect outRect = new Rect();
                            karton[i].terima.getGlobalVisibleRect(outRect);
                            if (!outRect.contains((int)event.getRawX(), (int)event.getRawY())) {
                                karton[i].terima.clearFocus();
                                InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
                            }
                        }

                    }
                }
                return false;
            }
        } );


        return view;
    }

    public void tableDisable(){
        for(int i = 0; i<karton.length; i++){
            if (karton[i].produk.getSelectedItemPosition()==0) {
                karton[i].line.setEnabled(false);
                karton[i].saldoAwal.setEnabled(false);
                karton[i].saldoAwal.setFocusable(false);
                karton[i].terima.setEnabled(false);
                karton[i].terima.setFocusable(false);
            } else {
                karton[i].line.setEnabled(true);
                karton[i].saldoAwal.setEnabled(true);
                karton[i].terima.setEnabled(true);
                karton[i].saldoAwal.setFocusable(true);
                karton[i].terima.setFocusable(true);
            }
        }
    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        getActivity().getMenuInflater().inflate(R.menu.navigation, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if(db.getDbCount("id", "savekarton")<=1){
            menu.findItem(R.id.navigation_undo).setEnabled(false);
            menu.findItem(R.id.navigation_undo).setIcon(R.drawable.ic_undo_inactive_24dp);
        }else {
            menu.findItem(R.id.navigation_undo).setEnabled(true);
            menu.findItem(R.id.navigation_undo).setIcon(R.drawable.ic_undo_active_24dp);
        }

    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Toast.makeText(getActivity(), "onActivityCreated Home", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStart() {
        super.onStart();
        Toast.makeText(getActivity(), "Start Home ", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void onResume() {
        super.onResume();
        Toast.makeText(getActivity(), "Resume Home", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onStop(){
        super.onStop();
        Toast.makeText(getActivity(),"Stop",Toast.LENGTH_SHORT).show();
    }



    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {

        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
