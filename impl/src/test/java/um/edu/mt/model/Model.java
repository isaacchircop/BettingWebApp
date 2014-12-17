package um.edu.mt.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;

import edu.uci.ics.jung.graph.util.Pair;
import net.sourceforge.czt.modeljunit.Action;
import net.sourceforge.czt.modeljunit.AllRoundTester;
import net.sourceforge.czt.modeljunit.FsmModel;
import net.sourceforge.czt.modeljunit.GreedyTester;
import net.sourceforge.czt.modeljunit.Tester;
import net.sourceforge.czt.modeljunit.VerboseListener;
import um.edu.mt.bd.Bet;
import um.edu.mt.bd.RiskLevel;
import um.edu.mt.impl.BetImpl;

public class Model implements FsmModel {

	final double PROB_PREMIUM = 0.25;
	final double PROB_PLACEANOTHER = 0.5;

	private String username = "";
	private String password = "";
	private boolean isPremium = false;
	private boolean isLoggedIn = false;
	private List<Bet> bets = new ArrayList<Bet>();

	private Random random = new Random();
	
	public ArrayList<String> responseTimes = new ArrayList<String>();
	
	public ArrayList<Pair> registerResponseTime = new ArrayList<Pair>();
	public ArrayList<Pair> betsResponseTime = new ArrayList<Pair>();
	public ArrayList<Pair> loginResponseTime = new ArrayList<Pair>();

	public State getState() {
		if(username.equals("")) {
			// Not registered
			return State.WAIT_FOR_REGISTER;
		}

		if(!isLoggedIn) {
			// Registered yet not logged in
			return State.WAIT_FOR_LOGIN;
		}

		if(isLoggedIn)
			return State.WAIT_FOR_BET;
		
		return null;
	}

	public void reset(boolean arg0) {
		username = "";
		password = "";
		isPremium = false;
		isLoggedIn = false;
	}


	public boolean registerGuard(){
		return getState() == State.WAIT_FOR_REGISTER;
	}
	@Action
	public void register() {
		// Set username and password
		username = "username" + random.nextInt(9999);
		password = "password123";

		// Create user account as free or premium depending on probability
		double chance = random.nextDouble();
		String user_type;
		if(chance < PROB_PREMIUM) {
			isPremium = true;
			user_type = "user_premium";
		} else {
			user_type = "user_free";
		}

		// Create account
		String urlParameters = "username="+username+"" +
				  			   "&password="+password+"" +
							   "&name=User" +
							   "&surname=User" +
							   "&dob=1994-03-17" +
							   "&user_type="+user_type+"" +
							   "&ccNumber=5555555555554444" +
							   "&ccExpiry=2017-06" +
							   "&cvv=123";

		try {
			URL url = new URL("http://localhost:8080/bettingapp/registerUser");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			double startTime = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			double elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Elapsed Time: " + elapsedTime);
			responseTimes.add(username+" Register "+ elapsedTime);	
			Pair mypair = new Pair(username, elapsedTime);
			registerResponseTime.add(mypair);
			reader.close();
			writer.close();
		}
		catch(IOException e){}
	}

	public boolean loginGuard(){
		return getState() == State.WAIT_FOR_LOGIN;
	}
	@Action
	public void login() {
		try {

			// Calculate chance of performing successful login
			String password = "";
			double invalidLoginChance = random.nextDouble();
			if (invalidLoginChance < 0.25) {
				password = "12345678";
			} else {
				password = this.password;
			}

			// Attempt login
			String urlParameters = "username=" + username +
								   "&password=" + password;

			URL url = new URL("http://localhost:8080/bettingapp/loginUser");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			double startTime = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			double elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Elapsed Time: " + elapsedTime);
			responseTimes.add(username+" Login "+ elapsedTime);	
			Pair mypair = new Pair(username, elapsedTime);
			loginResponseTime.add(mypair);
			String input = reader.readLine();
			reader.close();
			writer.close();
			if(input == null)
				isLoggedIn = true;
		}
		catch(Exception e){}
	}

	public boolean logoutGuard() {
		return getState() == State.WAIT_FOR_BET;
	}
	@Action
	public void logout() {
		isLoggedIn = false;
	}

	public boolean placeBetGuard() {
		return getState() == State.WAIT_FOR_BET;
	}
	@Action
	public void placeBet() {
		double amount;
		if (isPremium) {
			amount = random.nextInt(1900) + 100;
		} else {
			amount = random.nextInt(5) + 1;
		}

		// Decide on riskLevel probabilities

		try {
			String urlParameters = "username=" + username +
					  			   "&risk_level=" + "low" +
								   "&amount=" + amount;

			URL url = new URL("http://localhost:8080/bettingapp/placeBet");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);

			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			double startTime = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			double elapsedTime = System.currentTimeMillis() - startTime;
			System.out.println("Elapsed Time: " + elapsedTime);
			responseTimes.add(username+" Place Bet "+ elapsedTime);
			Pair mypair = new Pair(username, elapsedTime);
			betsResponseTime.add(mypair);
			String input = reader.readLine();
			reader.close();
			writer.close();

			if (input == null) {
				// Bet accepted by server
				bets.add(new BetImpl(username, RiskLevel.LOW, amount));
			}
			
			double placeanother = random.nextDouble();
			if (placeanother < PROB_PLACEANOTHER) {
				isLoggedIn = false;
			} 

		}
		catch(Exception e){}

	}

}
