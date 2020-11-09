package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Dataclientemenu {

    @SerializedName("idCliente")
    @Expose
    private String idCliente;
    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("correo")
    @Expose
    private String correo;

    /**
     * No args constructor for use in serialization
     *
     */
    public Dataclientemenu() {
    }

    /**
     *
     * @param idCliente
     * @param correo
     * @param nombre
     */
    public Dataclientemenu(String idCliente, String nombre, String correo) {
        super();
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.correo = correo;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

}