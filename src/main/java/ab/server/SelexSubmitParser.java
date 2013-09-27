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
import java.util.Map;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import ab.shared.AptamerBaseSelexSubmitParser;
import ab.shared.FreebaseHelper;
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
	protected void doPost(HttpServletRequest req, HttpServletResponse resp){
		Map<String, String[]> rm = req.getParameterMap();
		doGet(req, resp);
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) {
		JSONObject rm = new JSONObject();
		resp.setContentType("text/json");
		PrintWriter out = null;
		String in= null;
		String fn = null;
		try {
			out = resp.getWriter();
			in = req.getParameter("se");
			fn = req.getParameter("fn");
			if (in != null && fn !=null) {
				JSONObject jo = new JSONObject(in);			
				AptamerBaseSelexSubmitParser abssp = new AptamerBaseSelexSubmitParser(jo);
				//now convert the AptamerBaseSelexSubmitParser into a write query?
				SelexExperiment se = abssp.getSelexExperiment();
				if(se != null){
					int aPmid = se.getPmid();
					//now check that the experiment has a pmid
					if(aPmid >1 ){
						//first check if the PMID exists in freebase 
						String mid = FreebaseHelper.checkPmid(aPmid);
						if(mid == null){
							//start preparing the output
							//now check the interactions
							JSONArray interactions_arr = new JSONArray();
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
								//now get the json version for these interactions
								JSONObject j = anInteraction.getJSONObject();
								interactions_arr.put(j);
							}//for interactions
						
							
							rm.put("\"se\"", se.getJSONObject());
							
							rm.put("\"interactions\"", interactions_arr);
							
							out.println(rm);
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
