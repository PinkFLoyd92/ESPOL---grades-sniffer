package com.fenonimous.polstalk;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.fenonimous.polstalk.custom.CustomActivity;

public class Principal extends CustomActivity {
    private ListView lista_opciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.principal);
        setupActionBar(1);
        lista_opciones = (ListView)findViewById(R.id.list_principal);
        lista_opciones.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int itemPosition     = position;
                String  itemValue    = (String) lista_opciones.getItemAtPosition(position);

                switch(itemValue){
                    case "Mostrar Notas":{
                        Intent i = new Intent(getApplicationContext(), BuscarEstudiante.class);
                        startActivity(i);
                     break;
                    }
                    case "Cargar Calendario":{
                        Intent i = new Intent(getApplicationContext(), BuscarEstudiante.class);
                        i.putExtra("CALENDARIO",true);
                        startActivity(i);
                        break;
                    }
                    default:
                        break;
                }
            }
        });
    }
}
