package web.model.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GetTaskListWebResponse {
  private List<Task> tasks;

  @Data
  @AllArgsConstructor
  @NoArgsConstructor
  @Builder
  public static class Task {
    private String id;
    private String task;
    private String detail;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date schedule;
  }

}

