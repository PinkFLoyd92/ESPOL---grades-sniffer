package com.fenonimous.polstalk;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

/**
 * Created by CristianRenan on 20/03/2016.
 */
public class CallSoap {
    private static final String NAMESPACE = "http://tempuri.org/";
    private static final String URL = "https://ws.espol.edu.ec/saac/wsandroid.asmx";
    private static final String METHOD_NAME = "wsInfoUsuario";
    private static final String SOAP_ACTION = "http://tempuri.org/wsInfoUsuario";

    public CallSoap(){}

    public String CallUsuario(String usuario){
        String resultado;
        SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
        PropertyInfo argumento = new PropertyInfo();
        argumento.setName("usuario");
        argumento.setValue(usuario);
        argumento.setType(String.class);
        request.addProperty(argumento);

        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.setOutputSoapObject(request);
        HttpTransportSE httpTransport = new HttpTransportSE(URL);
        try {
            httpTransport.call(SOAP_ACTION, envelope);
            Object response_xml = envelope.getResponse();
            resultado = response_xml.toString();
        } catch (Exception e) {
            resultado = e.toString();
        }
        return resultado;
    }
}
