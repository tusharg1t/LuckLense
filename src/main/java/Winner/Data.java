package Winner;

import java.util.HashMap;
import java.util.Map;

public class Data {
	
	String main_seq;
	
	
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
	Data(){
		expoPR.put(1,0);
		expoPR.put(2,0);
		expoPR.put(3,0);
		expoPR.put(4,0);
		expoPR.put(5,0);
		expoPR.put(6,0);
		expoPR.put(7,0);
		expoPR.put(8,0);
		expoPR.put(9,0);
		expoPR.put(10,0);
		expoPR.put(11,0);
		isRefreshed = false;
		multiplier = 1;
		pitstop = 25;
		main_seq="LLWL";
		main_L=0;
		main_W=0;
		
		wallet_together=0;
		wallet_max=-9999;
		wallet_min=9999;
		
		displacement = 0;
		
	}
}
