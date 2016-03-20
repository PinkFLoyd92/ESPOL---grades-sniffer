package com.fenonimous.polstalk;

/**
 * Created by CristianRenan on 20/03/2016.
 */
public class Caller extends Thread {
    public CallSoap cs;

    public void run(){
        try{
            cs=new CallSoap();
            String resp=cs.CallUsuario("crpisco");
            MainActivity.resultado = resp;
        }catch(Exception ex)
        {MainActivity.resultado = ex.toString();}
    }
}
