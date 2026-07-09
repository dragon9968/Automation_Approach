package stepDefinitions;

import java.util.Map;

import dtos.UserLoginDTO;
import dtos.UserRegisterDTO;
import io.cucumber.java.DataTableType;

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
    
    @DataTableType
    public UserLoginDTO userLoginTransformer(Map<String, String> row) {
        return new UserLoginDTO(
            row.get("email"),
            row.get("password")
        );
    }
}