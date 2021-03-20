package fr.artapp.artservice.controller;
import fr.artapp.artservice.Exception.ExceptionDejaException;
import fr.artapp.artservice.Exception.ExisteDejaException;
import fr.artapp.artservice.Exception.OeuvreNotFoundException;
import fr.artapp.artservice.model.Categorie;
import fr.artapp.artservice.model.Oeuvre;
import fr.artapp.artservice.service.ArtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
<<<<<<< HEAD
import java.util.*;
=======
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
>>>>>>> 55a97fd3ef31d1846143a4a26253ca9e04357600

@RestController
public class Controleur  {

    @Autowired
    ArtService artService;

    @GetMapping(value = "/oeuvres")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Oeuvre> getAllOeuvres() {
        return artService.getAllOeuvres();
    }

<<<<<<< HEAD
    @GetMapping(value = "/oeuvres/{id}")
    public ResponseEntity getOeuvreById(@PathVariable Long id) {
        try {
            Optional<Oeuvre> oeuvre = artService.getOeuvreById(id);
            return ResponseEntity.ok(oeuvre.get());
        }
        catch(OeuvreNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
    }
=======

>>>>>>> 55a97fd3ef31d1846143a4a26253ca9e04357600

    @DeleteMapping(value = "/oeuvres/{id}")
    public ResponseEntity<String> suppressionOeuvre(@PathVariable("id") Long id) {
        try {
            artService.suppressionOeuvre(id);
        }
        catch(OeuvreNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
        return ResponseEntity.ok().build();
    }

    //to do Elodie: ajouter une exception
    @PostMapping(value = "/oeuvres")
    public ResponseEntity<Oeuvre> ajoutOeuvre(@RequestBody Oeuvre oeuvre, UriComponentsBuilder base) {
        artService.ajoutOeuvre(oeuvre);
        URI location = base.path("/art/oeuvres/{id}").buildAndExpand(oeuvre).toUri();
        return ResponseEntity.created(location).body(oeuvre);
    }

    @GetMapping(value = "/oeuvres/okk/{id}")
    public ResponseEntity<Oeuvre> getOeuvreById(@PathVariable Long id) {
        Optional<Oeuvre> oeuvre = artService.getOeuvreById(id);
        return ResponseEntity.ok(oeuvre.get());
    }

    @GetMapping(value = "/oeuvres/{nomCategorie}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Oeuvre>> getAllOeuvreByCategorie(@PathVariable String nomCategorie) {
        Categorie categorie =artService.getCategorieByNomcategorie(nomCategorie);
        Collection<Oeuvre> oeuvre = artService.getAllOeuvreByCategorie(categorie);
        if (categorie!=null && !oeuvre.isEmpty() ){

            return ResponseEntity.ok(oeuvre);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND ).build();
        }

        // il reste à ajouter la configuration pour l'authentification


    }

    @GetMapping(value = "/oeuvres/titre/{titre}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<Oeuvre>> getAllOeuvreBytitle(@PathVariable String titre) {
        Collection<Oeuvre> oeuvre = artService.getAllOeuvreByTitre(titre);

        // il reste à ajouter la configuration pour l'authentification

        if(!oeuvre.isEmpty()){
            return ResponseEntity.ok().body(oeuvre);
        }else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

    }



}

