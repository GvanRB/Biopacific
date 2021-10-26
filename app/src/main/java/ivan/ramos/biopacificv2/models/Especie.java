package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Especie {

    @SerializedName("idEspecie")
    @Expose
    private String idEspecie;
    @SerializedName("nombre")
    @Expose
    private String nombre;

    /**
     * No args constructor for use in serialization
     *
     */
    public Especie() {
    }

    /**
     *
     * @param idEspecie
     * @param nombre
     */
    public Especie(String idEspecie, String nombre) {
        super();
        this.idEspecie = idEspecie;
        this.nombre = nombre;
    }

    public String toString() {
        return nombre;
    }

    public String getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(String idEspecie) {
        this.idEspecie = idEspecie;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

}