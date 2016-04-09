package Thread;

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

import java.util.ArrayList;
import java.util.HashMap;

import com.fenonimous.polstalk.BuscarEstudiante;
import com.fenonimous.polstalk.model.Estudiante;
import com.fenonimous.polstalk.model.Materia;
import com.fenonimous.polstalk.model.SoapService;

/**
 * Created by sebas on 3/20/16.
 */

public class ThreadSoap extends Thread{
    private SoapService soapService;
    private HashMap mapa_datos;
    private Handler handler;
    private static final String NAMESPACE ="http://tempuri.org/";
    private static final String URL = "https://ws.espol.edu.ec/saac/wsandroid.asmx";
    private String webResponse;

    //constructor
    public ThreadSoap(Handler handler, HashMap mapa_datos) {
            this.handler = handler;
            this.mapa_datos = mapa_datos;
            this.soapService = new SoapService();
    }
    public ThreadSoap(){ this.soapService = new SoapService(); }

    public HashMap getMapa_datos() {
        return mapa_datos;
    }

    public void setMapa_datos(HashMap mapa_datos) {
        this.mapa_datos = mapa_datos;
    }

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    public String getWebResponse() {
        return webResponse;
    }

    public void setWebResponse(String webResponse) {
        this.webResponse = webResponse;
    }

    @Override
    public void run() {
        super.run();
        run_option((String)this.mapa_datos.get("soap_method"));

    }

    //funcion la cual recibe el soap_method enviado desde el UI la cual llama al metodo soap apropiado.
    public void run_option(String metodo_escogido){
        switch(metodo_escogido){
            case "HelloWorld":{/*TEST*/
                call_hello_world();
                break;
            }
            case "wsConsultarPersonaPorNombres":{
                call_wsConsultarPersonaPorNombres();
                break;
            }
            case "wsConsultaCalificaciones":{
                call_wsConsultaCalificaciones();
                break;
            }

            case "wsInfoEstudiante":{
                call_wsInfoEstudiante();
                break;
            }
            default:
            break;
        }
    }
    /*
    * @description: Esta funcion se encarga de buscar la informacion del estudiante cuando se ingresa el
    * numero de matricula
    */
    private Estudiante call_wsInfoEstudiante(){
        Estudiante estudiante = null;
        try {
            Message msg = this.handler.obtainMessage();
            Bundle b = new Bundle();
            //se muestra en el hilo principal(UI) algo indicando que se esta procesando la solicitud..
            b.putString("wsInfoEstudiante", "procesando");
            msg.setData(b);
            handler.handleMessage(msg);
            b.clear();
            //se obtiene el estudiante.
            estudiante = this.soapService.call_wsInfoEstudiante((String) ((HashMap) mapa_datos.get("parametros")).get("matricula"));

            b.putParcelable("matricula_estudiante", estudiante);
            b.putString("wsInfoEstudiante", "finalizado");
            msg.setData(b);
            handler.handleMessage(msg);
        }catch (Exception e){
            e.printStackTrace();
            Message msg = this.handler.obtainMessage();
            Bundle b = new Bundle();
            b.putString("wsInfoEstudiante", "error");
            msg.setData(b);
            handler.handleMessage(msg);
        }
        return estudiante;
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
            Log.d("test", response.toString()); // debe salir hello world
        } catch (Exception e) {
            e.printStackTrace();
            Message msg = this.handler.obtainMessage();
            Bundle b = new Bundle();
            b.putString("estado", "error");
            msg.setData(b);
            handler.handleMessage(msg);
        }
    }

    //public ArrayList<String> get_students_by_name(String nombre, String apellido){
    private void call_wsConsultarPersonaPorNombres() {
        ArrayList<Estudiante> estudiantes = null;
        this.webResponse = "";
        try {
            Message msg = this.handler.obtainMessage();
            Bundle b = new Bundle();
            //se muestra en el hilo principal(UI) algo indicando que se esta procesando la solicitud..
            b.putString("wsConsultarPersonaPorNombres", "procesando");
            msg.setData(b);
            handler.handleMessage(msg);
            //se obtienen los estudiantes.
            estudiantes = this.soapService.call_wsConsultarPersonaPorNombres((String) ((HashMap) mapa_datos.get("parametros")).get("nombres"),
                    (String) ((HashMap) mapa_datos.get("parametros")).get("apellidos"));

            b.clear();
            b.putString("wsConsultarPersonaPorNombres","finalizado");
            b.putParcelableArrayList("estudiantes", estudiantes);
            msg.setData(b);
            handler.handleMessage(msg);

            //si se entra en la excepcion enviamos un estado de error al ui y no cargamos la actividad del listview.
        } catch (Exception e) {//RUNTIME EXCEPTION,IOEXCEPTION
            e.printStackTrace();
            Message msg = this.handler.obtainMessage();
            Bundle b = new Bundle();
            b.putString("wsConsultarPersonaPorNombres", "error");
            msg.setData(b);
            handler.handleMessage(msg);

        }
    }
    private void call_wsConsultaCalificaciones(){
        ArrayList<Materia> materias = new ArrayList<>();
        this.webResponse = "";
        try {
            Message msg = this.handler.obtainMessage();
            Bundle b = new Bundle();
            //se muestra en el hilo principal(UI) algo indicando que se esta procesando la solicitud..
            b.putString("wsConsultaCalificaciones", "procesando");
            msg.setData(b);
            handler.handleMessage(msg);
            //se obtienen las materias
            materias = this.soapService.call_wsConsultaCalificaciones((String) ((HashMap) mapa_datos.get("parametros")).get("anio"),
                    (String) ((HashMap) mapa_datos.get("parametros")).get("termino"), (String) ((HashMap) mapa_datos.get("parametros")).get("estudiante"));
            b.clear();
            b.putString("wsConsultaCalificaciones","finalizado");
            b.putParcelableArrayList("materias", materias);
            msg.setData(b);
            handler.handleMessage(msg);

            //si se entra en la excepcion enviamos un estado de error al ui y no cargamos la actividad del listview.
        } catch (Exception e) {//RUNTIME EXCEPTION,IOEXCEPTION
            e.printStackTrace();
            Message msg = this.handler.obtainMessage();
            Bundle b = new Bundle();
            b.putString("wsConsultaCalificaciones", "error");
            msg.setData(b);
            handler.handleMessage(msg);

        }
    }

}
