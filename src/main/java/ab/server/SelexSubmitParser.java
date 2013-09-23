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
import java.util.List;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ab.shared.AptamerBaseSelexSubmitParser;
import ab.shared.FreebaseChecker;
import ab.shared.lib.AffinityExperiment;
import ab.shared.lib.Aptamer;
import ab.shared.lib.AptamerTarget;
import ab.shared.lib.Interaction;
import ab.shared.lib.SelexExperiment;


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
		JSONObject rm = new JSONObject();
		resp.setContentType("text/json");
		PrintWriter out = null;
		String in= null;
		String fn = null;
		try {
			out = resp.getWriter();
			try{
			in = req.getParameter("se");
			}catch(NullPointerException e){
				out.println("se parameter not specified!");
				e.printStackTrace();
			}
			try{
				fn = req.getParameter("fn");
			}catch(NullPointerException e){
				out.println("fn parameter not specified!");
				e.printStackTrace();
			}
			if (in.length() != 0 && fn.length() !=0) {
				JSONObject jo = new JSONObject(in);			
				AptamerBaseSelexSubmitParser abssp = new AptamerBaseSelexSubmitParser(jo);
				//now convert the AptamerBaseSelexSubmitParser into a write query?
				SelexExperiment se = abssp.getSelexExperiment();
				if(se != null){
					int aPmid = se.getPmid();
					//now check that the experiment has a pmid
					if(aPmid > 0){
						//first check if the PMID exists in freebase 
						String mid = FreebaseChecker.checkPmid(aPmid);
						if(mid == null){
							//now check the interactions
							List<Interaction> interactions = abssp.getInteractions();
							for (Interaction anInteraction : interactions) {
								//verify that each interaction has the following things
								//an aptamer target
								AptamerTarget at = anInteraction.getAptamer_target();
								if(at.getName().length() == 0){
									out.println("No target name provided. Please check submission :"+ fn);
								}
								//some affinityExperiments
								List<AffinityExperiment> ae_list = anInteraction.getAffinityExperiments();
								if(ae_list.size() == 0){
									out.println("No affinity experiments provided. Please check submission : "+ fn);
								}
								//check that all aptamers have types
								List<Aptamer> a_list = anInteraction.getAptamers();
								if(a_list.size() == 0){
									out.println("No aptamers provided. Please check submission : "+ fn);
								}else{
									for(Aptamer anApt: a_list){
										if(anApt.getPolymerType().length() == 0){
											out.println("No polymer type provided. Please check submission: "+ fn);
										}
									}
								}
							}//for interactions
							//now that all checks have passed
							//create a JSON representation of the Selex Experiment
							JSONObject se_json =  se.getJSONObject();
							out.println(se_json.toString());
							//create a JSON representation of the interactions
							JSONObject ints_json = interactions.getJSONObject()
							
						}else{
							out.println("PMID already in Freebase. Please check submission :"+fn);
						}
					}else{
						out.println("Invalid PMID provided : "+ aPmid + " for file : "+fn);
					}
				}
				
			}else{
				out.println("Please provide a selex experiment JSON and a filename using the 'se' and 'fn' parameters respectively");
			}
		}catch (JSONException e) {
			out.println("INVALID JSON");
			out.flush();
			e.printStackTrace();
		} catch(NullPointerException e){
			out.println("INVALID JSON error 2");
		}
		catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	
}
