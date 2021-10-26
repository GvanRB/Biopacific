package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiciosXmascotas {

    private String idOrden;
    private Integer estadoOrden;
    private String fechaRegistroOrden;
    private String nombreMedico;
    private String apellidoMedico;
    private String dniMedico;
    private String nombreMascota;
    private String generoMascota;
    private Integer edadMascota;
    private String nombrePropietario;
    private String nombrePerfil;
    private Integer costoPerfil;
    private Integer totalfactura;
    private String numeroFactura;

    public ServiciosXmascotas() {
    }

    public ServiciosXmascotas(String idOrden, Integer estadoOrden, String fechaRegistroOrden, String nombreMedico, String apellidoMedico,
                              String dniMedico, String nombreMascota, String generoMascota, Integer edadMascota, String nombrePropietario,
                              String nombrePerfil, Integer costoPerfil, Integer totalfactura, String numeroFactura) {
        this.idOrden = idOrden;
        this.estadoOrden = estadoOrden;
        this.fechaRegistroOrden = fechaRegistroOrden;
        this.nombreMedico = nombreMedico;
        this.apellidoMedico = apellidoMedico;
        this.dniMedico = dniMedico;
        this.nombreMascota = nombreMascota;
        this.generoMascota = generoMascota;
        this.edadMascota = edadMascota;
        this.nombrePropietario = nombrePropietario;
        this.nombrePerfil = nombrePerfil;
        this.costoPerfil = costoPerfil;
        this.totalfactura = totalfactura;
        this.numeroFactura = numeroFactura;
    }

    public String getIdOrden() {
        return idOrden;
    }

    public void setIdOrden(String idOrden) {
        this.idOrden = idOrden;
    }

    public Integer getEstadoOrden() {
        return estadoOrden;
    }

    public void setEstadoOrden(Integer estadoOrden) {
        this.estadoOrden = estadoOrden;
    }

    public String getFechaRegistroOrden() {
        return fechaRegistroOrden;
    }

    public void setFechaRegistroOrden(String fechaRegistroOrden) {
        this.fechaRegistroOrden = fechaRegistroOrden;
    }

    public String getNombreMedico() {
        return nombreMedico;
    }

    public void setNombreMedico(String nombreMedico) {
        this.nombreMedico = nombreMedico;
    }

    public String getApellidoMedico() {
        return apellidoMedico;
    }

    public void setApellidoMedico(String apellidoMedico) {
        this.apellidoMedico = apellidoMedico;
    }

    public String getDniMedico() {
        return dniMedico;
    }

    public void setDniMedico(String dniMedico) {
        this.dniMedico = dniMedico;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

    public String getGeneroMascota() {
        return generoMascota;
    }

    public void setGeneroMascota(String generoMascota) {
        this.generoMascota = generoMascota;
    }

    public Integer getEdadMascota() {
        return edadMascota;
    }

    public void setEdadMascota(Integer edadMascota) {
        this.edadMascota = edadMascota;
    }

    public String getNombrePropietario() {
        return nombrePropietario;
    }

    public void setNombrePropietario(String nombrePropietario) {
        this.nombrePropietario = nombrePropietario;
    }

    public String getNombrePerfil() {
        return nombrePerfil;
    }

    public void setNombrePerfil(String nombrePerfil) {
        this.nombrePerfil = nombrePerfil;
    }

    public Integer getCostoPerfil() {
        return costoPerfil;
    }

    public void setCostoPerfil(Integer costoPerfil) {
        this.costoPerfil = costoPerfil;
    }

    public Integer getTotalfactura() {
        return totalfactura;
    }

    public void setTotalfactura(Integer totalfactura) {
        this.totalfactura = totalfactura;
    }

    public String getNumeroFactura() {
        return numeroFactura;
    }

    public void setNumeroFactura(String numeroFactura) {
        this.numeroFactura = numeroFactura;
    }
}