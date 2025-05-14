package Pages;

import com.microsoft.playwright.Page;

import static Constants.ElementLocators.LoginPageElementLocators.*;

public class LoginPage {
    public void login(Page page, String username, String password){
        page.fill(usernameTextbox, username);
        page.fill(passwordTextbox, password);
        page.click(loginBtn);
    }
}
