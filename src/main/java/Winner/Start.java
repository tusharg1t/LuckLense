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
		System.out.println("Before Prod:4.2: Dont Waste Money! Earn Everything! JAI SHREE RAAM|| Perfect || OM NAMAH SHIVAI || JAI MATA JI || OM MAHA LAKSHMI NAMO NAMAH || OOOOOM || Om Aim Hreem Shreem Vagdevyai sarasvatyai Namah || Oh Rahawahe Namaha");
		
		Data data = new Data();
		data.multiplier = sc.nextDouble();
		new Algo().run(data, driver);
	}

}
