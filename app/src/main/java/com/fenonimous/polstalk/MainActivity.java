package com.fenonimous.polstalk;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import android.support.v7.widget.Toolbar;


public class MainActivity extends AppCompatActivity {

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public static String resultado="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
        Toolbar myToolbar = (Toolbar) findViewById(R.id.actionbar_principal);
        setSupportActionBar(myToolbar);
        Button boton = (Button)findViewById(R.id.llamarBtn);
        boton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultado = "START";
                Caller c = new Caller();
                c.start();
                while (resultado == "START") {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(resultado);
            }
        });
    }

}
