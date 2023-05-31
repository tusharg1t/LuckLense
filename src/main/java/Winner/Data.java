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
	
	int bonus_counter;
	Data(){
		
		for(int i = 1 ; i <= 15; i++) {
			expoPR.put(i, 0);
			expoL.put(i, 0);
			expoW.put(i,0);
		}
		
		isRefreshed = false;
		multiplier = 1;
		pitstop = 17;
		main_seq="OOmMahaLakshmiNamoNamah || Om Hrim Aaim Hrim Om Saraswate Namah || Jai Mata Di || W";
		main_L=0;
		main_W=0;
		
		wallet_together=0.8;
		wallet_max=-9999;
		wallet_min=9999;
		
		displacement = 0;
		
	}
}
