package edu.usal.model;

import javax.persistence.*;

@Entity
@Table(name = "perfiles")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String biografia;
    private String urlFoto;
    @OneToOne(mappedBy = "perfil")
    private Usuario usuario;

    public Perfil() {
    }

    public Perfil(Long id, String biografia, String urlFoto) {
        this.id = id;
        this.biografia = biografia;
        this.urlFoto = urlFoto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBiografia() {
        return biografia;
    }

    public void setBiografia(String biografia) {
        this.biografia = biografia;
    }

    public String getUrlFoto() {
        return urlFoto;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }
}
