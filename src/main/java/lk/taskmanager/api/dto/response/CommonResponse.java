package lk.taskmanager.api.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponse {
    private boolean success;
    private String message;
    private Object data;

    public static CommonResponse success(String message, Object data) {
        return CommonResponse.builder().success(true).message(message).data(data).build();
    }

    public static CommonResponse success(String message) {
        return CommonResponse.builder().success(true).message(message).build();
    }

    public static CommonResponse error(String message) {
        return CommonResponse.builder().success(false).message(message).build();
    }
}
