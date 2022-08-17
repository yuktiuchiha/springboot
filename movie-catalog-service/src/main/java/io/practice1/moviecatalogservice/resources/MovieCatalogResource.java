package io.practice1.moviecatalogservice.resources;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.practice1.moviecatalogservice.models.CatalogItem;
import io.practice1.moviecatalogservice.models.Movie;
import io.practice1.moviecatalogservice.models.Rating;
import io.practice1.moviecatalogservice.models.UserRating;
import io.practice1.moviecatalogservice.services.MovieInfo;
import io.practice1.moviecatalogservice.services.UserRatingInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

import java.lang.reflect.ParameterizedType;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/catalog")
public class MovieCatalogResource {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private WebClient.Builder WebClientBuilder;

    @Autowired
    MovieInfo movieInfo;

    @Autowired
    UserRatingInfo userRatingInfo;


    @RequestMapping("/{userId}")
    public List<CatalogItem> getCatalog(@PathVariable("userId") String userId){
        UserRating ratings = userRatingInfo.getRating(userId);
        return ratings.getUserRating().stream()
                .map(rating -> movieInfo.getCatalogItem(rating)
                )
                .collect(Collectors.toList());
    }

//        public List<CatalogItem> getFallBackCatalog(@PathVariable("userId") String userId){
//        return Arrays.asList(new CatalogItem("No movie", "", 0));
//    }

}



          /*  Movie movie = WebClientBuilder.build()
                    .get()
                    .uri("http://localhost:8081/movies/" + rating.getMovieId())
                    .retrieve()
                    .bodyToMono(Movie.class)
                    .block();  // to block execution until bodyToMono() execution ends
            */
