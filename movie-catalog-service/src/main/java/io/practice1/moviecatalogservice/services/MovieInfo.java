package io.practice1.moviecatalogservice.services;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.practice1.moviecatalogservice.models.CatalogItem;
import io.practice1.moviecatalogservice.models.Movie;
import io.practice1.moviecatalogservice.models.Rating;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfo {

    @Autowired
    private RestTemplate restTemplate;

    @HystrixCommand(fallbackMethod = "getFallBackCatalogItem")
    public CatalogItem getCatalogItem(Rating rating) {
        Movie movie = restTemplate.getForObject("http://movie-info-service/movies/" + rating.getMovieId(), Movie.class);
        return new CatalogItem(movie.getName(), movie.getDescription(), rating.getRating());
    }

    public CatalogItem getFallBackCatalogItem(Rating rating) {
        return new CatalogItem("Movie name not found", "", rating.getRating());

    }

}
