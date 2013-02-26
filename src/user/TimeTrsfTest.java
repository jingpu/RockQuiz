package user;

import java.util.Date;

import org.junit.Test;

public class TimeTrsfTest {

	@Test
	public void test() {
		Date date2 = new Date();
		Date date1 = new Date(date2.getTime() - 60000);
		System.out.println(date2.toString());
		System.out.println(date1.toString());
		System.out.println(date2.getTime());
		System.out.println(date1.getTime());
		System.out.println(TimeTrsf.dscr(date1, date2));
	}

}
