package ivan.ramos.biopacificv2.models;

public class PerfilExamenes {

    private String nombreExamen;
    private String tipoExamen;

    public PerfilExamenes() {
    }

    public PerfilExamenes(String nombreExamen, String tipoExamen) {
        this.nombreExamen = nombreExamen;
        this.tipoExamen = tipoExamen;
    }

    public String getNombreExamen() {
        return nombreExamen;
    }

    public void setNombreExamen(String nombreExamen) {
        this.nombreExamen = nombreExamen;
    }

    public String getTipoExamen() {
        return tipoExamen;
    }

    public void setTipoExamen(String tipoExamen) {
        this.tipoExamen = tipoExamen;
    }
}
