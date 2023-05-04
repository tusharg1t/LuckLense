package Winner;

import java.util.Scanner;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class Start {

	public static void main(String[] args) {
		ChromeOptions options = new ChromeOptions();
		options.addArguments("start-maximized");
		options.addArguments("--incognito");
		options.addArguments("--remote-allow-origins=*");
		WebDriver driver = new ChromeDriver(options);
		Scanner sc = new Scanner(System.in);
		driver.get("https://csgoempire.com");
		System.out.println("5+3 :: Colateral = 1022 :: SKIP SWITCH");
		System.out.println("Please Enter Your Capability : ");
		double multiplier = sc.nextDouble();
		Data data = new Data();
		data.multiplier = multiplier;
		new Algo().run(data, driver);
	}

}
