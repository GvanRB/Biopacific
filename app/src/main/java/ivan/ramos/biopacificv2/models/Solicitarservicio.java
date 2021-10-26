package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Solicitarservicio {

    private String idPerfil;
    private String idMedico;
    private String idMascota;
    private String fechaRegistro;

    public Solicitarservicio(){}

    public Solicitarservicio(String idPerfil, String idMedico, String idMascota, String fechaRegistro) {
        this.idPerfil = idPerfil;
        this.idMedico = idMedico;
        this.idMascota = idMascota;
        this.fechaRegistro = fechaRegistro;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getIdMedico() {
        return idMedico;
    }

    public void setIdMedico(String idMedico) {
        this.idMedico = idMedico;
    }

    public String getIdMascota() {
        return idMascota;
    }

    public void setIdMascota(String idMascota) {
        this.idMascota = idMascota;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}