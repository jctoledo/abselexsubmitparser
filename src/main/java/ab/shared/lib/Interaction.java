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

import java.util.List;

/**
 * @author  Jose Cruz-Toledo
 *
 */
public class Interaction {
	private List<AffinityExperiment> affinityExperiments;
	private List<Aptamer> aptamers;
	private AptamerTarget aptamer_target;
	
	public Interaction(List<AffinityExperiment> someAffExps, List<Aptamer> someApts, AptamerTarget anAptTarget){
		this.affinityExperiments = someAffExps;
		this.aptamer_target = anAptTarget;
		this.aptamers = someApts;
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


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Interaction [affinityExperiments=" + affinityExperiments
				+ ", aptamers=" + aptamers + ", aptamer_target="
				+ aptamer_target + "]";
	}
	
	

}
