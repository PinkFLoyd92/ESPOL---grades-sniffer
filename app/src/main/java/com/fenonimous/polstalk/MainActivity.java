package com.fenonimous.polstalk;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;

import Classes.WebService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.actionbar_principal);
        setSupportActionBar(myToolbar);
        WebService soap = new WebService();
        soap.setNombre_a_buscar("andres");
        soap.setApellido_a_buscar("caceres");
        soap.setOpcion_escogida("wsConsultarPersonaPorNombres"); // ponemos el nombre del metodo.
        soap.start();
       while(soap.getRun_state()!=1);
        //String response = soap.getWebResponse();
        //Log.d("app",response);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.items, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
