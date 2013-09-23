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


import com.freebase.json.JSON;

/**
 * @author  Jose Cruz-Toledo
 *
 */
public class FreebaseHelper {
	private static final String key = "AIzaSyB8ouPZ2w1rkMS3bGL6PVNJm6AHLTKFhC4";
	private static final String scheme = "https";
	private static final String host = "www.googleapis.com";
	private static final String path = "/freebase/v1/mqlread";

	/**
	 * Retrieve the MID of a given topic given a name and its type. For example passing in "Generic SELEX" and "/base/aptamer/selex_method" will return /m/0clfqg4
	 * @param aTopicName the name of a topic
	 * @param aTopicType the type of the topic
	 * @return the mid of the topic
	 */
	public static String getMidFromTopicNameAndType(String aTopicName, String aTopicType){
		String q = "[{\"mid\":null, \"type\":\"" + aTopicType
				+ "\", \"name\":\"" + aTopicName + "\"}]";
		String qs = "query=" + q.replace("\\", "") + "&key=" + key + "&cursor";
		URLReader ur = new URLReader(scheme, host, path, qs);
		JSON result = ur.getJSONContents();
		String topicMid = null;
		try {
			topicMid = result.get("result").get(0).get("mid").string();
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
		return topicMid;
	}
}
