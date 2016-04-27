package com.fenonimous.polstalk.model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by sebas on 4/3/16.
 */
public class Materia implements Parcelable{
    private Nota nota = new Nota(); // nota representa la nota general que tiene el estudiante en la materia.
    private int vez_tomada;
    private String estado="";
    private String nombre ="";
   private String tipoCurso=" ";
    private String codigo="";
    private ArrayList<Horario_Materia> horario = new ArrayList<>();

    public String getTipoCurso() {
        return tipoCurso;
    }

    public void setTipoCurso(String tipoCurso) {
        this.tipoCurso = tipoCurso;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Materia(String nombre, String codigo, ArrayList<Horario_Materia> horario){
        this.nombre = nombre;
        this.codigo = codigo;
        this.horario= horario;
    }
    public  Materia(String nombre, String estado, int vez_tomada, int nota1, int nota2, int nota3){
        this.nota=new Nota(nota1,nota2,nota3);
        this.nombre = nombre;
        this.estado = estado;
        this.vez_tomada = vez_tomada;

    }

    protected Materia(Parcel in) {
        nota= in.readParcelable(Nota.class.getClassLoader());
        vez_tomada = in.readInt();
        estado = in.readString();
        nombre = in.readString();
        tipoCurso = in.readString();
        codigo = in.readString();
        horario = new ArrayList<>();
        in.readTypedList(horario,Horario_Materia.CREATOR);
       /* final List lista_horario_temp = Arrays.asList(in.readParcelableArray(Horario_Materia.class.getClassLoader()));
      try{  horario = new ArrayList<Horario_Materia>(lista_horario_temp.size());
        horario.addAll(lista_horario_temp);
      }catch(NullPointerException e){
          e.printStackTrace();
          Log.d("ESTADO PARCE MATERIA","NO SE PUDO GENERAR EL HORARIO");
      }*/
        /*private Date hora_inicio;
        private Date hora_fin;
        private String tipoCurso;
        private String codigo;*/
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
        dest.writeParcelable(nota, flags);
        dest.writeInt(vez_tomada);
        dest.writeString(estado);
        dest.writeString(nombre);
        dest.writeString(tipoCurso);
        dest.writeString(codigo);
        //horario = new ArrayList<>();
        //dest.writeParcelableArray((Parcelable[])horario.toArray(),flags);
     //   dest.writeArray((Horario_Materia[])horario.toArray());
        dest.writeTypedList(horario);
    }
}
