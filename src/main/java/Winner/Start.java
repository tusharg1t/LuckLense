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
		System.out.println("PROD BOT V2"
				+ "\nYou will gain the target capital || You will gain the target capital || You will regain the target capital"
				+ "\nYou will not default || You will not default || You will not default"
				+ "\nTushar You will be rich || Tushar you will be rich || Tushar you will be rich"
				+ "\nTushar you will be dependable || Tushar you will be dependable || Tushar you will be dependable"
				+ "\nYou Will Not Break | You will not baeak | You will not break");
		System.out.println("Om Shantihi || Om Shantihi || Om Shantihi "
				+ "\n| Win | Win | Win || "
				+ "\n|| Profit || Profit || Profit | "
				+ "\n| StopLoss |StopLoss | StopLoss"
				+ "\nPranaam Maa"
				+ "\nSHAKTI | SARSWATI | LAXMI"
				+ "\n");
		
		Data data = new Data();
		data.multiplier = sc.nextDouble();
		new Algo().run(data, driver);
	}

}
