package Winner;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginService {
	public static void LogIn(WebDriver driver, String username, String password) {
		//click on Signin on main page
		//*[@id="empire-header"]/div[1]/div/div[3]/div[2]/div[3]/button
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"empire-header\"]/div[1]/div/div[3]/div[2]/div[3]/button")));
		
		WebElement loginButton = driver.findElement(By.xpath("//*[@id=\"empire-header\"]/div[1]/div/div[3]/div[2]/div[3]/button"));
        loginButton.click();
        System.out.println(loginButton);
        
		//put credentials can click sign in
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"responsive_page_template_content\"]/div[1]/div[1]/div/div/div/div[2]/div/form/div[4]/button")));
		
        WebElement usernameField = driver.findElement(By.xpath("//*[@id=\"responsive_page_template_content\"]/div[1]/div[1]/div/div/div/div[2]/div/form/div[1]/input"));
        WebElement passwordField = driver.findElement(By.xpath("//*[@id=\"responsive_page_template_content\"]/div[1]/div[1]/div/div/div/div[2]/div/form/div[2]/input"));

        usernameField.sendKeys(username);
        passwordField.sendKeys(password);

        WebElement signInButton = driver.findElement(By.xpath("//*[@id=\"responsive_page_template_content\"]/div[1]/div[1]/div/div/div/div[2]/div/form/div[4]/button"));
        signInButton.click();
        
        
		//click on signin again on page after cred intake page
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"imageLogin\"]")));
        WebElement signInButton2 = driver.findElement(By.xpath("//*[@id=\"imageLogin\"]"));
        signInButton2.click();
        
        
        //wait until page loads
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div/div/div[1]/div[2]/div/div[1]/button")));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div/div/div[1]/div[2]/div/div[2]/div[3]/div/div[2]")));
	}
	
}
