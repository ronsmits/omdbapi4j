package com.omdbapi;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Logger;

import org.json.JSONException;
import org.json.JSONObject;


public class Omdb extends RestClient{
	private final Logger logger = Logger.getLogger("omdb");
	
	public List<SearchResult> search(String string) throws OmdbMovieNotFoundException{
		try {
			String found = get("s="+URLEncoder.encode(string, "UTF-8"));
			logger.info("received "+found);
			JSONObject response= new JSONObject(found);
			if (response.has("Response") && response.get("Response").equals("False")){
				throw new OmdbMovieNotFoundException(string);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
