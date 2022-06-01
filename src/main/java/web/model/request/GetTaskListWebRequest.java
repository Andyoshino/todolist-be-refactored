package web.model.request;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class GetTaskListWebRequest {
  @NotNull
  private Integer pageSize;

  @NotNull
  @Min(1)
  private Integer page;
}
