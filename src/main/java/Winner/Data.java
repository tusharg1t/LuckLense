package Winner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Data {
	
	String main_seq;
	
	List<Double> wallet_graph_data = new ArrayList<Double>();
	double wallet_together;
	double wallet_max;
	double wallet_min;
	double fargate_wallet;
	
	String fargate_seq;
	
	int main_L;
	int main_W;
	double multiplier;
	boolean isRefreshed;
	int pitstop;
	double displacement;
	
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
	
	int bonus_counter;
	Data(){
		fargate = true;
		for(int i = 1 ; i <= 15; i++) {
			expoPR.put(i, 0);
			expoL.put(i, 0);
			expoW.put(i,0);
		}
		
		for(int i = 5 ; i <= 55; i++) {
			players_L.put(i,0);
			players_W.put(i,0);
		}
		wallet_graph_data.add(0.0);
		fargate_seq = "^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^L";
		isRefreshed = false;
		multiplier = 1;
		pitstop = 17;
		main_seq="^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T^T  Vijay Bhawah ||W";
		main_L=0;
		main_W=0;
		
		wallet_together=0.0;
		wallet_max=-9999;
		wallet_min=9999;
		fargate_wallet = 0;
		displacement = 0;
		
	}
}
