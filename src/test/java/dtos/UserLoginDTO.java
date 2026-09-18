package dtos;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data                  // Tự động sinh tất cả Getter, Setter, toString, equals, hashCode
@NoArgsConstructor     // Tự động sinh Constructor không tham số
@AllArgsConstructor    // Tự động sinh Constructor chứa đầy đủ tất cả tham số

public class UserLoginDTO {
    private String email;
    private String password;
}