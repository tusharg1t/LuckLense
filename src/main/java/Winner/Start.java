package Winner;

import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import Winner.Data;
public class Start {

	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--incognito");
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--browserName=chrome on windows");
		options.addArguments("--user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0.0.0 Safari/537.36");
		options.addArguments("--headless");
		options.addArguments("--window-size=1920,1080");
		options.addArguments("--mute-audio");
		WebDriver driver = new ChromeDriver(options);
		Scanner sc = new Scanner(System.in);
		driver.get("https://csgoempire.com");
		System.out.print("Enter bet_amount : ");
		
		Data data = new Data();
		data.multiplier = sc.nextDouble();
//		LoginService.LogIn(driver,"csgoempire_september", "L@k$hm!V!shnu_2023");
		new Algo().run(data, driver);
		driver.close();
	}

}
