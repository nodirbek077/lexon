package uz.nanotechsolutions.lexon.payload.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse extends AllResponse{
    private Integer userId;

    public UserResponse(Integer errorCode, String errorMessage, Integer userId) {
        super(errorCode, errorMessage);
        this.userId = userId;
    }

    public UserResponse(Integer errorCode, String errorMessage, Object object) {
        super(errorCode, errorMessage, object);
    }

    public UserResponse(Integer errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
