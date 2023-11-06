package com.example.palindromoces3.Modem;

import com.example.palindromoces3.Servlets.MyServlet;

public class Student {
    public String nombre;
    public int id;
    private String cedula;

    public Student(int id, String cedula, String nombre){
        this.id = id;
        this.cedula = cedula;
        this.nombre = nombre;
    }

    public Student(String cedula){
        this.cedula = cedula;
    }

    public Student() {

    }


    //-----------------------------------------------------------


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }


    @Override
    public String toString() {
        return "el estudiante se llama: "+ this.nombre+ " su documento es: "+ this.cedula;

    }
}
