package com.fenonimous.polstalk;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.fenonimous.polstalk.model.Materia;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import Thread.ThreadSoap;
/**
 * The Class ListaEstudiantes is the Activity class. It shows a list of all users of
 * this app. It also shows the Offline/Online status of users.
 */
public class ListaEstudiantes extends CustomActivity implements AdapterView.OnItemClickListener
{
	private ListView listView;
    private ArrayList<Estudiante> Estudiantes;
	private View mContentView;
	private View mLoadingView;
	private HashMap mapa_datos;
	private Estudiante estudiante_selected;
	private ThreadSoap soap; //objeto el cual recibe como parametro
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_estudiantes);
		inicializar_variables();
		setupActionBar(0);
        this.listView.setAdapter(new StudentAdapter(this, R.id.listaEstudiante, Estudiantes));
        this.listView.setOnItemClickListener(this);
	}

	public void inicializar_variables(){
		Estudiantes = getIntent().getParcelableArrayListExtra("estudiantes");
		listView = (ListView)findViewById(R.id.listaEstudiante);
		/*mContentView = findViewById(R.id.contenido);
		mLoadingView = findViewById(R.id.loading_spinner);*/
		mapa_datos = new HashMap();
	}
	/* (non-Javadoc)
	 * @see android.support.v4.app.FragmentActivity#onDestroy()
	 */
	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
    /*@Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        estudiante_selected = Estudiantes.get(position);
		HashMap parametros = new HashMap();
		parametros.put("matricula",estudiante_selected.getCodigo_estudiante());
		mapa_datos.put("soap_method","wsInfoEstudiante");
		mapa_datos.put("parametros",parametros);
		soap = new ThreadSoap(this.manejador_hilo,mapa_datos); //seteamos los parametros del hilo
		soap.start(); // damos run al hilo.

    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu_choose_user, menu);
		return super.onCreateOptionsMenu(menu);
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

	final Handler manejador_hilo = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			final String estado = msg.getData().getString("wsInfoEstudiante");
			if(estado == "procesando"){

				new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
					@Override
					public void run() {
						//Se está procesando la solicitud.
						Toast.makeText(getApplicationContext(),"Se está procesando la solicitud",Toast.LENGTH_LONG).show();
					}
				}, 100);
				//si se finaliza el proceso y  ha sido procesada correctamente la solicitud:
			}else if(estado == "finalizado"){

				new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
					Message msg;
					public Runnable init(Message msg){
						this.msg = msg;
						return this;
					}
					@Override
					//iniciamos la nueva actividad en esta parte.
					public void run() {

						Estudiante estudiante= this.msg.getData().getParcelable("matricula_estudiante");
						estudiante_selected.setEmail(estudiante.getEmail());
						estudiante_selected.setFactor_p(estudiante.getFactor_p());
						estudiante_selected.setPromedio_general(estudiante.getPromedio_general());
						estudiante_selected.setNombre_completo(estudiante.getNombre_completo());
						Intent estudiante_informacion_intent = new Intent(getApplicationContext(), EstudianteInformacion.class);
						estudiante_informacion_intent.putExtra("informacion_estudiante", estudiante_selected);
						startActivity(estudiante_informacion_intent);
					}
				}.init(msg),100);// parametros necesarios

				//si ha ocurrido un error del otro lado, se para la animacion, no se carga el listview y se muestra el mensaje de error.
			}else if(estado =="error"){
				new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
					@Override
					public void run() {
						Toast.makeText(getApplicationContext(), "no se han encontrado materias en el termino", Toast.LENGTH_SHORT).show();
					}
				},100);
			}
		}
	};
}
