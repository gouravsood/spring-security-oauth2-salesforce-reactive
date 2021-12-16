package de.rieckpil.blog;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.reactive.function.client.WebClient;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.oauth2AuthorizedClient;


import org.springframework.core.ParameterizedTypeReference;

import reactor.core.publisher.Mono;

@Controller
@RequestMapping("/")
public class AccountController {
 
    private static final String SF_API_URL = "https://gsood-svc-dev-ed.my.salesforce.com";
 
    private WebClient webClient;
 
    public AccountController(WebClient webClient) {
        this.webClient = webClient;
    }
 
    @GetMapping("/accounts")
    public String index(@RegisteredOAuth2AuthorizedClient OAuth2AuthorizedClient authorizedClient,
                        @AuthenticationPrincipal OAuth2User oauth2User,
                        Model model) {
        Mono<String> response = fetchAccounts(authorizedClient);        
        model.addAttribute("response", response);
        model.addAttribute("username", oauth2User.getAttributes().get("name"));
 
        return "index";
    }
    
 
    private Mono<String> fetchAccounts(OAuth2AuthorizedClient authorizedClient) {
        return this.webClient
                .get()
                .uri(SF_API_URL, uriBuilder ->
                        uriBuilder
                                .path("/services/data/v51.0/query/")
                                .queryParam("q","SELECT Id, Name FROM Account LIMIT 5")
                                .build()
                )
                .attributes(oauth2AuthorizedClient(authorizedClient))
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<String>() {});
    }
}