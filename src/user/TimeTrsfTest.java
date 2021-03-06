/*******************************************************************************
 * The MIT License (MIT)
 * Copyright (c) 2013 Jing Pu, Yang Zhao, You Yuan, Huijie Yu 
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to 
 * deal in the Software without restriction, including without limitation the
 * rights to use, copy, modify, merge, publish, distribute, sublicense, and/or 
 * sell copies of the Software, and to permit persons to whom the Software is 
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in 
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR 
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, 
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE 
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER 
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, 
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN 
 * THE SOFTWARE.
 ******************************************************************************/
package user;

import java.util.Date;

import org.junit.Test;

/**@author youyuan
 * **/
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
