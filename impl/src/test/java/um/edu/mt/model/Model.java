package um.edu.mt.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.Assert;
import org.junit.Assert;

import edu.uci.ics.jung.graph.util.Pair;
import net.sourceforge.czt.modeljunit.Action;
import net.sourceforge.czt.modeljunit.FsmModel;
import net.sourceforge.czt.modeljunit.LookaheadTester;
import net.sourceforge.czt.modeljunit.Tester;
import net.sourceforge.czt.modeljunit.VerboseListener;
import net.sourceforge.czt.modeljunit.coverage.ActionCoverage;
import net.sourceforge.czt.modeljunit.coverage.StateCoverage;
import net.sourceforge.czt.modeljunit.coverage.TransitionCoverage;
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
	public ArrayList<Pair> logoutResponseTime = new ArrayList<Pair>();

	public State getState() {
		if(username.equals(""))
			return State.WAIT_FOR_REGISTER;
		else if(!isLoggedIn)
			return State.WAIT_FOR_LOGIN;
		else 
			return State.WAIT_FOR_BET;
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
			
			Assert.assertTrue(((HttpURLConnection) conn).getResponseMessage().equals("OK"));
			double startTime = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			double elapsedTime = System.currentTimeMillis() - startTime;
			responseTimes.add(username+" Register "+ elapsedTime);	
			Pair mypair = new Pair(username, elapsedTime);
			registerResponseTime.add(mypair);
			reader.close();
			writer.close();
			
		}
		catch(IOException e){
		}
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
			
			if(invalidLoginChance < 0.25)
			{
				Assert.assertTrue(((HttpURLConnection) conn).getResponseMessage().equals("Forbidden"));
			}
			else {
				Assert.assertTrue(((HttpURLConnection) conn).getResponseMessage().equals("OK"));
			}			
			
			double startTime = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			double elapsedTime = System.currentTimeMillis() - startTime;
			responseTimes.add(username+" Login "+ elapsedTime);	
			Pair mypair = new Pair(username, elapsedTime);
			loginResponseTime.add(mypair);
			String input = reader.readLine();
			reader.close();
			writer.close();
			Assert.assertTrue(input == null);
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
		try {
			String urlParameters = "username=" + username;
			URL url = new URL("http://localhost:8080/bettingapp/logOut");
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
			writer.write(urlParameters);
			writer.flush();
			Assert.assertTrue(((HttpURLConnection) conn).getResponseMessage().equals("OK"));
			double startTime = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			double elapsedTime = System.currentTimeMillis() - startTime;
			responseTimes.add(username+" Place Bet "+ elapsedTime);
			Pair mypair = new Pair(username, elapsedTime);
			logoutResponseTime.add(mypair);
			reader.close();
			writer.close();
			isLoggedIn = false;
		}
		catch(Exception e){}
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

			Assert.assertTrue( ((HttpURLConnection) conn).getResponseMessage().equals("Accepted") || ((HttpURLConnection) conn).getResponseMessage().equals("Forbidden"));
			
			double startTime = System.currentTimeMillis();
			BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			double elapsedTime = System.currentTimeMillis() - startTime;
			responseTimes.add(username+" Place Bet "+ elapsedTime);
			Pair mypair = new Pair(username, elapsedTime);
			betsResponseTime.add(mypair);
			String input = reader.readLine();
			reader.close();
			writer.close();
			
			bets.add(new BetImpl(username, RiskLevel.LOW, amount));
			
			double placeanother = random.nextDouble();
			if (placeanother < PROB_PLACEANOTHER) {
				logout();
			} 

		}
		catch(Exception e){}

	}
	
	@Test
	public void test() {
		Model mymodel = new Model();
		
		TransitionCoverage tran = new TransitionCoverage();
		StateCoverage stat = new StateCoverage();
		ActionCoverage act = new ActionCoverage();

		Tester t = new LookaheadTester(mymodel);
		t.addListener(new VerboseListener());
		t.generate(15);
		t.buildGraph();
		//System.out.println("Transitions "+tran.getDetails());
		t.printCoverage();
		
	}
	

}
