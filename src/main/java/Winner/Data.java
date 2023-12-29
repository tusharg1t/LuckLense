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
	
	List<Double> wallet_graph_data = new ArrayList<Double>();
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
	
	int winner_0;
	int winner_1;
	int winner_2;
	int winner_3;
	int winner_4;
	int winner_5;
	int winner_6;
	int winner_7;
	int winner_8;
	
	Map<String,Integer> targets = new HashMap<String,Integer>();
	Map<Integer,Integer> expoPR = new HashMap<Integer,Integer>();
	Map<Integer,Integer> expoL = new HashMap<Integer,Integer>();
	Map<Integer,Integer> expoW = new HashMap<Integer,Integer>();
	
		// < number of players, count>
	Map<Integer,Integer> players_L = new HashMap<Integer,Integer>();
	Map<Integer,Integer> players_W = new HashMap<Integer,Integer>();
	
	int continuous_algo;
	int switching_algo;
	boolean fargate;
	double wallet_l3;
	String predict_l3;
	int bonus_counter;
	double empire_wallet;
	boolean start;
	Character[] prediction_cache = new Character[10];
	Character[] outcome_cache = new Character[10];
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
		Arrays.fill(outcome_cache, 'B');
		start = false;
		winner_0 = winner_1 = winner_2 = winner_3 = winner_4 = winner_5 = 0;
		l_w_difference_max = Integer.MIN_VALUE;
		predict_l3 = "BBBBBBBBBBBBBBB";
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
		pitstop = 5;
		main_seq="BWBWBBWBWBBWBWB";
		main_L=0;
		main_W=0;
		
		wallet_together=0.0;
		wallet_max=-9999;
		wallet_min=9999;
		fargate_wallet = 0;
		displacement = 0;
		
	}
}
