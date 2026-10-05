package negocio.modelo.dominio;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Chofer {

    private int idChofer;
    private Usuario usuario;
    private Categoria categoria;
    private String nombre;
    private String apellido;
    private String dni;
    private LocalDate fechaNacimiento;
    private String telefonoCelular;
    private List<Camion> camiones = new ArrayList<>();

    public Chofer() {
    }

    public Chofer(int idChofer, Usuario usuario, Categoria categoria, String nombre, String apellido,
                  String dni, LocalDate fechaNacimiento, String telefonoCelular) {
        this.idChofer = idChofer;
        this.usuario = usuario;
        this.categoria = categoria;
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.fechaNacimiento = fechaNacimiento;
        this.telefonoCelular = telefonoCelular;
    }

    // para chequear si el chofer puede manear x camión segun su categoria
    public boolean puedeManejar(Camion camion) {
        return camion.getToneladasMaximas() <= categoria.getToneladasMaximas();
    }

    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }

    public void agregarCamion(Camion camion) {
        this.camiones.add(camion);
    }

    public int getIdChofer() {
        return idChofer;
    }

    public void setIdChofer(int idChofer) {
        this.idChofer = idChofer;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDni() {
        return dni;
    }

    public void setDni(String dni) {
        this.dni = dni;
    }

    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getTelefonoCelular() {
        return telefonoCelular;
    }

    public void setTelefonoCelular(String telefonoCelular) {
        this.telefonoCelular = telefonoCelular;
    }

    public List<Camion> getCamiones() {
        return camiones;
    }

    public void setCamiones(List<Camion> camiones) {
        this.camiones = camiones;
    }

    @Override
    public String toString() {
        return getNombreCompleto() + " (DNI " + dni + ")";
    }
}
