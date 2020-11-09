package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Agregarmascota {
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("genero")
    @Expose
    private String genero;
    @SerializedName("raza")
    @Expose
    private String raza;
    @SerializedName("especie")
    @Expose
    private String especie;
    @SerializedName("idCliente")
    @Expose
    private String idCliente;

    /**
     * No args constructor for use in serialization
     *
     */
    public Agregarmascota() {
    }

    /**
     *
     * @param especie
     * @param raza
     * @param idCliente
     * @param genero
     * @param nombre
     */
    public Agregarmascota(String nombre, String genero, String raza, String especie, String idCliente) {
        super();
        this.nombre = nombre;
        this.genero = genero;
        this.raza = raza;
        this.especie = especie;
        this.idCliente = idCliente;
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

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }
}
