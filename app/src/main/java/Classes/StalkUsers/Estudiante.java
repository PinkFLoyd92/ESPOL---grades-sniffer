package Classes.StalkUsers;

/**
 * Created by sebas on 3/20/16.
 */
public class Estudiante {
    public String getCodigo_estudiante() {
        return codigo_estudiante;
    }

    public void setCodigo_estudiante(String codigo_estudiante) {
        this.codigo_estudiante = codigo_estudiante;
    }

    public String getNumero_identificacion() {
        return numero_identificacion;
    }

    public void setNumero_identificacion(String numero_identificacion) {
        this.numero_identificacion = numero_identificacion;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    private String codigo_estudiante,numero_identificacion,nombres, apellidos;

    public Estudiante(String codigo_estudiante, String numero_identificacion, String nombres, String apellidos) {
        this.codigo_estudiante = codigo_estudiante;
        this.numero_identificacion = numero_identificacion;
        this.nombres = nombres;
        this.apellidos = apellidos;
    }

}
