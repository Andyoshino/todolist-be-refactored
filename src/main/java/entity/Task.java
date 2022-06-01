package entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import entity.constant.CollectionName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = CollectionName.TASK)
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {
  private String id;
  private String task;
  private String detail;
  @JsonFormat(pattern = "yyyy-MM-dd")
  private Date schedule;
}
