package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class IniciarSesion {

    @SerializedName("estado")
    @Expose
    private Boolean estado;
    @SerializedName("idVeterinaria")
    @Expose
    private String idVeterinaria;

    /**
     * No args constructor for use in serialization
     *
     */
    public IniciarSesion() {
    }

    /**
     *
     * @param estado
     * @param idVeterinaria
     */
    public IniciarSesion(Boolean estado, String idVeterinaria) {
        super();
        this.estado = estado;
        this.idVeterinaria = idVeterinaria;
    }

    public Boolean getEstado() {
        return estado;
    }

    public void setEstado(Boolean estado) {
        this.estado = estado;
    }

    public String getIdVeterinaria() {
        return idVeterinaria;
    }

    public void setIdVeterinaria(String idVeterinaria) {
        this.idVeterinaria = idVeterinaria;
    }

}