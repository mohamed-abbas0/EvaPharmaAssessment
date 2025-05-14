package Pages;

import com.microsoft.playwright.Page;

import static Constants.ElementLocators.AdminPageElementLocators.*;

public class AdminPage {
    public int getNumberOfRecords(Page page){
        page.waitForSelector(numberOfRecordsLabel);
        return Integer.parseInt(page.innerText(numberOfRecordsLabel).replaceAll("[^0-9]", ""));
    }

    public String getEmployeeName(Page page){
        page.waitForSelector(employeeNameLabel);
        return page.innerText(employeeNameLabel);
    }

    public void addNewAdmin(Page page, String username, String password, String employeeName){
        page.click(addBtn);
        page.click(userRoleDropdownList);
        page.click(adminRoleOption);
        page.fill(employeeNameTextbox, employeeName);
        page.locator("role=option[name=/" + employeeName.split(" ")[0] + "/]").first().click();
        page.click(statusDropdownList);
        page.click(enabledStatusOption);
        page.fill(usernameTextbox, username);
        page.fill(passwordTextbox, password);
        page.fill(confirmPasswordTextbox, password);
        page.click(saveBtn);
    }

    public void searchForAdmin(Page page, String username){
        page.fill(usernameSearchTextbox, username);
        page.click(searchBtn);
    }

    public void deleteAdmin(Page page){
        page.click(deleteBtn);
        page.click(deleteConfirmationBtn);
        page.click(resetSearchBtn);
    }
}
