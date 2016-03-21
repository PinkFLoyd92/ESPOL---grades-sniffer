package Classes;

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
    ArrayList<Estudiante> estudiantes_from_consulta;
    private String opcion_escogida = "";
    private String nombre_a_buscar = "";
    private String apellido_a_buscar = "";
    public String getApellido_a_buscar() {
        return apellido_a_buscar;
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
    private static final String NAMESPACE = "https://ws.espol.edu.ec/";
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
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            webResponse = response.toString();
            run_state = 1;
            Log.d("app",webResponse);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
