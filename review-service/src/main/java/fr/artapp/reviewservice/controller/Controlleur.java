package fr.artapp.reviewservice.controller;

import fr.artapp.reviewservice.exceptions.LoginNotCorrectException;
import fr.artapp.reviewservice.exceptions.ReviewNotFoundException;
import fr.artapp.reviewservice.model.Review;
import fr.artapp.reviewservice.repository.ReviewRepository;
import fr.artapp.reviewservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ReplayProcessor;

import java.net.URI;
import java.security.Principal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@RestController
public class Controlleur {
    @Autowired
    ReviewService reviewService;


    // STREAM de notifications
    private ReplayProcessor<Review> notifications = ReplayProcessor.create(0, false);

    RestTemplate keycloakRestTemplate  = new RestTemplate(
            new BufferingClientHttpRequestFactory(
                    new SimpleClientHttpRequestFactory()
            )
    );

    @GetMapping(value = "/hello")
    @ResponseStatus(HttpStatus.OK)
    public Mono<String> hello(){
        String uri ="http://localhost:8089/api/art/hello";
        String result = keycloakRestTemplate.getForObject(uri, String.class);

        return Mono.just("hello rewiew ! "+result);
    }


    @GetMapping(value = "/avis/subscribe", produces = MediaType.APPLICATION_STREAM_JSON_VALUE)
    public Flux<Review> notification() {
        return Flux.from(notifications);
    }



    @PostMapping(value = "/avis")
    public ResponseEntity<Review> create(@RequestBody Review reviewBody, UriComponentsBuilder base) {
        reviewService.setReview(reviewBody);
        URI location = base.path("/api/avis/{id}").buildAndExpand(reviewBody).toUri();
        // notification d'un nouveau avis dans le Stream
        notifications.onNext(reviewBody);

        return ResponseEntity.created(location).body(reviewBody);
    }

    @GetMapping(value = "/avis")
    @ResponseStatus(HttpStatus.OK)
    public Collection<Review> getAllAvis() {
        return reviewService.getAllReview();
    }

    @DeleteMapping(value = "/avis/{id}")
    public ResponseEntity<String> suppressionAvis(Principal principal, @PathVariable("id") String id, UriComponentsBuilder base) {
        String login=principal.getName();
        try {
            reviewService.suppressionReview(id,login);
        } catch (ReviewNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        } catch (LoginNotCorrectException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
        return ResponseEntity.ok().body("Suppression effectuée : "+id);
    }

    @GetMapping(value = "/avis/{id}")
    public ResponseEntity<?> getAvisById(@PathVariable String id) {
        try {
            Optional<Review> review = reviewService.getReviewById(id);
            return ResponseEntity.ok(review.get());
        }
        catch(ReviewNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.toString());
        }
    }

    @GetMapping(value = "/avis/oeuvre/{idOeuvre}")
    public ResponseEntity<?> getAllReviewByIdOeuvre(@PathVariable Long idOeuvre) {
        return ResponseEntity.ok(reviewService.getAllReviewByIdOeuvre(idOeuvre));
    }


}
