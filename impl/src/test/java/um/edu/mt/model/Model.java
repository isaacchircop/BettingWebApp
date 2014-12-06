package um.edu.mt.model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Test;

import net.sourceforge.czt.modeljunit.Action;
import net.sourceforge.czt.modeljunit.AllRoundTester;
import net.sourceforge.czt.modeljunit.FsmModel;
import net.sourceforge.czt.modeljunit.Tester;
import net.sourceforge.czt.modeljunit.VerboseListener;

public class Model implements FsmModel {
	
	boolean isPremium = false;
	String username = "";
	boolean loggedin = false;
	String password = "12345678";
	
	public Object getState() {
		if(username.equals(""))
			return State.WAIT_FOR_REGISTER;
		else 
			if(!loggedin)
			return State.WAIT_FOR_LOGIN;
				else if(loggedin)
					return State.WAIT_FOR_BET;
		
		return 0;
	}

	public void reset(boolean arg0) {
		username = "";
		isPremium = false;
		loggedin = false;
	}
	
	public boolean RegisterGuard(){
		return getState().equals((State.WAIT_FOR_REGISTER));
	}
	
	public boolean LogInGuard(){
		double chance = Math.random();
		return((chance > 0.25) && (getState().equals((State.WAIT_FOR_LOGIN))));
	}
	
	@Action
	public void Register() throws Exception
	{
		username = RandomStringUtils.randomAlphanumeric(15);	
		double chance = Math.random();
		isPremium = (chance > 0.75);
		if(isPremium)
			addnewUser(username, "user_premium");
		else 
			addnewUser(username, "user_free");
	}
	
	
	@Action
	public void LogIn() throws Exception
	{
		if(LogInUser(username,password).equals("LOGIN_SUCCESS"))
			loggedin = true;
	}
	
	@Test
	public void test() {
		Tester t = new AllRoundTester(new Model());
		t.addListener(new VerboseListener());
		t.generate(10);
		t.buildGraph();
	}
	
	private void addnewUser(String username, String user_type) throws Exception {
		String urlParameters = "username="+username+"&password=12345678&name=User&surname=User&dob=1994-03-17&user_type="+user_type+"&ccNumber=5555555555554444&ccExpiry=2017-06&cvv=123";
		URL url = new URL("http://localhost:8080/bettingapp/registerUser");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
		writer.write(urlParameters);
		writer.flush();
		try{BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); reader.close();}
		catch(Exception e){}
		writer.close();
	}
	
	private String LogInUser(String username, String password) throws Exception {
		String urlParameters = "username="+username+"&password="+password;
		URL url = new URL("http://localhost:8080/bettingapp/loginUser");
		URLConnection conn = url.openConnection();
		conn.setDoOutput(true);
		OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
		writer.write(urlParameters);
		writer.flush();
		String input="";
		try{BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream())); 
		input = reader.readLine();
		reader.close();}
		catch(Exception e){}
		writer.close();
		return input;
	}
	

}
