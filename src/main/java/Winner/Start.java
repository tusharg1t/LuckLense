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
		System.out.println("WE WIN BY NOT REPEATING MISTAKES:"
				+ "\n\t1.\tDon't Gamble on your own!"
				+ "\n\t2.\tDon't bet exponential"
				+ "\n\t3.\tDon't put any more money into it, it will give you back just hold"
				+ "\n\t4.\tYou have enough time, it will work when time and effort is right."
				+ "\n\t5.\tKeep Improving the algorithm");
		System.out.print("Enter bet_amount : ");
		
		Data data = new Data();
		data.multiplier = sc.nextDouble();
		new Algo().run(data, driver);
		driver.close();
	}

}
