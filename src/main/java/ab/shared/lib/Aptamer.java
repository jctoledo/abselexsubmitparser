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
import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONException;

import ab.shared.FreebaseHelper;

/**
 * @author Jose Cruz-Toledo
 * 
 */
public class Aptamer {
	private String polymerType;
	private String sequence;
	private String modifiedResidues;
	private String sequencePattern;
	private List<String> secondaryStructureNames;
	private List<String> secondaryStructureMids;
	private String application;
	private String mutationalAnalysis;

	public Aptamer(String aPolymerType, String aSeq, String someModResidues,
			String seqPatt, List<String> someSSNames, String anAppli,
			String aMutAnaly) {
		polymerType = aPolymerType;
		sequence = aSeq;
		modifiedResidues = someModResidues;
		sequencePattern = seqPatt;
		secondaryStructureNames = someSSNames;
		application = anAppli;
		mutationalAnalysis = aMutAnaly;
		secondaryStructureMids = makeSSMids();
	}

	/**
	 * @return
	 */
	private List<String> makeSSMids() {
		List<String> rm = new ArrayList<String>();
		for (String aName : this.getSecondaryStructureNames()) {
			if (aName.length() > 0) {
				String anMid = FreebaseHelper.getMidFromTopicNameAndType(aName,
						"/base/aptamer/nucleic_acid_secondary_structure");
				if (anMid != null) {
					rm.add(anMid);
				}
			}
		}
		return rm;
	}

	public JSONObject getJSONObject() {
		try {
			JSONObject rm = new JSONObject();
			rm.put("\"polymer_type\"", this.getPolymerType());
			rm.put("\"sequence\"", this.getSequence());
			if (this.getModifiedResidues()!=null&&this.getModifiedResidues().length() > 0) {
				rm.put("\"modified_residues\"", this.getModifiedResidues());
			}
			if (this.getSequencePattern()!=null&&this.getSequencePattern().length() > 0) {
				rm.put("\"sequence_pattern\"", this.getSequencePattern());
			}
			if (this.getSecondaryStructureMidsJsonArray()!=null&&this.getSecondaryStructureMidsJsonArray().length() > 0) {
				rm.put("\"secondary_structures_mids\"",
						this.getSecondaryStructureMids());
			}
			if (this.getSecondaryStructureNamesJsonArray() != null &&this.getSecondaryStructureNamesJsonArray().length() > 0) {
				rm.put("\"secondary_structures_names\"",
						this.getSecondaryStructureNamesJsonArray());
			}
			if(this.getApplication() != null&& this.getApplication().length()>0){
				rm.put("\"application\"", this.getApplication());
			}
			if(this.getMutationalAnalysis() != null && this.getMutationalAnalysis().length()>0){
				rm.put("\"mutational_analysis\"", this.getMutationalAnalysis());
			}
			return rm;
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @return the polymerType
	 */
	public String getPolymerType() {
		return polymerType;
	}

	/**
	 * @param polymerType
	 *            the polymerType to set
	 */
	public void setPolymerType(String polymerType) {
		this.polymerType = polymerType;
	}

	/**
	 * @return the sequence
	 */
	public String getSequence() {
		return sequence;
	}

	/**
	 * @param sequence
	 *            the sequence to set
	 */
	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	/**
	 * @return the modifiedResidues
	 */
	public String getModifiedResidues() {
		return modifiedResidues;
	}

	/**
	 * @param modifiedResidues
	 *            the modifiedResidues to set
	 */
	public void setModifiedResidues(String modifiedResidues) {
		this.modifiedResidues = modifiedResidues;
	}

	/**
	 * @return the sequencePattern
	 */
	public String getSequencePattern() {
		return sequencePattern;
	}

	/**
	 * @param sequencePattern
	 *            the sequencePattern to set
	 */
	public void setSequencePattern(String sequencePattern) {
		this.sequencePattern = sequencePattern;
	}

	/**
	 * @return the secondaryStructureNames
	 */
	public List<String> getSecondaryStructureNames() {
		return secondaryStructureNames;
	}

	public JSONArray getSecondaryStructureNamesJsonArray() {
		List<String> s = this.getSecondaryStructureNames();
		JSONArray rm = new JSONArray();
		if (s.size() > 0) {
			for (String aN : s) {
				rm.put(aN);
			}
			return rm;
		}
		return null;
	}

	/**
	 * @param secondaryStructureNames
	 *            the secondaryStructureNames to set
	 */
	public void setSecondaryStructureNames(List<String> secondaryStructureNames) {
		this.secondaryStructureNames = secondaryStructureNames;
	}

	/**
	 * @return the secondaryStructureMids
	 */
	public List<String> getSecondaryStructureMids() {
		return secondaryStructureMids;
	}

	public JSONArray getSecondaryStructureMidsJsonArray() {
		List<String> s = this.getSecondaryStructureMids();
		JSONArray rm = new JSONArray();
		if (s.size() > 0) {
			for (String aN : s) {
				rm.put(aN);
			}
			return rm;
		}
		return null;
	}

	/**
	 * @param secondaryStructureMids
	 *            the secondaryStructureMids to set
	 */
	public void setSecondaryStructureMids(List<String> secondaryStructureMids) {
		this.secondaryStructureMids = secondaryStructureMids;
	}

	/**
	 * @return the application
	 */
	public String getApplication() {
		return application;
	}

	/**
	 * @param application
	 *            the application to set
	 */
	public void setApplication(String application) {
		this.application = application;
	}

	/**
	 * @return the mutationalAnalysis
	 */
	public String getMutationalAnalysis() {
		return mutationalAnalysis;
	}

	/**
	 * @param mutationalAnalysis
	 *            the mutationalAnalysis to set
	 */
	public void setMutationalAnalysis(String mutationalAnalysis) {
		this.mutationalAnalysis = mutationalAnalysis;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Aptamer [polymerType=" + polymerType + ", sequence=" + sequence
				+ ", modifiedResidues=" + modifiedResidues
				+ ", sequencePattern=" + sequencePattern
				+ ", secondaryStructureNames=" + secondaryStructureNames
				+ ", secondaryStructureMids=" + secondaryStructureMids
				+ ", application=" + application + ", mutationalAnalysis="
				+ mutationalAnalysis + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((application == null) ? 0 : application.hashCode());
		result = prime
				* result
				+ ((modifiedResidues == null) ? 0 : modifiedResidues.hashCode());
		result = prime
				* result
				+ ((mutationalAnalysis == null) ? 0 : mutationalAnalysis
						.hashCode());
		result = prime * result
				+ ((polymerType == null) ? 0 : polymerType.hashCode());
		result = prime
				* result
				+ ((secondaryStructureMids == null) ? 0
						: secondaryStructureMids.hashCode());
		result = prime
				* result
				+ ((secondaryStructureNames == null) ? 0
						: secondaryStructureNames.hashCode());
		result = prime * result
				+ ((sequence == null) ? 0 : sequence.hashCode());
		result = prime * result
				+ ((sequencePattern == null) ? 0 : sequencePattern.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Aptamer))
			return false;
		Aptamer other = (Aptamer) obj;
		if (application == null) {
			if (other.application != null)
				return false;
		} else if (!application.equals(other.application))
			return false;
		if (modifiedResidues == null) {
			if (other.modifiedResidues != null)
				return false;
		} else if (!modifiedResidues.equals(other.modifiedResidues))
			return false;
		if (mutationalAnalysis == null) {
			if (other.mutationalAnalysis != null)
				return false;
		} else if (!mutationalAnalysis.equals(other.mutationalAnalysis))
			return false;
		if (polymerType == null) {
			if (other.polymerType != null)
				return false;
		} else if (!polymerType.equals(other.polymerType))
			return false;
		if (secondaryStructureMids == null) {
			if (other.secondaryStructureMids != null)
				return false;
		} else if (!secondaryStructureMids.equals(other.secondaryStructureMids))
			return false;
		if (secondaryStructureNames == null) {
			if (other.secondaryStructureNames != null)
				return false;
		} else if (!secondaryStructureNames
				.equals(other.secondaryStructureNames))
			return false;
		if (sequence == null) {
			if (other.sequence != null)
				return false;
		} else if (!sequence.equals(other.sequence))
			return false;
		if (sequencePattern == null) {
			if (other.sequencePattern != null)
				return false;
		} else if (!sequencePattern.equals(other.sequencePattern))
			return false;
		return true;
	}
}
