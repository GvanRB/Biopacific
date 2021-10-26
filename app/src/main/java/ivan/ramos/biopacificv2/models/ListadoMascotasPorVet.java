package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListadoMascotasPorVet {

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
    @SerializedName("raza")
    @Expose
    private String raza;
    @SerializedName("especie")
    @Expose
    private String especie;

    /**
     * No args constructor for use in serialization
     *
     */
    public ListadoMascotasPorVet() {
    }

    /**
     *
     * @param nombrePropietario
     * @param especie
     * @param raza
     * @param genero
     * @param idMascota
     * @param nombre
     * @param edad
     */
    public ListadoMascotasPorVet(String idMascota, String nombre, String genero, Integer edad, String nombrePropietario, String raza, String especie) {
        super();
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.genero = genero;
        this.edad = edad;
        this.nombrePropietario = nombrePropietario;
        this.raza = raza;
        this.especie = especie;
    }

    @Override
    public String toString() {
        return idMascota+" - "+nombre;
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

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public String getEspecie() {
        return especie;
    }

    public void setEspecie(String especie) {
        this.especie = especie;
    }

}