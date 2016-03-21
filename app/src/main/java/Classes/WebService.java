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
import java.util.List;


/**
 * Created by sebas on 3/20/16.
 */

public class WebService extends Thread{
    private int run_state = -1;
    private static final String NAMESPACE = "https://ws.espol.edu.ec/";
    private static final String URL = "https://ws.espol.edu.ec/saac/wsandroid.asmx";
    private static final String SOAP_ACTION = "http://tempuri.org/HelloWorld";
    private static final String METHOD_NAME = "HelloWorld";
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
        fill_students_by_name("xd","");

    }

    public int getRun_state() {
        return run_state;
    }

    public void setRun_state(int run_state) {
        this.run_state = run_state;
    }

    private final SoapSerializationEnvelope getSoapSerializationEnvelope(SoapObject request) {
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
        envelope.dotNet = true;
        envelope.implicitTypes = true;
        envelope.setAddAdornments(false);
        envelope.setOutputSoapObject(request);

        return envelope;
    }
    //public ArrayList<String> get_students_by_name(String nombre, String apellido){
    public void fill_students_by_name(String nombre, String apellido) {
        this.webResponse = "";
        try {
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);
            HttpTransportSE androidHttpTransport = new HttpTransportSE(URL);

            androidHttpTransport.call(SOAP_ACTION, envelope);
            SoapPrimitive response = (SoapPrimitive) envelope.getResponse();
            webResponse = response.toString();
            run_state = 1;
            Log.d("app",response.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
