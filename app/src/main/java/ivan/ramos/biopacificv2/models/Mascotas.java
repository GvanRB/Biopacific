package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Mascotas {

    @SerializedName("idMascota")
    @Expose
    private String idMascota;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("genero")
    @Expose
    private String genero;
    @SerializedName("edad")
    @Expose
    private Integer edad;
    @SerializedName("nombrePropietario")
    @Expose
    private String nombrePropietario;
    @SerializedName("idVeterinaria")
    @Expose
    private String idVeterinaria;
    @SerializedName("idRaza")
    @Expose
    private String idRaza;
    @SerializedName("idEspecie")
    @Expose
    private String idEspecie;

    public String getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(String idEspecie) {
        this.idEspecie = idEspecie;
    }

    /**
     * No args constructor for use in serialization
     *
     */
    public Mascotas() {
    }

    /**
     *
     * @param nombrePropietario
     * @param idRaza
     * @param genero
     * @param idMascota
     * @param nombre
     * @param edad
     * @param idVeterinaria
     */
    public Mascotas(String idMascota, String nombre, String genero, Integer edad, String nombrePropietario, String idVeterinaria, String idRaza) {
        super();
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.nombrePropietario = nombrePropietario;
        this.idVeterinaria = idVeterinaria;
        this.idRaza = idRaza;
    }
    public String toString() {
        return nombre;
    }

    public String getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(String idMascota) {
        this.idMascota = idMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public String getIdVeterinaria() {
        return idVeterinaria;
    }

    public void setIdVeterinaria(String idVeterinaria) {
        this.idVeterinaria = idVeterinaria;
    }

    public String getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(String idRaza) {
        this.idRaza = idRaza;
    }

}