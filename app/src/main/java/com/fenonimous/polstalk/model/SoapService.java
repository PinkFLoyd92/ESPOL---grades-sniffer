package com.fenonimous.polstalk.model;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by sebas on 4/2/16.
 */
public class SoapService {
    private static final String NAMESPACE ="http://tempuri.org/";
    private static final String URL = "https://ws.espol.edu.ec/saac/wsandroid.asmx";

    /*
    * @description: Esta funcion es llamada desde la clase ThreadSoap, y se encarga de obtener la informacion del estudiante
    *  mediante su matricula
    */
    public Estudiante call_wsInfoEstudiante(String matricula){
        Estudiante estudiante = null;
        SoapObject request = new SoapObject(NAMESPACE,"wsInfoEstudiante");
        PropertyInfo matricula_send = new PropertyInfo();
        matricula_send.setName("codigoEstudiante");
        matricula_send.setValue(matricula);
        matricula_send.setType(String.class);
        request.addProperty(matricula_send);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
        androidHttpTransport.debug = true;
        try {
            androidHttpTransport.call("http://tempuri.org/wsInfoEstudiante",envelope);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }

        SoapObject response = null;
        try {
            response = (SoapObject) envelope.getResponse();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }
        if(response != null){
            SoapObject root = (SoapObject) response.getProperty(1);
            SoapObject datos_root2 = (SoapObject) root.getProperty("NewDataSet");
            SoapObject datos_persona;
            for (int i= 0; i<datos_root2.getPropertyCount();i++){
                datos_persona = (SoapObject) datos_root2.getProperty(i);
                String codigo_estudiante = datos_persona.getProperty("COD_ESTUDIANTE").toString();
                String nombre_completo = datos_persona.getProperty("NOMBRECOMPLETO").toString();
                String email = datos_persona.getProperty("EMAIL").toString();
                int factor_p = Integer.parseInt(datos_persona.getProperty("FACTORP").toString());
                float promedio_general = Float.parseFloat(datos_persona.getProperty("PROMEDIOGENERAL").toString());
                String identificacion = datos_persona.getProperty("IDENTIFICACION").toString();
                try {
                    estudiante = new Estudiante(codigo_estudiante, identificacion, nombre_completo, promedio_general, factor_p, email);
                }catch(RuntimeException e){
                    Log.d("fail", "fail");
                }
            }
        }else{
            System.out.println("El response es null OJO, clase SoapService-> funcion call_wsInfoEstudiante");
        }
        return estudiante;
    }

   /*
    * @description: Esta funcion es llamada desde la clase ThreadSoap, y se encarga de obtener la lista de estudiantes que
    * coincide con los nombres y apellidos
    */
    public ArrayList<Estudiante> call_wsConsultarPersonaPorNombres(String nombre, String apellido) {
            ArrayList<Estudiante> estudiantes = new ArrayList<>();
            String webResponse= "";
            SoapObject request = new SoapObject(NAMESPACE, "wsConsultarPersonaPorNombres");
            PropertyInfo nombre_send =new PropertyInfo();
            nombre_send.setName("nombre");
            nombre_send.setValue(nombre);
            nombre_send.setType(String.class);
            request.addProperty(nombre_send);

            PropertyInfo apellido_send =new PropertyInfo();
            apellido_send.setName("apellido");
            apellido_send.setValue(apellido);
            apellido_send.setType(String.class);
            request.addProperty(apellido_send);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.debug = true;
        try {
            androidHttpTransport.call("http://tempuri.org/wsConsultarPersonaPorNombres", envelope);
        } catch (IOException e1) {
            e1.printStackTrace();
        } catch (XmlPullParserException e1) {
            e1.printStackTrace();
        }

        SoapObject response = null;

        try {
            response = (SoapObject) envelope.getResponse();
        } catch (SoapFault soapFault) {
            soapFault.printStackTrace();
        }

            SoapObject root = (SoapObject) response.getProperty(1);
            SoapObject datos_root2 = (SoapObject) root.getProperty("NewDataSet");
            SoapObject datos_persona = new SoapObject();
            for (int i= 0; i<datos_root2.getPropertyCount();i++){
                datos_persona = (SoapObject) datos_root2.getProperty(i);
                String nombres = datos_persona.getProperty("NOMBRES").toString();
                String apellidos = datos_persona.getProperty("APELLIDOS").toString();
                try {
                    String cod_estudiante = datos_persona.getProperty("CODESTUDIANTE").toString();
                    String numero_identificacion = datos_persona.getProperty("NUMEROIDENTIFICACION").toString();
                    Estudiante temp = new Estudiante(cod_estudiante,numero_identificacion,nombres,apellidos);
                    estudiantes.add(temp);
                }catch(RuntimeException e){
                    Log.d("fail", "fail");
                }
            }
           return estudiantes;
    }

    public String call_hello_world() {
        try {
            SoapObject request = new SoapObject(NAMESPACE,"HelloWorld");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);
            androidHttpTransport.call("http://tempuri.org/HelloWorld", envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            return response.toString();
            //Log.d("test", response.toString()); // debe salir hello world
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


}
