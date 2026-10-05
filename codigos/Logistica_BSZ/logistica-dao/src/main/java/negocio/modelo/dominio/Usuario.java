package negocio.modelo.dominio;

public class Usuario {

    private int idUsuario;
    private String nombreUsuario;
    private String password;
    private Perfil perfil;

    public Usuario() {
    }

    public Usuario(int idUsuario, String nombreUsuario, String password, Perfil perfil) {
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.password = password;
        this.perfil = perfil;
    }

    public boolean esAdmin() {
        return perfil == Perfil.ADMIN;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "idUsuario=" + idUsuario +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", perfil=" + perfil +
                '}';
    }
}
