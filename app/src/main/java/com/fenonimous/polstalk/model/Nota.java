package com.fenonimous.polstalk.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.fenonimous.polstalk.utils.IntegerComparator;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.Stack;

/**
 * Created by sebas on 4/3/16.
 */
public class Nota implements Parcelable{
    private int primer_termino, segundo_termino, tercer_termino;
    public Nota(){
        inicializar_notas();
    }

    public Nota(int primer_termino, int segundo_termino, int tercer_termino){
        this.primer_termino = primer_termino;
        this.segundo_termino = segundo_termino;
        this.tercer_termino = tercer_termino;
    }

    protected Nota(Parcel in) {
        primer_termino = in.readInt();
        segundo_termino = in.readInt();
        tercer_termino = in.readInt();
    }

    public static final Creator<Nota> CREATOR = new Creator<Nota>() {
        @Override
        public Nota createFromParcel(Parcel in) {
            return new Nota(in);
        }

        @Override
        public Nota[] newArray(int size) {
            return new Nota[size];
        }
    };

    private void inicializar_notas() {
        this.primer_termino = 0;
        this.segundo_termino = 0;
        this.tercer_termino = 0;
    }

    public int getPrimer_termino() {
        return primer_termino;
    }

    public void setPrimer_termino(int primer_termino) {
        this.primer_termino = primer_termino;
    }

    public int getSegundo_termino() {
        return segundo_termino;
    }

    public void setSegundo_termino(int segundo_termino) {
        this.segundo_termino = segundo_termino;
    }

    public int getTercer_termino() {
        return tercer_termino;
    }

    public void setTercer_termino(int tercer_termino) {
        this.tercer_termino = tercer_termino;
    }

    // se obtiene el promedio de las 2 mejores notas.
    private int get_promedio(){
        int total = 0;
        PriorityQueue<Integer> cola_notas;
        Comparator<Integer> comparator = new IntegerComparator();
        cola_notas = new PriorityQueue<>(3, comparator); // capacidad inicial, y comparador usado.
        cola_notas.add(this.getPrimer_termino());
        cola_notas.add(this.getSegundo_termino());
        cola_notas.add(this.getTercer_termino());

        while (cola_notas.size() !=1){
            total+=cola_notas.remove();
        }
        return total/2;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(primer_termino);
        dest.writeInt(segundo_termino);
        dest.writeInt(tercer_termino);
    }
}
