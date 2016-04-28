package com.fenonimous.polstalk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 4/26/16.
 */
public class Horario_Materia implements Parcelable{
    private String hora_inicio;
    private String hora_fin;
    private String nombre_dia;
    private String aula;
    private String bloque;
    private String idaula;

    public Horario_Materia(String hora_inicio, String hora_fin, String nombre_dia, String aula, String bloque, String idaula) {
        this.hora_inicio = hora_inicio;
        this.hora_fin = hora_fin;
        this.nombre_dia = nombre_dia;
        this.aula = aula;
        this.bloque = bloque;
        this.idaula = idaula;
    }

    public String getHora_inicio() {
        return hora_inicio;
    }

    public void setHora_inicio(String hora_inicio) {
        this.hora_inicio = hora_inicio;
    }

    public String getHora_fin() {
        return hora_fin;
    }

    public void setHora_fin(String hora_fin) {
        this.hora_fin = hora_fin;
    }

    public String getNombre_dia() {
        return nombre_dia;
    }

    public void setNombre_dia(String nombre_dia) {
        this.nombre_dia = nombre_dia;
    }

    public String getAula() {
        return aula;
    }

    public void setAula(String aula) {
        this.aula = aula;
    }

    public String getBloque() {
        return bloque;
    }

    public void setBloque(String bloque) {
        this.bloque = bloque;
    }

    public String getIdaula() {
        return idaula;
    }

    public void setIdaula(String idaula) {
        this.idaula = idaula;
    }

    protected Horario_Materia(Parcel in) {
        hora_inicio = in.readString();
        hora_fin = in.readString();
        nombre_dia = in.readString();
        aula = in.readString();
        bloque = in.readString();
        idaula = in.readString();
    }

    public static final Creator<Horario_Materia> CREATOR = new Creator<Horario_Materia>() {
        @Override
        public Horario_Materia createFromParcel(Parcel in) {
            return new Horario_Materia(in);
        }

        @Override
        public Horario_Materia[] newArray(int size) {
            return new Horario_Materia[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hora_inicio);
        dest.writeString(hora_fin);
        dest.writeString(nombre_dia);
        dest.writeString(aula);
        dest.writeString(bloque);
        dest.writeString(idaula);
    }
}
