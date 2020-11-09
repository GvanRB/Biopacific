package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Usuarios {

    @SerializedName("idUsuario")
    @Expose
    private String idUsuario;
    @SerializedName("nombreUsuario")
    @Expose
    private String nombreUsuario;
    @SerializedName("contraseña")
    @Expose
    private String contraseña;

    public Usuarios(){

    }

    public Usuarios(String idUsuario, String nombreUsuario, String contraseña) {
        super();
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
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
