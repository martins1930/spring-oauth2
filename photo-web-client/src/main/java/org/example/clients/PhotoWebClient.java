package org.example.clients;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.OAuth2AuthorizedClientRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServletOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class PhotoWebClient {

  public static void main(String[] args) {
    SpringApplication.run(PhotoWebClient.class, args);
  }

  @Bean
  public RestTemplate getRestTemplate() {
    return new RestTemplate();
  }

  @Bean
  public WebClient webClient(
      ClientRegistrationRepository client, OAuth2AuthorizedClientRepository auth) {

    ServletOAuth2AuthorizedClientExchangeFilterFunction oauthFilter =
        new ServletOAuth2AuthorizedClientExchangeFilterFunction(client, auth);

    oauthFilter.setDefaultOAuth2AuthorizedClient(true);

    return WebClient.builder().apply(oauthFilter.oauth2Configuration()).build();
  }
}
