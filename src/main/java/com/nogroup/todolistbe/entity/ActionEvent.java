package com.nogroup.todolistbe.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "history")
public class ActionEvent {
  @Id
  private String identifier;
  private String action;
  private String updatedBy;
  private List<String> updatedFields;
  private Task oldValue;
  private Task newValue;
}
