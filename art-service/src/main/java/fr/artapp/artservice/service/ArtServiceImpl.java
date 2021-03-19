package fr.artapp.artservice.service;

import fr.artapp.artservice.Exception.ExceptionDejaException;
import fr.artapp.artservice.Exception.OeuvreNotFoundException;
import fr.artapp.artservice.model.Categorie;
import fr.artapp.artservice.model.Oeuvre;
import fr.artapp.artservice.repository.OeuvreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
import java.util.Optional;

@Service
public class ArtServiceImpl implements ArtService{

    @Autowired
    OeuvreRepository oeuvreRepository;

    private Collection<Oeuvre> lesOeuvres = new ArrayList<>();
    @Override
    public Collection<Oeuvre> getAllOeuvres() {
        return (Collection<Oeuvre>)  oeuvreRepository.findAll();
    }

    @Override
    public Optional<Oeuvre> getOeuvreById(Long id) {
        return oeuvreRepository.findById(id);
    }

    @Override
    public Oeuvre ajoutOeuvre(Oeuvre oeuvre) {
        return oeuvreRepository.save(oeuvre);
    }


    public Oeuvre creerOeuvre(String titre) throws ExceptionDejaException {
        System.out.println("les oeuvres : " + lesOeuvres);

        if (this.lesOeuvres.contains(titre)){
            throw new ExceptionDejaException();
        }else {
            Oeuvre newOeuvre = new Oeuvre(titre);
            lesOeuvres.add(newOeuvre);

            oeuvreRepository.save(newOeuvre);
            return newOeuvre;
        }
    }

    @Override
    public void suppressionOeuvre(Long id) throws OeuvreNotFoundException {
        Optional<Oeuvre> oeuvre = oeuvreRepository.findById(id);
        if (Objects.isNull(oeuvre)){
            throw new OeuvreNotFoundException();
        }
        oeuvreRepository.deleteById(id);
    }

    @Override
    public Collection<Oeuvre> getAllOeuvreByCategorie(Categorie categorie) {
        return oeuvreRepository.findAllByCategorie(categorie);
    }

    @Override
    public Collection<Oeuvre> getAllOeuvreByTitre(String titre) {
        return oeuvreRepository.findAllByTitre(titre);
    }

    /*
    @Override
    public void modifierOeuvreTitre(String title) {
        //to do
    }

    @Override
    public boolean oeuvreExiste(Long id) {
        return oeuvreRepository.existsById(id);
    }
    */



}
