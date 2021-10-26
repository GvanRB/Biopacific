package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Raza {

    @SerializedName("idRaza")
    @Expose
    private String idRaza;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("idEspecie")
    @Expose
    private String idEspecie;

    /**
     * No args constructor for use in serialization
     *
     */
    public Raza() {
    }

    /**
     *
     * @param idRaza
     * @param nombre
     * @param idEspecie
     */
    public Raza(String idRaza, String nombre, String idEspecie) {
        super();
        this.idRaza = idRaza;
        this.nombre = nombre;
        this.idEspecie = idEspecie;
    }
    public String toString() {
        return nombre;
    }

    public String getIdRaza() {
        return idRaza;
    }

    public void setIdRaza(String idRaza) {
        this.idRaza = idRaza;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdEspecie() {
        return idEspecie;
    }

    public void setIdEspecie(String idEspecie) {
        this.idEspecie = idEspecie;
    }

}

