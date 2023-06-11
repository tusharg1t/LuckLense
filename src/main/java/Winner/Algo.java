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
		boolean fargate = false;
		boolean toggle = true;
		String predict_l2 = "";
		try {

			String timer = "";
			String current_coin = "";
			boolean update = false;
			String ct_count = "";
			String t_count = "";

			String ct_amount = "";
			String t_amount = "";
			String predicted = "";
			Map<String, Integer> ct_name_rank_map = new HashMap<String, Integer>();
			Map<String, Integer> t_name_rank_map = new HashMap<String, Integer>();

			while (true) {

				WebElement count_down_element = driver
						.findElement(By.xpath("//*[@id=\"page-scroll\"]/div[1]/div[2]/div/div[2]/div[3]/div/div[2]"));
				timer = count_down_element.getAttribute("innerText").toString();

				if (Double.parseDouble(timer) < 17 && Double.parseDouble(timer) > 10 && !bonus_placed) {
					bonus_placed = true;

//					placer.clearAmount(driver);
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
							data.targets.replace(x, 0);
						}
					}

				}

				if (Double.parseDouble(timer) < 5 && Double.parseDouble(timer) > 0 && !update) {

					WebElement ct_overview = driver.findElement(
							By.xpath("//*[@id=\"page-scroll\"]/div[1]/div[2]/div/div[6]/div[1]/div/div[1]"));
					WebElement t_overview = driver.findElement(
							By.xpath("//*[@id=\"page-scroll\"]/div[1]/div[2]/div/div[6]/div[3]/div/div[1]"));

					t_betters = Integer.parseInt(t_overview.getText().toString().split(" ")[0]);
					ct_betters = Integer.parseInt(ct_overview.getText().toString().split(" ")[0]);

					if (!predicted.equals("")) {
						if (predicted.equals(current_coin)) {

							main_seq += "W";

							int total = t_betters + ct_betters;
							data.players_W.putIfAbsent(total, 0);
							data.players_W.replace(total, data.players_W.get(total) + 1);

						} else {

							main_seq += "L";

							int total = t_betters + ct_betters;
							data.players_L.putIfAbsent(total, 0);
							if (!current_coin.equals("bonus"))
								data.players_L.replace(total, data.players_L.get(total) + 1);

						}

						t_name_rank_map.clear();
						ct_name_rank_map.clear();

						if (main_seq.charAt(main_seq.length() - 1) == main_seq.charAt(main_seq.length() - 2))
							data.continuous_algo++;
						else
							data.switching_algo++;

						int continuous;
						if (main_seq.charAt(main_seq.length() - 1) == 'L') {
							continuous = (new Algo()).countOfCharFromLast(main_seq, 'W');
							if (continuous > 0)
								data.expoPR.replace(continuous, data.expoPR.get(continuous) + 1);

							if (continuous > 0)
								data.expoW.replace(continuous, data.expoW.get(continuous) + 1);
						} else {
							continuous = (new Algo()).countOfCharFromLast(main_seq, 'L');
							if (continuous > 0)
								data.expoPR.replace(continuous, data.expoPR.get(continuous) + 1);

							if (continuous > 0)
								data.expoL.replace(continuous, data.expoL.get(continuous) + 1);

						}

					}

					if (data.pitstop < 0 && !predicted.equals("")) {
						data.wallet_graph_data.add(Double.parseDouble(df.format(wallet).toString()));

						if (current_coin.equals(predicted)) {

							data.fargate_wallet += data.multiplier;

						} else {

							data.fargate_wallet -= data.multiplier;

						}

					}

					if (data.pitstop < 0 && fargate && !predict_l2.equals("")) {

						if (current_coin.equals(predict_l2)) {

							data.fargate_seq += "W";
							wallet += data.multiplier;
							win_count++;
						} else {

							data.fargate_seq += "L";
							wallet -= data.multiplier;
							lost_count++;
						}

					}
					
					if(wallet >= 5*data.multiplier) {
						for(int i = 0 ; i < 5 ; i++)System.out.println("*");
						System.out.println("WON");
						for(int i = 0 ; i < 5 ; i++)System.out.println("*");
					}
					
					
					if(wallet <= -8*data.multiplier) {
						for(int i = 0 ; i < 5 ; i++)System.out.println("*");
						System.out.println("Lost");
						for(int i = 0 ; i < 5 ; i++)System.out.println("*");
					}
					if (current_coin.equals("bonus") && fargate && data.pitstop < 0) {

						data.bonus_counter++;
					}

					if (data.fargate_wallet >= 5) {
						fargate = false;
						data.fargate_wallet = 0;
					}

					if (data.fargate_wallet < -5 && !fargate
							&& main_seq.charAt(main_seq.length() - 1) == 'W') {
						fargate = true;
						data.fargate_wallet = 0;
					}

					if (data.fargate_wallet <= -2 && fargate) {
						fargate = false;
						data.fargate_wallet = 0;
					}

					predict_l2 = "";

//					if(main_seq.charAt(main_seq.length()-1) == 'W'
//							&&main_seq.charAt(main_seq.length()-2) == 'W'
//							&&main_seq.charAt(main_seq.length()-3) == 'L'
//							)
//						fargate = true;
//					
//					if(main_seq.charAt(main_seq.length()-1) == 'L'
//							&& main_seq.charAt(main_seq.length()-2) == 'L'
//							&&main_seq.charAt(main_seq.length()-3) == 'L'
//							&&main_seq.charAt(main_seq.length()-4) == 'L'
//							&&main_seq.charAt(main_seq.length()-5) == 'W'
//							)
//						fargate = true;

					if (wallet > wallet_max)
						wallet_max = wallet;

					if (wallet < wallet_min)
						wallet_min = wallet;

					if (wallet_max - wallet > displacement)
						displacement = wallet_max - wallet;

					WebElement t_bet_container = driver.findElement(
							By.xpath("//*[@id=\"page-scroll\"]/div[1]/div[2]/div/div[6]/div[3]/div/div[2]"));
					String[] t_players = t_bet_container.getAttribute("innerText").trim().split("\n");
					WebElement ct_bet_container = driver.findElement(
							By.xpath("//*[@id=\"page-scroll\"]/div[1]/div[2]/div/div[6]/div[1]/div/div[2]"));
					String[] ct_players = ct_bet_container.getAttribute("innerText").trim().split("\n");
					int ct_xp = 0;
					int t_xp = 0;
					double amount_ct = 0;
					double amount_t = 0;
					for (int i = 0; i < ct_players.length; i += 3) {
//													   <Name,Rank>
						ct_name_rank_map.put(ct_players[i + 1], Integer.parseInt(ct_players[i]));
						ct_xp += Integer.parseInt(ct_players[i]);

					}

					for (int i = 0; i < t_players.length; i += 3) {
						t_name_rank_map.put(t_players[i + 1], Integer.parseInt(t_players[i]));
						t_xp += Integer.parseInt(t_players[i]);
					}

					int ct_targets = 0;
					int t_targets = 0;

					for (String x : ct_name_rank_map.keySet()) {
						if (data.targets.containsKey(x))
							if (data.targets.get(x) > 2)
								ct_targets++;
					}

					for (String x : t_name_rank_map.keySet()) {
						if (data.targets.containsKey(x))
							if (data.targets.get(x) > 2)
								t_targets++;
					}

//					if((t_targets+ct_targets) > (t_betters+ct_betters)/7)
//						toggle = true;
//					else
//						toggle = false;

					if (((t_targets > ct_targets) && (t_betters > ct_betters))
							|| ((t_targets < ct_targets) && (t_betters < ct_betters))) {
						toggle = true;
					} else
						toggle = false;

					if (data.pitstop-- < 0 && t_targets != ct_targets) {

						if (toggle == true) {
							if (t_targets > ct_targets) {
								predicted = "ct";
							} else
								predicted = "t";

						} else if (toggle == false) {
							if (t_betters < ct_betters) {
								predicted = "ct";
							} else
								predicted = "t";

						}

						if (fargate) {

							predict_l2 = predicted;
//									placer.placeBet(driver, predicted, data.multiplier);
						}
					} else {
						predicted = "";
					}

					System.out.println(new SimpleDateFormat("HH:mm:ss").format(Calendar.getInstance().getTime()));
					System.out.println("Total betters = " + (t_betters + ct_betters));
					if (predict_l2.equals("")) {
						System.out.println("In PitStop || current coin : " + current_coin);
						System.out.println("Sequence : " + main_seq.substring(main_seq.length() - 57));
						System.out.println("Fargate wallet : " + data.fargate_wallet+"  ::  "+"Wallet : " + df.format(wallet) + "  :: " + "Displacement:"
								+ df.format(displacement) + "  ::  " + "Wallet Max:" + df.format(wallet_max) + "  ::  "
								+ "Wallet Min : " + df.format(wallet_min));
					} else {

						System.out.println("ct_targets : " + ct_targets + "  :: " + "t_targets : " + t_targets);
						System.out.println(
								"=======================================================================================================");
						System.out.println("Current Coin is : " + current_coin + "  ::  " + "Predicted : " + predicted);
						System.out.println(
								"W : L Ratio = " + win_count + " : " + lost_count + " : " + data.bonus_counter);
						System.out.println("Sequence : " + main_seq.substring(main_seq.length() - 57));
						System.out.println("Fargate Sequence : " + data.fargate_seq);

						System.out.println(
								"=======================================================================================================");
						System.out.println("Fargate wallet : " + data.fargate_wallet+"  ::  "+"Wallet : " + df.format(wallet) + "  :: " + "Displacement:"
								+ df.format(displacement) + "  ::  " + "Wallet Max:" + df.format(wallet_max) + "  ::  "
								+ "Wallet Min : " + df.format(wallet_min));
						System.out.println("\nContinus Algo Result : " + data.continuous_algo + "  ::  "
								+ "Switching Algo Result : " + data.switching_algo);

						System.out.println("\nExponential W : " + data.expoW);
						System.out.println("Exponential L : " + data.expoL);
						System.out.println("\nPlayer Heat Map L : " + data.players_L);
						System.out.println("Player Heat Map W : " + data.players_W);

					}

					update = true;

					System.out.println("\n\n\n\n");

				} else if (Double.parseDouble(timer) == 0.00) {
					update = false;
					bonus_placed = false;
				}

			}
		} catch (Exception e) {
			e.printStackTrace(System.out);
			System.out.println(data.wallet_graph_data.toString());
			data.displacement = displacement;
			data.main_seq = main_seq;
			data.main_W = win_count;
			data.main_L = lost_count;
			data.wallet_together = wallet;
			data.wallet_max = wallet_max;
			data.wallet_min = wallet_min;
			new Algo().run(data, driver);
			System.out.println("\nMain Sequence : " + main_seq);
			return;
		}

	}

	public int countOfCharFromLast(String toCheckIn, char toCheck) {
		int cnt = 0;
		for (int i = toCheckIn.length() - 2; i >= 0; i--) {
			if (toCheckIn.charAt(i) == toCheck) {
				cnt++;
			} else
				break;
		}
		return cnt;
	}
}
