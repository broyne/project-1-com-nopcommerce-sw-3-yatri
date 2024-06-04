package computer;

import browserfactory.BaseTest;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import utilities.Utility;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TestSuite extends Utility {
    String baseUrl = "https://demo.nopcommerce.com/";


    @Before
    public void setUp() {
        openBrowser(baseUrl);
    }

    @Test
    public void verifyProductArrangeInAlphaBaticalOrder() {
        //clickOnElement(By.linkText("Computers"));
        WebElement computer = driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Computers']"));
        WebElement software = driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Desktops']"));
        Actions action = new Actions(driver);
        action.moveToElement(computer).moveToElement(software).click().build().perform();
        //call method from utility class
        selectByTextFromDropDown(By.id("products-orderby"), "Name: Z to A");
        List<WebElement> productNamesElements = driver.findElements(By.xpath("//h2[@class='product-title']//a"));

        List<String> actualProductNames = new ArrayList<>();
        for (WebElement productName : productNamesElements) {
            actualProductNames.add(productName.getText());
        }

        List<String> expectedProducts = new ArrayList<>(actualProductNames);
        expectedProducts.sort(Collections.reverseOrder());
        actualProductNames.sort(Collections.reverseOrder());
        System.out.println(actualProductNames);
        System.out.println(expectedProducts);
        Assert.assertEquals("Product Does not match", expectedProducts, actualProductNames);

    }
    @Test
    public void verifyProductAddedToShoppingCartSuccessFully() throws InterruptedException {
        //2.1 click on computer menu and click on desktop
        WebElement computer = driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Computers']"));
        //2.2 Click on desktop
        WebElement software = driver.findElement(By.xpath("//ul[@class='top-menu notmobile']//a[normalize-space()='Desktops']"));
        Actions action = new Actions(driver);
        action.moveToElement(computer).moveToElement(software).click().build().perform();
        //2.3 Select sort by position "A to Z"
        //call method from utility class
        selectByTextFromDropDown(By.id("products-orderby"), "Name: A to Z");
        //2.4 click on add to cart
        clickOnElement(By.xpath("(//button[@type='button'][normalize-space()='Add to cart'])[1]"));
        //2.5 Verify the text
        String expectedResult = "Build your own computer";
        String actualResult = getTextFromTheElement(By.xpath("//div[@class = 'product-name']/h1"));
        Assert.assertEquals("Not redirected to add cart", expectedResult, actualResult);
        //2.6 Select "2.2 GHz Intel Pentium Dual-Core E2200" using Select class
        selectByTextFromDropDown(By.cssSelector("#product_attribute_1"), "2.2 GHz Intel Pentium Dual-Core E2200");
        //2.7 Select "8GB [+$60.00]" using Select class
        selectByTextFromDropDown(By.cssSelector("#product_attribute_2"), "8GB [+$60.00]");
        //2.8 Select HDD radio "400 GB [+$100.00]
        clickOnElement(By.xpath("//input[@id='product_attribute_3_7']"));
        //2.9 Select OS radio "Vista Premium [+$60.00]"
        clickOnElement(By.xpath("//input[@id = 'product_attribute_4_9']"));
        //2.10 Check Two Check boxes "Microsoft Office [+$50.00]" and "Total Commander
        clickOnElement(By.id("product_attribute_5_12"));
        //2.11 Verify the price "$1,475.00"
        Thread.sleep(2000);
        String expectedText = "$1,475.00";
        String actualText = getTextFromTheElement(By.id("price-value-1"));
        Assert.assertEquals("Price not match", expectedText, actualText);
        //2.12 Click on add to cart
        clickOnElement(By.cssSelector("#add-to-cart-button-1"));
        //2.13 Verify text and close the bar clicking on the cross button.
        String expected = "The product has been added to your shopping cart";
        String actual = getTextFromTheElement(By.xpath("//p[@class='content']"));
        Assert.assertEquals("Text match", expected, actual);
        WebElement closeButton = driver.findElement(By.xpath("//span[@title='Close']"));
        action.moveToElement(closeButton).click().build().perform();
        //2.14 MouseHover on "Shopping cart" and Click on "GO TO CART" button.
        JavascriptExecutor js = ((JavascriptExecutor) driver);
        js.executeScript("window.scrollTo(0, -document.body.scrollHeight);");
        WebElement shoppingCart = driver.findElement(By.xpath("//span[@class='cart-label' and text() = 'Shopping cart']"));
        action.moveToElement(shoppingCart).click().build().perform();
        js.executeScript("setLocation('/cart')");
        //2.15 Verify the message "Shopping cart
        String expectedMessage = "Shopping cart";
        String actualMessage = getTextFromTheElement(By.xpath("//div[@class='page-title']/h1"));
        Assert.assertEquals("User is not in shopping cart ", expectedMessage, actualMessage);
        //2.16 Change the Qty to "2" and Click on "Update shopping cart
        clearText(By.xpath("//input[contains(@class, 'qty-input')]"));
        Thread.sleep(1000);
        sendTextToElement(By.className("qty-input"), "2");
        Thread.sleep(2000);
        clickOnElement(By.name("updatecart"));
        //2.17 Verify the Total"$2,950.00"
        String expectedTotal = "$2,950.00";
        String actualTotal = getTextFromTheElement(By.xpath("//span[@class='value-summary']//strong"));
        Assert.assertEquals("Total price does not match!", expectedTotal, actualTotal);
        //2.18 click on checkbox “I agree with the terms of service
        clickOnElement(By.id("termsofservice"));
        //2.19 Click on “CHECKOUT”
        clickOnElement(By.id("checkout"));
        //2.20 Verify the Text “Welcome, Please Sign In!
        String expected1 = "Welcome, Please Sign In!";
        String actual1 = getTextFromTheElement(By.xpath("//h1"));
        Assert.assertEquals("Card not get update", expected1, actual1);
        //2.21
        clickOnElement(By.xpath("//button[contains(text(),'Checkout as Guest')]"));
        Thread.sleep(2000);
        //2.22 fill all the mandatory field
        sendTextToElement(By.id("BillingNewAddress_FirstName"), "Priyanka");
        sendTextToElement(By.id("BillingNewAddress_LastName"), "Korat");
        sendTextToElement(By.id("BillingNewAddress_Email"), "sanganipriyanka8@gmail.com");
        sendTextToElement(By.id("BillingNewAddress_Company"), "Prime");
        selectByTextFromDropDown(By.id("BillingNewAddress_CountryId"), "Australia");
        sendTextToElement(By.id("BillingNewAddress_City"), "Sydney");
        sendTextToElement(By.id("BillingNewAddress_Address1"), "Schofield");
        sendTextToElement(By.id("BillingNewAddress_ZipPostalCode"), "201514");
        sendTextToElement(By.id("BillingNewAddress_PhoneNumber"), "+0404621478");
        Thread.sleep(2000);
        //2.23 Click on “CONTINUE”
        clickOnElement(By.name("save"));
        //2.24 click on radio button "Next day Air()$0.00.
        clickOnElement(By.xpath("//label[normalize-space()='Next Day Air ($0.00)']"));
        //2.25 Click on “CONTINUE”
        clickOnElement(By.xpath("//button[@class='button-1 shipping-method-next-step-button']"));
        Thread.sleep(2000);
        //2.26 Select Radio Button “Credit Card”
        clickOnElement(By.id("paymentmethod_1"));
        Thread.sleep(2000);
        //2.27 Select “Master card” From Select credit card dropdown
        clickOnElement(By.xpath("//button[@class='button-1 payment-method-next-step-button']"));
        //2.28 Fill all the details
        selectByTextFromDropDown(By.id("CreditCardType"), "Master card");
        sendTextToElement(By.id("CardholderName"), "Priyanka korat");
        sendTextToElement(By.id("CardNumber"), "0000 0000 0000 0000");
        selectByTextFromDropDown(By.id("ExpireMonth"), "12");
        selectByTextFromDropDown(By.id("ExpireYear"), "2027");
        sendTextToElement(By.id("CardCode"), "5623");
        //2.29 Click on “CONTINUE”
        clickOnElement(By.xpath("//button[@class='button-1 payment-info-next-step-button']"));
        //2.30 Verify “Payment Method” is “Credit Card”
        String expectedText2 = "Payment Method: Credit Card";
        String actualText2 = getTextFromTheElement(By.xpath("//li[@class='payment-method']"));
        Assert.assertEquals("payment method not selected ", expectedText2, actualText2);
        //2.32 Verify “Shipping Method” is “Next Day Air”
        String expectedText3 = "Shipping Method: Next Day Air";
        String actualText3 = getTextFromTheElement(By.xpath("//li[@class='shipping-method']"));
        Assert.assertEquals(expectedText3, actualText3);
        //2.33 Verify Total is “$2,950.00”
        String expectedText4 = "$2,950.00";
        String actualText4 = getTextFromTheElement(By.xpath("//span[@class='value-summary']//strong[contains(text(),'$2,950.00')]"));
        Assert.assertEquals(expectedText4, actualText4);
        //2.34 Click on “CONFIRM”
        clickOnElement(By.xpath("//button[normalize-space()='Confirm']"));
        //2.35 Verify the Text “Thank You”
        String expectedText5 = "Thank you";
        String actualText5 = getTextFromTheElement(By.xpath("//h1[normalize-space()='Thank you']"));
        Assert.assertEquals(expectedText5, actualText5);
        //2.36 Verify the message “Your order has been successfully processed!”
        String expectedText6 = "Your order has been successfully processed!";
        String actualText6 = getTextFromTheElement(By.xpath("//strong[normalize-space()='Your order has been successfully processed!']"));
        Assert.assertEquals(expectedText6, actualText6);
        //2.37 Click on “CONTINUE”
        clickOnElement(By.xpath("//button[normalize-space()='Continue']"));
        //2.37 Verify the text “Welcome to our store”
        String expectedText7 = "Welcome to our store";
        String actualText7 = getTextFromTheElement(By.xpath("//h2[normalize-space()='Welcome to our store']"));
        Assert.assertEquals(expectedText7, actualText7);
    }

    @After
    public void tearDown() {
        //closeBrowser();
    }
}
