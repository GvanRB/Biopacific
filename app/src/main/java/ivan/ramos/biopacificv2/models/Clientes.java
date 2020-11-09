package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Clientes {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("direccion")
    @Expose
    private String direccion;
    @SerializedName("telefono")
    @Expose
    private String telefono;
    @SerializedName("nombreContacto")
    @Expose
    private String nombreContacto;
    @SerializedName("distrito")
    @Expose
    private String distrito;
    @SerializedName("nombreUsuario")
    @Expose
    private String nombreUsuario;
    @SerializedName("contraseña")
    @Expose
    private String contraseña;

    /**
     * No args constructor for use in serialization
     *
     */
    public Clientes() {
    }

    /**
     *
     * @param nombreContacto
     * @param distrito
     * @param contraseña
     * @param direccion
     * @param nombreUsuario
     * @param telefono
     * @param nombre
     */
    public Clientes(String nombre, String direccion, String telefono, String nombreContacto,
                    String distrito, String nombreUsuario, String contraseña) {
        super();
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.nombreContacto = nombreContacto;
        this.distrito = distrito;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getNombreContacto() {
        return nombreContacto;
    }

    public void setNombreContacto(String nombreContacto) {
        this.nombreContacto = nombreContacto;
    }

    public String getDistrito() {
        return distrito;
    }

    public void setDistrito(String distrito) {
        this.distrito = distrito;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

}
