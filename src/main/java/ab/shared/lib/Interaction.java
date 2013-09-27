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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;

/**
 * @author Jose Cruz-Toledo
 * 
 */
public class Interaction {
	private List<AffinityExperiment> affinityExperiments;
	private List<Aptamer> aptamers;
	private AptamerTarget aptamer_target;

	public Interaction(List<AffinityExperiment> someAffExps,
			List<Aptamer> someApts, AptamerTarget anAptTarget) {
		this.affinityExperiments = someAffExps;
		this.aptamer_target = anAptTarget;
		this.aptamers = someApts;
	}

	public JSONObject getJSONObject() throws JSONException {
		JSONObject rm = new JSONObject();
		// get the aptamer target
		if (this.getAptamer_target() != null) {
			JSONObject at = new JSONObject();
			at.put("\"name\"", this.getAptamer_target().getName());
			if (this.getAptamer_target().getName().length() > 0) {
				at.put("\"mid\"", this.getAptamer_target().getMid());
			}
			rm.put("aptamer_target", at);
		} else {
			throw new JSONException("No target specified! errorno 43200");
		}
		// get the affinity experiment details
		List<AffinityExperiment> ae_list = this.getAffinityExperiments();
		if (ae_list.size() > 0) {
			JSONArray ae_arr = new JSONArray();
			for (AffinityExperiment ae : ae_list) {
				JSONObject a = new JSONObject();
				if (ae.getAffinityMethodsNamesJsonArray().length() > 0) {
					a.put("\"affinity_methods_names\"",
							ae.getAffinityMethodsNamesJsonArray());
				}
				if (ae.getBufferingAgentMidsJsonArray().length() > 0) {
					a.put("\"affinity_methods_mids\"",
							ae.getAffinityMethodsMidsJsonArray());
				}
				if (ae.getKd() > 0) {
					a.put("\"kd\"", ae.getKd());
				}
				if (ae.getKdError() != null && ae.getKdError().length() > 0) {
					a.put("\"kd_error\"", ae.getKdError());
				}
				if (ae.getKdRange() != null && ae.getKdRange().length() > 0) {
					a.put("\"kd_range\"", ae.getKdRange());
				}
				if (ae.getMetalCationConcentrationsJsonArray() != null
						&& ae.getMetalCationConcentrationsJsonArray().length() > 0) {
					a.put("ae_metal_cation_concs",
							ae.getMetalCationConcentrationsJsonArray());
				}
				if (ae.getpH() != null && ae.getpH() > 0) {
					a.put("\"ph\"", ae.getpH());
				}
				if (ae.getTemperature() != null && ae.getTemperature() > 0) {
					a.put("\"temperature\"", ae.getTemperature());
				}
				ae_arr.put(a);
				rm.put("\"affinity_experiments\"", ae_arr);
			}
		} else {
			throw new JSONException(
					"No affinity experiments specified!! errno 43431");
		}
		// now the aptamers
		List<Aptamer> ap_list = this.getAptamers();
		JSONArray aptamers = new JSONArray();
		for (Aptamer anApt : ap_list) {
			aptamers.put(anApt.getJSONObject());
		}
		rm.put("\"aptamers\"", aptamers);

		return rm;
	}

	/**
	 * Searches the aptamer list for all instances of minimal aptamers and
	 * returns a map where the key is an aptamer and the value are any minimal
	 * aptamers derived from it
	 * 
	 * @param someAptamers
	 *            a list of aptamers that may contain minimal aptamers
	 * @return a map where the key is an aptamer and the value is a list of
	 *         minimal aptamers derived from it
	 */
	private Map<Aptamer, List<MinimalAptamer>> findMinimalAptamers(
			List<Aptamer> someAptamers) {
		HashMap<Aptamer, List<MinimalAptamer>> rm = new HashMap<Aptamer, List<MinimalAptamer>>();
		// first find all the minimal aptamers
		for (Aptamer anApt : someAptamers) {
			if (anApt instanceof MinimalAptamer) {
				MinimalAptamer ma = (MinimalAptamer) anApt;
				// find the parent aptamer in someApts
				for (Aptamer aParent : someAptamers) {
					if (!aParent.equals(ma)) {
						if (ma.getParent().equals(aParent)) {
							// found a parent
							if (rm.containsKey(aParent)) {
								// get the list and add one at the end
								rm.get(aParent).add(ma);
							} else {
								// create a list and add it at key
								ArrayList<MinimalAptamer> al = new ArrayList<MinimalAptamer>();
								al.add(ma);
								rm.put(aParent, al);
							}
						}
					}
				}
			}
		}
		return rm;
	}

	/**
	 * @return the affinityExperiments
	 */
	public List<AffinityExperiment> getAffinityExperiments() {
		return affinityExperiments;
	}

	/**
	 * @return the aptamers
	 */
	public List<Aptamer> getAptamers() {
		return aptamers;
	}

	/**
	 * @return the aptamer_target
	 */
	public AptamerTarget getAptamer_target() {
		return aptamer_target;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Interaction [affinityExperiments=" + affinityExperiments
				+ ", aptamers=" + aptamers + ", aptamer_target="
				+ aptamer_target + "]";
	}

}
