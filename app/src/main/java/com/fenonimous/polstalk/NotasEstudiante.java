package com.fenonimous.polstalk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.fenonimous.polstalk.custom.CustomActivity;
import com.fenonimous.polstalk.model.Estudiante;
import com.fenonimous.polstalk.model.Materia;
import Thread.ThreadSoap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotasEstudiante extends CustomActivity
{
    private EditText nombre_estudiante;
    private TextView text_anio;
    private EditText select_anio;
    private TextView text_termino;
    private Spinner select_termino;
    private Button btnBuscarNotas;
    private  ThreadSoap soap;
    private HashMap mapa_datos;
    private Estudiante estudiante;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notas_estudiante);
        setupActionBar(0);
        inicializar_variables();
        List<String> spinnerArray =  new ArrayList<String>();
        spinnerArray.add("1");
        spinnerArray.add("2");
        spinnerArray.add("3");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        select_termino.setAdapter(adapter);



    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }

    private void inicializar_variables(){
        estudiante = getIntent().getParcelableExtra("estudiante");
        nombre_estudiante = (EditText)findViewById(R.id.nombre_estudiante);
        //text_anio = (TextView)findViewById(R.id.text_anio);
        select_anio = (EditText)findViewById(R.id.select_anio);
        text_termino = (TextView)findViewById(R.id.text_termino);
        select_termino= (Spinner)findViewById(R.id.select_termino);
        btnBuscarNotas = (Button)findViewById(R.id.btnBuscarNotas);
        mapa_datos = new HashMap();

        nombre_estudiante.setText(estudiante.getNombre_completo());
    }

    public void consultarPersona(View view){
        HashMap parametros = new HashMap();
        parametros.put("anio",select_anio.getText().toString());
        parametros.put("termino",select_termino.getSelectedItem().toString());
        parametros.put("estudiante",estudiante.getCodigo_estudiante());
        mapa_datos.put("soap_method","wsConsultaCalificaciones");
        mapa_datos.put("parametros",parametros);
        soap = new ThreadSoap(this.manejador_hilo,mapa_datos); //seteamos los parametros del hilo
        soap.start(); // damos run al hilo.

    }

    // handler en el main thread, el cual se comunica con el hilo que procesa el pedido del webservice.
    final Handler manejador_hilo = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String estado = msg.getData().getString("wsConsultaCalificaciones");
            if(estado == "procesando"){

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //mDilatingDotsProgressBar.showNow();
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

                        ArrayList<Materia> materias = this.msg.getData().getParcelableArrayList("materias");


                        Intent i = new Intent(getApplicationContext(), TablaNotasEstudiante.class);
                        i.putParcelableArrayListExtra("materias",materias);
                        startActivity(i);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_choose_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

}
