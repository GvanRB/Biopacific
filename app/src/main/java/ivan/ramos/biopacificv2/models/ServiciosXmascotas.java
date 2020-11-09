package ivan.ramos.biopacificv2.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ServiciosXmascotas {

    @SerializedName("nombre")
    @Expose
    private String nombre;
    @SerializedName("costo")
    @Expose
    private Integer costo;
    @SerializedName("estado")
    @Expose
    private String estado;
    @SerializedName("fechaRegistro")
    @Expose
    private String fechaRegistro;
    @SerializedName("nombreMascota")
    @Expose
    private String nombreMascota;

    /**
     * No args constructor for use in serialization
     *
     */
    public ServiciosXmascotas() {
    }

    /**
     *
     * @param estado
     * @param costo
     * @param nombreMascota
     * @param fechaRegistro
     * @param nombre
     */
    public ServiciosXmascotas(String nombre, Integer costo, String estado, String fechaRegistro, String nombreMascota) {
        super();
        this.nombre = nombre;
        this.costo = costo;
        this.estado = estado;
        this.fechaRegistro = fechaRegistro;
        this.nombreMascota = nombreMascota;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getCosto() {
        return costo;
    }

    public void setCosto(Integer costo) {
        this.costo = costo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(String fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public String getNombreMascota() {
        return nombreMascota;
    }

    public void setNombreMascota(String nombreMascota) {
        this.nombreMascota = nombreMascota;
    }

}