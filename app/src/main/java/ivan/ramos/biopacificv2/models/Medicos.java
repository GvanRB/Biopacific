package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medicos {

    private String idMedico;
    private String nombre;
    private String apellido;
    private String edad;
    private String dni;
    private String sexo;
    private String idVeterinaria;



    public Medicos(){}

    public Medicos(String idMedico, String nombre, String apellido, String edad, String dni, String sexo, String idVeterinaria) {
        this.idMedico = idMedico;
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.dni = dni;
        this.sexo = sexo;
        this.idVeterinaria = idVeterinaria;
    }

    public String toString() {
        return nombre+" "+apellido;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public String getSexo() {
        return sexo;
    }

    public void setSexo(String sexo) {
        this.sexo = sexo;
    }

    public String getIdVeterinaria() {
        return idVeterinaria;
    }

    public void setIdVeterinaria(String idVeterinaria) {
        this.idVeterinaria = idVeterinaria;
    }
}