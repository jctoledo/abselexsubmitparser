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



/**
 * @author Jose Cruz-Toledo
 * 
 */
public class SelexExperiment {

	private int pmid;
	private String doi;
	private String reference;
	private List<String> selexMethodsNames;
	private List<String> selexMethodsMids;
	private List<String> partitioningMethodsNames;
	private List<String> partitioningMethodMids;
	private List<String> recoveryMethodsNames;
	private List<String> recoveryMethodsMids;
	private List<String> bufferingAgentNames;
	private List<String> bufferingAgentMids;
	private int selectionRounds;
	private String templateSequence;
	private String templateBias;
	private Double temperature;
	private Double pH;
	private List<String> metalCationConcs;

	/**
	 * @param aPmid
	 * @param aDoi
	 * @param aReference
	 * @param someselexMethodsNames
	 * @param somepartitioningMethodsNames
	 * @param somerecoveryMethodsNames
	 * @param numOfSelRounds
	 * @param aTemplateSequence
	 * @param templateBias
	 * @param somebufferingAgentNames
	 * @param aTemperature
	 * @param apH
	 * @param aMetalCationConc
	 */
	public SelexExperiment(int aPmid, String aDoi, String aReference,
			List<String> someselexMethodsNames,
			List<String> somepartitioningMethodsNames,
			List<String> somerecoveryMethodsNames, int numOfSelRounds,
			String aTemplateSequence, String aTemplateBias,
			List<String> somebufferingAgentNames, Double aTemperature,
			Double apH, List<String> aMetalCationConc) {
		this.pmid = aPmid;
		this.doi = aDoi;
		this.reference = aReference;
		this.selexMethodsNames = someselexMethodsNames;
		this.partitioningMethodsNames = somepartitioningMethodsNames;
		this.recoveryMethodsNames = somerecoveryMethodsNames;
		this.selectionRounds = numOfSelRounds;
		this.templateSequence = aTemplateSequence;
		this.templateBias = aTemplateBias;
		this.bufferingAgentNames = somebufferingAgentNames;
		this.temperature = aTemperature;
		this.pH = apH;
		this.metalCationConcs = aMetalCationConc;

		this.selexMethodsMids = makeSMMids();
		this.partitioningMethodMids = makePMMids();
		this.recoveryMethodsMids = makeRMMids();
		this.bufferingAgentMids = makeBAMids();
	}



	/**
	 * @param pmid2
	 * @param aDoi
	 * @param aRef
	 * @param selexMethodNames
	 * @param partitioningMethodNames
	 * @param recoveryMethodNames
	 * @param numOfRounds
	 * @param templateSeq
	 * @param templateBias2
	 * @param buffAgentNames
	 * @param temperature2
	 * @param ph2
	 * @param metalCationConcs2
	 */
	public SelexExperiment(int pmid2, String aDoi, String aRef,
			List<String> selexMethodNames,
			List<String> partitioningMethodNames,
			List<String> recoveryMethodNames, String numOfRounds,
			String templateSeq, String templateBias2,
			List<String> buffAgentNames, String temperature2, String ph2,
			List<String> metalCationConcs2) {
		// TODO Auto-generated constructor stub
	}



	/**
	 * @return
	 */
	private List<String> makeBAMids() {
		List<String> rm = new ArrayList<String>();
		for (String aName : this.getBufferingAgentNames()) {
			if (aName.length() > 0) {
				String anMid = FreebaseHelper.getMidFromTopicNameAndType(aName,
						"/base/aptamer/buffering_agent");
				if (anMid != null) {
					rm.add(anMid);
				}
			}
		}
		return rm;
	}

	private List<String> makeRMMids() {
		List<String> rm = new ArrayList<String>();
		for (String aName : this.getRecoveryMethodsNames()) {
			if (aName.length() > 0) {
				String anMid = FreebaseHelper.getMidFromTopicNameAndType(aName,
						"/base/aptamer/recovery_methods");
				if (anMid != null) {
					rm.add(anMid);
				}
			}
		}
		return rm;
	}

	private List<String> makePMMids() {
		List<String> rm = new ArrayList<String>();
		for (String aName : this.getPartitioningMethodsNames()) {
			if (aName.length() > 0) {
				String anMid = FreebaseHelper.getMidFromTopicNameAndType(aName,
						"/base/aptamer/separation_methods");
				if (anMid != null) {
					rm.add(anMid);
				}
			}
		}
		return rm;
	}

	private List<String> makeSMMids() {
		List<String> rm = new ArrayList<String>();
		for (String aName : this.getSelexMethodsNames()) {
			if (aName.length() > 0) {
				String anMid = FreebaseHelper.getMidFromTopicNameAndType(aName,
						"/base/aptamer/selex_method");
				if (anMid != null) {
					rm.add(anMid);
				}
			}
		}
		return rm;
	}

	/**
	 * @return the pmid
	 */
	public int getPmid() {
		return pmid;
	}

	/**
	 * @return the doi
	 */
	public String getDoi() {
		return doi;
	}

	/**
	 * @return the reference
	 */
	public String getReference() {
		return reference;
	}

	/**
	 * @return the selexMethodsNames
	 */
	public List<String> getSelexMethodsNames() {
		return selexMethodsNames;
	}

	/**
	 * @return the partitioningMethodsNames
	 */
	public List<String> getPartitioningMethodsNames() {
		return partitioningMethodsNames;
	}

	/**
	 * @return the recoveryMethodsNames
	 */
	public List<String> getRecoveryMethodsNames() {
		return recoveryMethodsNames;
	}

	/**
	 * @return the selectionRounds
	 */
	public int getSelectionRounds() {
		return selectionRounds;
	}

	/**
	 * @return the templateSequence
	 */
	public String getTemplateSequence() {
		return templateSequence;
	}

	/**
	 * @return the templateBias
	 */
	public String getTemplateBias() {
		return templateBias;
	}

	/**
	 * @return the bufferingAgentNames
	 */
	public List<String> getBufferingAgentNames() {
		return bufferingAgentNames;
	}

	/**
	 * @return the temperature
	 */
	public Double getTemperature() {
		return temperature;
	}

	/**
	 * @return the pH
	 */
	public Double getpH() {
		return pH;
	}

	/**
	 * @return the metalCationConcs
	 */
	public List<String> getMetalCationConcs() {
		return metalCationConcs;
	}

	/**
	 * @return the selexMethodsMids
	 */
	public List<String> getSelexMethodsMids() {
		return selexMethodsMids;
	}

	/**
	 * @return the partitioningMethodMids
	 */
	public List<String> getPartitioningMethodMids() {
		return partitioningMethodMids;
	}

	/**
	 * @return the recoveryMethodsMids
	 */
	public List<String> getRecoveryMethodsMids() {
		return recoveryMethodsMids;
	}

	/**
	 * @return the bufferingAgentMids
	 */
	public List<String> getBufferingAgentMids() {
		return bufferingAgentMids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SelexExperiment [pmid=" + pmid + ", doi=" + doi
				+ ", reference=" + reference + ", selexMethodsNames="
				+ selexMethodsNames + ", selexMethodsMids=" + selexMethodsMids
				+ ", partitioningMethodsNames=" + partitioningMethodsNames
				+ ", partitioningMethodMids=" + partitioningMethodMids
				+ ", recoveryMethodsNames=" + recoveryMethodsNames
				+ ", recoveryMethodsMids=" + recoveryMethodsMids
				+ ", bufferingAgentNames=" + bufferingAgentNames
				+ ", bufferingAgentMids=" + bufferingAgentMids
				+ ", selectionRounds=" + selectionRounds
				+ ", templateSequence=" + templateSequence + ", templateBias="
				+ templateBias + ", temperature=" + temperature + ", pH=" + pH
				+ ", metalCationConcs=" + metalCationConcs+"]";
	}

}
