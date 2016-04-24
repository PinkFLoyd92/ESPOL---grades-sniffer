package com.fenonimous.polstalk;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.GridLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import com.fenonimous.polstalk.custom.CustomActivity;
import com.fenonimous.polstalk.model.Materia;
import java.util.ArrayList;

/**
 * Created by sebas on 4/9/16.
 */
public class TablaNotasEstudiante extends CustomActivity{
    private TableLayout tabla_notas_estudiante;
    private ArrayList<Materia> materias;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tabla_notas_estudiante);
        setupActionBar(0);
        inicializar_variables();
        llenar_tabla();

    }

    /* (non-Javadoc)
     * @see android.support.v4.app.FragmentActivity#onDestroy()
     */
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
    }
    private void inicializar_variables() {
        tabla_notas_estudiante = (TableLayout)findViewById(R.id.tabla_notas_estudiante);
        materias = getIntent().getParcelableArrayListExtra("materias");
        //si no hay maaterias se presenta un mensaje y se cierra la actividad.
        if(materias == null){
            finish();
        }

    }

    private void llenar_tabla(){
        for (Materia materia:materias){
            TableRow row = new TableRow(this);
            // agregamos listviews a la tabla.
            TextView[] textViews = new TextView[4];

                textViews[0] = new TextView(this);
            textViews[0].setText(materia.getNombre());
                row.addView(textViews[0]);


            textViews[1] = new TextView(getApplicationContext());
            int nota = materia.getNota().getPrimer_termino();
            textViews[1].setText(materia.getNota().getPrimer_termino()+"");
            row.addView(textViews[1]);

            textViews[2] = new TextView(getApplicationContext());
            textViews[2].setText(materia.getNota().getSegundo_termino()+"");
            row.addView(textViews[2]);

            textViews[3] = new TextView(getApplicationContext());
            textViews[3].setText(materia.getNota().getTercer_termino()+"");
            row.addView(textViews[3]);
            //row.setBackground(getResources().getDrawable(R.drawable.bordex));
            row.setPadding(5,5,5,5);
            // add the TableRow to the TableLayout
            tabla_notas_estudiante.addView(row, new TableLayout.LayoutParams(GridLayout.LayoutParams.WRAP_CONTENT, GridLayout.LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_choose_user, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
