package Winner;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Bookie {
	
	boolean placeBet(WebDriver driver, String side, double amount) {
		try {
		WebElement input = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[4]/div/div[1]/input"));
		WebElement ct = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[5]/div[1]/button"));
		WebElement t = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[5]/div[3]/button"));
		if((amount) != 0) {
			input.clear();
			input.sendKeys(Double.toString(amount));
			
//			if(side.equals("ct"))
//				ct.click();
//			else
//				t.click();
			
			input.clear();
		}
		
			return false;
		}catch(Exception e) {
			driver.navigate().refresh();
			
			return true;
		}
	}	
	

}
