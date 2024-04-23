package org.techtest.starling.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.client.ClientHttpResponse;

@AllArgsConstructor
@Getter
public class ApiRuntimeException extends RuntimeException {
   private final ClientHttpResponse response;
   private final String message;
}
