package stepDefinitions;

import dtos.UserRegisterDTO;
import io.cucumber.java.DataTableType;
import java.util.Map;

public class DataTransformer {

    @DataTableType
    public UserRegisterDTO userRegisterTransformer(Map<String, String> row) {
        return new UserRegisterDTO(
            row.get("firstName"),
            row.get("middleName"),
            row.get("lastName"),
            row.get("email"),
            row.get("password"),
            row.get("confirmPassword")
        );
    }
}