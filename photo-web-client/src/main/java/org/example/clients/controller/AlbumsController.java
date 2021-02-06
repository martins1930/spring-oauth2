package org.example.clients.controller;

import java.util.List;
import org.example.clients.domain.Album;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@Controller
public class AlbumsController {

  private final OAuth2AuthorizedClientService oAuth2AuthorizedClientService;

  private final RestTemplate restTemplate;

  private final WebClient webClient;

  public AlbumsController(
      OAuth2AuthorizedClientService oAuth2AuthorizedClientService,
      RestTemplate restTemplate,
      WebClient webClient) {
    this.oAuth2AuthorizedClientService = oAuth2AuthorizedClientService;
    this.restTemplate = restTemplate;
    this.webClient = webClient;
  }

  @GetMapping("/albums")
  public String getAlbumsEntry(
      Model model, @AuthenticationPrincipal OidcUser principal,
      OAuth2AuthenticationToken oauthToken) {

    OAuth2AuthorizedClient oAuth2AuthorizedClient =
        oAuth2AuthorizedClientService.loadAuthorizedClient(
            oauthToken.getAuthorizedClientRegistrationId(), oauthToken.getName());

    String jwtAccessToken = oAuth2AuthorizedClient.getAccessToken().getTokenValue();
    System.out.println("jwtAccessToken = " + jwtAccessToken);

    System.out.println("principal = " + principal);

    OidcIdToken idToken = principal.getIdToken();
    String tokenValue = idToken.getTokenValue();
    System.out.println("tokenValue = " + tokenValue);

    HttpHeaders headers = new HttpHeaders();
    headers.add("Authorization", "Bearer " + jwtAccessToken);

    ResponseEntity<List<Album>> response = restTemplate.exchange(
        "http://localhost:8082/albums",
        HttpMethod.GET,
        new HttpEntity<>(headers),
        new ParameterizedTypeReference<List<Album>>() {
        });

    model.addAttribute("albums", response.getBody());

    return "albums";
  }

  @GetMapping("/albums-webclient")
  public String getAlbumsEntryWebClient(Model model) {

    String url = "http://localhost:8082/albums";
    List<Album> albums = webClient.get().uri(url)
        .retrieve()
        .bodyToMono(new ParameterizedTypeReference<List<Album>>() {
        })
        .block();

    model.addAttribute("albums", albums);

    return "albums";
  }

}
