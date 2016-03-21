package com.fenonimous.polstalk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.ArrayList;

import Classes.StalkUsers.Estudiante;
import Classes.WebService;

public class MainActivity extends AppCompatActivity {
    EditText nombres;
    EditText apellidos;
    EditText matricula;
    Spinner dropdown_estudiantes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        nombres = (EditText)findViewById(R.id.nombres);
        apellidos = (EditText)findViewById(R.id.apellidos);
        dropdown_estudiantes = (Spinner)findViewById(R.id.spinner_estudiantes);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.actionbar_principal);
        setSupportActionBar(myToolbar);


        //String response = soap.getWebResponse();
        //Log.d("app",response);

    }
    //click en boton submit de consultar.
    public void consultarPersonaPorNombres(View view){
        WebService soap = new WebService();
        soap.setNombre_a_buscar(nombres.getText().toString());
        soap.setApellido_a_buscar(apellidos.getText().toString());
        soap.setOpcion_escogida("wsConsultarPersonaPorNombres"); // ponemos el nombre del metodo.
        //soap.setOpcion_escogida("HelloWorld");
        soap.start();
        while(soap.getRun_state()!=1);
        ArrayList<Estudiante> estudiantes = soap.getEstudiantes_from_consulta();
        ArrayList<String> estudiantes_nombres = new ArrayList<>();
        for (Estudiante e: estudiantes) {
            estudiantes_nombres.add(e.getNombres());
        }
        ArrayAdapter<String> student_adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, estudiantes_nombres);
                dropdown_estudiantes.setAdapter(student_adapter);
                dropdown_estudiantes.setSelection(0);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
