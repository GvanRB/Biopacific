package ivan.ramos.biopacificv2.models;

public class Veterinarias {

    private String idVeterinaria;
    private String nombre;
    private String direccion;
    private String telefono;
    private String razonSocial;
    private String idDistrito;
    private String ruc;
    private String correo;
    private String contrasena;
    private Integer estado;
    private String idUsuario;

    public String getIdVeterinaria() {
        return idVeterinaria;
    }

    public String setIdVeterinaria(String idVeterinaria) {
        this.idVeterinaria = idVeterinaria;
        return idVeterinaria;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Veterinarias() { }

    public Veterinarias(String nombre, String direccion, String telefono, String razonSocial, String idDistrito, String ruc, String correo, String contrasena) {
        this.nombre = nombre;
        this.direccion = direccion;
        this.telefono = telefono;
        this.razonSocial = razonSocial;
        this.idDistrito = idDistrito;
        this.ruc = ruc;
        this.correo = correo;
        this.contrasena = contrasena;
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

    public String getRazonSocial() {
        return razonSocial;
    }

    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }

    public String getIdDistrito() {
        return idDistrito;
    }

    public void setIdDistrito(String idDistrito) {
        this.idDistrito = idDistrito;
    }

    public String getRuc() {
        return ruc;
    }

    public void setRuc(String ruc) {
        this.ruc = ruc;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }
}