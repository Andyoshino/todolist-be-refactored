package com.nogroup.todolistbe;

import com.fasterxml.jackson.core.type.TypeReference;
import com.nogroup.todolistbe.helper.JsonHelper;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.function.Consumer;

@SpringBootTest
@TestPropertySource({"/application-test.properties"})
@AutoConfigureWebTestClient
public abstract class AbstractIntegrationTest {

  @Autowired
  JsonHelper jsonHelper;

  @Autowired
  WebTestClient webTestClient;

  public <T> Consumer<EntityExchangeResult<byte[]>> mapAndAssert(TypeReference<T> typeReference,
      Consumer<T> assertion) {
    return entityExchangeResult -> {
      try {
        T tResponse =
            jsonHelper.fromJson(new String(entityExchangeResult.getResponseBody()), typeReference);
        assertion.accept(tResponse);
      } catch (Exception e) {
        Assertions.fail();
      }
    };
  }

}
