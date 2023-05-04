package Winner;

public class Data {
	String count_seq;
	String amount_seq;
	String main_seq;
	String outcome_seq;
	String committ_seq;
	int committ_win;
	int committ_lost;
	int skip_beat_counter;
	
	int max_g_continuous;
	int max_l_continuous;
	int g_count;
	int g_amount;
	int l_count;
	int l_amount;
	
	int win_counter;
	
	int wallet_dynamic;
	double goal_amount;
	
	float wallet_together;
	float wallet_together_max;
	float wallet_together_min;
	
	int cnt_continuous_cnt;
	int cnt_switcher_cnt;
	
	int amt_continuous_cnt;
	int amt_switcher_cnt;
	
	int wallet_dynamic_together;
	
	int number_of_ones_count;
	int number_of_twos_count;
	int number_of_threes_count;
	int number_of_fours_count;
	int number_of_fives_count;
	int number_of_worst_cases_count;
	
	int number_of_ones_amount;
	int number_of_twos_amount;
	int number_of_threes_amount;
	int number_of_fours_amount;
	int number_of_fives_amount;
	int number_of_worst_cases_amount;
	
	int number_of_ones_main;
	int number_of_twos_main;
	int number_of_threes_main;
	int number_of_fours_main;
	int number_of_fives_main;
	int number_of_sixes_main;
	int number_of_sevens_main;
	int number_of_eights_main;
	int number_of_nines_main;
	int number_of_tens_main;
	int number_of_worst_cases_main;
	
	int continuity_counter_amount;
	int continuity_counter_count;
	int continuity_counter_main;
	int smooth_count;
	int spikey_amount;
	int smooth_amount;
	int spikey_count;
	
	int main_count_g;
	int main_count_l;
	int main_win_cause_l;
	int main_win_cause_g;
	String main_checker;
	
	int main_L;
	int main_W;
	double multiplier;
	boolean isRefreshed;
	String committ_side;
	boolean doToggle;
	String helper_seq;
	boolean was_bet_placed = false;
	Data(){
		skip_beat_counter = 0;
		doToggle = true;
		win_counter = 500;
		committ_win = 0;
		committ_win = 0;
		committ_side="";
		helper_seq = "WWW";
		committ_seq = "OOMRahawehNamahWW";
		was_bet_placed = false;
		isRefreshed = false;
		multiplier = 0.05;
		main_count_g = 0 ;
		outcome_seq = "";
		main_count_l = 0;
		main_win_cause_l = 0;
		main_win_cause_g = 0;
		main_checker = "";
		count_seq="GGG";
		amount_seq="GGG";
		main_seq="WWW";
		main_L=0;
		main_W=0;
		g_count=0;
		l_count=0;
		g_amount=0;
		l_amount=0;
		wallet_together=0;
		wallet_together_max=-9999;
		wallet_together_min=9999;
		max_g_continuous=0;
		max_l_continuous=0;
		goal_amount=0;
		cnt_continuous_cnt=0;
		cnt_switcher_cnt=0;
		
		amt_continuous_cnt=0;
		amt_switcher_cnt=0;
		
		number_of_ones_count=0;
		number_of_twos_count=0;
		number_of_threes_count=0;
		number_of_fours_count=0;
		number_of_fives_count=0;
		number_of_worst_cases_count=0;
		
		number_of_ones_amount=0;
		number_of_twos_amount=0;
		number_of_threes_amount=0;
		number_of_fours_amount=0;
		number_of_fives_amount=0;
		number_of_worst_cases_amount=0;
		
		number_of_ones_main=0;
		number_of_twos_main=0;
		number_of_threes_main=0;
		number_of_fours_main=0;
		number_of_fives_main=0;
		number_of_sixes_main=0;
		number_of_sevens_main=0;
		number_of_eights_main=0;
		number_of_nines_main=0;
		number_of_tens_main=0;
		number_of_worst_cases_main=0;
		
		continuity_counter_amount=1;
		continuity_counter_count=1;
		continuity_counter_main=1;
		
		smooth_count=0;
		spikey_count=0;
		smooth_amount=0;
		spikey_amount=0;
	}
}
