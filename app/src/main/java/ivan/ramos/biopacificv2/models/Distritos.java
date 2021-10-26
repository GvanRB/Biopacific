package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Distritos {

    @SerializedName("idDistrito")
    @Expose
    private String idDistrito;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    /**
     * No args constructor for use in serialization
     *
     */
    public Distritos() {
    }

    /**
     *
     * @param idDistrito
     * @param nombre
     */
    public Distritos(String idDistrito, String nombre) {
        super();
        this.idDistrito = idDistrito;
        this.nombre = nombre;
    }

    public String toString() {
        return nombre;
    }
    public String getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(String idDistrito) {
        this.idDistrito = idDistrito;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}