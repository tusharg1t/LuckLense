package Winner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public class Data {
	
	String main_seq;
	Map<String, TechniqueStats> techStat = new HashMap<>();
	
	List<Double> wallet_graph_data = new ArrayList<Double>();
	List<Character> rolls = new ArrayList<>();
	boolean diceRollRatioFlag = false;
	double wallet_together;
	double target;
	int chase_wins;
	int chase_losses;
	int nextOpportunity;
	double wallet_max;
	double wallet_min;
	double fargate_wallet;
	double wallet_stop;
	boolean phase_1;
	String fargate_seq;
	int l_w_difference_max;
	int main_L;
	int main_W;
	double multiplier;
	boolean isRefreshed;
	int pitstop;
	double displacement;
	int chance;
	
	int win_cnt_commited;
	int loss_cnt_commited;
	
	Map<String,Integer> targets = new HashMap<String,Integer>();
	Map<String,Double> amountRecord = new HashMap<String,Double>();
	Map<Integer,Integer> expoPR = new HashMap<Integer,Integer>();
	Map<Integer,Integer> expoL = new HashMap<Integer,Integer>();
	Map<Integer,Integer> expoW = new HashMap<Integer,Integer>();
	Map<String, int[]> audit = new HashMap<String, int[]>();
		// < number of players, count>
	Map<Integer,Integer> players_L = new HashMap<Integer,Integer>();
	Map<Integer,Integer> players_W = new HashMap<Integer,Integer>();
	
	//To keep track of max amount bet by player so far
	Map<String, Double> playerMaxBetRecord = new HashMap<>();
	
	int continuous_algo;
	int switching_algo;
	boolean fargate;
	double wallet_l3;
	String predict_l3;
	int bonus_counter;
	double empire_wallet;
	boolean start;
	Character[] prediction_cache = new Character[10];
	Character[] outcome_cache = new Character[310];
	double global_wallet;
	String global_outcome_seq;
	int global_win_cnt;
	int global_loss_cnt;
	Data(){
		wallet_stop = 100;
		global_outcome_seq="";
		global_win_cnt=0;
		global_loss_cnt=0;
		global_wallet = 0;
		Arrays.fill(prediction_cache, 'B');
		Arrays.fill(outcome_cache, 'X');
		start = false;
		win_cnt_commited = 0;
		loss_cnt_commited = 0;
		l_w_difference_max = Integer.MIN_VALUE;
		predict_l3 = "=)";
		wallet_l3 = 0;
		chase_wins = 0;
		chase_losses = 0;
		empire_wallet = 0;
		fargate = false;
		target = 0;
		phase_1 = false;
		
		for(int i = 1 ; i <= 15; i++) {
			expoPR.put(i, 0);
			expoL.put(i, 0);
			expoW.put(i,0);
		}
		nextOpportunity = 10;
		
		for(int i = 5 ; i <= 55; i++) {
			players_L.put(i,0);
			players_W.put(i,0);
		}
		wallet_graph_data.add(0.0);
		fargate_seq = "BWBWBBWBWBBWBWB";
		isRefreshed = false;
		multiplier = 1;
		pitstop = 20;
		main_seq="BWBWBBWBWBBWBWB";
		main_L=0;
		main_W=0;
		
		wallet_together=0.0;
		wallet_max=-9999;
		wallet_min=9999;
		fargate_wallet = 0;
		displacement = 0;
		
		
		techStat.put("MAJOR", new TechniqueStats("",0,"",0));
		techStat.put("WINSUM", new TechniqueStats("",0,"",0));
		techStat.put("TARGET0", new TechniqueStats("",0,"",0));
		techStat.put("TARGET2", new TechniqueStats("",0,"",0));
		techStat.put("RAGE", new TechniqueStats("",0,"",0));
		techStat.put("SCORE", new TechniqueStats("",0,"",0));
		techStat.put("AMT", new TechniqueStats("",0,"",0));
		techStat.put("CNT", new TechniqueStats("",0,"",0));
		techStat.put("XPSUM", new TechniqueStats("",0,"",0));
		techStat.put("MAXXP", new TechniqueStats("",0,"",0));
		techStat.put("WHOFIRST", new TechniqueStats("",0,"",0));
		
		audit.put("MAJOR", new int[] {0,0,0});
		audit.put("WINSUM", new int[] {0,0,0});
		audit.put("TARGET0", new int[] {0,0,0});
		audit.put("TARGET2", new int[] {0,0,0});
		audit.put("RAGE", new int[] {0,0,0});
		audit.put("SCORE", new int[] {0,0,0});
		audit.put("AMT", new int[] {0,0,0});
		audit.put("CNT", new int[] {0,0,0});
		audit.put("XPSUM", new int[] {0,0,0});
		audit.put("MAXXP", new int[] {0,0,0});
		audit.put("WHOFIRST", new int[] {0,0,0});
		
		
	}
	@Override
	public String toString() {
		return "Data [main_seq=" + main_seq + ",\n\n*********************************************************************************************\n\n wallet_graph_data=" + wallet_graph_data + ",\n\n*********************************************************************************************\n\n wallet_together="
				+ wallet_together + ",\n\n*********************************************************************************************\n\n target=" + target + ",\n\n*********************************************************************************************\n\n chase_wins=" + chase_wins + ",\n\n*********************************************************************************************\n\n chase_losses="
				+ chase_losses + ",\n\n*********************************************************************************************\n\n nextOpportunity=" + nextOpportunity + ",\n\n*********************************************************************************************\n\n wallet_max=" + wallet_max + ",\n\n*********************************************************************************************\n\n wallet_min="
				+ wallet_min + ",\n\n*********************************************************************************************\n\n fargate_wallet=" + fargate_wallet + ",\n\n*********************************************************************************************\n\n wallet_stop=" + wallet_stop + ",\n\n*********************************************************************************************\n\n phase_1="
				+ phase_1 + ",\n\n*********************************************************************************************\n\n fargate_seq=" + fargate_seq + ",\n\n*********************************************************************************************\n\n l_w_difference_max=" + l_w_difference_max + ",\n\n*********************************************************************************************\n\n main_L="
				+ main_L + ",\n\n*********************************************************************************************\n\n main_W=" + main_W + ",\n\n*********************************************************************************************\n\n multiplier=" + multiplier + ",\n\n*********************************************************************************************\n\n isRefreshed=" + isRefreshed
				+ ",\n\n*********************************************************************************************\n\n pitstop=" + pitstop + ",\n\n*********************************************************************************************\n\n displacement=" + displacement + ",\n\n*********************************************************************************************\n\n win_cnt_commited=" + win_cnt_commited
				+ ",\n\n*********************************************************************************************\n\n loss_cnt_commited=" + loss_cnt_commited + ",\n\n*********************************************************************************************\n\n targets=" + targets + ",\n\n*********************************************************************************************\n\n amountRecord=" + amountRecord
				+ ",\n\n*********************************************************************************************\n\n expoPR=" + expoPR + ",\n\n*********************************************************************************************\n\n expoL=" + expoL + ",\n\n*********************************************************************************************\n\n expoW=" + expoW + ",\n\n*********************************************************************************************\n\n audit=" + audit + ",\n\n*********************************************************************************************\n\n players_L="
				+ players_L + ",\n\n*********************************************************************************************\n\n players_W=" + players_W + ",\n\n*********************************************************************************************\n\n continuous_algo=" + continuous_algo + ",\n\n*********************************************************************************************\n\n switching_algo="
				+ switching_algo + ",\n\n*********************************************************************************************\n\n fargate=" + fargate + ",\n\n*********************************************************************************************\n\n wallet_l3=" + wallet_l3 + ",\n\n*********************************************************************************************\n\n predict_l3=" + predict_l3
				+ ",\n\n*********************************************************************************************\n\n bonus_counter=" + bonus_counter + ",\n\n*********************************************************************************************\n\n empire_wallet=" + empire_wallet + ",\n\n*********************************************************************************************\n\n start=" + start
				+ ",\n\n*********************************************************************************************\n\n prediction_cache=" + Arrays.toString(prediction_cache) + ",\n\n*********************************************************************************************\n\n outcome_cache="
				+ Arrays.toString(outcome_cache) + ",\n\n*********************************************************************************************\n\n global_wallet=" + global_wallet + ",\n\n*********************************************************************************************\n\n global_outcome_seq="
				+ global_outcome_seq + ",\n\n*********************************************************************************************\n\n global_win_cnt=" + global_win_cnt + ",\n\n*********************************************************************************************\n\n global_loss_cnt=" + global_loss_cnt
				+ "]";
	}
	
	
	
}
