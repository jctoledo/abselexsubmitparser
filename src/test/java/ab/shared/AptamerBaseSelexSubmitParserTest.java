/**
 * Copyright (c) 2013  Jose Cruz-Toledo
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
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
 */
package ab.shared;


import java.io.File;
import java.io.IOException;
import java.net.URL;

import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONObject;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;


/**
 * @author  Jose Cruz-Toledo
 *
 */
public class AptamerBaseSelexSubmitParserTest {
	private static File inf = null;
	private static JSONObject testJson = null;
	private AptamerBaseSelexSubmitParser absp = null;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		//load in a sample minimal aptamer containing json object
		URL in = AptamerBaseSelexSubmitParserTest.class.getClass().getResource("/minimal_test.txt");
		String in_loc = in.getFile();
		inf = new File(in_loc);
		String contents = FileUtils.readFileToString(inf);
		testJson = new JSONObject(contents);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		inf = null;
		testJson = null;
		
	}

	@Test
	public void test() throws IOException {
		 absp = new AptamerBaseSelexSubmitParser(testJson);
		
		System.out.println(testJson);
		
		//fail("Not yet implemented");
	}

}
