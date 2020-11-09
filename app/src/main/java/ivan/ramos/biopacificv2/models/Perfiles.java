package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Perfiles {

    @SerializedName("idPerfil")
    @Expose
    private String idPerfil;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("costo")
    @Expose
    private Integer costo;

    /**
     * No args constructor for use in serialization
     *
     */
    public Perfiles() {
    }

    /**
     *
     * @param idPerfil
     * @param costo
     * @param nombre
     */
    public Perfiles(String idPerfil, String nombre, Integer costo) {
        super();
        this.idPerfil = idPerfil;
        this.nombre = nombre;
        this.costo = costo;
    }

    @Override
    public String toString() {
        return nombre ;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

}