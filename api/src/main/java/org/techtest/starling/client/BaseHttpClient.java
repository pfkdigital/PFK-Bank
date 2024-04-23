package org.techtest.starling.client;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;


// This class is used to create a base http client that can be used to make requests to the Starling API.
@Getter
@Configuration
public class BaseHttpClient {
  private final RestClient restClient;

  public BaseHttpClient(
      @Value("${starling.baseUrl}") String baseUrl,
      @Value("${starling.accessToken}") String accessKey,
      RestClient.Builder restClientBuilder) {
    this.restClient =
        restClientBuilder
            .baseUrl(baseUrl)
            .defaultHeader("user-agent", "Nuh Ali")
            .defaultHeader("Authorization", "Bearer " + accessKey)
            .build();
  }
}
