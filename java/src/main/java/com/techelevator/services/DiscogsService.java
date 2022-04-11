package com.techelevator.services;

import com.techelevator.model.SearchResponse;
import com.techelevator.model.SearchResult;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class DiscogsService {
    private static final String BASE_URL = "https://api.discogs.com/database/search?q=";
    private static final String API_KEY = "&key=TUKZuQXIiunYiCqSkMhZ&secret=rbFacmbFapyyedTfUNNsLcYBbnVHLMVm";

    private RestTemplate restTemplate = new RestTemplate();

    public SearchResponse search(String expression) {
        String url = BASE_URL + expression + API_KEY;
        SearchResponse searchResponse = restTemplate.getForObject(url, SearchResponse.class);
        for(SearchResult searchResult : searchResponse.getSearchResults()) {
            restTemplate.getForObject(searchResult.getResourceURL(), );
        }
    }

}
