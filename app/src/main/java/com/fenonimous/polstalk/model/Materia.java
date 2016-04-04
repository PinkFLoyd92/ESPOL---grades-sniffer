package com.fenonimous.polstalk.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by sebas on 4/3/16.
 */
public class Materia implements Parcelable{
    private Nota nota; // nota representa la nota general que tiene el estudiante en la materia.
    private int vez_tomada;
    private String estado;
    private String nombre;


    public  Materia(String nombre, String estado,int vez_tomada,int nota1, int nota2, int nota3){
        this.nota=new Nota(nota1,nota2,nota3);
        this.nombre = nombre;
        this.estado = estado;
        this.vez_tomada = vez_tomada;

    }
    protected Materia(Parcel in) {
        vez_tomada = in.readInt();
        estado = in.readString();
        nota= in.readParcelable(Nota.class.getClassLoader());
    }
    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public static final Creator<Materia> CREATOR = new Creator<Materia>() {
        @Override
        public Materia createFromParcel(Parcel in) {
            return new Materia(in);
        }

        @Override
        public Materia[] newArray(int size) {
            return new Materia[size];
        }
    };

    public Nota getNota() {
        return nota;
    }

    public void setNota(Nota nota) {
        this.nota = nota;
    }

    public int getVez_tomada() {
        return vez_tomada;
    }

    public void setVez_tomada(int vez_tomada) {
        this.vez_tomada = vez_tomada;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(vez_tomada);
        dest.writeString(estado);
        dest.writeParcelable(nota,flags);
    }
}
