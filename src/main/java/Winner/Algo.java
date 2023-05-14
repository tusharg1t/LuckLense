package Winner;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.tools.JavaFileManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.netty.util.internal.MathUtil;

public class Algo {
	private DecimalFormat df = new DecimalFormat("0.00");
	public void run(Data data, WebDriver driver) {
		driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String main_seq = data.main_seq;
		int win_count = data.main_W;
		int lost_count = data.main_L;
		double wallet = data.wallet_together;
		double displacement = data.displacement;

		double wallet_max = data.wallet_max;
		double wallet_min = data.wallet_min;
		Bookie placer = new Bookie();
		boolean bonus_placed = true;
		int ct_betters;
		int t_betters;
		try {	
			
			
			String timer = "";
			String current_coin = "";
			boolean update = false;
			String ct_count = "";
			String t_count = "";
			
			String ct_amount = "";
			String t_amount = "";
			String predicted = "";
			Map<String, Integer> ct_name_rank_map = new HashMap<String,Integer>();
			Map<String, Integer> t_name_rank_map = new HashMap<String,Integer>();
			
			while(true) {
				
				
					
				WebElement count_down_element = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[2]/div[3]/div/div[2]"));
				timer=count_down_element.getAttribute("innerText");
				
				
				if(Double.parseDouble(timer) < 17 && Double.parseDouble(timer)>10 && !bonus_placed) {
					bonus_placed = true;
					
					
					List<WebElement> current_coin_elements = driver.findElements(By.xpath("//div[@class='previous-rolls-item']"));
					current_coin=current_coin_elements.get(9).getAttribute("innerHTML").contains("-ct")?"ct":
						current_coin_elements.get(9).getAttribute("innerHTML").contains("-t")?"t":"bonus";
				
					if(current_coin.equals("ct")) {
						// set the winners as target
						for(String x : ct_name_rank_map.keySet()) {
							data.targets.putIfAbsent(x, 0);
							data.targets.replace(x, data.targets.get(x)+1);
						}
						//remove target from loosers
						for(String x : t_name_rank_map.keySet()) {
							data.targets.putIfAbsent(x, 0);
							data.targets.replace(x, 0);
						}
					}else
					if(current_coin.equals("t")) {
						// set the winners as target
						for(String x : t_name_rank_map.keySet()) {
							data.targets.putIfAbsent(x, 0);
							data.targets.replace(x, data.targets.get(x)+1);
						}
						//remove target from loosers
						for(String x : ct_name_rank_map.keySet()) {
							data.targets.putIfAbsent(x, 0);
							data.targets.replace(x, 0);
						}
					}
					
					System.out.println("\n\n\n\n\n\n\n\n\n\n\n\n\n\nMap Updated ");
					// clear player maps
					t_name_rank_map.clear();
					ct_name_rank_map.clear();
				}
				
				if(Double.parseDouble(timer)<5 && Double.parseDouble(timer)>0 && !update) {
					
				WebElement ct_overview = driver.findElement(By.xpath("/html/body/div/div[1]/div[3]/div/div/div[1]/div[2]/div/div[6]/div[1]/div/div[1]/div[1]"));
				WebElement t_overview = driver.findElement(By.xpath("/html/body/div/div[1]/div[3]/div/div/div[1]/div[2]/div/div[6]/div[3]/div/div[1]/div[1]"));
				
				t_betters = Integer.parseInt(t_overview.getText().toString().split(" ")[0]);
				ct_betters = Integer.parseInt(ct_overview.getText().toString().split(" ")[0]);
					
					if(!predicted.equals("") && data.pitstop <= 0) {
						if(predicted.equals(current_coin)) {
							System.out.println("Won");
							main_seq+="W";
							win_count++;
							wallet += data.multiplier;
							
						}else {
							
							System.out.println("Lost ");
							main_seq+="L";
							lost_count++;
							wallet -= data.multiplier;
						}
						
						int continuous = (new Algo()).countOfCharFromLast(main_seq,'L');
						if(continuous>0)
						data.expoPR.replace(continuous, data.expoPR.get(continuous)+1);
						
						continuous = (new Algo()).countOfCharFromLast(main_seq,'W');
						if(continuous>0)
						data.expoPR.replace(continuous, data.expoPR.get(continuous)+1);
						
						
					}
					
					
					
					
					if(wallet > wallet_max)
						wallet_max = wallet;
					
					if(wallet < wallet_min)
						wallet_min = wallet;
					
					if(wallet_max - wallet > displacement)
							displacement = wallet_max - wallet;
					
					
					
					WebElement t_bet_container = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[6]/div[3]/div/div[2]/div"));
					String[] t_players = t_bet_container.getAttribute("innerText").trim().split("\n");
					WebElement ct_bet_container = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[6]/div[1]/div/div[2]/div"));
					String[] ct_players = ct_bet_container.getAttribute("innerText").trim().split("\n");
					int ct_xp = 0;
					int t_xp = 0;
					double amount_ct = 0;
					double amount_t = 0;
					for(int i = 0 ; i < ct_players.length ; i+=3) {
//													   <Name,Rank>
						ct_name_rank_map.put(ct_players[i+1], Integer.parseInt(ct_players[i]));
						ct_xp+=Integer.parseInt(ct_players[i]);
						
					}
					
					for(int i = 0 ; i < t_players.length ; i+=3) {
						t_name_rank_map.put(t_players[i+1], Integer.parseInt(t_players[i]));
						t_xp+=Integer.parseInt(t_players[i]);
					}
					
					
					
					int ct_targets = 0;
					int t_targets = 0;
					
					for(String x : ct_name_rank_map.keySet()) {
						if(data.targets.containsKey(x))
						if(data.targets.get(x) > 0)
							ct_targets+=data.targets.get(x);
					}
					
					for(String x : t_name_rank_map.keySet()) {
						if(data.targets.containsKey(x))
						if(data.targets.get(x) > 0)
							t_targets+=data.targets.get(x);
					}
					
//					if(wallet>(35*data.multiplier) || wallet <= (-80*data.multiplier)) {
//						System.out.println("Closing Balance : "+ wallet);
//						
//						break;
//					}
					
					// && (Math.abs( ct_xp - t_xp) > (Math.max(ct_xp, t_xp)/4))
					if(data.pitstop--<=0 ) {
						if( t_targets > ct_targets ) {
							predicted = "ct";
						}else
							predicted = "t";
						
						
						placer.placeBet(driver, predicted , data.multiplier);
						
					}else {
						predicted = "";
					}
					
				
					
					
					if(predicted.equals("")) {
						System.out.println(data.expoPR.toString());
					}else {
						System.out.println(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
						System.out.println("Total betters = "+(t_betters+ct_betters));
						System.out.println("ct_targets : "+ct_targets);
						System.out.println("t_targets : "+t_targets);
						System.out.println("Predicted : "+predicted);
						System.out.println("W : L Ratio = "+win_count + " : "+lost_count);
						System.out.println("Sequence : "+ main_seq);
						System.out.println("Wallet : "+ df.format(wallet));
						System.out.println("Displacement : "+df.format(displacement));
						System.out.println("Wallet Max : "+df.format( wallet_max));
						System.out.println("Wallet Min : "+df.format( wallet_min));
						System.out.println(data.expoPR.toString());
					}
					
					update=true;
					
				}else
					if(Double.parseDouble(timer)==0.00) {
						update=false;
						bonus_placed = false;
					}
				
			}
		}catch(Exception e) {
			Throwable underlyingException = e.getCause();
            underlyingException.printStackTrace();
			data.displacement = displacement;
			data.main_seq = main_seq;
			data.main_W = win_count;
			data.main_L = lost_count;
			data.wallet_together = wallet;
			data.wallet_max = wallet_max;
			data.wallet_min = wallet_min;
			new Algo().run(data,driver);
			
		}
		
	}
	
	
	public int countOfCharFromLast(String toCheckIn, char toCheck) {
		int cnt = 0;
			for(int i = toCheckIn.length()-1 ; i >= 0 ; i--) {
				if(toCheckIn.charAt(i) == toCheck) {
					cnt++;
				}else
					break;
			}
		return cnt;
	}
}
