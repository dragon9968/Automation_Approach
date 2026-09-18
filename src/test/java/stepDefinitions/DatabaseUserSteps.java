package stepDefinitions;

import db.queries.UserQueries;
import org.testng.Assert;
import java.util.List;
import java.util.Map;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DatabaseUserSteps {

    @Given("the database is cleared of email {string}")
    public void clearEmail(String email) {
        // Hứng lấy số dòng bị ảnh hưởng
        int rows = UserQueries.deleteUserByEmail(email);
        System.out.println("--> [DB SETUP] Đã dọn dẹp " + rows + " bản ghi có email: " + email);
    }

    @When("I insert a new user with details:")
    public void insertUserWithDataTable(List<Map<String, String>> data) {
        Map<String, String> row = data.get(0);
        int rows = UserQueries.insertUser(row.get("email"), row.get("pass"), row.get("status"));

        Assert.assertEquals(rows, 1, "❌ Lỗi: Thêm mới user thất bại!");
        System.out.println("--> [DB INSERT] Đã thêm thành công user: " + row.get("email"));
    }

    @When("I update status of user with email {string} to {string}")
    public void updateStatus(String email, String newStatus) {
        int rows = UserQueries.updateUserStatus(email, newStatus);
        Assert.assertEquals(rows, 1, "❌ Lỗi: Cập nhật trạng thái thất bại!");
    }

    @Then("I verify user exists with details:")
    public void verifyUserWithDataTable(List<Map<String, String>> data) {
        Map<String, String> row = data.get(0);
        String email = row.get("email");

        String actualPass = UserQueries.getUserPassword(email);
        String actualStatus = UserQueries.getUserStatus(email);

        Assert.assertEquals(actualPass, row.get("pass"), "❌ Lỗi: Password dưới DB không khớp!");
        Assert.assertEquals(actualStatus, row.get("status"), "❌ Lỗi: Status dưới DB không khớp!");
    }

    @Then("I verify user with email {string} no longer exists in the database")
    public void verifyNotExists(String email) {
        String actualPass = UserQueries.getUserPassword(email);
        Assert.assertNull(actualPass, "❌ Lỗi: User vẫn còn tồn tại trong DB!");
    }

    @Then("I verify status of user with email {string} is {string} in the database")
    public void verifyStatus(String email, String expectedStatus) {
        String actualStatus = UserQueries.getUserStatus(email);
        Assert.assertEquals(actualStatus, expectedStatus, "❌ Lỗi: Trạng thái dưới DB không khớp!");
        System.out.println("--> [DB SELECT] Xác nhận trạng thái của " + email + " đã chuyển sang: " + actualStatus);
    }

    @When("I delete user with email {string} from the database")
    public void deleteUser(String email) {
        int rows = UserQueries.deleteUserByEmail(email);
        Assert.assertEquals(rows, 1, "❌ Lỗi: Xóa user thất bại!");
        System.out.println("--> [DB DELETE] Đã xóa thành công user: " + email);
    }
}