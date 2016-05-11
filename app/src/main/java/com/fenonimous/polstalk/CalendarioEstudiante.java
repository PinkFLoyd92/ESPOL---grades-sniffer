package com.fenonimous.polstalk;

import android.graphics.Color;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.alamkanak.weekview.WeekView;
import com.alamkanak.weekview.WeekViewEvent;
import com.fenonimous.polstalk.custom.CustomActivity;
import com.alamkanak.weekview.MonthLoader;
import com.fenonimous.polstalk.model.Estudiante;
import com.fenonimous.polstalk.model.Horario_Materia;
import com.fenonimous.polstalk.model.Materia;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import Thread.ThreadSoap;
public class CalendarioEstudiante extends CustomActivity {
    private ArrayList<Materia> materias;
    private WeekView mWeekView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendario_estudiante);

        inicializar_variables();
        // Get a reference for the week view in the layout.
        mWeekView = (WeekView) findViewById(R.id.weekView);

        // Set an action when any event is clicked.
        MonthLoader.MonthChangeListener mMonthChangeListener = new MonthLoader.MonthChangeListener() {
            private ArrayList<Materia> materias;

            public MonthLoader.MonthChangeListener setMonthListener(ArrayList<Materia> materias){
                this.materias = materias;
                return this;
            }

            @Override
            public List<WeekViewEvent> onMonthChange(int newYear, int newMonth) {
                int index_color = 0;
                int[] colores = new int[6];
                colores[0] = Color.GREEN;
                colores[1] = Color.GRAY;
                colores[2] = Color.DKGRAY;
                colores[3] = Color.YELLOW;
                colores[4] = Color.CYAN;
                colores[5] = Color.MAGENTA;
                List<WeekViewEvent> events = new ArrayList<WeekViewEvent>();
                int counter = 0;
                for(Materia materia: materias){
                    String datos_materia = materia.getNombre()+
                            "\n Paralelo: " +materia.getParalelo();
                    for (Horario_Materia horario_materia: materia.getHorario()){
                        String info_materia = datos_materia+"\n"+horario_materia.getAula()
                                +"\n"+horario_materia.getBloque();
                        try {
                            String tiempo_inicio = horario_materia.getHora_inicio().substring(2);
                            String tiempo_fin = horario_materia.getHora_fin().substring(2);

                            String[] array_inicio = tiempo_inicio.split("H");
                            String hora_inicio = array_inicio[0];
                            String minuto_inico = "0";
                            if(array_inicio.length > 1){
                                 minuto_inico = array_inicio[1].substring(0,array_inicio[1].length()-1);
                            }

                            String[] array_fin = tiempo_fin.split("H");
                            String hora_fin = array_fin[0];
                            String minuto_fin = "0";
                            if(array_fin.length > 1){
                                minuto_fin = array_fin[1].substring(0,array_fin[1].length()-1);
                            }
                            String dia_clase = horario_materia.getNombre_dia();
                            Calendar startTime = Calendar.getInstance();

                            startTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hora_inicio));
                            startTime.set(Calendar.MINUTE, Integer.valueOf(minuto_inico));
                            startTime.set(Calendar.MONTH, newMonth - 1);
                            startTime.set(Calendar.YEAR, newYear);

                            Calendar endTime = (Calendar) startTime.clone();
                            endTime.set(Calendar.HOUR_OF_DAY, Integer.valueOf(hora_fin));
                            //endTime.add(Calendar.HOUR_OF_DAY, 9);
                            endTime.set(Calendar.MONTH, newMonth -1);
                            endTime.set(Calendar.YEAR, newYear);

                            endTime.set(Calendar.MINUTE, Integer.valueOf(minuto_fin) == 0? 0:Integer.valueOf(minuto_fin)-2);

                            //endTime.set(Calendar.MINUTE, 30);

                            switch(dia_clase){
                                case "LUNES":{
                                    startTime.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
                                    endTime.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
                                    break;
                                }
                                case "MARTES":{
                                    startTime.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
                                    endTime.set(Calendar.DAY_OF_WEEK,Calendar.TUESDAY);
                                    break;
                                }
                                case "MIERCOLES":{
                                    startTime.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
                                    endTime.set(Calendar.DAY_OF_WEEK,Calendar.WEDNESDAY);
                                    break;
                                }
                                case "JUEVES":{
                                    startTime.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
                                    endTime.set(Calendar.DAY_OF_WEEK,Calendar.THURSDAY);
                                    break;
                                }
                                case "VIERNES":{
                                    startTime.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
                                    endTime.set(Calendar.DAY_OF_WEEK,Calendar.FRIDAY);
                                    break;
                                }
                                case "SABADO":{
                                    startTime.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
                                    endTime.set(Calendar.DAY_OF_WEEK,Calendar.SATURDAY);
                                    break;
                                }
                                default:
                                    break;
                            }

                            WeekViewEvent event = new WeekViewEvent(counter++,info_materia, startTime, endTime);
                            event.setColor(colores[index_color]);

                            events.add(event);
                        }catch(Exception e){
                            e.printStackTrace();
                        }



                    }
                    index_color = index_color == 5 ? 0: index_color + 1;

                }
                // Populate the week view with some events.


                return events;
            }
        }.setMonthListener(materias);
        mWeekView.setOnEventClickListener(new WeekView.EventClickListener(){

            @Override
            public void onEventClick(WeekViewEvent event, RectF eventRect) {

            }
        });

// The week view has infinite scrolling horizontally. We have to provide the events of a
// month every time the month changes on the week view.
        mWeekView.setMonthChangeListener(mMonthChangeListener);

// Set long press listener for events.
        mWeekView.setEventLongPressListener(new WeekView.EventLongPressListener(){

            @Override
            public void onEventLongPress(WeekViewEvent event, RectF eventRect) {

            }
        });
    }

    public void inicializar_variables(){
        this.materias = getIntent().getParcelableArrayListExtra("materias");
        Log.d("estudiantes",materias.toString());
    }


}
