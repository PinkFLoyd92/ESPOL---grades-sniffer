package com.fenonimous.polstalk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenonimous.polstalk.custom.CustomActivity;
import com.fenonimous.polstalk.model.Estudiante;

import java.util.ArrayList;
import java.util.List;

/**
 * The Class ListaEstudiantes is the Activity class. It shows a list of all users of
 * this app. It also shows the Offline/Online status of users.
 */
public class ListaEstudiantes extends CustomActivity
{
	private ListView listView;
    private ArrayList<Estudiante> Estudiantes;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_estudiantes);
		listView = (ListView)findViewById(R.id.listaEstudiante);
        Bundle bundle = getIntent().getExtras();
        Estudiantes = bundle.getParcelableArrayList("estudiantes");
        this.listView.setAdapter(new StudentAdapter(this, R.id.listaEstudiante,Estudiantes));
	}

	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}


	/**
	 * Cargar lista de estudiantes
	 */
	private void loadStudentsList()
	{

	}

	/**
	 * Clase adaptadora que maneja los elementos que se tienen en
	 * el listview de estudiantes.
	 */
	private class StudentAdapter<T> extends ArrayAdapter<T>
	{

		public StudentAdapter(Context context, int resource, List<T> objects) {
			super(context, resource, objects);
		}

        /*
        * Referencias: https://amatellanes.wordpress.com/2013/04/14/ejemplo-de-listview-en-android/
        *
        * Retorna en el View elaborado e inflado de un elemento en una posición especifica.
        */
		@Override
		public View getView(int pos, View v, ViewGroup parent)
		{
            View vista = v;
            if(v == null){
                //crear una nueva vista(view) dentro del listView
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                vista = inflater.inflate(R.layout.item_estudiante, parent, false);
            }
            //agregar la informacion en el listview
            TextView txt_estudiante = (TextView)vista.findViewById(R.id.item_estudiante);
            T item_estudiante = (T)getItem(pos);
            txt_estudiante.setText( ((Estudiante)item_estudiante).getNombres() + " " + ((Estudiante)item_estudiante).getApellidos() );
            return vista;

		}

	}
}
