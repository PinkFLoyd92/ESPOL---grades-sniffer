package com.fenonimous.polstalk.model;

import android.os.Parcel;
import android.os.Parcelable;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sebas on 3/20/16.
 */
public class Estudiante implements Parcelable {
    private String codigo_estudiante,numero_identificacion,nombres, apellidos;

    public Estudiante(String codigo_estudiante, String numero_identificacion, String nombres, String apellidos) {
        this.codigo_estudiante = codigo_estudiante;
        this.numero_identificacion = numero_identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }
//necesario para poder serializar la clase Estudiante y enviar el arraylist de estudiante a la nueva actividad.
    protected Estudiante(Parcel in) {
        codigo_estudiante = in.readString();
        numero_identificacion = in.readString();
        nombres = in.readString();
        apellidos = in.readString();
    }

    public static final Creator<Estudiante> CREATOR = new Creator<Estudiante>() {
        @Override
        public Estudiante createFromParcel(Parcel in) {
            return new Estudiante(in);
        }

        @Override
        public Estudiante[] newArray(int size) {
            return new Estudiante[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(codigo_estudiante);
        dest.writeString(numero_identificacion);
        dest.writeString(nombres);
        dest.writeString(apellidos);
    }

    public String getCodigo_estudiante() {
        return codigo_estudiante;
    }

    public void setCodigo_estudiante(String codigo_estudiante) {
        this.codigo_estudiante = codigo_estudiante;
    }

    public String getNumero_identificacion() {
        return numero_identificacion;
    }

    public void setNumero_identificacion(String numero_identificacion) {
        this.numero_identificacion = numero_identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }





}
