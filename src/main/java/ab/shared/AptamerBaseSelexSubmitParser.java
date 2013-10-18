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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


import ab.shared.lib.*;

/**
 * @author Jose Cruz-Toledo
 * 
 */
public class AptamerBaseSelexSubmitParser {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2415871140688842740L;

	private File jsonFile = null;

	/**
	 * A map where the key is the form page number and the value is the JSON for
	 * that page
	 */
	private Map<Integer, String> pageMap = new HashMap<Integer, String>();
	private List<Interaction> interactions;
	private SelexExperiment selexExperiment;

	@SuppressWarnings("unchecked")
	public AptamerBaseSelexSubmitParser(JSONObject aJSONObject) {
		Iterator<String> itr = aJSONObject.keys();
		while (itr.hasNext()) {
			try {
				String key = itr.next();
				String s;
				s = aJSONObject.getString(key);
				Integer aKey = null;
				aKey = Integer.parseInt(key.replaceAll("\"", ""));
				pageMap.put(aKey, s);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		interactions = makeInteractions(pageMap.get(2), pageMap.get(3),
				pageMap.get(4));
		selexExperiment = makeSelexExperiment(pageMap.get(1), pageMap.get(2));
	}
	
	@SuppressWarnings("unchecked")
	public AptamerBaseSelexSubmitParser(File anInputFile) {
		jsonFile = anInputFile;
		try {
			String str = FileUtils.readFileToString(anInputFile);
			String[] s_str = str.split(">\\n\\n");
			String cleanJSON = '[' + s_str[1].trim() + ']';
			try {
				JSONArray ja = new JSONArray(cleanJSON);
				int len = ja.length();
				for (int i = 0; i < len; i++) {
					JSONObject jo = ja.getJSONObject(i);
					Iterator<String> itr = jo.keys();
					while (itr.hasNext()) {
						String key = itr.next();
						String s = jo.getString(key);
						Integer aKey = null;
						aKey = Integer.parseInt(key.replaceAll("\"", ""));
						pageMap.put(aKey, s);
					}
				}
			} catch (JSONException e2) {
				e2.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		interactions = makeInteractions(pageMap.get(2), pageMap.get(3),
				pageMap.get(4));
		selexExperiment = makeSelexExperiment(pageMap.get(1), pageMap.get(2));
	}

	/**
	 * Construct a selex experiment from
	 * 
	 * @param string
	 * @param string2
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private SelexExperiment makeSelexExperiment(String pageOne, String pageTwo) {
		SelexExperiment rm = null;
		try {
			// get the selex experiment details from page one
			JSONObject jo = new JSONObject(pageOne);
			String aPmid_str = jo.getString("pmid");
			int pmid = 0;
			String aDoi = jo.getString("doi");
			String aRef = jo.getString("reference");
			if (aPmid_str.length() > 0) {
				pmid = Integer.parseInt(aPmid_str);
			}
			// get the selex experiment details from page two
			JSONObject jo2 = new JSONObject(pageTwo);
			// get the selex methods
			List<String> selexMethodNames = new LinkedList<String>();
			Iterator<String> sel_methodsItr = null;
			try{
				sel_methodsItr = jo2.getJSONObject("selexMethod")
					.keys();
			}catch(JSONException e){
				return null;
			}
			while (sel_methodsItr.hasNext()) {
				String sm = sel_methodsItr.next();
				if (!sm.equals("Not Described")) {
					selexMethodNames.add(sm);
				}
			}
			// get the partitioning methods
			List<String> partitioningMethodNames = new LinkedList<String>();
			Iterator<String> part_methNamesItr = jo2.getJSONObject(
					"partitioningMethod").keys();
			while (part_methNamesItr.hasNext()) {
				String pm = part_methNamesItr.next();
				if (!pm.equals("Not Described")) {
					partitioningMethodNames.add(pm);
				}
			}
			// get the recovery methods
			List<String> recoveryMethodNames = new LinkedList<String>();
			Iterator<String> rec_methNamesItr = jo2.getJSONObject(
					"recoveryMethod").keys();
			while (rec_methNamesItr.hasNext()) {
				String recm = rec_methNamesItr.next();
				if (!recm.equals("Not Described")) {
					recoveryMethodNames.add(recm);
				}
			}
			// get the buffering agent
			List<String> buffAgentNames = new LinkedList<String>();
			Iterator<String> buff_agNamesItr = jo2.getJSONObject(
					"bufferingAgent").keys();
			while (buff_agNamesItr.hasNext()) {
				String buffAgentName = buff_agNamesItr.next();
				if (!buffAgentName.equals("Not Described")) {
					buffAgentNames.add(buffAgentName);
				}
			}
			// get the number of rounds
			int numOfRounds = 0;
			if (jo2.getString("numOfRounds").length() > 0) {
				numOfRounds = Integer.parseInt(jo2.getString("numOfRounds"));
			}
			// get the template sequence
			String templateSeq = jo2.getString("templateSequence");
			// get template bias
			String templateBias = jo2.getString("templateBias");
			// get the temperature
			Double temperature = -1.0;
			if (jo2.getString("temperature").length() > 0) {
				temperature = Double.parseDouble(jo2.getString("temperature"));
			}
			// get the ph
			Double ph = -1.0;
			if (jo2.getString("ph").length() > 0) {
				ph = Double.parseDouble(jo2.getString("ph"));
			}
			// String metalCationConcs
			List<String> metalCationConcs = new ArrayList<String>();
			if (jo2.getString("metal_cation_concentrations") != null) {
				metalCationConcs = Arrays.asList(jo2.getString(
						"metal_cation_concentrations").split("; "));
			}

			// create a SelexExperiment object
			rm = new SelexExperiment(pmid, aDoi, aRef, selexMethodNames,
					partitioningMethodNames, recoveryMethodNames, numOfRounds,
					templateSeq, templateBias, buffAgentNames, temperature, ph,
					metalCationConcs);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return rm;
	}

	@SuppressWarnings("unchecked")
	private List<Interaction> makeInteractions(String pageTwo,
			String pageThree, String pageFour) {
		List<Interaction> rm = new LinkedList<Interaction>();
		try {
			JSONObject jo = new JSONObject(pageTwo);
			JSONObject idpt = jo.getJSONObject("interactant_details_page_two");
			// iterate over the interactions
			Iterator<String> itr = idpt.keys();
			while (itr.hasNext()) {
				String anInteractionNum = itr.next();
				List<Aptamer> aptaList = new LinkedList<Aptamer>();
				List<AffinityExperiment> affList = new LinkedList<AffinityExperiment>();
				if (anInteractionNum.length() < 2) {
					Integer numOfAptamers = null;
					String aptamerType = null;
					Integer numOfAffExperiments = null;
					String targetName = null;
					try{
					// get the number of aptamers for this interaction
					 numOfAptamers = Integer.parseInt((String) idpt
							.getJSONObject(anInteractionNum).get(
									"numberOfAptamers"));
					 aptamerType = (String) idpt.getJSONObject(
							anInteractionNum).get("aptamerType");
					// get the number of affinity experiments for this
					// interaction
					 numOfAffExperiments = Integer
							.parseInt((String) idpt.getJSONObject(
									anInteractionNum).getString(
									"numberOfAffinityExperiments"));
					 targetName = idpt.getJSONObject(anInteractionNum)
							.getString("targetN");
					}catch(JSONException e){
						e.printStackTrace();
						return null;
					}
					AptamerTarget at = new AptamerTarget(targetName);

					// iterate over the aptamers for interaction
					// anInteractionNum
					for (int i = 1; i <= numOfAptamers; i++) {
						JSONObject aptamerDets = new JSONObject(pageThree)
								.getJSONObject("interaction_details")
								.getJSONObject(anInteractionNum)
								.getJSONObject(Integer.toString(i));
						String seq = aptamerDets.getString("sequence");
						Integer numOfMinApts = Integer.parseInt(aptamerDets
								.getString("numOfMinAptamers"));
						String modRes = aptamerDets.getJSONObject(
								"extra_details").getString("modified_residues");
						String seq_patt = aptamerDets.getJSONObject(
								"extra_details").getString("sequence_pattern");
						String application = aptamerDets.getJSONObject(
								"extra_details").getString("application");
						String has_mut_anal = aptamerDets.getJSONObject(
								"extra_details").getString(
								"hasMutationalAnalysis");

						List<String> secondaryStructureNames = new ArrayList<String>();
						JSONObject ss = aptamerDets.getJSONObject(
								"extra_details").getJSONObject("struct_sel");
						Iterator<String> itr2 = ss.keys();
						while (itr2.hasNext()) {
							String ssn = itr2.next();
							if (!ssn.equals("Not Described")) {
								secondaryStructureNames.add(ssn);
							}
						}
						// now make me some aptamers!
						Aptamer a = new Aptamer(aptamerType, seq, modRes,
								seq_patt, secondaryStructureNames, application,
								has_mut_anal);
						aptaList.add(a);
					}// for aptamers

					// iterate over the affinity experiments
					for (int j = 1; j <= numOfAffExperiments; j++) {
						JSONObject affExpDets = new JSONObject(pageThree)
								.getJSONObject("interaction_details")
								.getJSONObject(anInteractionNum)
								.getJSONObject("k" + Integer.toString(j))
								.getJSONObject("kd");
						Double kdVal = null;
						if (affExpDets.getString("value") != null) {
							try {
								kdVal = Double.parseDouble(affExpDets
										.getString("value"));
							} catch (NumberFormatException e2) {
								kdVal = null;
							}
						}
						String range = null;
						if (affExpDets.getString("range") != null) {
							range = affExpDets.getString("range");
						}
						String error = null;
						if (affExpDets.getString("error") != null) {
							error = affExpDets.getString("error");
						}
						List<String> affinityMethodNames = new ArrayList<String>();
						Iterator<String> itr3 = affExpDets.getJSONObject(
								"affinityMethod").keys();
						while (itr3.hasNext()) {
							String affMeth = itr3.next();
							if (!affMeth.equals("Not Described")) {
								affinityMethodNames.add(affMeth);
							}
						}
						List<String> buffAgentNames = new ArrayList<String>();
						Iterator<String> itr4 = affExpDets.getJSONObject(
								"bufferingAgent").keys();
						while (itr4.hasNext()) {
							String buffMeth = itr4.next();
							if (!buffMeth.equals("Not Described")) {
								buffAgentNames.add(buffMeth);
							}
						}
						Double ph = null;
						if (affExpDets.getString("ph") != null) {
							try {
								ph = Double.parseDouble(affExpDets
										.getString("ph"));
							} catch (NumberFormatException x32) {
								ph = null;
							}
						}
						Double temp = null;
						if (affExpDets.getString("temperature") != null) {
							try {
								temp = Double.parseDouble(affExpDets
										.getString("temperature"));
							} catch (NumberFormatException x33) {
								temp = null;
							}
						}
						List<String> metalCationConcs = new ArrayList<String>();
						if (affExpDets.getString("metal_cation_concentrations") != null) {
							metalCationConcs = Arrays.asList(affExpDets
									.getString("metal_cation_concentrations")
									.split("; "));
						}
						// now make me an affinity experiment
						AffinityExperiment ae = new AffinityExperiment(kdVal,
								range, error, affinityMethodNames,
								buffAgentNames, ph, metalCationConcs, temp);
						affList.add(ae);
					}// for affinityExperiments
					// create an Interaction object
					Interaction anInteraction = new Interaction(affList,
							aptaList, at);
					rm.add(anInteraction);
				}
			}
			// now chekc out whats on page 4
			JSONObject jo4 = new JSONObject(pageFour);
			//System.out.println(jo4);
			
			JSONObject minAptDets = null;
			try {
				minAptDets = jo4.getJSONObject("additional_dets");
				Iterator<String> interactionItr = minAptDets.keys();
				while (interactionItr.hasNext()) {
					String anInteractionNum = interactionItr.next();
					// now iterate over the aptamers
					Iterator<String> aptamerIterator = minAptDets
							.getJSONObject(anInteractionNum).keys();
					while (aptamerIterator.hasNext()) {
						String anAptamerNum = aptamerIterator.next();
						// get minimal aptamers for anAptamerNum
						JSONObject minimalApts = minAptDets
								.getJSONObject(anInteractionNum)
								.getJSONObject(anAptamerNum)
								.getJSONObject("minimalAptamer");
						// now iterate over the minimal aptamers
						Iterator<String> minAptItr = minimalApts.keys();
						while (minAptItr.hasNext()) {
							String aMinAptNum = minAptItr.next();
							JSONObject aMinApt = minimalApts
									.getJSONObject(aMinAptNum);
							// now build a minimal aptamer
							String seq = aMinApt.getString("sequence");
							// get the aptamer details
							JSONObject minAptDetails = aMinApt
									.getJSONObject("more");
							String mod_residues = null;
							if (minAptDetails.getString("modified_residues") != null) {
								mod_residues = minAptDetails
										.getString("modified_residues");
							}
							String seq_pattern = null;
							if (minAptDetails.getString("sequence_pattern") != null) {
								seq_pattern = minAptDetails
										.getString("sequence_pattern");
							}
							String application = null;
							if (minAptDetails.getString("application") != null) {
								application = minAptDetails
										.getString("application");
							}
							String hasMutationalAnalysis = null;
							if (minAptDetails
									.getString("hasMutationalAnalysis") != null) {
								hasMutationalAnalysis = minAptDetails
										.getString("hasMutationalAnalysis");
							}
							List<String> sec_structs = new ArrayList<String>();
							Iterator<String> itr4 = minAptDetails
									.getJSONObject("struct_sel").keys();
							while (itr4.hasNext()) {
								String ss_sel = itr4.next();
								if (!ss_sel.equals("Not Described")) {
									sec_structs.add(ss_sel);
								}
							}
							// now create a minimal aptamer?
							Aptamer parentApt = rm
									.get(Integer.parseInt(anInteractionNum) - 1)
									.getAptamers()
									.get(Integer.parseInt(anAptamerNum) - 1);
							if (parentApt != null) {
								// create a minimal aptamer
								List<Aptamer> minimalAptamerList = new LinkedList<Aptamer>();
								MinimalAptamer ma = new MinimalAptamer(
										parentApt.getPolymerType(), seq,
										mod_residues, seq_pattern, sec_structs,
										application, hasMutationalAnalysis,
										parentApt);
								minimalAptamerList.add(ma);

								JSONObject aff_exp = aMinApt
										.getJSONObject("kd").getJSONObject(
												"dets");
								Double kdVal = null;
								if (aff_exp.getString("value") != null) {
									try {
										kdVal = Double.parseDouble(aff_exp
												.getString("value"));
									} catch (NumberFormatException e2) {
										kdVal = null;
									}
								}
								String range = null;
								if (aff_exp.getString("range") != null) {
									range = aff_exp.getString("range");
								}
								String error = null;
								if (aff_exp.getString("error") != null) {
									error = aff_exp.getString("error");
								}
								List<String> affinityMethodNames = new ArrayList<String>();
								Iterator<String> itr3 = aff_exp.getJSONObject(
										"affinityMethod").keys();
								while (itr3.hasNext()) {
									String affMeth = itr3.next();
									if (!affMeth.equals("Not Described")) {
										affinityMethodNames.add(affMeth);
									}
								}
								List<String> buffAgentNames = new ArrayList<String>();
								Iterator<String> itr14 = aff_exp.getJSONObject(
										"bufferingAgent").keys();
								while (itr4.hasNext()) {
									String buffMeth = itr14.next();
									if (!buffMeth.equals("Not Described")) {
										buffAgentNames.add(buffMeth);
									}
								}
								Double ph = null;
								if (aff_exp.getString("ph") != null) {
									try {
										ph = Double.parseDouble(aff_exp
												.getString("ph"));
									} catch (NumberFormatException x32) {
										ph = null;
									}
								}
								Double temp = null;
								if (aff_exp.getString("temperature") != null) {
									try {
										temp = Double.parseDouble(aff_exp
												.getString("temperature"));
									} catch (NumberFormatException x33) {
										temp = null;
									}
								}
								List<String> metalCationConcs = new ArrayList<String>();
								if (aff_exp
										.getString("metal_cation_concentrations") != null) {
									metalCationConcs = Arrays
											.asList(aff_exp
													.getString(
															"metal_cation_concentrations")
													.split("; "));
								}
								// now make me an affinity experiment
								AffinityExperiment ae = new AffinityExperiment(
										kdVal, range, error,
										affinityMethodNames, buffAgentNames,
										ph, metalCationConcs, temp);
								List<AffinityExperiment> aema = new LinkedList<AffinityExperiment>();
								aema.add(ae);

								// now make an interaction
								Interaction minAptInt = new Interaction(
										aema,
										minimalAptamerList,
										rm.get(Integer
												.parseInt(anInteractionNum) - 1)
												.getAptamer_target());
								rm.add(minAptInt);
							}
						}
					}
				}
			} catch (JSONException e4) {
				minAptDets = null;
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return rm;
	}

	/**
	 * @return the interactions
	 */
	public List<Interaction> getInteractions() {
		return interactions;
	}

	/**
	 * @return the selexExperiment
	 */
	public SelexExperiment getSelexExperiment() {
		return selexExperiment;
	}

}
