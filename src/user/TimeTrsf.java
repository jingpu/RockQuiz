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

import java.text.SimpleDateFormat;
import java.util.Date;

/**@author youyuan
 * **/

public class TimeTrsf {
	 
	private static long ahour = 1000*60*60;
	private static long amin = 1000*60;
	private static long asec = 1000;
	
	public static String dscr(Date date1, Date date2){
		SimpleDateFormat fmt1 = new SimpleDateFormat("yyyyMMdd");
		SimpleDateFormat fmt2 = new SimpleDateFormat("yyyy");
		StringBuilder description = new StringBuilder();
		if(fmt1.format(date1).equals(fmt1.format(date2))){
			long difference = date2.getTime() - date1.getTime();
			
			if(difference >= 2*ahour) {
				description.append(difference/ahour).append(" hours ago");
			} else if(difference >= ahour) {
				description.append("1 hour ago");
			} else if(difference >= 2*amin) {
				description.append(difference/amin).append(" mins ago");
			} else if(difference >= amin) {
				description.append("1 min ago");
			} else if(difference >= 2*asec) {
				description.append(difference/asec).append(" secs ago");
			} else {
				description.append(" just now");
			} 
		} else if(fmt2.format(date1).equals(fmt2.format(date2))){
			SimpleDateFormat fmt3 = new SimpleDateFormat("MMM dd 'at' hh:mm");
			description.append(fmt3.format(date1));
		} else {
			SimpleDateFormat fmt4 = new SimpleDateFormat("yyyy-MM-dd");
			description.append(fmt4.format(date1));
		}
		return description.toString();
	}
}
