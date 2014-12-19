package um.edu.mt.impl;

import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class UserAccountImplTest {

	UserAccountImpl useraccount;
	Calendar date = Calendar.getInstance();
	Calendar ccExpiry = Calendar.getInstance();
	
	@Before
	public void setup() {
		date.set(1990, 5, 30);
		ccExpiry.set(2016, 12, 12);
		useraccount = new UserAccountImpl("toni", "12345678", "joseph", "bonucci", date, false, "4000056655665556", ccExpiry, "123");
	}

	@After
	public void teardown() {
		date.set(1990, 5, 30);
		ccExpiry.set(2016, 12, 12);
		useraccount = new UserAccountImpl("toni", "12345678", "joseph", "bonucci", date, false, "4000056655665556", ccExpiry, "123");
	}
	
	@Test
	public void test_block(){
		useraccount.block();
		assertTrue(useraccount.isBlocked());
		try {
			Thread.sleep(100);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		assertTrue(useraccount.unblkTimeLeft()>=100);
	}
	
	@Test
	public void test_blocktimeleft(){
		useraccount.block();
		try {
			Thread.sleep(100);
		} catch(InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		assertTrue(useraccount.unblkTimeLeft()>=100);
	}
	
	@Test
	public void test_blocktimeleft_0(){
		useraccount.unblock();
		assertTrue(useraccount.unblkTimeLeft()==0);
	}
	
	@Test
	public void test_unblock(){
		useraccount.block();
		useraccount.unblock();
		assertFalse(useraccount.isBlocked());
	}
	
	@Test
	public void test_incrLoginTries(){
		int tries = useraccount.getLoginTries();
		useraccount.incrLoginTries();
		assertTrue(tries+1 == useraccount.getLoginTries());
	}
	
	@Test
	public void test_resetLoginTries(){
		useraccount.incrLoginTries();
		useraccount.incrLoginTries();
		useraccount.resetLoginTries();
		assertTrue(useraccount.getLoginTries()==0);
	}
	

}
