package analytics;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class DiceRollRatio {
	
	public static void analyzeRatio(WebDriver driver) {
		System.out.println("Enter the starting seed : ");
		int seed = 3581;
		
		
		try {
			List<Double> ratio = new ArrayList<>();
			List<Integer> lable = new ArrayList<>();
			List<Integer> rolls = new ArrayList<>();
			List<Character> rollsCleaned = new ArrayList<>();
			
			double maxRatio = Double.MIN_VALUE;
			double minRatio = Double.MAX_VALUE;
			
			for(int itr = 0 ; itr < 5; itr++) {
				
				driver.get(String.format("https://csgoempire.com/history?seed=%s", Integer.toString(seed--)));
				WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
				wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"app\"]/div[1]/div[2]/div/div/div[1]/div[2]/div[2]/div[1]")));
				
				List<WebElement> grid = driver.findElements(By.className("text-seed"));
				System.out.println("Grid Size : "+grid.size());
				
				
				int i = 0;
				
				int offset = ratio.size();
	 			for(WebElement element : grid) {
					int roll = Integer.parseInt(element.getText().trim().split(" - ")[1].trim());
					lable.add(i);
					rolls.add(roll);
					
					if(roll == 0)
						rollsCleaned.add('B');
					else
						if(roll >7 && roll <= 14)
							rollsCleaned.add('C');
						else
							if(roll > 0  && roll <= 7)
								rollsCleaned.add('T');
				}
	 			
			}
			
			int analysisRange = 1000;
			for(int i = 0 ; i < rolls.size()-analysisRange ; i++) {
				int diceCount = 0;
				for(int j = i ; j < i+analysisRange ; j++) {
					if(rolls.get(j) == 0)
						diceCount++;
				}
				double currentRatio = (double)analysisRange/diceCount;
				ratio.add(currentRatio);
				if(currentRatio > maxRatio)
					maxRatio = currentRatio;
				if(currentRatio < minRatio)
					minRatio = currentRatio;
			}
			
			System.out.println("Lable : "+Arrays.toString(lable.toArray()));
 			System.out.println("Data : "+Arrays.toString(ratio.toArray()));
 			System.out.println("Max "+maxRatio);
 			System.out.println("Min "+minRatio);
 			
 			
 			double goldenRatio = 0.2;
 			double offset = 0.2;
 			int oldCount = 0;
 			
 			double maxGoldenRatio = Double.MIN_VALUE;
 			int maxWallet = Integer.MIN_VALUE;
 			while(true) {
 				boolean didBet = false;
 	 			int diceCount = 0;
 	 			int totalBetOn = 0;
 	 			for(int x = 0; x < rollsCleaned.size()-500; x++) {
 	 				
 	 				
 	 				int partitions = 5;
 	 				int internalRange = 100;
 	 				int itr = x;
 	 				List<Double> partitionRatio = new ArrayList<>();
 	 				for(int i = 0 ; i < partitions; i++) {
 	 					int partitionDiceCount = 0;
 	 					for(int j = itr; j < itr+internalRange; j++) {
 	 						if(rollsCleaned.get(j) == 'B')
 	 							partitionDiceCount++;
 	 					}
 	 					itr += 100;
 	 					
 	 					partitionRatio.add((double)internalRange/partitionDiceCount);
 	 				}
 	 				
 	 				double startSum = (double)(partitionRatio.get(0) );
 	 				double endSum = (double)(partitionRatio.get(4) );
 	 				
 	 				if((endSum) > (startSum) * goldenRatio ) {
 	 					didBet = true;
 	 				}else
 	 					didBet = false;
 	 				
 	 				
 	 				if(didBet) {
 	 					if(rollsCleaned.get(x)=='B')
 	 						diceCount++;
 	 					totalBetOn++;
 	 				}
 	 				
 	 			}
 	 			
 	 			System.out.println("Golden Ratio : "+goldenRatio);
 	 			System.out.println("Total Bet On: "+totalBetOn);
 				System.out.println("Dice Found: "+diceCount);
 				int wallet = 13*diceCount - totalBetOn;
 				System.out.println("Wallet : " + wallet);
 				if(maxWallet < wallet) {
 					maxGoldenRatio = goldenRatio;
 					maxWallet = wallet;
 				}
 				
 					goldenRatio+=offset;
 				if(oldCount == totalBetOn) {
 					System.out.println("\n\n\n\n\nBest Golden Ratio : " + maxGoldenRatio);
 					System.out.println("Best Wallet : " + maxWallet);
 					break;
 				}
 				oldCount = totalBetOn;
 				System.out.println("\n\n\n\n");
 			}
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			driver.close();
		}
		
	}
	
	
}
