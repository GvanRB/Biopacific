package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Solicitarservicio {

    @SerializedName("idPerfil")
    @Expose
    private String idPerfil;
    @SerializedName("idCliente")
    @Expose
    private String idCliente;
    @SerializedName("idMascota")
    @Expose
    private String idMascota;
    @SerializedName("costoServicio")
    @Expose
    private Integer costoServicio;
    @SerializedName("fechaRegistro")
    @Expose
    private String fechaRegistro;

    /**
     * No args constructor for use in serialization
     *
     */
    public Solicitarservicio() {
    }

    /**
     *
     * @param idCliente
     * @param idPerfil
     * @param fechaRegistro
     * @param costoServicio
     * @param idMascota
     */
    public Solicitarservicio(String idPerfil, String idCliente, String idMascota, Integer costoServicio, String fechaRegistro) {
        super();
        this.idPerfil = idPerfil;
        this.idCliente = idCliente;
        this.idMascota = idMascota;
        this.costoServicio = costoServicio;
        this.fechaRegistro = fechaRegistro;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(String idCliente) {
        this.idCliente = idCliente;
    }

    public String getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(String idMascota) {
        this.idMascota = idMascota;
    }

    public Integer getCostoServicio() {
        return costoServicio;
    }

    public void setCostoServicio(Integer costoServicio) {
        this.costoServicio = costoServicio;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

}