package com.fenonimous.polstalk;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.zl.reik.dilatingdotsprogressbar.DilatingDotsProgressBar;

import java.util.ArrayList;

import Classes.StalkUsers.Estudiante;
import Classes.WebService;

public class MainActivity extends AppCompatActivity {
    EditText nombres;
    EditText apellidos;
    EditText matricula;
    Spinner dropdown_estudiantes;
    DilatingDotsProgressBar mDilatingDotsProgressBar;
    WebService soap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombres = (EditText)findViewById(R.id.nombres);
        apellidos = (EditText)findViewById(R.id.apellidos);
        dropdown_estudiantes = (Spinner)findViewById(R.id.spinner_estudiantes);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.actionbar_principal);
        setSupportActionBar(myToolbar);
        mDilatingDotsProgressBar = (DilatingDotsProgressBar) findViewById(R.id.progress);
        //soap = new WebService(manejador_hilo);
        //String response = soap.getWebResponse();
        //Log.d("app",response);

    }
    // handler en el main thread.
    final Handler manejador_hilo = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int estado = msg.getData().getInt("estado");
            if(estado == 1){

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mDilatingDotsProgressBar.showNow();
                    }
                }, 100);

            }else if(estado == -1){

                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ArrayList<Estudiante> estudiantes = soap.getEstudiantes_from_consulta();
                        ArrayList<String> estudiantes_nombres = new ArrayList<>();
                        for (Estudiante e: estudiantes) {
                            estudiantes_nombres.add(e.getNombres());
                        }
                        ArrayAdapter<String> student_adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, estudiantes_nombres);
                        dropdown_estudiantes.setAdapter(student_adapter);
                        dropdown_estudiantes.setSelection(0);
                        mDilatingDotsProgressBar.hide();
                    }
                },100);
                // se inicia la actividad del listview.
                Log.d("estado: ","terminado"+estado);
            }else if(estado == -2){
                new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        ArrayAdapter<String> student_adapter = new ArrayAdapter<String>(getApplicationContext(),
                                android.R.layout.simple_list_item_1, new ArrayList<String>());
                        dropdown_estudiantes.setAdapter(student_adapter);
                        mDilatingDotsProgressBar.hide();
                        Toast.makeText(getApplicationContext(),"no se han encontrado estudiantes",Toast.LENGTH_SHORT).show();
                    }
                },100);
            }
        }
    };


    //click en boton submit de consultar.
    public void consultarPersonaPorNombres(View view){
        soap = new WebService(this.manejador_hilo);
        soap.setNombre_a_buscar(nombres.getText().toString());
        soap.setApellido_a_buscar(apellidos.getText().toString());
        soap.setOpcion_escogida("wsConsultarPersonaPorNombres"); // ponemos el nombre del metodo.
        //soap.setOpcion_escogida("HelloWorld");
        // el hilo solo se puede ejecutar una vez.
        soap.start();
        //while(soap.getRun_state()!=1);


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
