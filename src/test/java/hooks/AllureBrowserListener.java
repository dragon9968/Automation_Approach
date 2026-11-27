package hooks;

import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.Label;
import io.qameta.allure.model.Parameter;
import io.qameta.allure.model.TestResult;

import java.util.ArrayList;
import java.util.List;

public class AllureBrowserListener implements TestLifecycleListener {

    @Override
    public void beforeTestWrite(TestResult result) {
        String browser = CucumberHooks.CURRENT_BROWSER.get();
        if (browser != null && !browser.isEmpty()) {
            // 1. Gắn tên trình duyệt vào đầu tên Test Case
            result.setName("[" + browser + "] " + result.getName());

            // 2. Tách riêng testCaseId và historyId để Allure không gộp Chrome & Firefox làm 1
            if (result.getTestCaseId() != null) {
                result.setTestCaseId(result.getTestCaseId() + "_" + browser);
            }
            String baseHistoryId = result.getHistoryId() != null ? result.getHistoryId() : result.getFullName();
            result.setHistoryId(baseHistoryId + "_" + browser);

            // 3. Gom nhóm thành 2 thư mục gốc CHROME và FIREFOX trong tab Suites
            List<Label> labels = result.getLabels() != null ? new ArrayList<>(result.getLabels()) : new ArrayList<>();
            labels.add(new Label().setName("parentSuite").setValue(browser));
            result.setLabels(labels);

            // 4. Tạo ArrayList mới để tránh lỗi UnsupportedOperationException khi thêm Parameter
            List<Parameter> parameters = result.getParameters() != null ? new ArrayList<>(result.getParameters()) : new ArrayList<>();
            parameters.add(new Parameter().setName("Browser").setValue(browser));
            result.setParameters(parameters);
        }
    }

    @Override
    public void afterTestWrite(TestResult result) {
        CucumberHooks.CURRENT_BROWSER.remove();
    }
}