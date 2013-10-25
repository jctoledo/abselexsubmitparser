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

import org.codehaus.jettison.json.JSONObject;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

import com.freebase.json.JSON;

/**
 * @author  Jose Cruz-Toledo
 *
 */
public class FreebaseHelper {

	/**
	 * Query Freebase and retrieve the mid of the selex experiment topic that has the passed in pmid
	 * @param aPmid a pubmed id
	 * @return if a pmid is found the topic's mid is returned. Null otherwise
	 */
	public static String checkPmid(int aPmid){
		JSONArray q = new JSONArray();
		JSONObject root = new JSONObject();
		try{
			root.put("\"mid\"", JSONObject.NULL);
			root.put("\"type\"", "/base/aptamer/experiment");
			root.put("\"b:type\"", "/base/aptamer/selex_experiment");
			root.put("\"/base/aptamer/experiment/pubmed_id\"", "\""+aPmid+"\"");
			q.put(root);
			String query = q.toString();
			query = query.replace("\\\"", "");
			query = query.replace("\\\\", "");
			query = query.replace("\\/", "/");
			URLReader ur = new URLReader(FreebaseCredentials.getScheme(),
					FreebaseCredentials.getHost(), FreebaseCredentials.getPath(),
					query, FreebaseCredentials.getKey());
			JSONObject rc = ur.getJSON();
			JSONArray r = rc.getJSONArray("result");
			if(r.length() == 0){
				return null;
			}else{
				String s = r.getJSONObject(0).getString("mid");
				return s;
			}
		}catch (JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Query Freebase and retrieve the mid of the selex experiment topic that has the passed in doi
	 * @param aDoi a document object identifier
	 * @return if a doi is found the topic's mid is returned. Null otherwise
	 */
	public static String checkDoi(String aDoi){
		JSONArray q = new JSONArray();
		JSONObject root = new JSONObject();
		try{
			root.put("\"mid\"", JSONObject.NULL);
			root.put("\"type\"", "/base/aptamer/experiment");
			root.put("\"b:type\"", "/base/aptamer/selex_experiment");
			root.put("\"/base/aptamer/experiment/digital_object_identifier\"", "\""+aDoi+"\"");
			q.put(root);
			String query = q.toString();
			query = query.replace("\\\"", "");
			query = query.replace("\\\\", "");
			query = query.replace("\\/", "/");
			URLReader ur = new URLReader(FreebaseCredentials.getScheme(),
					FreebaseCredentials.getHost(), FreebaseCredentials.getPath(),
					query, FreebaseCredentials.getKey());
			JSONObject rc = ur.getJSON();
			JSONArray r = rc.getJSONArray("result");
			if(r.length() == 0){
				return null;
			}else{
				String s = r.getJSONObject(0).getString("mid");
				return s;
			}
		}catch (JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * Query Freebase and retrieve the mid of the selex experiment topic that has the passed in reference string
	 * @param aRef a reference string
	 * @return if a a reference is found the topic's mid is returned. Null otherwise
	 */
	public static String checkReference(String aRef){
		JSONArray q = new JSONArray();
		JSONObject root = new JSONObject();
		try{
			root.put("\"mid\"", JSONObject.NULL);
			root.put("\"type\"", "/base/aptamer/experiment");
			root.put("\"b:type\"", "/base/aptamer/selex_experiment");
			root.put("\"/base/aptamer/experiment/has_bibliographic_reference\"", "\""+aRef+"\"");
			q.put(root);
			String query = q.toString();
			query = query.replace("\\\"", "");
			query = query.replace("\\\\", "");
			query = query.replace("\\/", "/");
			URLReader ur = new URLReader(FreebaseCredentials.getScheme(),
					FreebaseCredentials.getHost(), FreebaseCredentials.getPath(),
					query, FreebaseCredentials.getKey());
			JSONObject rc = ur.getJSON();
			JSONArray r = rc.getJSONArray("result");
			if(r.length() == 0){
				return null;
			}else{
				String s = r.getJSONObject(0).getString("mid");
				return s;
			}
		}catch (JSONException e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * Retrieve the MID of a given topic given a name and its type. For example passing in "Generic SELEX" and "/base/aptamer/selex_method" will return /m/0clfqg4
	 * @param aTopicName the name of a topic
	 * @param aTopicType the type of the topic
	 * @return the mid of the topic
	 */
	public static String getMidFromTopicNameAndType(String aTopicName, String aTopicType){
		JSONArray q = new JSONArray();
		JSONObject root = new JSONObject();
		String topicMid = null;
		try{
			root.put("\"mid\"", JSONObject.NULL);
			root.put("\"type\"", aTopicType);
			root.put("\"name\"", aTopicName);
			q.put(root);
			String query = q.toString();
			query = query.replace("\\\"", "");
			query = query.replace("\\\\", "");
			query = query.replace("\\/", "/");
			URLReader ur = new URLReader(FreebaseCredentials.getScheme(),
					FreebaseCredentials.getHost(), FreebaseCredentials.getPath(),
					query, FreebaseCredentials.getKey());
			JSONObject rc = ur.getJSON();
			JSONArray r = rc.getJSONArray("result");
			topicMid = r.getJSONObject(0).getString("mid");
			return topicMid;
			
		}catch (JSONException e){
			return null;
		}
	}
}
