package Winner;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
		double multiplier = sc.nextDouble();
		data.multiplier = multiplier;
//		LoginService.LogIn(driver,"csgoempire_september", "L@k$hm!V!shnu_2023");
		data = new Algo().run(data, driver);
		
		int round = 1;
		while(true) {
			
			if(data.global_wallet <= 0)
				multiplier = data.multiplier * 0.5;
			else
				multiplier = data.multiplier*1.5;
			
			multiplier = Math.floor(multiplier * 100) / 100;
			String result = data.global_win_cnt > data.global_loss_cnt ? "WON":"LOST";
			String fileName = "Round"+(round++)+"_"+result+".txt";
			FileWriter fileWriter;
			try {
				
				fileWriter = new FileWriter(fileName);
				PrintWriter printWriter = new PrintWriter(fileWriter);
			    printWriter.print(data.toString());
			    printWriter.close();
			    Thread.sleep(100000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Data newData = new Data();
			newData.multiplier = multiplier;
			data = new Algo().run(newData, driver);
		}
//		driver.close();
	}

}
