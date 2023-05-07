package Winner;

import java.text.DecimalFormat;
import java.util.List;
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
		int g_count=data.g_count;
		int g_amount=data.g_amount;
		int needToSleep = 0;
		int l_count=data.l_count;
		int l_amount=data.l_amount;
		int committ_win = data.committ_win;
		int committ_lost = data.committ_lost;
		String count_seq = data.count_seq;
		String amount_seq= data.amount_seq;
		String outcome_seq = data.outcome_seq;
		int skip_beat_counter = data.skip_beat_counter;
		boolean doToggle = data.doToggle;
		String helper_seq = data.helper_seq;
		boolean was_bet_placed = data.was_bet_placed;

		String committ_seq = data.committ_seq;
		String committ_side = data.committ_side;
		String main_seq= data.main_seq;
		float wallet_together=data.wallet_together;
		float wallet_together_min=data.wallet_together_min;
		float wallet_together_max=data.wallet_together_max;
		
		int max_g_continuous=data.max_g_continuous;
		int max_l_continuous=data.max_l_continuous;
		double goal_amount=data.goal_amount;
		boolean isRefreshed = data.isRefreshed;
		int cnt_continuous_cnt = data.cnt_continuous_cnt;
		int cnt_switcher_cnt = data.cnt_switcher_cnt;
		
		int amt_continuous_cnt = data.amt_continuous_cnt;
		int amt_switcher_cnt = data.amt_switcher_cnt;
		Scanner sc = new Scanner(System.in);
		int win_counter = data.win_counter;
		int number_of_ones_count=data.number_of_ones_count;
		int number_of_twos_count=data.number_of_twos_count;
		int number_of_threes_count=data.number_of_threes_count;
		int number_of_fours_count=data.number_of_fours_count;
		int number_of_fives_count=data.number_of_fives_count;
		int number_of_worst_cases_count=data.number_of_worst_cases_count;
		
		int number_of_ones_amount=data.number_of_ones_amount;
		int number_of_twos_amount=data.number_of_twos_amount;
		int number_of_threes_amount=data.number_of_threes_amount;
		int number_of_fours_amount=data.number_of_fours_amount;
		int number_of_fives_amount=data.number_of_fives_amount;
		int number_of_worst_cases_amount=data.number_of_worst_cases_amount;
		
		int number_of_ones_main=data.number_of_ones_main;
		int number_of_twos_main=data.number_of_twos_main;
		int number_of_threes_main=data.number_of_threes_main;
		int number_of_fours_main=data.number_of_fours_main;
		int number_of_fives_main=data.number_of_fives_main;
		int number_of_sixes_main=data.number_of_sixes_main;
		int number_of_sevens_main=data.number_of_sevens_main;
		int number_of_eights_main=data.number_of_eights_main;
		int number_of_nines_main=data.number_of_nines_main;
		int number_of_tens_main=data.number_of_tens_main;
		int number_of_worst_cases_main=data.number_of_worst_cases_main;
		
		int continuity_counter_count = data.continuity_counter_count;
		int continuity_counter_amount = data.continuity_counter_amount;
		int continuity_counter_main = data.continuity_counter_main;
		
		int main_L = data.main_L;
		int main_W = data.main_W;
		
		int main_count_g = data.main_count_g;
		int main_count_l = data.main_count_l;
		int main_win_cause_g = data.main_win_cause_g;
		int main_win_cause_l = data.main_win_cause_l;
		
		String main_checker = data.main_checker;
		String prediction_final="";
		boolean halt = false;
		boolean was_onhalt = false;
		boolean toggle = false;
		double bet_amount = 1;
		try {
//			WebElement account_amount = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[1]/div/div/span/span/div/span"));
			
			int loss=0;
			String outcome = "";
			String next="";
			boolean printed=false;
			int count_E=0;
			int count_O=0;
			
			int amount_E=0;
			int amount_O=0;
			
			String current_coin="";
			String ct_count="";
			String t_count="";

			String ct_amount="";
			String t_amount="";
			String predict_amount="";
			String predict_count="";
			String timer="";
			String wallet_prediction = "";
			boolean update=false;
			int bonus_counter = 0;
			
//			ct_count=ct_count_element.getAttribute("innerText").trim().split(" ")[0];
//			t_count=t_count_element.getAttribute("innerText").trim().split(" ")[0];
//			
//
//			ct_amount=ct_amount_element.getAttribute("innerText").replace(",", "");
//			t_amount=t_amount_element.getAttribute("innerText").replace(",", "");
//			
			String dynamic_predict="";
			String together_predict="";
			String dynamic_together_predict="";
			Bookie coingobbler = new Bookie();
			
			String prediction_type_G_or_L = "L";
			String fixed_betsOn = "";
			while(true) {
				
				
				WebElement count_down_element = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[2]/div[3]/div/div[2]"));
				timer=count_down_element.getAttribute("innerText");
				
				if(Double.parseDouble(timer)<15 && Double.parseDouble(timer)>0 && update==false) {
					
					if(wallet_together >= 1000*data.multiplier) {
						driver.close();
						break;
					}
						
					
						List<WebElement> current_coin_elements = driver.findElements(By.xpath("//div[@class='previous-rolls-item']"));
						current_coin=current_coin_elements.get(9).getAttribute("innerHTML").contains("-ct")?"ct":
							current_coin_elements.get(9).getAttribute("innerHTML").contains("-t")?"t":"bonus";
					
					
					
					if(current_coin.equals(predict_count)) {
//						wallet_together-=1.5;
						count_seq+="G";
						g_count++;
					}
					else
					if(current_coin.equals("bonus")) {
						count_seq+="B";
					
						bonus_counter++;
					}
					else {
						
						count_seq+="L";
						l_count++;
					}
					
					
					
					
					if(!prediction_final.equals(""))
					if(current_coin.equals(prediction_final)) {
						
						main_seq+="W";
						
						helper_seq += "W";
						
					
						main_W++;
						
						if(main_checker.equals("G")){
							main_win_cause_g++;
						}else
						if(main_checker.equals("L"))
							main_win_cause_l++;
					}else {
						
						
						main_seq+="L";
						
						
						helper_seq += "L";
						
						
						main_L++;
						
						if(main_checker.equals("G")) {
							main_count_g++;
						}else
							if(main_checker.equals("L")) {
								main_count_l++;
							}
					}
					
					if(!committ_side.equals("")) {
						
					if(current_coin.equals(committ_side) ) {
						wallet_together += bet_amount;
						committ_seq += "W";
						committ_win++;
					}else {
						wallet_together -= bet_amount;
						committ_seq += "L";
						committ_lost++;
					}
					
					}	
					
					
					if(current_coin.equals(predict_amount)) {
						amount_seq+="G";
						g_amount++;
					}
					else
					if(current_coin.equals("bonus")) {
						amount_seq+="B";
						bonus_counter++;
					}
					else {
						amount_seq+="L";
						l_amount++;
					}
					
					if(amount_seq.length()>3)
					if(amount_seq.charAt(amount_seq.length()-2)==amount_seq.charAt(amount_seq.length()-1)||amount_seq.charAt(amount_seq.length()-1)=='B'||amount_seq.charAt(amount_seq.length()-2)=='B') {
						continuity_counter_amount++;
					}else {
						if(continuity_counter_amount==1)
							number_of_ones_amount++;
						else
							if(continuity_counter_amount==2)
								number_of_twos_amount++;
							else
								if(continuity_counter_amount==3)
									number_of_threes_amount++;
								else
									if(continuity_counter_amount==4)
										number_of_fours_amount++;
									else
										if(continuity_counter_amount==5)
											number_of_fives_amount++;
										else
									if(continuity_counter_amount>5)
										number_of_worst_cases_amount++;
						
						continuity_counter_amount=1;
									
					}
					
					
					
					
					if(committ_seq.length()>3 && !prediction_final.equals("") && was_bet_placed)
						if(committ_seq.charAt(committ_seq.length()-2)==committ_seq.charAt(committ_seq.length()-1) && committ_seq.charAt(committ_seq.length()-1) == 'L') {
							continuity_counter_main++;
							
						}else
						{
							
							if(continuity_counter_main==1 && committ_seq.charAt(committ_seq.length()-2)=='L')
								number_of_ones_main++;
							else
								if(continuity_counter_main==2 && committ_seq.charAt(committ_seq.length()-2)=='L')
									number_of_twos_main++;
								else
									if(continuity_counter_main==3 && committ_seq.charAt(committ_seq.length()-2)=='L')
										number_of_threes_main++;
									else
										if(continuity_counter_main==4 && committ_seq.charAt(committ_seq.length()-2)=='L')
											number_of_fours_main++;
										else
											if(continuity_counter_main==5 && committ_seq.charAt(committ_seq.length()-2)=='L')
												number_of_fives_main++;
											else
												if(continuity_counter_main==6 && committ_seq.charAt(committ_seq.length()-2)=='L')
													number_of_sixes_main++;
												else
													if(continuity_counter_main==7 && committ_seq.charAt(committ_seq.length()-2)=='L')
														number_of_sevens_main++;
													else
														if(continuity_counter_main==8 && committ_seq.charAt(committ_seq.length()-2)=='L')
															number_of_eights_main++;
														else
															if(continuity_counter_main==9 && committ_seq.charAt(committ_seq.length()-2)=='L')
																number_of_nines_main++;
															else
																if(continuity_counter_main==10 && committ_seq.charAt(committ_seq.length()-2)=='L')
																	number_of_tens_main++;
																else
																	if(continuity_counter_main>10)
																		number_of_worst_cases_main++;
							
								
							if(continuity_counter_main > 8) {
								win_counter = 5000;
							}
							
							if(continuity_counter_main >= 6) {
								if(toggle)
									toggle = false;
								else
									toggle = true;
								System.out.println("\n==============Skipping the Switch================\n\n");
							}
								
									continuity_counter_main = 1;
							
						}
					
					if(
							committ_seq.charAt(committ_seq.length()-1) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-4) == 'L'
						&& committ_seq.charAt(committ_seq.length()-5) == 'L'
						&& committ_seq.charAt(committ_seq.length()-6) == 'L'
						&& committ_seq.charAt(committ_seq.length()-7) == 'L'
						&& committ_seq.charAt(committ_seq.length()-8) == 'L'
						&& committ_seq.charAt(committ_seq.length()-9) == 'L'
						&& committ_seq.charAt(committ_seq.length()-10) == 'L'
						&& committ_seq.charAt(committ_seq.length()-11) == 'L'
						&& committ_seq.charAt(committ_seq.length()-12) == 'L'
						&& committ_seq.charAt(committ_seq.length()-13) == 'L'
						&& main_seq.charAt(committ_seq.length()-14) == 'L'
						&& committ_seq.charAt(committ_seq.length()-15) == 'L'
						){
								bet_amount = 0;
					}else
					if(
							committ_seq.charAt(committ_seq.length()-1) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-2) == 'L'  
						&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-4) == 'L'
						&& committ_seq.charAt(committ_seq.length()-5) == 'L'
						&& committ_seq.charAt(committ_seq.length()-6) == 'L'
						&& committ_seq.charAt(committ_seq.length()-7) == 'L'
						&& committ_seq.charAt(committ_seq.length()-8) == 'L'
						&& committ_seq.charAt(committ_seq.length()-9) == 'L'
						&& committ_seq.charAt(committ_seq.length()-10) == 'L'
						&& committ_seq.charAt(committ_seq.length()-11) == 'L'
						&& committ_seq.charAt(committ_seq.length()-12) == 'L'
						&& committ_seq.charAt(committ_seq.length()-13) == 'L'
						&& committ_seq.charAt(committ_seq.length()-14) == 'L'
						){
								bet_amount = 0;
					}else
					if(
							committ_seq.charAt(committ_seq.length()-1) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-4) == 'L'
						&& committ_seq.charAt(committ_seq.length()-5) == 'L'
						&& committ_seq.charAt(committ_seq.length()-6) == 'L'
						&& committ_seq.charAt(committ_seq.length()-7) == 'L'
						&& committ_seq.charAt(committ_seq.length()-8) == 'L'
						&& committ_seq.charAt(committ_seq.length()-9) == 'L'
						&& committ_seq.charAt(committ_seq.length()-10) == 'L'
						&& committ_seq.charAt(committ_seq.length()-11) == 'L'
						&& committ_seq.charAt(committ_seq.length()-12) == 'L'
						&& committ_seq.charAt(committ_seq.length()-13) == 'L'
						){
								bet_amount = 0;
					}else
					if(
							committ_seq.charAt(committ_seq.length()-1) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-4) == 'L'
						&& committ_seq.charAt(committ_seq.length()-5) == 'L'
						&& committ_seq.charAt(committ_seq.length()-6) == 'L'
						&& committ_seq.charAt(committ_seq.length()-7) == 'L'
						&& committ_seq.charAt(committ_seq.length()-8) == 'L'
						&& committ_seq.charAt(committ_seq.length()-9) == 'L'
						&& committ_seq.charAt(committ_seq.length()-10) == 'L'
						&& committ_seq.charAt(committ_seq.length()-11) == 'L'
						&& committ_seq.charAt(committ_seq.length()-12) == 'L'
						
						){
								bet_amount = 0;
					}else
					if(
							committ_seq.charAt(committ_seq.length()-1) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-4) == 'L'
						&& committ_seq.charAt(committ_seq.length()-5) == 'L'
						&& committ_seq.charAt(committ_seq.length()-6) == 'L'
						&& committ_seq.charAt(committ_seq.length()-7) == 'L'
						&& committ_seq.charAt(committ_seq.length()-8) == 'L'
						&& committ_seq.charAt(committ_seq.length()-9) == 'L'
						&& committ_seq.charAt(committ_seq.length()-10) == 'L'
						&& committ_seq.charAt(committ_seq.length()-11) == 'L'
						
						){
								bet_amount = 0;
					}else
					if(
							committ_seq.charAt(committ_seq.length()-1) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-4) == 'L'
						&& committ_seq.charAt(committ_seq.length()-5) == 'L'
						&& committ_seq.charAt(committ_seq.length()-6) == 'L'
						&& committ_seq.charAt(committ_seq.length()-7) == 'L'
						&& committ_seq.charAt(committ_seq.length()-8) == 'L'
						&& committ_seq.charAt(committ_seq.length()-9) == 'L'
						&& committ_seq.charAt(committ_seq.length()-10) == 'L'
						){
								bet_amount = 0;
					}else
					if(
							committ_seq.charAt(committ_seq.length()-1) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-4) == 'L'
						&& committ_seq.charAt(committ_seq.length()-5) == 'L'
						&& committ_seq.charAt(committ_seq.length()-6) == 'L'
						&& committ_seq.charAt(committ_seq.length()-7) == 'L'
						&& committ_seq.charAt(committ_seq.length()-8) == 'L'
						&& committ_seq.charAt(committ_seq.length()-9) == 'L'
						){
								bet_amount = 0;
					}else
					if(
							committ_seq.charAt(committ_seq.length()-1) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-4) == 'L'
						&& committ_seq.charAt(committ_seq.length()-5) == 'L'
						&& committ_seq.charAt(committ_seq.length()-6) == 'L'
						&& committ_seq.charAt(committ_seq.length()-7) == 'L'
						&& committ_seq.charAt(committ_seq.length()-8) == 'L'
						){
								bet_amount = 0 ;
					}
					else 
						if(
								committ_seq.charAt(committ_seq.length()-1) == 'L' 
							&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
							&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
							&& committ_seq.charAt(committ_seq.length()-4) == 'L'
							&& committ_seq.charAt(committ_seq.length()-5) == 'L'
							&& committ_seq.charAt(committ_seq.length()-6) == 'L'
							&& committ_seq.charAt(committ_seq.length()-7) == 'L'
							){
									bet_amount = 0;
						}
					else 
						if(
								committ_seq.charAt(committ_seq.length()-1) == 'L' 
							&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
							&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
							&& committ_seq.charAt(committ_seq.length()-4) == 'L'
							&& committ_seq.charAt(committ_seq.length()-5) == 'L'
							&& committ_seq.charAt(committ_seq.length()-6) == 'L'
							){
									bet_amount = 127;
						}else
					if(
							committ_seq.charAt(committ_seq.length()-1) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-4) == 'L'
						&& committ_seq.charAt(committ_seq.length()-5) == 'L'
						){
								bet_amount = 63;
					}else 
					if(
							committ_seq.charAt(committ_seq.length()-1) == 'L' 
					&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
					&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
					&& committ_seq.charAt(committ_seq.length()-4) == 'L'
					){
							bet_amount = 31;
					}else 
						if(
								committ_seq.charAt(committ_seq.length()-1) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-3) == 'L' 
						){
								bet_amount = 15;
					}else 
						if(
								committ_seq.charAt(committ_seq.length()-1) == 'L' 
						&& committ_seq.charAt(committ_seq.length()-2) == 'L' 
						){
								bet_amount = 7;
					}else 
						if(
								committ_seq.charAt(committ_seq.length()-1) == 'L' 
						){
								bet_amount = 3;
					}else
						bet_amount = 1;
			
					
					if(amount_seq.charAt(amount_seq.length()-1) == amount_seq.charAt(amount_seq.length()-2) || amount_seq.charAt(amount_seq.length()-1)=='B' || amount_seq.charAt(amount_seq.length()-2)=='B')
						amt_continuous_cnt++;
					else
						amt_switcher_cnt++;
					
					if(wallet_together > wallet_together_max)
						wallet_together_max = wallet_together;
					
					if(wallet_together < wallet_together_min)
						wallet_together_min = wallet_together;
				
					
					//perfect stop to died
					if(!committ_side.equals("") ){
						committ_side = "";
					}
					
					WebElement ct_count_element = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[6]/div[1]/div/div[1]/div[1]"));
					WebElement t_count_element = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[6]/div[3]/div/div[1]/div[1]"));
					
					WebElement ct_amount_element = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[6]/div[1]/div/div[1]/div[2]/span/div/span"));
					WebElement t_amount_element = driver.findElement(By.xpath("/html/body/div[1]/div[1]/div[3]/div/div/div[1]/div[2]/div/div[6]/div[3]/div/div[1]/div[2]/span/div/span"));

					
					ct_count=ct_count_element.getAttribute("innerText").trim().split(" ")[0];
					t_count=t_count_element.getAttribute("innerText").trim().split(" ")[0];
					

					ct_amount=ct_amount_element.getAttribute("innerText").replace(",", "");
					t_amount=t_amount_element.getAttribute("innerText").replace(",", "");
					
					//predicting g of count
					if(Double.parseDouble(ct_amount)>Double.parseDouble(t_amount))
						predict_amount="ct";
					else
						predict_amount="t";
					
					if(Integer.parseInt(ct_count)>Integer.parseInt(t_count))
						predict_count="ct";
					else
						predict_count="t";
					

					
					if(count_seq.charAt(count_seq.length()-1)==count_seq.charAt(count_seq.length()-2)) {
						data.smooth_count++;
					}else {
						data.spikey_count++;
					}
					
					if(amount_seq.charAt(amount_seq.length()-1)==amount_seq.charAt(amount_seq.length()-2)) {
						data.smooth_amount++;
					}else {
						data.spikey_amount++;
					}
					
					
					bet_amount *= data.multiplier;
					 DecimalFormat df = new DecimalFormat("###.##");
					bet_amount = Double.parseDouble(df.format(bet_amount));
					
					
					if(committ_seq.charAt(committ_seq.length()-1) == 'W')
						data.isRefreshed = false;
			
					
					if(data.isRefreshed || win_counter <= 0) {
						bet_amount = 0;
					}			
							if( 
									count_seq.charAt(count_seq.length()-1) != 'B'
//							&& predict_amount == predict_count
							&&
							Integer.parseInt(ct_count)+Integer.parseInt(t_count) > 10
							){
						
								double g_ratio = ((double)main_win_cause_g/(double)main_count_g) ;
										double l_ratio = ((double)main_win_cause_l/(double)main_count_l);
									

										
//									 if(doToggle)
									 {
										 if(toggle){
											 //G
											 if(predict_count.equals("ct") ) {
											
												 if( main_seq.length() > 25 ) {
													coingobbler.placeBet(driver, "ct", bet_amount);
													committ_side = "ct";
													win_counter--;
												 }
													prediction_final="ct";
											 }else {
												 if(main_seq.length() > 25) {
													coingobbler.placeBet(driver, "t", bet_amount);
													committ_side = "t";
													win_counter--;
												 }
													prediction_final="t";
											 }
											 
											 toggle = false;
											 main_checker = prediction_type_G_or_L = "G";
										 }else{
											 
											 //L
											 if(predict_count.equals("ct") ) {
												 if(main_seq.length() > 25 ) {
													coingobbler.placeBet(driver, "t", bet_amount);
													committ_side = "t";
													win_counter--;
												 }
													prediction_final="t";
											 }else {
												 if(main_seq.length() > 25 ) {
													coingobbler.placeBet(driver, "ct", bet_amount);
													committ_side = "ct";
													win_counter--;
												 }
													prediction_final="ct";
											 }
											 
											 toggle = true;
											 main_checker = prediction_type_G_or_L = "L";
										 }
									 }
//									 else{
//										 //dont toggle stay on the max one! G or L
//										 String gorl = amount_seq.length() > 90 ? amount_seq.substring(75) : amount_seq;
//										 int g = 0;
//										 int l = 0;
//										 for(int ind = 0 ; ind < gorl.length() ; ind++) {
//											 if(gorl.charAt(ind) == 'L')
//												 l++;
//											 else
//											 if(gorl.charAt(ind) == 'G')
//													 g++;
//										 }
//										 
//										 if(g > l) {
//											 if(predict_amount.equals("ct") ) {
//													
//												 if(win_counter-- > 0 )
//													data.isRefreshed = coingobbler.placeBet(driver, "ct", bet_amount);
//													
//													prediction_final="ct";
//											 }else {
//												 if(win_counter-- > 0 )
//													data.isRefreshed = coingobbler.placeBet(driver, "t", bet_amount);
//													
//													prediction_final="t";
//											 }
//											 
//											 main_checker = prediction_type_G_or_L = "G";
//										 }else {
//											 //go with l
//											 if(predict_amount.equals("ct") ) {
//												 if(win_counter-- > 0 )
//													data.isRefreshed = coingobbler.placeBet(driver, "t", bet_amount);
//													
//													prediction_final="t";
//											 }else {
//												 if(win_counter-- > 0 )
//													data.isRefreshed = coingobbler.placeBet(driver, "ct", bet_amount);
//													
//													prediction_final="ct";
//											 }
//											 
//											 main_checker = prediction_type_G_or_L = "L";
//										 }
//									 }
									
									 
									 
								
							if(!committ_side.equals(""))
								{
//									System.out.println("*****************************************************************\n");
//									System.out.println("Count Seq  : "+count_seq);
////									System.out.println("Count Main  : "+main_seq);
//									System.out.println("Committ Sequence  : "+committ_seq);
//									System.out.println("Puting amount : "+ bet_amount);
//									System.out.println("Capacity : " + win_counter);
//									if((main_win_cause_g <= 5 || main_win_cause_l <= 5 || main_count_g <= 5 || main_count_l <= 5))
//										System.out.println("Not Placing the Bet! Pilot Assessment Phase");
//									System.out.println("\n\n___________________________________For Main___________________");
//									System.out.println("Ones : "+number_of_ones_main);
//									System.out.println("Twos : "+number_of_twos_main);
//									System.out.println("Threes : "+number_of_threes_main);
//									System.out.println("Fours : "+number_of_fours_main);
//									System.out.println("Fives : "+number_of_fives_main);
//									System.out.println("Sixes : "+number_of_sixes_main);
//									System.out.println("______________________________________________________");
//									System.out.println("Sevens : "+number_of_sevens_main);
//									System.out.println("Eights : "+number_of_eights_main);
//									System.out.println("Nines : "+number_of_nines_main);
//									System.out.println("Tens : "+number_of_tens_main);
//									System.out.println("WC : "+number_of_worst_cases_main);
//									System.out.println("W Main count : "+main_W);
//									System.out.println("L Main count : "+main_L);
//
//									System.out.println("W committ count : "+committ_win);
//									System.out.println("L committ count : "+committ_lost);
//									System.out.println("Wallet :"+wallet_together);
//									System.out.println("Toggeling or not :"+doToggle);
////									System.out.println("Wallet together Max Score :"+wallet_together_max);
////									System.out.println("Wallet together Min Score :"+wallet_together_min);
//									System.out.println("Number of Losses caused by L :"+ main_count_l);
//									System.out.println("Number of Losses caused by G :"+ main_count_g);
//									System.out.println("Number of Win caused by G :"+ main_win_cause_g);
//									System.out.println("Number of Win caused by L :"+ main_win_cause_l);
//									System.out.println("Ration comparision : G>"+g_ratio + "       L>"+ l_ratio);
//									System.out.println("Prediction type : "+prediction_type_G_or_L);
//									System.out.println("Final Prediction : "+prediction_final);
//									System.out.println("Final Committed Side : "+committ_side);
//									System.out.println("Latest Roll : "+current_coin);
//									System.out.println("Difference between CT and T count : ("+Integer.parseInt(ct_count)+" - "+Integer.parseInt(t_count)+" ) => "+ Math.abs(Integer.parseInt(ct_count)-Integer.parseInt(t_count)));
//									System.out.println("amt == cnt ; 1-7 only double ; only toggle ; Count bug fixed <:: Time : > " + java.time.LocalDateTime.now());
//									System.out.println("\n\n\n\n\n");
								
//								System.out.println(",{\"Committ_W\":"+committ_win+",\"Committ_L\":"+committ_lost+
//										",\"Main_W\":"+main_W+",\"Main_L\":"+main_L+"}");								
								}else {
//									System.out.println("Not committing this time");								
								}
							
							if(!committ_side.equals(""))
							System.out.println(
									",\n{\"Time\":\""+ java.time.LocalDateTime.now() +
									"\",\"Prediction\":\""+committ_side+
									"\",\"Total_Riskers\":\""+(Integer.parseInt(ct_count)+Integer.parseInt(t_count))+
									"\",\n\"Committ Seq\":\""+committ_seq
									+
									"\",\n\"Win Countdown\":\""+win_counter+
									"\",\"Committ_W\":\""+committ_win+
									"\",\"Committ_L\":\""+committ_lost+
									"\",\n\"Main_W\":\""+main_W+
									"\",\"Main_L\":\""+main_L+
									"\",\"Wallet\":\""+wallet_together+
									"\",\"Putting Amount\":\""+bet_amount+
									"\",\"One\":\""+number_of_ones_main+
									"\",\"Two\":\""+number_of_twos_main+
									"\",\"Three\":\""+number_of_threes_main+
									"\",\"Four\":\""+number_of_fours_main+
									"\",\"Five\":\""+number_of_fives_main+
									"\",\"Six\":\""+number_of_sixes_main+
									"\",\"Seven\":\""+number_of_sevens_main+
									"\",\"Eight\":\""+number_of_eights_main+
									"\",\"Nine\":\""+number_of_nines_main+
									"\",\"Ten\":\""+number_of_tens_main+
									"\",\"WC\":\""+number_of_worst_cases_main+
									
									"\"\n}");	
							}
							else{
									prediction_final="";
//									System.out.println("skipping\n\n");
								}
						
						
					
					
					
					
					if(amount_seq.length()>100)
						amount_seq=amount_seq.substring(1);
					

					if(count_seq.length()>100)
						count_seq=count_seq.substring(1);
					
					if(main_seq.length()>100)
						main_seq=main_seq.substring(1);
					
					if(committ_seq.length()>100)
						committ_seq=committ_seq.substring(1);
					
					//code to find out max possible continuous g and l
					int count_amt=1;
					int count_cnt=1;
					boolean breakit=false;
					if(!committ_side.equals("")) {
						was_bet_placed = true;
					}else {
						was_bet_placed = false;
					}
					
					
					update=true;
					
				}else
					if(Double.parseDouble(timer)==0.00) {
						update=false;
					}
				
			}
		}catch(Exception e) {
			System.out.println("Supreme Refresh");
			data.committ_lost = committ_lost;
			data.committ_win = committ_win;
			data.bet_amount = bet_amount;
			data.doToggle = doToggle;
			data.committ_side = committ_side;
			data.main_checker = main_checker;
			data.main_count_g = main_count_g;
			data.main_count_l = main_count_l;
			data.main_win_cause_g = main_win_cause_g;
			data.main_win_cause_l = main_win_cause_l;
			data.amount_seq=amount_seq;
			data.outcome_seq=outcome_seq;
			data.count_seq=count_seq;
			data.main_seq=main_seq;
			data.g_amount=g_amount;
			data.g_count=g_count;
			data.skip_beat_counter = skip_beat_counter;
			data.l_amount=l_amount;
			data.l_count=l_count;
			data.wallet_together_min=wallet_together_min;
			data.wallet_together_max=wallet_together_max;
			data.wallet_together=wallet_together;
			data.max_g_continuous=max_g_continuous;
			data.max_l_continuous=max_l_continuous;
			data.helper_seq = helper_seq;
			data.goal_amount = goal_amount;
			data.cnt_switcher_cnt = cnt_switcher_cnt;
			data.cnt_continuous_cnt = cnt_continuous_cnt;
			data.amt_switcher_cnt = amt_switcher_cnt;
			data.amt_continuous_cnt = amt_continuous_cnt;
			data.number_of_ones_amount=number_of_ones_amount;
			data.number_of_threes_amount=number_of_threes_amount;
			data.number_of_twos_amount=number_of_twos_amount;
			data.number_of_fours_amount=number_of_fours_amount;
			data.number_of_fives_amount=number_of_fives_amount;
			data.number_of_worst_cases_amount=number_of_worst_cases_amount;
			data.was_bet_placed = was_bet_placed;
			data.number_of_ones_count=number_of_ones_count;
			data.number_of_threes_count=number_of_threes_count;
			data.number_of_twos_count=number_of_twos_count;
			data.number_of_fours_count=number_of_fours_count;
			data.number_of_fives_count=number_of_fives_count;
			data.number_of_worst_cases_count=number_of_worst_cases_count;
			data.isRefreshed = true;
			data.number_of_ones_main=number_of_ones_main;
			data.number_of_threes_main=number_of_threes_main;
			data.number_of_twos_main=number_of_twos_main;
			data.number_of_fours_main=number_of_fours_main;
			data.number_of_fives_main=number_of_fives_main;
			data.number_of_sixes_main=number_of_sixes_main;
			data.number_of_sevens_main=number_of_sevens_main;
			data.number_of_eights_main=number_of_eights_main;
			data.number_of_nines_main=number_of_nines_main;
			data.number_of_tens_main=number_of_tens_main;
			data.number_of_worst_cases_main=number_of_worst_cases_main;
			data.main_L = main_L;
			data.main_W = main_W;
			data.win_counter = win_counter;
			data.committ_seq = committ_seq;
//			System.out.println(e.getMessage());
			new Algo().run(data,driver);

		}
	}
	
	

}
