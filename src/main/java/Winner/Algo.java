package Winner;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.tools.JavaFileManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.netty.util.internal.MathUtil;

public class Algo {
	public void run(Data data, WebDriver driver) {
		driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String main_seq = data.main_seq;
		int win_count = data.main_W;
		int lost_count = data.main_L;
		try {	
			
			
			String timer = "";
			String current_coin = "";
			boolean update = false;
			String ct_count = "";
			String t_count = "";
			
			String ct_amount = "";
			String t_amount = "";
			String predicted = "";
			
			while(true) {
				
				
				WebElement count_down_element = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[2]/div[3]/div/div[2]"));
				timer=count_down_element.getAttribute("innerText");
				double time_double = Double.parseDouble(timer);
				
				if(Double.parseDouble(timer)<5 && Double.parseDouble(timer)>0 && update==false) {
					
					
					List<WebElement> current_coin_elements = driver.findElements(By.xpath("//div[@class='previous-rolls-item']"));
					current_coin=current_coin_elements.get(9).getAttribute("innerHTML").contains("-ct")?"ct":
						current_coin_elements.get(9).getAttribute("innerHTML").contains("-t")?"t":"bonus";
				
				
					if(predicted.equals(current_coin)) {
						System.out.println("Won");
						main_seq+="W";
						win_count++;
					}else {
						System.out.println("Lost ");
						main_seq+="L";
						lost_count++;
					}
					
					Map<String, Integer> ct_name_rank_map = new HashMap<String,Integer>();
					Map<String, Integer> t_name_rank_map = new HashMap<String,Integer>();
					
					
					WebElement t_bet_container = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[6]/div[3]/div/div[2]"));
					String[] t_players = t_bet_container.getAttribute("innerText").trim().split("\n");
					
					WebElement ct_bet_container = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[6]/div[1]/div/div[2]"));
					String[] ct_players = ct_bet_container.getAttribute("innerText").trim().split("\n");
					int ct_xp = 0;
					int t_xp = 0;
					for(int i = 0 ; i < ct_players.length ; i+=3) {
						ct_name_rank_map.put(ct_players[i+1], Integer.parseInt(ct_players[i]));
						ct_xp+=Integer.parseInt(ct_players[i]);
					}
					
					for(int i = 0 ; i < t_players.length ; i+=3) {
						t_name_rank_map.put(t_players[i+1], Integer.parseInt(t_players[i]));
						t_xp+=Integer.parseInt(t_players[i]);
					}
					
					if(Math.min(ct_xp, t_xp) * 1.5 > Math.max(ct_xp, t_xp))
					if(ct_xp > t_xp )
						predicted = "ct";
					else
						predicted = "t";
					else
						if(ct_xp < t_xp )
							predicted = "ct";
						else
							predicted = "t";
							
					
					System.out.println("\n\n\n\nt_xp : "+ct_xp);
					System.out.println("t_xp : "+t_xp);
					System.out.println("Predicted : "+predicted);
					System.out.println("W : L Ratio = "+win_count + " : "+lost_count);
					System.out.println("Sequence : "+ main_seq);
						
					update=true;
					
				}else
					if(Double.parseDouble(timer)==0.00) {
						update=false;
					}
				
			}
		}catch(Exception e) {
			System.out.println("Supreme Refresh");
			data.main_seq = main_seq;
			data.main_W = win_count;
			data.main_L = lost_count;
			new Algo().run(data,driver);
		}
		finally{
			driver.close();
		}
	}
	
	

}
