package stepDefinitions;

import org.testng.Assert;
import commons.DatabaseHelper;
import java.util.List;
import java.util.Map;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DatabaseUserSteps {

    // 1. DỌN DẸP TRƯỚC KHI TEST
    @Given("the database is cleared of email {string}")
    public void clearEmail(String email) {
        String sql = "DELETE FROM users_test WHERE email = ?";
        DatabaseHelper.executeUpdate(sql, email); // Thổi bay user cũ trong 1 dòng code!
        System.out.println("--> [DB SETUP] Đã dọn dẹp sạch sẽ email: " + email);
    }

    // 2. THỰC THI INSERT
    @When("I insert a new user with email {string}, password {string} and status {string}")
    public void insertUser(String email, String pass, String status) {
        String sql = "INSERT INTO users_test (email, pass, status) VALUES (?, ?, ?)";
        int rows = DatabaseHelper.executeUpdate(sql, email, pass, status);

        Assert.assertEquals(rows, 1, "❌ Lỗi: Thêm mới user thất bại!");
        System.out.println("--> [DB INSERT] Đã thêm thành công user: " + email);
    }

    // 3. THỰC THI SELECT (Verify Password)
    @Then("I verify user with email {string} has password {string} in the database")
    public void verifyPassword(String email, String expectedPass) {
        String sql = "SELECT pass FROM users_test WHERE email = ?";
        String actualPass = DatabaseHelper.getSingleValue(sql, "pass", email);

        Assert.assertEquals(actualPass, expectedPass, "❌ Lỗi: Password lưu dưới DB bị sai!");
        System.out.println("--> [DB SELECT] Xác nhận password của " + email + " đúng là: " + actualPass);
    }
    @When("I insert a new user with details:")
    public void insertUserWithDataTable(List<Map<String, String>> data) {
        // Chuyển DataTable thành List Map (Key là tên cột, Value là dữ liệu ô)
        Map<String, String> row = data.get(0);
        String sql = "INSERT INTO users_test (email, pass, status) VALUES (?, ?, ?)";
        // Bốc dữ liệu từ Map truyền thẳng vào hàm executeUpdate
        int rows = commons.DatabaseHelper.executeUpdate(sql,
                row.get("email"),
                row.get("pass"),
                row.get("status")
        );
        Assert.assertEquals(rows, 1, "❌ Lỗi: Thêm mới user thất bại!");
        System.out.println("--> [DB INSERT] Đã thêm thành công user: " + row.get("email"));
    }
    // 4. THỰC THI UPDATE
    @When("I update status of user with email {string} to {string}")
    public void updateStatus(String email, String newStatus) {
        String sql = "UPDATE users_test SET status = ? WHERE email = ?";
        int rows = DatabaseHelper.executeUpdate(sql, newStatus, email);
        Assert.assertEquals(rows, 1, "❌ Lỗi: Cập nhật trạng thái thất bại!");
        System.out.println("--> [DB UPDATE] Đã cập nhật trạng thái của " + email + " thành " + newStatus);
    }

    @Then("I verify user exists with details:")
    public void verifyUserWithDataTable(List<Map<String, String>> data) { // 🌟 Khai báo thẳng List Map ở đây luôn!
        // 🌟 Chỉ cần lấy dòng số 0 ra xài, không cần gọi .asMaps() nữa!
        Map<String, String> row = data.get(0);
        String email = row.get("email");
        // Thực hiện query lấy cả pass và status lên để so sánh
        String sqlPass = "SELECT pass FROM users_test WHERE email = ?";
        String sqlStatus = "SELECT status FROM users_test WHERE email = ?";
        String actualPass = commons.DatabaseHelper.getSingleValue(sqlPass, "pass", email);
        String actualStatus = commons.DatabaseHelper.getSingleValue(sqlStatus, "status", email);
        // Tiến hành Assert kiểm tra đồng thời cả 2 cột dưới DB
        Assert.assertEquals(actualPass, row.get("pass"), "❌ Lỗi: Password dưới DB không khớp!");
        Assert.assertEquals(actualStatus, row.get("status"), "❌ Lỗi: Status dưới DB không khớp!");
        System.out.println("--> [DB SELECT] Xác nhận thông tin user " + email + " khớp chuẩn xác 100%!");
    }

    // 5. THỰC THI SELECT (Verify Status)
    @Then("I verify status of user with email {string} is {string} in the database")
    public void verifyStatus(String email, String expectedStatus) {
        String sql = "SELECT status FROM users_test WHERE email = ?";
        String actualStatus = DatabaseHelper.getSingleValue(sql, "status", email);

        Assert.assertEquals(actualStatus, expectedStatus, "❌ Lỗi: Trạng thái dưới DB không khớp!");
        System.out.println("--> [DB SELECT] Xác nhận trạng thái của " + email + " đã chuyển sang: " + actualStatus);
    }

    // 6. THỰC THI DELETE
    @When("I delete user with email {string} from the database")
    public void deleteUser(String email) {
        String sql = "DELETE FROM users_test WHERE email = ?";
        int rows = DatabaseHelper.executeUpdate(sql, email);

        Assert.assertEquals(rows, 1, "❌ Lỗi: Xóa user thất bại!");
        System.out.println("--> [DB DELETE] Đã xóa user: " + email);
    }

    // 7. THỰC THI SELECT (Xác nhận đã biến mất)
    @Then("I verify user with email {string} no longer exists in the database")
    public void verifyNotExists(String email) {
        String sql = "SELECT email FROM users_test WHERE email = ?";
        String actualEmail = DatabaseHelper.getSingleValue(sql, "email", email);

        Assert.assertNull(actualEmail, "❌ Lỗi: User vẫn còn tồn tại trong DB!");
        System.out.println("--> [DB SELECT] Tuyệt vời! Xác nhận user đã biến mất không dấu vết.");
    }
}