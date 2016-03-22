package Classes;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import Classes.StalkUsers.Estudiante;


/**
 * Created by sebas on 3/20/16.
 */

public class WebService extends Thread{
    public WebService(Handler handler) {
            this.handler = handler;
    }
    private Handler handler;
    private ArrayList<Estudiante> estudiantes_from_consulta;
    private String opcion_escogida = "";
    private String nombre_a_buscar = "";
    private String apellido_a_buscar = "";
    public String getApellido_a_buscar() {
        return apellido_a_buscar;
    }

    public ArrayList<Estudiante> getEstudiantes_from_consulta() {
        return estudiantes_from_consulta;
    }

    public void setEstudiantes_from_consulta(ArrayList<Estudiante> estudiantes_from_consulta) {
        this.estudiantes_from_consulta = estudiantes_from_consulta;
    }

    public void setApellido_a_buscar(String apellido_a_buscar) {
        this.apellido_a_buscar = apellido_a_buscar;
    }

    public String getNombre_a_buscar() {
        return nombre_a_buscar;
    }

    public void setNombre_a_buscar(String nombre_a_buscar) {
        this.nombre_a_buscar = nombre_a_buscar;
    }

    public String getOpcion_escogida() {
        return opcion_escogida;
    }

    public void setOpcion_escogida(String opcion_escogida) {
        this.opcion_escogida = opcion_escogida;
    }

    private int run_state = -1;
    private static final String NAMESPACE ="http://tempuri.org/";
    private static final String URL = "https://ws.espol.edu.ec/saac/wsandroid.asmx";
    private String webResponse;

    public String getWebResponse() {
        return webResponse;
    }

    public void setWebResponse(String webResponse) {
        this.webResponse = webResponse;
    }

    @Override
    public void run() {
        super.run();
        run_option();

    }
    //llamarla desde otra clase.
public void clear_variables(){
    this.opcion_escogida = "";
    this.nombre_a_buscar = "";
    this.apellido_a_buscar = "";
    for(Estudiante e: this.estudiantes_from_consulta){
        estudiantes_from_consulta.remove(e);
    }
    this.run_state = -1;
}
    public int getRun_state() {
        return run_state;
    }

    public void setRun_state(int run_state) {
        this.run_state = run_state;
    }
    public void run_option(){
        switch(this.opcion_escogida){
            case "HelloWorld":{
                call_hello_world();
                this.run_state = -1; // se resetea el estado para llamar a mas metodos
                break;
            }
            case "wsConsultarPersonaPorNombres":{
                call_wsConsultarPersonaPorNombres();
                this.run_state= -1;
            }
            default:
                break;
        }
    }
    public void test(){

    }
    private void call_hello_world() {
        try {
            SoapObject request = new SoapObject(NAMESPACE,"HelloWorld");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            androidHttpTransport.call("http://tempuri.org/HelloWorld", envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            webResponse = response.toString();
            run_state = 1;
            Log.d("test", webResponse); // debe salir hello world
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //public ArrayList<String> get_students_by_name(String nombre, String apellido){
    public void call_wsConsultarPersonaPorNombres() {
        this.estudiantes_from_consulta = new ArrayList<>();
        this.webResponse = "";
        try {
            Message msg = this.handler.obtainMessage();
            Bundle b = new Bundle();
            //se muestra en el hilo principal la barra de avance del proceso de capturar estudiantes.
            b.putInt("estado", 1);
            msg.setData(b);
            handler.handleMessage(msg);

            SoapObject request = new SoapObject(NAMESPACE, "wsConsultarPersonaPorNombres");
            PropertyInfo nombre =new PropertyInfo();
            nombre.setName("nombre");
            nombre.setValue(this.getNombre_a_buscar());
            nombre.setType(String.class);
            request.addProperty(nombre);

            PropertyInfo apellido =new PropertyInfo();
            apellido.setName("apellido");
            apellido.setValue(this.getApellido_a_buscar());
            apellido.setType(String.class);
            request.addProperty(apellido);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
            androidHttpTransport.call("http://tempuri.org/wsConsultarPersonaPorNombres", envelope);
            SoapObject response = (SoapObject) envelope.getResponse();
            webResponse = response.toString();

            SoapObject root = (SoapObject) response.getProperty(1);
            SoapObject datos_root2 = (SoapObject) root.getProperty("NewDataSet");
            SoapObject datos_persona = new SoapObject();
            for (int i= 0; i<datos_root2.getPropertyCount();i++){
                datos_persona = (SoapObject) datos_root2.getProperty(i);
                String nombres = datos_persona.getProperty("NOMBRES").toString();
                String apellidos = datos_persona.getProperty("APELLIDOS").toString();
                String cod_estudiante = datos_persona.getProperty("CODESTUDIANTE").toString();
                String numero_identificacion = datos_persona.getProperty("NUMEROIDENTIFICACION").toString();
                Estudiante temp = new Estudiante(cod_estudiante,numero_identificacion,nombres,apellidos);
                this.estudiantes_from_consulta.add(temp);
            }
            //StringBuilder stringBuilder = new StringBuilder();
            Log.d("app",datos_root2.getPropertyCount()+"");
            Log.d("app", datos_persona.toString());
            Log.d("app",datos_persona.getPropertyCount()+"");
            run_state = 1;

            b.clear();
            b.putInt("estado",-1);
            msg.setData(b);
            handler.handleMessage(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
