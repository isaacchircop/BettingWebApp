package um.edu.mt.model;

import org.testng.annotations.Test;

import net.sourceforge.czt.modeljunit.GreedyTester;
import net.sourceforge.czt.modeljunit.Tester;
import net.sourceforge.czt.modeljunit.VerboseListener;

public class ModelRunner {
	
	@Test(threadPoolSize = 100, invocationCount = 100)
	public void test() {
		Tester t = new GreedyTester(new Model());
		t.addListener(new VerboseListener());
		t.generate(100);
		t.buildGraph();
	}

}
