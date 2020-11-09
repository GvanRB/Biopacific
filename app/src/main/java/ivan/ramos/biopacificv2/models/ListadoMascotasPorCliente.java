package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ListadoMascotasPorCliente {

    @SerializedName("idMascota")
    @Expose
    private String idMascota;
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

    /**
     * No args constructor for use in serialization
     *
     */
    public ListadoMascotasPorCliente() {
    }

    /**
     *
     * @param especie
     * @param raza
     * @param genero
     * @param idMascota
     * @param nombre
     */
    public ListadoMascotasPorCliente(String idMascota, String nombre, String genero, String raza, String especie) {
        super();
        this.idMascota = idMascota;
        this.nombre = nombre;
        this.genero = genero;
        this.raza = raza;
        this.especie = especie;
    }

    @Override
    public String toString() {
        return  idMascota +" - "+ nombre;
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
