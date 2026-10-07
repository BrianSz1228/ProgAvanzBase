package negocio.modelo.dominio;

public class Categoria {

    private int idCategoria;
    private String nombre;
    private int toneladasMaximas;

    public Categoria() {
    }

    public Categoria(int idCategoria, String nombre, int toneladasMaximas) {
        this.idCategoria = idCategoria;
        this.nombre = nombre;
        this.toneladasMaximas = toneladasMaximas;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getToneladasMaximas() {
        return toneladasMaximas;
    }

    public void setToneladasMaximas(int toneladasMaximas) {
        this.toneladasMaximas = toneladasMaximas;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
