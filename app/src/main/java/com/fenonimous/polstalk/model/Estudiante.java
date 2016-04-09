package com.fenonimous.polstalk.model;

import android.os.Parcel;
import android.os.Parcelable;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sebas and cristian on 3/20/16.
 */
public class Estudiante implements Parcelable {
    private String codigo_estudiante , numero_identificacion , nombres , apellidos, email, nombre_completo;
    private float promedio_general;
    private int factor_p;


    public Estudiante(String codigo_estudiante, String numero_identificacion, String nombres, String apellidos) {
        this.codigo_estudiante = codigo_estudiante;
        this.numero_identificacion = numero_identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.nombre_completo = nombres +" "+ apellidos;
    }
    public Estudiante(String codigo_estudiante, String numero_identificacion, String nombre_completo, float promedio, int factorp, String email){
        this.codigo_estudiante = codigo_estudiante;
        this.numero_identificacion = numero_identificacion;
        this.nombre_completo = nombre_completo;
        this.promedio_general = promedio;
        this.factor_p = factorp;
        this.email = email;
        this.nombres = "";
        this.apellidos = "";
    }
//necesario para poder serializar la clase Estudiante y enviar el arraylist de estudiante a la nueva actividad.
    public Estudiante(Parcel in) {
        codigo_estudiante = in.readString();
        numero_identificacion = in.readString();
        nombres = in.readString();
        apellidos = in.readString();
        promedio_general = in.readFloat();
        factor_p = in.readInt();
        email = in.readString();
        nombre_completo = in.readString();
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
        dest.writeFloat(promedio_general);
        dest.writeInt(factor_p);
        dest.writeString(email);
        dest.writeString(nombre_completo);
    }

    private void readFromParcel(Parcel in) {
        codigo_estudiante = in.readString();
        numero_identificacion = in.readString();
        nombres = in.readString();
        apellidos = in.readString();
        promedio_general = in.readFloat();
        factor_p = in.readInt();
        email = in.readString();
        nombre_completo = in.readString();
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public float getPromedio_general() {
        return promedio_general;
    }

    public void setPromedio_general(float promedio_general) {
        this.promedio_general = promedio_general;
    }

    public int getFactor_p() {
        return factor_p;
    }

    public void setFactor_p(int factor_p) {
        this.factor_p = factor_p;
    }
}
