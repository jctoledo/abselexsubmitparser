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
package ab.shared.lib;

import java.util.ArrayList;
import java.util.List;
import org.codehaus.jettison.json.JSONArray;

import ab.shared.FreebaseHelper;


/**
 * @author Jose Cruz-Toledo
 * 
 */
public class AffinityExperiment {
	Double kd;
	String kdRange;
	String kdError;
	List<String> affinityMethodsNames;
	List<String> affinityMethodsMids;
	List<String> buffAgentNames;
	List<String> buffAgentMids;
	Double pH;
	List<String> metalCationConcentrations;
	Double temperature;

	public AffinityExperiment(Double aKd, String aKdRange, String aKdError,
			List<String> someAffinityMethodNames,
			List<String> someBufferingAgentNames, Double apH,
			List<String> someMetalCationConcs, Double aTemperature) {
		kd = aKd;
		kdRange = aKdRange;
		affinityMethodsNames = someAffinityMethodNames;
		buffAgentNames = someBufferingAgentNames;
		pH = apH;
		metalCationConcentrations = someMetalCationConcs;
		temperature = aTemperature;
		affinityMethodsMids = makeAMMids();
		buffAgentMids = makeBAMids();
	}

	/**
	 * Iterates over the affinity methods names and queries freebase in search
	 * for their corresponding MIDs
	 * 
	 * @return
	 */
	private List<String> makeAMMids() {
		List<String> rm = new ArrayList<String>();
		for (String aName : this.getAffinityMethodsNames()) {
			if (aName.length() > 0) {
				String anMid = FreebaseHelper.getMidFromTopicNameAndType(aName,
						"/base/aptamer/affinity_method");
				if (anMid != null) {
					rm.add(anMid);
				}
			}
		}
		return rm;
	}
	/**
	 * Iterates over the Buffering Agents names and queries freebase in search
	 * for their corresponding MIDs
	 * 
	 * @return
	 */
	private List<String> makeBAMids(){
		List<String> rm = new ArrayList<String>();
		for(String aName: this.getBuffAgentNames()){
			if(aName.length()>0){
				String anMid = FreebaseHelper.getMidFromTopicNameAndType(aName, "/base/aptamer/buffering_agent");
				if(anMid != null){
					rm.add(anMid);
				}
			}
		}
		return rm;		
	}
	/**
	 * @return the kd
	 */
	public Double getKd() {
		return kd;
	}

	/**
	 * @return the kdRange
	 */
	public String getKdRange() {
		return kdRange;
	}

	/**
	 * @return the kdError
	 */
	public String getKdError() {
		return kdError;
	}

	/**
	 * @return the affinityMethodsNames
	 */
	public List<String> getAffinityMethodsNames() {
		return affinityMethodsNames;
	}

	public JSONArray getAffinityMethodsNamesJsonArray(){
		JSONArray rm = new JSONArray();
		for(String amn: affinityMethodsNames){
			rm.put(amn);
		}
		return rm;
	}
	/**
	 * @return the affinityMethodsMids
	 */
	public List<String> getAffinityMethodsMids() {
		return affinityMethodsMids;
	}
	public JSONArray getAffinityMethodsMidsJsonArray(){
		JSONArray rm = new JSONArray();
		for(String amn: affinityMethodsMids){
			rm.put(amn);
		}
		return rm;
	}

	/**
	 * @return the buffAgentNames
	 */
	public List<String> getBuffAgentNames() {
		return buffAgentNames;
	}
	public JSONArray getBufferingAgentNamesJsonArray(){
		JSONArray rm = new JSONArray();
		for(String amn: buffAgentNames){
			rm.put(amn);
		}
		return rm;
	}
	
	/**
	 * @return the buffAgentMids
	 */
	public List<String> getBuffAgentMids() {
		return buffAgentMids;
	}

	public JSONArray getBufferingAgentMidsJsonArray(){
		JSONArray rm = new JSONArray();
		for(String amn: buffAgentMids){
			rm.put(amn);
		}
		return rm;
	}
	/**
	 * @return the pH
	 */
	public Double getpH() {
		return pH;
	}

	/**
	 * @return the metalCationConcentrations
	 */
	public List<String> getMetalCationConcentrations() {
		return metalCationConcentrations;
	}
	public JSONArray getMetalCationConcentrationsJsonArray(){
		JSONArray rm = new JSONArray();
		for(String amn: metalCationConcentrations){
			rm.put(amn);
		}
		return rm;
	}

	/**
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AffinityExperiment [kd=" + kd + ", kdRange=" + kdRange
				+ ", kdError=" + kdError + ", affinityMethodsNames="
				+ affinityMethodsNames + ", affinityMethodsMids="
				+ affinityMethodsMids + ", buffAgentNames=" + buffAgentNames
				+ ", buffAgentMids=" + buffAgentMids + ", pH=" + pH
				+ ", metalCationConcentrations=" + metalCationConcentrations
				+ ", temperature=" + temperature + "]";
	}

	
}
