package um.edu.mt.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import edu.uci.ics.jung.graph.util.Pair;
import net.sourceforge.czt.modeljunit.GreedyTester;
import net.sourceforge.czt.modeljunit.Tester;
import net.sourceforge.czt.modeljunit.VerboseListener;

public class ModelRunner {
	
	public ArrayList<String> responseTimes = new ArrayList<String>();
	
	public ArrayList<Pair> registerResponseTime = new ArrayList<Pair>();
	public ArrayList<Pair> betsResponseTime = new ArrayList<Pair>();
	public ArrayList<Pair> loginResponseTime = new ArrayList<Pair>();
	
	@AfterClass
	public void output_responsetimes()
	{
		PrintWriter out;
		double total = 0;
		double avg = 0;
		try {
			out = new PrintWriter("register_responsetimes_30User.txt");		
			out.println("\nRegister Response Time");
			for(Pair p : registerResponseTime)
			{
				out.print(p.getFirst()+ " ");
				out.println(String.valueOf(p.getSecond()));
				total = total + ((Double)(p.getSecond())).doubleValue();
			}
			
			avg = total/registerResponseTime.size();
			out.println("\nAverage Response Time: " + avg);
			
			avg = 0.0;
			total = 0.0;
			
			out.close();
			
			out = new PrintWriter("login_responsetimes_30User.txt");
			out.println("\nLogin Response Time");
			
			for(Pair p : loginResponseTime)
			{
				out.print(p.getFirst()+ " ");
				out.println(String.valueOf(p.getSecond()));
				total = total + ((Double)(p.getSecond())).doubleValue();
			}
			
			avg = total/loginResponseTime.size();
			out.println("\nAverage Response Time: " + avg);
			
			avg = 0.0;
			total = 0.0;
			
			out.close();
			
			out = new PrintWriter("bets_responsetimes_30User.txt");
			out.println("\nPlace Bet Response Time");
			for(Pair p : betsResponseTime)
			{
				out.print(p.getFirst()+ " ");
				out.println(String.valueOf(p.getSecond()));
				total = total + ((Double)(p.getSecond())).doubleValue();
			}
			
			avg = total/betsResponseTime.size();
			out.println("\nAverage Response Time: " + avg);
			
			avg = 0.0;
			total = 0.0;
			
			out.close();
				
		} catch (FileNotFoundException e) 
			{e.printStackTrace();}
	}
	
	@Test(threadPoolSize = 30, invocationCount = 30)
	public void test() {
		Model mymodel = new Model();
		Tester t = new GreedyTester(mymodel);
		t.addListener(new VerboseListener());
		t.generate(10);
		
		for(Pair p : mymodel.registerResponseTime)
		{
			registerResponseTime.add(p);	
		}

		for(Pair p : mymodel.loginResponseTime)
		{
			loginResponseTime.add(p);	
		}
		
		for(Pair p : mymodel.betsResponseTime)
		{
			betsResponseTime.add(p);	
		}
	}

}
