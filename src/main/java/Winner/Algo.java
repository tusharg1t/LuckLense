package Winner;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collector;

import javax.tools.JavaFileManager;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import io.netty.util.internal.MathUtil;
import Winner.Data;

/*
 * CHANGES TO DO 
 * *Make Prediction on all rolls
 * *Implement Bonus Place
 * 
 */
public class Algo {
	private DecimalFormat df = new DecimalFormat("0.00");

	public Data run(Data data, WebDriver driver) {
		driver.navigate().refresh();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		String main_seq = data.main_seq;
		int win_count = data.main_W;
		int lost_count = data.main_L;
		double wallet = data.wallet_together;
		double displacement = data.displacement;

		double wallet_max = data.wallet_max;
		double wallet_min = data.wallet_min;
		Bookie winner = new Bookie();
		boolean bonus_placed = true;
		int ct_betters;
		int t_betters;
		boolean fargate = data.fargate;
		boolean toggle = false;
		String predict_l2 = "";
		double bet_amount = 0.0;
		boolean scan_1 = false;
		boolean scan_2 = false;
		boolean scan_3 = false;
		int who_first = 0; // 1 ct : 2 t
		
		int t_ragers = 0;
		int ct_ragers = 0;
		String predictionBy = "";
		int ct_techStat_wins = 0;
		int t_techStat_wins = 0;
		try {

			String timer = "";
			String current_coin = "";
			boolean update = false;
			String ct_count = "";
			String t_count = "";

			Double ct_amount = 0.00;
			Double t_amount = 0.00;
			String predicted = "";
			Map<String, Integer> ct_name_rank_map = new HashMap<String, Integer>();
			Map<String, Integer> t_name_rank_map = new HashMap<String, Integer>();
			
			Map<String, Integer> ct_name_amount_map = new HashMap<String, Integer>();
			Map<String, Integer> t_name_amount_map = new HashMap<String, Integer>();
			
			
			int t_vote = 0;
			int ct_vote = 0;
			int winsum_max =0;
			String winsum_strat = "";
			
			while (true) {

				// exact div in shich time element is there
				WebElement count_down_element = driver.findElement(
						By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div/div/div[1]/div[2]/div/div[2]/div[3]/div/div[2]"));
				timer = count_down_element.getAttribute("innerText").toString();

				if (Double.parseDouble(timer) < 15 && Double.parseDouble(timer) > 13 && !bonus_placed) {
					bonus_placed = true;

					List<WebElement> current_coin_elements = driver
							.findElements(By.xpath("//div[@class='previous-rolls-item']"));
					current_coin = current_coin_elements.get(9).getAttribute("innerHTML").contains("-ct") ? "ct"
							: current_coin_elements.get(9).getAttribute("innerHTML").contains("-t") ? "t" : "bonus";

					for (int ind = 0; ind < 9; ind++) {
						data.outcome_cache[ind] = data.outcome_cache[ind + 1];
					}

					data.outcome_cache[9] = current_coin.toUpperCase().charAt(0);
					
					if(!predicted.equals("")){
						// update audit logs for those with predection same
						String toLookFor = predicted;
						Set<String> keys = new HashSet<>();
						for(Map.Entry<String, TechniqueStats> entry: data.techStat.entrySet()) {
							if(toLookFor.equals(entry.getValue().prediction))
								keys.add(entry.getKey());
						}
						
						//updates audits for all algorithms
						for(Map.Entry<String, int[]> entry : data.audit.entrySet()) {
							if(keys.contains(entry.getKey())){
								int[] algoRes = data.audit.get(entry.getKey());
								
								
								if(predicted.equals(current_coin)) {
									algoRes[0]+=1;
								}else {
									if(current_coin.equals("bonus")) {
										algoRes[2]+=1;
									}else {
										algoRes[1]+=1;
									}
								}

								data.audit.put(entry.getKey(), algoRes);
							}else {
								int[] algoRes = data.audit.get(entry.getKey());
								
								
								if(predicted.equals(current_coin)) {
									algoRes[1]+=1;
								}else {
									if(current_coin.equals("bonus")) {
										algoRes[2]+=1;
									}else {
										algoRes[0]+=1;
									}
								}

								data.audit.put(entry.getKey(), algoRes);
							}
							
						}
						
						
//						//only update audits for predictionby algorithms
//						if(!predictionBy.equals("")) {
//							int[] algoRes = data.audit.get(predictionBy);
//							if(predicted.equals(current_coin)) {
//								algoRes[1]+=1;
//							}else {
//								if(current_coin.equals("bonus")) {
//									algoRes[2]+=1;
//								}else {
//									algoRes[0]+=1;
//								}
//							}
//							
//							data.audit.put(predictionBy, algoRes);
//						}
					}
					

					if (current_coin.equals("ct")) {

						for (String x : ct_name_rank_map.keySet()) {
							data.targets.putIfAbsent(x, 0);
							data.targets.replace(x, data.targets.get(x) + 1);
						}

						for (String x : t_name_rank_map.keySet()) {
							data.targets.putIfAbsent(x, 0);
							if (data.targets.get(x) > 0)
								data.targets.replace(x, 0);
							else
								data.targets.replace(x, 0);

						}
					} else if (current_coin.equals("t")) {
						// set the winners as target
						for (String x : t_name_rank_map.keySet()) {
							data.targets.putIfAbsent(x, 0);
							data.targets.replace(x, data.targets.get(x) + 1);
						}
						
						// remove target from loosers
						for (String x : ct_name_rank_map.keySet()) {
							data.targets.putIfAbsent(x, 0);
							if (data.targets.get(x)- 2 > 0)
								data.targets.replace(x, 0);
							else
								data.targets.replace(x, 0);
						}
					}
					
					ct_techStat_wins =0;
					t_techStat_wins =0;
					
					
					for(Map.Entry<String, TechniqueStats> entry : data.techStat.entrySet()) {
						if(entry.getValue().prediction.equals(current_coin)) {
							if(entry.getValue().winCount < 0)
								entry.getValue().winCount = 0;
//							
							entry.getValue().winCount++;
							entry.getValue().sequence+="W";
						}else
							if(!entry.getValue().prediction.equals("")){
							if(entry.getValue().winCount > 0)
								entry.getValue().winCount = 0;
							
							entry.getValue().winCount = 0;
							entry.getValue().sequence+="L";
						}
							
						if(entry.getValue().prediction.equals("t") )
							t_techStat_wins += entry.getValue().winCount;
						else
						if(entry.getValue().prediction.equals("ct"))
							ct_techStat_wins += entry.getValue().winCount;
							
						
						entry.getValue().prediction = "";
					}
					winsum_max = 0;
					winsum_strat = "";
					for(Map.Entry<String, TechniqueStats> entry : data.techStat.entrySet()) {
						int strat_wins = countOfCharFromLastTen(entry.getValue().sequence,'W',7);
						entry.getValue().winSum = strat_wins;
						
						if(entry.getValue().winSum > winsum_max) {
							winsum_max = entry.getValue().winSum;
							winsum_strat = entry.getKey();
						}
					}

				}

				if (Double.parseDouble(timer) < 13 && Double.parseDouble(timer) > 11 && !scan_1) {

					t_name_rank_map.clear();
					ct_name_rank_map.clear();
					t_vote = 0;
					ct_vote = 0;
					
					t_ragers = 0;
					ct_ragers = 0;

					int temp_t_xp = 0;
					int temp_ct_xp = 0;
					scan_1 = true;
					
					String[] ct_players = scanCTPlayers(driver);
					String[] t_players = scanTPlayers(driver);
					for (int i = 0; i < ct_players.length; i += 3) {
						
						
						if (!ct_name_rank_map.containsKey(ct_players[i + 1])) {
							
							if(data.amountRecord.get(ct_players[i+1])!= null && Double.parseDouble(ct_players[i+2].replaceAll(",", "")) > data.amountRecord.get(ct_players[i+1]))
								ct_ragers++;
							data.amountRecord.replace(ct_players[i+1], Double.parseDouble(ct_players[i+2].replaceAll(",", "")));
							
							ct_name_rank_map.put(ct_players[i + 1], Integer.parseInt(ct_players[i]));
							temp_ct_xp += Integer.parseInt(ct_players[i]);
						}
						
						

					}

					for (int i = 0; i < t_players.length; i += 3) {
						if (!t_name_rank_map.containsKey(t_players[i + 1])) {
							if(data.amountRecord.get(t_players[i+1])!= null && Double.parseDouble(t_players[i+2].replaceAll(",", "")) > data.amountRecord.get(t_players[i+1]))
								t_ragers++;
							data.amountRecord.replace(t_players[i+1], Double.parseDouble(t_players[i+2].replaceAll(",", "").replaceAll(",", "")));
							
							t_name_rank_map.put(t_players[i + 1], Integer.parseInt(t_players[i]));
							temp_t_xp = Integer.parseInt(t_players[i]);
						}
						
					}

					if (ct_name_rank_map.size() > t_name_rank_map.size())
						who_first = 1;
					else if (ct_name_rank_map.size() < t_name_rank_map.size())
						who_first = 2;
					else
						who_first = 0;

				}
				if (Double.parseDouble(timer) < 10 && Double.parseDouble(timer) > 9 && !scan_2) {
					
					scan_2 = true;
					String[] ct_players = scanCTPlayers(driver);
					String[] t_players = scanTPlayers(driver);
					
					for (int i = 0; i < ct_players.length; i += 3) {
						if (!ct_name_rank_map.containsKey(ct_players[i + 1])) {
							
							if(data.amountRecord.get(ct_players[i+1])!= null && Double.parseDouble(ct_players[i+2].replaceAll(",", "")) > data.amountRecord.get(ct_players[i+1]))
								ct_ragers++;
							data.amountRecord.put(ct_players[i+1], Double.parseDouble(ct_players[i+2].replaceAll(",", "")));
							
							ct_name_rank_map.put(ct_players[i + 1], Integer.parseInt(ct_players[i]));
						
						}
							
						
						
					}

					for (int i = 0; i < t_players.length; i += 3) {
						if (!t_name_rank_map.containsKey(t_players[i + 1])) {
							if(data.amountRecord.get(t_players[i+1])!= null && Double.parseDouble(t_players[i+2].replaceAll(",", "")) > data.amountRecord.get(t_players[i+1]))
								t_ragers++;
							data.amountRecord.put(t_players[i+1], Double.parseDouble(t_players[i+2].replaceAll(",", "")));
							
							t_name_rank_map.put(t_players[i + 1], Integer.parseInt(t_players[i]));
						}

						
					}
				}
				if (Double.parseDouble(timer) < 6 && Double.parseDouble(timer) > 5 && !scan_3) {
					scan_3 = true;
					String[] ct_players = scanCTPlayers(driver);
					String[] t_players = scanTPlayers(driver);
					for (int i = 0; i < ct_players.length; i += 3) {
						if (!ct_name_rank_map.containsKey(ct_players[i + 1])) {
							if(data.amountRecord.get(ct_players[i+1])!= null && Double.parseDouble(ct_players[i+2].replaceAll(",", "")) > data.amountRecord.get(ct_players[i+1]))
								ct_ragers++;
							data.amountRecord.put(ct_players[i+1], Double.parseDouble(ct_players[i+2].replaceAll(",", "")));
							
							ct_name_rank_map.put(ct_players[i + 1], Integer.parseInt(ct_players[i]));
						}
				
					}

					for (int i = 0; i < t_players.length; i += 3) {
						if (!t_name_rank_map.containsKey(t_players[i + 1])) {
							if(data.amountRecord.get(t_players[i+1])!= null && Double.parseDouble(t_players[i+2].replaceAll(",", "")) > data.amountRecord.get(t_players[i+1]))
								t_ragers++;
							data.amountRecord.put(t_players[i+1], Double.parseDouble(t_players[i+2].replaceAll(",", "")));
							
							t_name_rank_map.put(t_players[i + 1], Integer.parseInt(t_players[i]));
						}
						
					}

				}

				if (Double.parseDouble(timer) <= 2 && Double.parseDouble(timer) > 0 && !update) {

					int ct_targets = 0;
					int t_targets = 0;

					int ct_targets_1 = 0;
					int t_targets_1 = 0;

					int ct_targets_2 = 0;
					int t_targets_2 = 0;

					int ct_targets_3 = 0;
					int t_targets_3 = 0;

					int ct_targets_4 = 0;
					int t_targets_4 = 0;

					int ct_targets_5 = 0;
					int t_targets_5 = 0;

					int ct_targets_6 = 0;
					int t_targets_6 = 0;

					int ct_targets_7 = 0;
					int t_targets_7 = 0;

					int ct_targets_8 = 0;
					int t_targets_8 = 0;
					// Sample Fetch(example) : 71 Bets Total
					WebElement ct_overview = driver.findElement(By.xpath(
							"//*[@id=\"app\"]/div[1]/div[2]/div/div/div[1]/div[2]/div/div[6]/div[1]/div/div[1]/div[1]"));
					WebElement t_overview = driver.findElement(By.xpath(
							"//*[@id=\"app\"]/div[1]/div[2]/div/div/div[1]/div[2]/div/div[6]/div[3]/div/div[1]/div[1]"));

					WebElement t_amount_overview = driver.findElement(By.xpath(
							"//*[@id=\"app\"]/div[1]/div[2]/div/div/div[1]/div[2]/div/div[6]/div[3]/div/div/div[2]/span/div"));
					WebElement ct_amount_overview = driver.findElement(By.xpath(
							"//*[@id=\"app\"]/div[1]/div[2]/div/div/div[1]/div[2]/div/div[6]/div[1]/div/div[1]/div[2]/span/div"));

					t_amount = Double.parseDouble(t_amount_overview.getText().toString().replaceAll(",", ""));
					ct_amount = Double.parseDouble(ct_amount_overview.getText().toString().replaceAll(",", ""));
					t_betters = Integer.parseInt(t_overview.getText().toString().split(" ")[0]);
					ct_betters = Integer.parseInt(ct_overview.getText().toString().split(" ")[0]);

					
					
					if (!predicted.equals("")) {
						if (predicted.equals(current_coin)) {

							main_seq += "W";
							
							int total = t_betters + ct_betters;
							data.players_W.putIfAbsent(total, 0);
							data.players_W.replace(total, data.players_W.get(total) + 1);

						} else {

							if (!current_coin.equals("bonus")) {
								main_seq += "L";
							}else {
								main_seq += "L";
							}
							
							int total = t_betters + ct_betters;
							data.players_L.putIfAbsent(total, 0);
							if (!current_coin.equals("bonus"))
								data.players_L.replace(total, data.players_L.get(total) + 1);

						}

						if (fargate)
							if (data.fargate_seq.charAt(data.fargate_seq.length() - 1) == data.fargate_seq
									.charAt(data.fargate_seq.length() - 2))
								data.continuous_algo++;
							else
								data.switching_algo++;

						int continuous;

						if (fargate)
							if (data.fargate_seq.charAt(data.fargate_seq.length() - 1) == 'L') {
								continuous = (new Algo()).countOfCharFromLastAfterSwitch(data.fargate_seq, 'W');
								if (continuous > 0)
									data.expoPR.replace(continuous, data.expoPR.get(continuous) + 1);

								if (continuous > 0)
									data.expoW.replace(continuous, data.expoW.get(continuous) + 1);
							} else if (data.fargate_seq.charAt(data.fargate_seq.length() - 1) == 'W') {
								continuous = (new Algo()).countOfCharFromLastAfterSwitch(data.fargate_seq, 'L');
								if (continuous > 0)
									data.expoPR.replace(continuous, data.expoPR.get(continuous) + 1);

								if (continuous > 0)
									data.expoL.replace(continuous, data.expoL.get(continuous) + 1);

							} else if (data.fargate_seq.charAt(data.fargate_seq.length() - 1) == 'B') {
								continuous = (new Algo()).countOfCharFromLastAfterSwitch(data.fargate_seq,
										data.fargate_seq.charAt(data.fargate_seq.length() - 2));
								if (continuous > 0)
									data.expoPR.replace(continuous, data.expoPR.get(continuous) + 1);

								if (continuous > 0 && data.fargate_seq.charAt(data.fargate_seq.length() - 2) == 'L')
									data.expoL.replace(continuous, data.expoL.get(continuous) + 1);
								else if (continuous > 0
										&& data.fargate_seq.charAt(data.fargate_seq.length() - 2) == 'W')
									data.expoW.replace(continuous, data.expoW.get(continuous) + 1);

							}

					}
					
					predictionBy="";

					if (data.pitstop < 0 && !predicted.equals("")) {

						if (!(data.wallet_graph_data.get(data.wallet_graph_data.size() - 1) == Double
								.parseDouble(df.format(data.fargate_wallet).toString().replaceAll(",", ""))))
							data.wallet_graph_data.add(
									Double.parseDouble(df.format(data.fargate_wallet).toString().replaceAll(",", "")));

						if (current_coin.equals(predicted)) {

							wallet += data.multiplier;

						} else {

							wallet -= data.multiplier;

						}

					}

					if (data.pitstop < 0 && fargate && !data.predict_l3.equals("")) {

						if (current_coin.equals(data.predict_l3)) {

							data.wallet_l3 += bet_amount;
							data.wallet_l3 = (data.wallet_l3 * 100) / 100;
							data.fargate_seq += "W";

						} else {

							data.wallet_l3 -= bet_amount;
							data.wallet_l3 = (data.wallet_l3 * 100) / 100;
							if (!current_coin.equals("bonus")) {

								data.fargate_seq += "L";
							} else
								data.fargate_seq += "B";
						}

						if (current_coin.equals("bonus") && bet_amount > 0)
							data.bonus_counter++;

						data.predict_l3 = "";

					}

					if (data.pitstop < 0 && !predict_l2.equals("")) {

						if (current_coin.equals("bonus")) {

							// In case of bonus what to do on fargate
							data.fargate_wallet -= data.multiplier;
						} else if (current_coin.equals(predict_l2)) {

							data.fargate_wallet += data.multiplier;
							win_count++;
							if (bet_amount > 0)
								data.win_cnt_commited++;
						} else {

							data.fargate_wallet -= data.multiplier;
							lost_count++;
							if (bet_amount > 0)
								data.loss_cnt_commited++;
						}

					}

					if (!fargate && current_coin.equals("bonus"))
						driver.navigate().refresh();

					predict_l2 = "";

					if (data.wallet_l3 > wallet_max)
						wallet_max = data.wallet_l3;

					if (data.wallet_l3 < wallet_min)
						wallet_min = data.wallet_l3;

					if (wallet_max - data.wallet_l3 > displacement)
						displacement = wallet_max - data.wallet_l3;

					int ct_xp = 0;
					int t_xp = 0;
					int max_ct_xp = 0;
					int max_t_xp = 0;

					String[] ct_players = scanCTPlayers(driver);
					String[] t_players = scanTPlayers(driver);
					for (int i = 0; i < ct_players.length; i += 3) {
						if (!ct_name_rank_map.containsKey(ct_players[i + 1])){
							if(data.amountRecord.get(ct_players[i+1])!= null && Double.parseDouble(ct_players[i+2].replaceAll(",", "")) > data.amountRecord.get(ct_players[i+1]))
								t_ragers++;
							data.amountRecord.put(ct_players[i+1], Double.parseDouble(ct_players[i+2].replaceAll(",", "")));
						}
						
//													   <Name,Rank>
						ct_name_rank_map.putIfAbsent(ct_players[i + 1], Integer.parseInt(ct_players[i]));
						ct_xp += Integer.parseInt(ct_players[i]);

						
						
						
						if (Integer.parseInt(ct_players[i]) > max_ct_xp)
							max_ct_xp = ct_xp;
					}

					for (int i = 0; i < t_players.length; i += 3) {
						if (!t_name_rank_map.containsKey(t_players[i + 1])){
							if(data.amountRecord.get(t_players[i+1])!= null && Double.parseDouble(t_players[i+2].replaceAll(",", "")) > data.amountRecord.get(t_players[i+1]))
								t_ragers++;
							data.amountRecord.put(t_players[i+1], Double.parseDouble(t_players[i+2].replaceAll(",", "")));
						}
						
						t_name_rank_map.putIfAbsent(t_players[i + 1], Integer.parseInt(t_players[i]));
						t_xp += Integer.parseInt(t_players[i]);
						
						

						if (Integer.parseInt(t_players[i]) > max_t_xp)
							max_t_xp = t_xp;
					}
					
//					if (Math.ceil(data.wallet_l3 * 100) / 100 >= Math.floor(5 * data.multiplier * 100) / 100) {
//						System.out.println("\n\n\n\n\n\n******==Success==********" + data.wallet_l3);
//						data.global_wallet += 5 * data.multiplier;
//
//						data.wallet_l3 = 0;
//						data.nextOpportunity = main_seq.length() + 50;
//						data.start = false;
//						wallet_max = 0;
//						wallet_min = 0;
//						displacement = 0;
//						data.global_win_cnt++;
//						data.global_outcome_seq += "W";
//						if (data.global_wallet >= Math.floor(5 * data.multiplier * 100) / 100)
//							return data;
//					}
//
//					if (Math.floor(data.wallet_l3 * 100) / 100 <= Math.ceil(-5 * data.multiplier * 100) / 100) {
//						System.out.println("---------==THINK==---------" + data.wallet_l3);
//						data.global_wallet -= 5 * data.multiplier;
//						data.wallet_l3 = 0;
//						data.nextOpportunity = main_seq.length() + 50;
//						data.start = false;
//						wallet_max = 0;
//						wallet_min = 0;
//						displacement = 0;
//						data.global_loss_cnt += 1;
//						data.global_outcome_seq += "L";
//						Thread.sleep(1000000);
//						if (data.global_wallet <= 0)
//							return data;
//						if(data.global_win_cnt - data.global_loss_cnt < -1)
//							return data;
//
//					}

					int ctWinSum = 0;
					int tWinSum = 0;
					for (String x : ct_name_rank_map.keySet()) {
						if (data.targets.containsKey(x)) {
							if (data.targets.get(x) >= 2)
								ct_targets_2++;
							else if (data.targets.get(x) >= 1)
								ct_targets_1++;
							else if (data.targets.get(x) >= 0)
								ct_targets++;

						}

						ctWinSum += data.targets.get(x) != null ? data.targets.get(x) : 0;
					}

					for (String x : t_name_rank_map.keySet()) {
						if (data.targets.containsKey(x)) {
							if (data.targets.get(x) >= 2)
								t_targets_2++;
							else if (data.targets.get(x) >= 1)
								t_targets_1++;
							else if (data.targets.get(x) >= 0)
								t_targets++;
						}
						
						tWinSum += data.targets.get(x) != null ? data.targets.get(x) : 0;
					}

					if (data.pitstop-- < 0 ) {

						int l_cnt = countOfCharFromLastTen(main_seq, 'L',7);
						int w_cnt = countOfCharFromLastTen(main_seq, 'W',7);
						int w_cnt_20 = countOfCharFromLastTen(main_seq, 'W',20);

						fargate = true;
						int ct_score = 0;
						int t_score = 0;

						
						if (t_targets_2 < ct_targets_2)
							ct_score += 5;
						else if (t_targets_2 > ct_targets_2)
							t_score += 5;

						if (t_targets_1 < ct_targets_1)
							ct_score += 5;
						else if (t_targets_1 > ct_targets_1)
							t_score += 5;
						
						if (t_targets < ct_targets)
							ct_score += 5;
						else if (t_targets > ct_targets)
							t_score += 5;

						if (t_score < ct_score) {
							predicted = "ct";
						} else if (t_score > ct_score)
							predicted = "t";
						else
							predicted = "";

						
						boolean enable = false;
						enable = enableRound(data.outcome_cache);
						String tempVote ="";
						String maxWagerers = "";
						if (t_ragers > ct_ragers)
							tempVote = "t";
						else if (t_ragers < ct_ragers)
							tempVote = "ct";
						
						if(t_betters > ct_betters)
							maxWagerers = "t";
						else
							if(t_betters < ct_betters)
								maxWagerers = "ct";
						
//						 //VOTE 1 : SOLID
						if(tWinSum >  ctWinSum  ) {
//							t_vote += 27;
//							System.out.println("WINSUM CT : T ==> "+ctWinSum+" :: "+tWinSum);
//							predictionBy = "WINSUM";
//							
							data.techStat.get("WINSUM").prediction = "ct";
						}else if(tWinSum  < ctWinSum ) {
//							ct_vote += 27;
//							System.out.println("WINSUM CT : T ==> "+ctWinSum+" :: "+tWinSum);
//							predictionBy = "WINSUM";
//							

							data.techStat.get("WINSUM").prediction = "t";
						}
						
						if (predicted.equals("t") ) {
//							ct_vote += 27;
//							System.out.println("SCORE CT : T ==> "+ct_vote+" :: "+t_vote);
//							predictionBy = "SCORE";
							

							data.techStat.get("SCORE").prediction = "ct";
						}else if (predicted.equals("ct") ) {
//							t_vote += 27;
//							System.out.println("SCORE CT : T ==> "+ct_vote+" :: "+t_vote);
//							predictionBy = "SCORE";
//							
							data.techStat.get("SCORE").prediction = "t";
						}
						
						if( t_amount < ct_amount) {
							data.techStat.get("AMT").prediction = "ct";
						}else
						if(t_amount > ct_amount) {
							data.techStat.get("AMT").prediction = "t";
						}
						
						if( t_betters > ct_betters) {
							data.techStat.get("CNT").prediction = "ct";
						}else
						if(t_betters < ct_betters) {
							data.techStat.get("CNT").prediction = "t";
						}
						
						if( ct_xp < t_xp) {
							data.techStat.get("XPSUM").prediction = "ct";
						}else
						if(ct_xp > t_xp) {
							data.techStat.get("XPSUM").prediction = "t";
						}
						
						if( max_t_xp > max_ct_xp) {
							data.techStat.get("MAXXP").prediction = "ct";
						}else
						if(max_t_xp < max_ct_xp) {
							data.techStat.get("MAXXP").prediction = "t";
						}
						
						if( t_targets_2 < ct_targets_2) {
							data.techStat.get("TARGET2").prediction = "t";
						}else
						if(t_targets_2 > ct_targets_2) {
							data.techStat.get("TARGET2").prediction = "ct";
						}
						
						if( t_targets > ct_targets) {
							data.techStat.get("TARGET0").prediction = "t";
						}else
						if(t_targets < ct_targets) {
							data.techStat.get("TARGET0").prediction = "ct";
						}
						
						if( who_first == 1) {
							data.techStat.get("WHOFIRST").prediction = "t";
						}else
						if(who_first == 0) {
							data.techStat.get("WHOFIRST").prediction = "ct";
						}
						
						char major = getMajorOutcome(data.outcome_cache);
						
						if(major == 'T') {
							data.techStat.get("MAJOR").prediction = "t";
						}else 
						if(major == 'C') {
							data.techStat.get("MAJOR").prediction = "ct";
						}
						
						
						if (ct_vote > t_vote)
							predicted = "ct";
						else if (t_vote > ct_vote)
							predicted = "t";
						else
							predicted = "";

						if( (t_ragers>= ct_ragers || ct_ragers >= t_ragers) ) {

//							System.out.println("Prediction By Rage Detector ct : t == " + ct_ragers + " : " + t_ragers);
//							predicted = tempVote;
//							predictionBy = "RAGE";
//							
							data.techStat.get("RAGE").prediction = tempVote;
						}
						
						
						
						
						String key = "";
						int max = -101;
						boolean ambiguous = true;
						
						int ct_continuityCriteria = 0;
						int t_continuityCriteria = 0;
						
						for(Map.Entry<String, TechniqueStats> entry : data.techStat.entrySet()) {
							
							TechniqueStats techStatObj = entry.getValue();
							
							if(Math.abs(techStatObj.winCount) < 5) {
								
								if(techStatObj.prediction.equals("ct"))
									ct_continuityCriteria++;
								

								if(techStatObj.prediction.equals("t"))
									t_continuityCriteria++;
							}else {
								if(techStatObj.prediction.equals("ct"))
									t_continuityCriteria++;
								

								if(techStatObj.prediction.equals("t"))
									ct_continuityCriteria++;
							}
							
							if(entry.getValue().winCount > max) {
								max = entry.getValue().winCount;
								key = entry.getKey();
								ambiguous = false;
							}else
							if(entry.getValue().winCount == max && !entry.getValue().prediction.equals(data.techStat.get(key).prediction)) {
								ambiguous = true;
							}
						}
						
						
						if (fargate) {

							int degree = 0;
							
							
							if(key.equals("")) {
								predicted = data.techStat.get(winsum_strat).prediction;
								key = winsum_strat;
							}else {
								
////								//FILTER B
////								if(predicted.equals(data.techStat.get(key).prediction)) {
////										predicted = data.techStat.get(key).prediction;
////										
////										
////										//FILTER X
////										if(predicted.equals(data.techStat.get(winsum_strat).prediction)) {
////											predicted = predicted.equals("t")?"ct":predicted.equals("ct")?"t":"";
////											degree = 1;
////										}
////								}
//								
//								if(ambiguous && !winsum_strat.equals("")) {
//									//FILTER A
//									predicted = data.techStat.get(winsum_strat).prediction;
//									predictionBy = "";
//								}else {
//									predicted = data.techStat.get(key).prediction;
//									predictionBy=key;
//								}
//
//								predicted = predicted.equals("t")?"ct":predicted.equals("ct")?"t":"";
								
								
								System.out.println("CRITERIA > ct:t  "+ct_continuityCriteria+":"+t_continuityCriteria);
								if(ct_continuityCriteria > t_continuityCriteria)
									predicted = "ct";
								else
								if(ct_continuityCriteria < t_continuityCriteria)
										predicted = "t";
								else {
									predicted = "";
								}
								
								if(winsum_max > 1)
								degree = 1;
								
							}
							
							if(data.techStat.get("RAGE").sequence.length() < 20 || t_betters+ct_betters < 10 )
								degree = 0;
							
							
							
							
							predict_l2 = predicted;
							data.predict_l3 = predict_l2;
							
							
							
							bet_amount = Double.parseDouble(df.format(data.multiplier * degree));

							winner.placeBet(driver, data.predict_l3, bet_amount);
							if (!data.predict_l3.equals(""))
								for (int ind = 0; ind < 9; ind++) {
									data.prediction_cache[ind] = data.prediction_cache[ind + 1];
								}
							if (!data.predict_l3.equals(""))
								data.prediction_cache[9] = data.predict_l3.toUpperCase().charAt(0);
						} else {
							predict_l2 = data.predict_l3 = "";

						}
						System.out.println("TIMER : " + Double.parseDouble(timer));
					} else {
						predicted = "";
					}

					if (Math.abs(win_count - lost_count) > data.l_w_difference_max) {
						data.l_w_difference_max = Math.abs(win_count - lost_count);
					}
					System.out.println("GLOBAL WALLET == " + data.global_wallet);
					System.out.println("GLOBAL SEQUENCE == " + data.global_outcome_seq);
					System.out.println("GLOBAL Loss : WIN == " + data.global_loss_cnt + " : " + data.global_win_cnt);
					
					if (!data.predict_l3.equals("")) {
						System.out.println("Bet Amount : " + bet_amount);
						System.out.println(
								"=======================================================================================================");
						System.out.println("OutCome Cache    : " + Arrays.toString(data.outcome_cache));
						System.out.println("Prediction Cache : " + Arrays.toString(data.prediction_cache));
						System.out.println("                                Prediction By : "+ predictionBy);
						System.out.println(
								"Current Coin is : " + current_coin );
						System.out.println("W : L Ratio Fargate SEQ = " + win_count + " : " + lost_count);
						System.out.println("W : L Ratio Committed Rolls = " + data.win_cnt_commited + " : "
								+ data.loss_cnt_commited + " : " + data.bonus_counter);
						System.out.println("Sequence         : " + main_seq
								.substring(main_seq.length() - (main_seq.length() > 50 ? 50 : main_seq.length()))+"  >>>>>  ( "+predictionBy+" : "+ct_techStat_wins+":"+t_techStat_wins+" ) "+data.predict_l3+"\n");
						

						for(Map.Entry<String, TechniqueStats> entry: data.techStat.entrySet()) {
							
							System.out.println("Sequence   "+entry.getKey()+": " + entry.getValue().sequence
									.substring(entry.getValue().sequence.length() - (entry.getValue().sequence.length() > 50 ? 50 : entry.getValue().sequence.length())) + "  >>>>> " + entry.getValue().prediction);
							
						}
//						System.out.println("Fargate Sequence : " + data.fargate_seq.substring(data.fargate_seq.length()
//								- (data.fargate_seq.length() > 50 ? 50 : data.fargate_seq.length())));
						System.out.println("Fargate wallet : " + data.fargate_wallet + "  ::  " + "Wallet L3: "
								+ df.format(data.wallet_l3) + "  :: " + "Displacement:" + df.format(displacement)
								+ "  ::  " + "Wallet Max:" + df.format(wallet_max) + "  ::  " + "Wallet Min : "
								+ df.format(wallet_min));
						System.out.println(
								"=======================================================================================================");

						System.out.println("\nContinus Algo Result : " + data.continuous_algo + "  ::  "
								+ "Switching Algo Result : " + data.switching_algo);
						System.out.println("\nExponential W : " + data.expoW);
						System.out.println("Exponential L : " + data.expoL);
						
						
						System.out.println(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
					}

					update = true;
					
					for(String res : data.audit.keySet()) {
						int[] auditRes = data.audit.get(res);
						System.out.println("AUDIT INSIGHTS <W:L:B> : " +res+" == " + auditRes[0]+" : "+auditRes[1]+" : "+auditRes[2]);
					}
					System.out.println("\n\n\n\n");

				}

				if (Double.parseDouble(timer) == 0.00) {
					update = false;
					bonus_placed = false;
					scan_1 = false;
					scan_2 = false;
					scan_3 = false;
				}

			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println("\nContinus Algo Result : " + data.continuous_algo + "  ::  "
					+ "Switching Algo Result : " + data.switching_algo);

			System.out.println("\nExponential W : " + data.expoW);
			System.out.println("Exponential L : " + data.expoL);
			System.out.println("\nPlayer Heat Map L : " + data.players_L);
			System.out.println("Player Heat Map W : " + data.players_W);
			System.out.println(data.wallet_graph_data.toString());
			data.displacement = displacement;
			data.main_seq = main_seq;
			data.main_W = win_count;
			data.main_L = lost_count;
			data.wallet_together = wallet;
			data.wallet_max = wallet_max;
			data.wallet_min = wallet_min;
			data.fargate = fargate;
			data = new Algo().run(data, driver);
			System.out.println("\nMain Sequence : " + main_seq);
			return data;
		}

	}

	public int countOfCharFromLastAfterSwitch(String toCheckIn, char toCheck) {
		int cnt = 0;
		for (int i = toCheckIn.length() - 2; i >= 0; i--) {
			if (toCheckIn.charAt(i) == toCheck) {
				cnt++;
			} else
				break;
		}
		return cnt;
	}
	
	public int getChance(String seq) {
		int chance = 1;
		int itr = seq.length()-2;
		while(itr-1 >= 0 && seq.charAt(itr) == seq.charAt(itr-1)) {
			chance++;
			itr--;
		}
		
		return Math.round(chance/3);
	}

	public int countOfCharFromLastTen(String toCheckIn, char toCheck, int range) {
		int cnt = 0;
		int checkTill = toCheckIn.length() - (toCheckIn.length() > range ? range : toCheckIn.length());
		for (int i = toCheckIn.length() - 1; i >= checkTill; i--) {
			if (toCheckIn.charAt(i) == toCheck) {
				cnt++;
			}
		}
		return cnt;
	}

	public int continuousCountForLastinArray(Character[] toCheckIn, int windowSize) {
		int cnt = 0;
		for (int i = 9; i > 0; i--) {
			if (toCheckIn[i] == toCheckIn[i - 1]) {
				cnt++;
			}
		}
		return cnt;
	}

	public String[] scanTPlayers(WebDriver driver) {

		// the top level non altering div which shows the players which have placed bets
		WebElement t_bet_container = driver.findElement(
				By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div/div/div[1]/div[2]/div/div[6]/div[3]/div/div[2]"));
		String[] t_players = t_bet_container.getAttribute("innerText").trim().split("\n");
		t_players = Arrays.stream(t_players).filter(element -> element != null && !element.equals(""))
				.toArray(String[]::new);
		return t_players;

	}

	public String[] scanCTPlayers(WebDriver driver) {
		WebElement ct_bet_container = driver.findElement(
				By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div/div/div[1]/div[2]/div/div[6]/div[1]/div/div[2]"));
		String[] ct_players = ct_bet_container.getAttribute("innerText").trim().split("\n");
		ct_players = Arrays.stream(ct_players).filter(element -> element != null && !element.equals(""))
				.toArray(String[]::new);

		return ct_players;
	}

	public boolean enableRound(Character[] toCheckIn) {
		// continuous||type > 7 then amount min wins
		int continuousCnt = continuousCountForLastinArray(toCheckIn, 10);
		boolean ret = false;
		int t = 0;
		int ct = 0;
		int bonus = 0;

		for (Character x : toCheckIn) {
			if (x == 'T')
				t++;
			else if (x == 'C')
				ct++;
			else
				bonus++;
		}
		
		System.out.println("T"+t+"  CT"+ct+"  Bonus"+bonus);
		if (ct+bonus <= 6 && ct+bonus >= 4)
			ret = true;

		return ret;
	}
	
	public Character getMajorOutcome(Character[] toCheckIn) {
		// continuous||type > 7 then amount min wins
		int continuousCnt = continuousCountForLastinArray(toCheckIn, 10);
		boolean ret = false;
		int t = 0;
		int ct = 0;
		int bonus = 0;

		for (Character x : toCheckIn) {
			if (x == 'T')
				t++;
			else if (x == 'C')
				ct++;
			else
				bonus++;
		}
		
		if(t > ct)
			return 'T';
		else
		if(t < ct)
			return 'C';
		
		return 'X';
	}

}
