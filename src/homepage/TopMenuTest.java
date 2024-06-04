package homepage;

import browserfactory.BaseTest;
import com.beust.jcommander.Parameter;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import utilities.Utility;

public class TopMenuTest extends Utility {

    String baseUrl = "https://demo.nopcommerce.com/";

    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    /**
     * 1.1 create method with name "selectMenu" it has one parameter name "menu" of type
     * string
     */

    public void selectMenu(String menu) {
        //Click on menu name
        clickOnElement(By.xpath("//ul[@class='top-menu notmobile']//a[contains(text(), '" + menu + "')]"));

    }

    @Test
    public void verifyPageNavigation() {
        //pass any name
        String menuName = "Apparel";
        // call selectMenu method
        selectMenu(menuName);
        //Verify current page
        String expectedTitle = "nopCommerce demo store. " + menuName;
        String actualTitle = driver.getTitle();
        Assert.assertEquals("Page title does not match!", expectedTitle, actualTitle);
        System.out.println("The current page is " + actualTitle);

    }


    @After
    public void tearDown() {
        //closeBrowser();
    }
}
