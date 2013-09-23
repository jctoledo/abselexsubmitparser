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
package ab.server;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ab.shared.AptamerBaseSelexSubmitParser;
import ab.shared.lib.Interaction;


/**
 * @author Jose Cruz-Toledo
 * 
 */
public class SelexSubmitParser extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5714015034095536468L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		resp.setContentType("text/json");
		PrintWriter out = null;
		try {
			out = resp.getWriter();
			String in = req.getParameter("se");
			if (in.length() != 0) {
				JSONObject jo = new JSONObject(in);
				AptamerBaseSelexSubmitParser abssp = new AptamerBaseSelexSubmitParser(jo);
				Interaction i =abssp.getInteractions().get(0);
				out.println(i.toString());
			}
		}
		catch (JSONException e) {
			out.println("INVALID JSON");
			out.flush();
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
}
