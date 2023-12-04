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
import Winner.Data;

/*
 * CHANGES TO DO 
 * *Make Prediction on all rolls
 * *Implement Bonus Place
 * 
 */
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
		boolean fargate = data.fargate;
		boolean toggle = false;
		String predict_l2 = "";
		double bet_amount = 0.0;
		boolean scan_1 = false;
		boolean scan_2 = false;
		boolean scan_3 = false;

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
			int t_vote = 0;
			int ct_vote = 0;
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

					if (current_coin.equals("ct")) {
						// set the winners as target
						for (String x : ct_name_rank_map.keySet()) {
							data.targets.putIfAbsent(x, 0);
							data.targets.replace(x, data.targets.get(x) + 1);
						}
						// remove target from loosers
						for (String x : t_name_rank_map.keySet()) {
							data.targets.putIfAbsent(x, 0);
							if (data.targets.get(x) > 0)
								data.targets.replace(x, data.targets.get(x) - 1);

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
							if (data.targets.get(x) > 0)
								data.targets.replace(x, data.targets.get(x) - 1);
						}
					}
				}

				if (Double.parseDouble(timer) < 13 && Double.parseDouble(timer) > 11 && !scan_1) {

					t_name_rank_map.clear();
					ct_name_rank_map.clear();
					t_vote = 0;
					ct_vote = 0;
					
					int temp_t_xp = 0;
					int temp_ct_xp = 0;
					scan_1 = true;
					String[] ct_players = scanCTPlayers(driver);
					String[] t_players = scanTPlayers(driver);
					for (int i = 0; i < ct_players.length; i += 3) {
						if (!ct_name_rank_map.containsKey(ct_players[i + 1])) {
							ct_name_rank_map.put(ct_players[i + 1], Integer.parseInt(ct_players[i]));
							temp_ct_xp += Integer.parseInt(ct_players[i]);
						}
					
					}

					for (int i = 0; i < t_players.length; i += 3) {
						if (!t_name_rank_map.containsKey(t_players[i + 1])) {
							t_name_rank_map.put(t_players[i + 1], Integer.parseInt(t_players[i]));
							temp_t_xp = Integer.parseInt(t_players[i]);
						}
					}
					
				}
				if (Double.parseDouble(timer) < 10 && Double.parseDouble(timer) > 9 && !scan_2) {
					scan_2 = true;
					String[] ct_players = scanCTPlayers(driver);
					String[] t_players = scanTPlayers(driver);
					for (int i = 0; i < ct_players.length; i += 3) {
						if (!ct_name_rank_map.containsKey(ct_players[i + 1]))
							ct_name_rank_map.put(ct_players[i + 1], Integer.parseInt(ct_players[i]));
					}

					for (int i = 0; i < t_players.length; i += 3) {
						if (!t_name_rank_map.containsKey(t_players[i + 1]))
							t_name_rank_map.put(t_players[i + 1], Integer.parseInt(t_players[i]));
					}
					
					if(ct_name_rank_map.size() > t_name_rank_map.size())
						t_vote++;
					else 
					if(ct_name_rank_map.size() < t_name_rank_map.size())
						ct_vote++;
				
				}
				if (Double.parseDouble(timer) < 8 && Double.parseDouble(timer) > 6 && !scan_3) {
					scan_3 = true;
					String[] ct_players = scanCTPlayers(driver);
					String[] t_players = scanTPlayers(driver);
					for (int i = 0; i < ct_players.length; i += 3) {
						if (!ct_name_rank_map.containsKey(ct_players[i + 1]))
							ct_name_rank_map.put(ct_players[i + 1], Integer.parseInt(ct_players[i]));
					}

					for (int i = 0; i < t_players.length; i += 3) {
						if (!t_name_rank_map.containsKey(t_players[i + 1]))
							t_name_rank_map.put(t_players[i + 1], Integer.parseInt(t_players[i]));
					}
					
					
				}

				if (Double.parseDouble(timer) < 5 && Double.parseDouble(timer) > 0 && !update) {

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

							if (!current_coin.equals("bonus"))
								main_seq += "L";
							else
								main_seq += "L";

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

					if (data.pitstop < 0 && !predicted.equals("")) {

						if (!(data.wallet_graph_data.get(data.wallet_graph_data.size() - 1) == Double
								.parseDouble(df.format(data.fargate_wallet).toString().replaceAll(",", ""))))
							data.wallet_graph_data.add(Double.parseDouble(df.format(data.fargate_wallet).toString().replaceAll(",", "")));

						if (current_coin.equals(predicted)) {

							wallet += data.multiplier;

						} else {

							wallet -= data.multiplier;

						}

					}

					if (data.pitstop < 0 && fargate && !data.predict_l3.equals("")) {

						if (current_coin.equals(data.predict_l3)) {

							data.wallet_l3 += bet_amount;
							data.fargate_seq += "W";

						} else {

							data.wallet_l3 -= bet_amount;

							if (!current_coin.equals("bonus")) {
								data.fargate_seq += "L";
							} else
								data.fargate_seq += "L";
						}

						if (current_coin.equals("bonus") && data.start)
							data.bonus_counter++;

						data.predict_l3 = "";

					}

					if (data.pitstop < 0 && !predict_l2.equals("")) {

						if (current_coin.equals("bonus")) {

							// In case of bonus what to do on fargate
						} else if (current_coin.equals(predict_l2)) {

							data.fargate_wallet += data.multiplier;
							if (data.start)
								win_count++;
						} else {

							data.fargate_wallet -= data.multiplier;
							if (data.start)
								lost_count++;
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
//													   <Name,Rank>
						ct_name_rank_map.putIfAbsent(ct_players[i + 1], Integer.parseInt(ct_players[i]));
						ct_xp += Integer.parseInt(ct_players[i]);

						if(ct_xp > max_ct_xp)
							max_ct_xp = ct_xp;
					}

					for (int i = 0; i < t_players.length; i += 3) {
						t_name_rank_map.putIfAbsent(t_players[i + 1], Integer.parseInt(t_players[i]));
						t_xp += Integer.parseInt(t_players[i]);
						
						if(t_xp > max_t_xp)
							max_t_xp = t_xp;
					}

					System.out.println("CT_MAP : " + ct_name_rank_map.toString());

					System.out.println("CT_MAP : " + t_name_rank_map.toString());

					for (String x : ct_name_rank_map.keySet()) {
						if (data.targets.containsKey(x)) {
							if (data.targets.get(x) >= 8)
								ct_targets_8++;
							else if (data.targets.get(x) >= 7)
								ct_targets_7++;
							else if (data.targets.get(x) >= 6)
								ct_targets_6++;
							else if (data.targets.get(x) >= 5)
								ct_targets_5++;
							else if (data.targets.get(x) >= 4)
								ct_targets_4++;
							else if (data.targets.get(x) >= 3)
								ct_targets_3++;
							else if (data.targets.get(x) >= 2)
								ct_targets_2++;
							else if (data.targets.get(x) >= 1)
								ct_targets_1++;
							else if (data.targets.get(x) >= 0)
								ct_targets++;

						}
					}

					for (String x : t_name_rank_map.keySet()) {
						if (data.targets.containsKey(x)) {
							if (data.targets.get(x) >= 8)
								t_targets_8++;
							else if (data.targets.get(x) >= 7)
								t_targets_7++;
							else if (data.targets.get(x) >= 6)
								t_targets_6++;
							else if (data.targets.get(x) >= 5)
								t_targets_5++;
							else if (data.targets.get(x) >= 4)
								t_targets_4++;
							else if (data.targets.get(x) >= 3)
								t_targets_3++;
							else if (data.targets.get(x) >= 2)
								t_targets_2++;
							else if (data.targets.get(x) >= 1)
								t_targets_1++;
							else if (data.targets.get(x) >= 0)
								t_targets++;

						}
					}

					data.winner_0 += t_targets + ct_targets;
					data.winner_1 += t_targets_1 + ct_targets_1;
					data.winner_2 += t_targets_2 + ct_targets_2;
					data.winner_3 += t_targets_3 + ct_targets_3;
					data.winner_4 += t_targets_4 + ct_targets_4;
					data.winner_5 += t_targets_5 + ct_targets_5;
					data.winner_6 += t_targets_6 + ct_targets_6;
					data.winner_7 += t_targets_7 + ct_targets_7;
					data.winner_8 += t_targets_8 + ct_targets_8;


					if (data.pitstop-- < 0) {

						int l_cnt = countOfCharFromLastTen(data.fargate_seq, 'L');
						int w_cnt = countOfCharFromLastTen(data.fargate_seq, 'W');
						System.out.println("W count in last 15 main_seq: " + w_cnt);


						fargate = true;
						int ct_score = 0;
						int t_score = 0;


						
						if (t_targets_3 > ct_targets_3)
							t_score += 1;
						else if (t_targets_3 < ct_targets_3)
							ct_score += 1;

						if (t_targets_4 > ct_targets_4)
							t_score += 1;
						else if (t_targets_4 < ct_targets_4)
							ct_score += 1;

						

						if (t_score < ct_score) {
							predicted = "ct";
						} else if (t_score > ct_score)
							predicted = "t";
						else
							predicted = "";

			
						
						
						//VOTE 1 : Absolute
						if(predicted.equals("t"))
								t_vote+=3;
						else 
						if(predicted.equals("ct"))
								ct_vote+=3;
						
						//VOTE 2 : Ambiguous
						if(ct_betters < t_betters)
								t_vote++;
						else 
						if(ct_betters > t_betters)
								ct_vote++;
						
						
						//VOTE 3 : Absolute
						if(ct_amount > t_amount)
							t_vote++;
						else 
						if(ct_amount < t_amount)
							ct_vote++;
						
						
						//VOTE 4 : Absolute
						if(t_xp > ct_xp)
							t_vote+=1;
						else 
						if(ct_xp < t_xp)
							ct_vote+=1;
						
						//VOTE 5 : 
						if (t_targets_2 > ct_targets_2)
							t_vote += 2;
						else if (t_targets_2 < ct_targets_2)
							ct_vote += 2;
						
						//VOTE 5 : 
						if (t_targets_1 > ct_targets_1)
							ct_vote += 3;
						else if (t_targets_1 < ct_targets_1)
							t_vote += 3;

					
						
						System.out.println("EXIT POLL ct : t >>>> "+ct_vote+" : "+t_vote);
						if(ct_vote > t_vote)
							predicted = "ct";
						else
						if(t_vote > ct_vote)
							predicted = "t";
						else
							predicted= "";
						
						System.out.println("Can Start ? " + data.start);
						System.out.println("T Score : CT Score ==> " + t_score + " : " + ct_score);
						if (fargate) {
							
							
							
							predict_l2 = predicted;

							
								data.predict_l3 = predict_l2;
								
							int degree = 1;
						 
//							if (!data.start) {
//								if (w_cnt >= 7 && data.fargate_seq.length() >= data.nextOpportunity)
//									data.start = true;
//
//								degree = 0;
//							}
							char lastElement = data.fargate_seq.charAt(data.fargate_seq.length() - 11);
							System.out.println("Last Outcome Element to be Removed :>>> "+lastElement);
							
							bet_amount = Double.parseDouble(df.format(data.multiplier * degree));

							if(!data.predict_l3.equals(""))
							for(int ind = 0 ; ind < 9 ; ind++) {
								data.prediction_cache[ind] = data.prediction_cache[ind+1];
							}
							if(!data.predict_l3.equals(""))
							data.prediction_cache[9] = data.predict_l3.toUpperCase().charAt(0);
						} else {
							predict_l2 = data.predict_l3 = "";

						}
					} else {
						predicted = "";
					}

					if (Math.abs(win_count - lost_count) > data.l_w_difference_max) {
						data.l_w_difference_max = Math.abs(win_count - lost_count);
					}
//					System.out.println("Chasers W:L ======>  " + data.chase_wins + " : " + data.chase_losses);
//					System.out.println("Empire Wallet = " + data.empire_wallet);
					System.out.println(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
//					System.out.println("LOSS WIN DIFFERENCE MAX : " + data.l_w_difference_max);
//					System.out.println("Toggle : " + toggle);

//					System.out.println("Decision Set Size: " + (ct_name_rank_map.size() + t_name_rank_map.size()));
//					System.out.println("Total betters = " + (t_betters + ct_betters));
//					System.out.println("AMOUNT CT:T >>>> "+ct_amount+" : "+t_amount);
					
					if (data.predict_l3.equals("")) {
//						System.out.println("Predict L2: " + predict_l2);
//						System.out.println("In PitStop || current coin : " + current_coin);
//						System.out.println("Fargate wallet : " + data.fargate_wallet + "  ::  " + "Wallet L3: "
//								+ df.format(data.wallet_l3) + "  :: " + "Displacement:" + df.format(displacement)
//								+ "  ::  " + "Wallet Max:" + df.format(wallet_max) + "  ::  " + "Wallet Min : "
//								+ df.format(wallet_min));
//						System.out.println(
//								"W : L Ratio = " + win_count + " : " + lost_count + " : " + data.bonus_counter);
//												System.out.println("Sequence         : " + main_seq.substring(main_seq.length() - 57));
//						System.out.println(
//										   "Fargate Sequence : " + data.fargate_seq.substring(data.fargate_seq.length() - 57));
					} else {

						System.out.println("Bet Amount : " + bet_amount);
						System.out.println(
								"=======================================================================================================");
						System.out.println("CT_XP vs T_XP :> " + ct_xp + " : " + t_xp);
						System.out.println("Prediction Cache : "+Arrays.toString(data.prediction_cache));
						System.out.println(
								"Current Coin is : " + current_coin + "  ::  " + "Predicted L3: " + data.predict_l3);
						System.out.println(
								"W : L Ratio = " + win_count + " : " + lost_count + " : " + data.bonus_counter);
						System.out.println("Sequence         : " + main_seq.substring(main_seq.length() - 57));
						System.out.println(
										   "Fargate Sequence : " + data.fargate_seq.substring(data.fargate_seq.length() - 57));
						System.out.println("Fargate wallet : " + data.fargate_wallet + "  ::  " + "Wallet L3: "
								+ df.format(data.wallet_l3) + "  :: " + "Displacement:" + df.format(displacement)
								+ "  ::  " + "Wallet Max:" + df.format(wallet_max) + "  ::  " + "Wallet Min : "
								+ df.format(wallet_min));
						System.out.println(
								"=======================================================================================================");

						System.out.println("\nContinus Algo Result : " + data.continuous_algo + "  ::  "
								+ "Switching Algo Result : " + data.switching_algo);
//
						System.out.println("\nExponential W : " + data.expoW);
						System.out.println("Exponential L : " + data.expoL);
//						System.out.println("\nPlayer Heat Map L : " + data.players_L);
//						System.out.println("Player Heat Map W : " + data.players_W);

					}

					update = true;

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
			new Algo().run(data, driver);
			System.out.println("\nMain Sequence : " + main_seq);
			return;
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

	public int countOfCharFromLastTen(String toCheckIn, char toCheck) {
		int cnt = 0;
		for (int i = toCheckIn.length() - 1; i >= toCheckIn.length() - 11; i--) {
			if (toCheckIn.charAt(i) == toCheck) {
				cnt++;
			}
		}
		return cnt;
	}
	

	public int continuousCountForLastTwenty(String toCheckIn) {
		int cnt = 0;
		for (int i = toCheckIn.length() - 1; i >= toCheckIn.length() - 21; i--) {
			if (toCheckIn.charAt(i) == toCheckIn.charAt(i - 1)) {
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

		return t_players;

	}

	public String[] scanCTPlayers(WebDriver driver) {
		WebElement ct_bet_container = driver.findElement(
				By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div/div/div[1]/div[2]/div/div[6]/div[1]/div/div[2]"));
		String[] ct_players = ct_bet_container.getAttribute("innerText").trim().split("\n");
		return ct_players;
	}

}
