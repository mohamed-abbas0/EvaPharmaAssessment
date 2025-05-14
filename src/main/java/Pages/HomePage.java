package Pages;

import com.microsoft.playwright.Page;

import static Constants.ElementLocators.HomePageElementLocators.*;

public class HomePage {
    public void navigateToAdminPage(Page page){
       page.click(adminPanelBtn);
    }
}
