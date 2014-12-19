package um.edu.mt.model;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import edu.uci.ics.jung.graph.util.Pair;
import net.sourceforge.czt.modeljunit.GreedyTester;
import net.sourceforge.czt.modeljunit.LookaheadTester;
import net.sourceforge.czt.modeljunit.RandomTester;
import net.sourceforge.czt.modeljunit.Tester;
import net.sourceforge.czt.modeljunit.VerboseListener;
import net.sourceforge.czt.modeljunit.coverage.ActionCoverage;
import net.sourceforge.czt.modeljunit.coverage.StateCoverage;
import net.sourceforge.czt.modeljunit.coverage.TransitionCoverage;
import net.sourceforge.czt.modeljunit.coverage.TransitionPairCoverage;

public class ModelRunner {
	
	public ArrayList<String> responseTimes = new ArrayList<String>();
	
	public ArrayList<Pair> registerResponseTime = new ArrayList<Pair>();
	public ArrayList<Pair> betsResponseTime = new ArrayList<Pair>();
	public ArrayList<Pair> loginResponseTime = new ArrayList<Pair>();
	public ArrayList<Pair> logoutResponseTime = new ArrayList<Pair>(); 
	
	@AfterClass
	public void output_responsetimes()
	{
		PrintWriter out;
		double total = 0;
		double avg = 0;
		try {
			out = new PrintWriter("register_responsetimes_5User.txt");		
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
			
			out = new PrintWriter("login_responsetimes_5User.txt");
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
			
			out = new PrintWriter("bets_responsetimes_5User.txt");
			out.println("\nPlace Bet Response Time");
			for(Pair p : betsResponseTime)
			{
				out.print(p.getFirst()+ " ");
				out.println(String.valueOf(p.getSecond()));
				total = total + ((Double)(p.getSecond())).doubleValue();
			}
			
			
			avg = total/betsResponseTime.size();
			out.println("\nAverage Response Time: " + avg);
			out.close();
						
			avg = 0.0;
			total = 0.0;
			
			out = new PrintWriter("logout_responsetimes_5User.txt");
			out.println("\nLog Out Response Time");
			for(Pair p : logoutResponseTime)
			{
				out.print(p.getFirst()+ " ");
				out.println(String.valueOf(p.getSecond()));
				total = total + ((Double)(p.getSecond())).doubleValue();
			}
			
			avg = total/logoutResponseTime.size();
			out.println("\nAverage Response Time: " + avg);
			
			out.close();
				
		} catch (FileNotFoundException e) 
			{e.printStackTrace();}
	}
	
	@Test(threadPoolSize = 1, invocationCount = 1)
	public void test() {
		Model mymodel = new Model();
		
		TransitionCoverage tran = new TransitionCoverage();
		TransitionPairCoverage tranp = new TransitionPairCoverage();
		StateCoverage stat = new StateCoverage();
		ActionCoverage act = new ActionCoverage();

		//Tester t = new GreedyTester(mymodel);
		//Tester t = new RandomTester(mymodel);
		Tester t = new LookaheadTester(mymodel);
		t.addCoverageMetric(tran);
		t.addCoverageMetric(stat);
		t.addCoverageMetric(act);
		t.addCoverageMetric(tranp);
		
		//t.addListener(new VerboseListener());
		t.generate(20);
		t.buildGraph();
		t.printCoverage();
		
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
		
		for(Pair p : mymodel.logoutResponseTime)
		{
			logoutResponseTime.add(p);	
		}
	}

}
