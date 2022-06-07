package com.nogroup.todolistbe.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JsonHelper {

  private final ObjectMapper objectMapper;

  public <T> T fromJson(String json, TypeReference<T> typeReference) throws JsonProcessingException {
    try {
      return objectMapper.readValue(json, typeReference);
    } catch (Throwable err) {
      throw err;
    }
  }

  public <T> T fromJson(String json, Class<T> tClass) throws JsonProcessingException {
    try {
      return this.objectMapper.readValue(json, tClass);
    } catch (Throwable err) {
      throw err;
    }
  }

  public <T> String toJson(T object) throws JsonProcessingException {
    try {
      return objectMapper.writeValueAsString(object);
    } catch (Throwable err) {
      throw err;
    }
  }

}
