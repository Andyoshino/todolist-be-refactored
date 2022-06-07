package com.nogroup.todolistbe.web.model.response;

import com.nogroup.todolistbe.entity.ActionEvent;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetHistoryWebResponse {
  private List<ActionEvent> actionEvents;
}
