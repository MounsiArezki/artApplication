package fr.artapp.artservice.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Oeuvre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Column (name = "titre")
    private String titre;
    private LocalDateTime date;
    @ManyToOne
    private Categorie categorie;
    private Long userId;

    public Oeuvre (String titre){
        this.titre=titre;
    }

    public Oeuvre() {

    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

