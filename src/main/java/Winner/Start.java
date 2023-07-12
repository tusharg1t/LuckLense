package Winner;

import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import Winner.Data;
public class Start {

	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--incognito");
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);
		Scanner sc = new Scanner(System.in);
		driver.get("https://csgoempire.com");
	
		
		Data data = new Data();
		data.multiplier = sc.nextDouble();
		System.out.println("ALL THE BEST: OM HRIM AIM HRIM OM SARASWATE NAMAH:  ");
		new Algo().run(data, driver);
	}

}
