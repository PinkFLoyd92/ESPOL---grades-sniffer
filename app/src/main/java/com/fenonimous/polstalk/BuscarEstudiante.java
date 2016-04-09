package com.fenonimous.polstalk;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.fenonimous.polstalk.custom.CustomActivity;
import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.ArrayList;
import java.util.HashMap;

import com.fenonimous.polstalk.model.Estudiante;
import Thread.ThreadSoap;

public class BuscarEstudiante extends CustomActivity {

    private EditText nombres;
    private EditText apellidos;
    private EditText matricula;
    private CheckBox select_matricula;
    //Spinner dropdown_estudiantes;
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    private ThreadSoap soap; //objeto el cual recibe como parametro
    /*Hashmap formado de la siguiente estructura:
    soap_method:String, parametros:<parametros enviados>
    Este diccionario es enviado al hilo para ahi decidir la funcion.*/
    private HashMap mapa_datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buscar_estudiante);
        inicializar_variables(); //se apunta a los elementos del xml buscar_estudiante, entre otros.


    }

    public void inicializar_variables(){
        mapa_datos = new HashMap();
        nombres = (EditText)findViewById(R.id.nombre_estudiante);
        apellidos = (EditText)findViewById(R.id.apellido_estudiante);
        matricula = (EditText) findViewById(R.id.matricula_estudiante);
        select_matricula=(CheckBox)findViewById(R.id.select_matricula);
    }


    // handler en el main thread, el cual se comunica con el hilo que procesa el pedido del webservice.
    final Handler manejador_hilo = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            String estado = msg.getData().getString("wsConsultarPersonaPorNombres");
            /*aqui se agrega la animacion.*/
            if(estado == "procesando"){

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(getApplicationContext(),"SE ESTA PROCESANDO",Toast.LENGTH_SHORT).show();
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
                        ArrayList<Estudiante> estudiantes = this.msg.getData().getParcelableArrayList("estudiantes");
                        Intent i = new Intent(getApplicationContext(),ListaEstudiantes.class);
                        i.putParcelableArrayListExtra("estudiantes",estudiantes);
                        startActivity(i);
                        //finish();
                        Toast.makeText(getApplicationContext(),"Se obtuvieron los estudiantes, falta mostrarlos",Toast.LENGTH_LONG);
                    }
                }.init(msg),100);// parametros necesarios

                //si ha ocurrido un error del otro lado, se para la animacion, no se carga el listview y se muestra el mensaje de error.
            }else if(estado =="error"){
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        /*ArrayAdapter<String> student_adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, new ArrayList<String>());
                        dropdown_estudiantes.setAdapter(student_adapter);
                        mDilatingDotsProgressBar.hide();*/
                        Toast.makeText(getApplicationContext(),"no se han encontrado estudiantes",Toast.LENGTH_SHORT).show();
                    }
                },100);
            }
        }
    };


    //click en boton submit de consultar.
    public void consultarPersona(View view){
        boolean checked = select_matricula.isChecked();
        HashMap parametros;
        //inicializamos el hilo enviandole el objeto handler creado,
        //Si estamos consultando por nombre y apellido.
        if(!checked) {
            parametros = new HashMap();
            parametros.put("nombres",nombres.getText().toString().trim() );
            parametros.put("apellidos",apellidos.getText().toString().trim() );

            mapa_datos.put("soap_method","wsConsultarPersonaPorNombres");
            mapa_datos.put("parametros", parametros);
            soap = new ThreadSoap(this.manejador_hilo,mapa_datos);
            soap.start(); // damos run al hilo.
        }else {/*Si se busca por matricula ---> agregar.*/            //Toast.makeText(getApplicationContext(),"AUN NO SE HA IMPLEMENTADO",Toast.LENGTH_LONG);
            parametros = new HashMap();
            parametros.put("matricula", matricula.getText().toString().trim() );
            mapa_datos.put("soap_method","wsInfoEstudiante");
            mapa_datos.put("parametros",parametros);
            soap.setHandler(this.manejador_hilo);
            soap.setMapa_datos(mapa_datos);
            soap.start();
            /*while (resultado == "START") {
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }*/
        }

    }


    //funcion que maneja el click en el checkbox para mostrar o no los widgets a mostrar.
    public void onCheckBoxClick(View view){
        boolean checked = ((CheckBox) view).isChecked();
        //si esta seleccionada, mostramos solo el input de matricula caso contrario mostramos los 2 otros campos .
        if(checked){
            matricula.setVisibility(View.VISIBLE);
            nombres.setVisibility(View.GONE);
            apellidos.setVisibility(View.GONE);
            nombres.setText("");
            apellidos.setText("");
            matricula.setText("");
        }else{
            matricula.setVisibility(View.GONE);
            nombres.setVisibility(View.VISIBLE);
            apellidos.setVisibility(View.VISIBLE);
            nombres.setText("");
            apellidos.setText("");
            matricula.setText("");
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
