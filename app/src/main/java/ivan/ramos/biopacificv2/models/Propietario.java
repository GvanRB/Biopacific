package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Propietario {

    @SerializedName("idPersona")
    @Expose
    private String idPersona;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    /**
     * No args constructor for use in serialization
     *
     */
    public Propietario() {
    }

    /**
     *
     * @param nombre
     * @param idPersona
     */
    public Propietario(String idPersona, String nombre) {
        super();
        this.idPersona = idPersona;
        this.nombre = nombre;
    }
    public String toString() {
        return nombre;
    }

    public String getIdPersona() {
        return idPersona;
    }

    public void setIdPersona(String idPersona) {
        this.idPersona = idPersona;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}