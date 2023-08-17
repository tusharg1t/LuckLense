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
		System.out.println("UAT BOT 10 Threashold");
		System.out.println("Om Shanti || Om Shanti || Om Shanti "
				+ "\n| Win | Win | Win || "
				+ "\n|| Profit || Profit || Profit | "
				+ "\n| StopLoss |StopLoss | StopLoss");
		
		Data data = new Data();
		data.multiplier = sc.nextDouble();
		new Algo().run(data, driver);
	}

}
