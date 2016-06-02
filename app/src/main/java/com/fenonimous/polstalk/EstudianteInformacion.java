package com.fenonimous.polstalk;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.fenonimous.polstalk.custom.CustomActivity;
import com.fenonimous.polstalk.model.Estudiante;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Esta clase tiene como vista al layout estudiante_informacion.xml, que va a mostrar la informacion de un estudiante
 * como es su matricula, nombre, apellido, promedio general, carrera, etc
 * */
public class EstudianteInformacion extends CustomActivity {
    private ListView listView;
    private ImageView imageView;
    private Button btn_notas;
    private ArrayList<Pair<String,String>> informacion_estudiante = new ArrayList<>();
    private Estudiante estudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.estudiante_informacion);
        setupActionBar(0);

        estudiante = (Estudiante) getIntent().getParcelableExtra("informacion_estudiante");
        this.listView = (ListView)findViewById(R.id.listaInformacionEstudiante);
        btn_notas = (Button)findViewById(R.id.btn_ver_notas);

        informacion_estudiante.add( new Pair<String, String>("Nombres", estudiante.getNombres() +" "+ estudiante.getApellidos()) );
        informacion_estudiante.add( new Pair<String, String>("Email", estudiante.getEmail()) );
        informacion_estudiante.add( new Pair<String, String>("Factor p", estudiante.getFactor_p() + "") );
        informacion_estudiante.add( new Pair<String, String>("Promedio general", estudiante.getPromedio_general() + "") );
        /* HAY QUE AGREGAR MAS CAMPOS COMO SON CARRERA Y FACULTAD*/
        StudentInformationAdapter adaptador = new StudentInformationAdapter(this, R.id.listaInformacionEstudiante, informacion_estudiante);
        this.listView.setAdapter(adaptador);
        adaptador.notifyDataSetChanged();
    }

    //se inicia la actividad para consultar notas de persona.
    public void consultarNotasPersona(View view){
        String matricula = estudiante.getCodigo_estudiante();
        String nombre = estudiante.getNombre_completo();
        Intent i = new Intent(getApplicationContext(), NotasEstudiante.class);
        i.putExtra("estudiante",this.estudiante);
        startActivity(i);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_choose_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private class StudentInformationAdapter<T> extends ArrayAdapter<T>{

        public StudentInformationAdapter(Context context, int resource, List<T> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int pos, View v, ViewGroup parent)
        {
            if(v == null){
                LayoutInflater inflater = (LayoutInflater)getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                v = inflater.inflate(R.layout.estudiante_detalle, parent, false);
            }
            TextView encabezado = (TextView)v.findViewById(R.id.encabezado);
            TextView contenido = (TextView)v.findViewById(R.id.contenido);
            T pair_estudiante_detalle = (T)getItem(pos);//El tipo de dato dentro del arraylist es un HashMap, mirar el metodo onCreate()

            encabezado.setText( ( (Pair)pair_estudiante_detalle ).first.toString() );

            contenido.setText( ( (Pair)pair_estudiante_detalle ).second == null ? "" : ((Pair)pair_estudiante_detalle ).second.toString() );
            return v;

        }
    }
}