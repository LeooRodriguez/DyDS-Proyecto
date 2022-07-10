package Model.Wikipedia;

import Model.SearchResult;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class WikipediaSearch {

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://en.wikipedia.org/w/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .build();

    WikipediaSearchAPI searchAPI = retrofit.create(WikipediaSearchAPI.class);
    WikipediaPageAPI pageAPI = retrofit.create(WikipediaPageAPI.class);
    WikipediaFullPagAPI fullPageAPI = retrofit.create(WikipediaFullPagAPI.class);
    private boolean completeArticle = true;
    String selectedResultTitle = null;
    String text = "";

    public ArrayList<SearchResult> searchOnWikipedia(String title){

        ArrayList<SearchResult> results = new ArrayList<>();
        try{
            Response<String> callForSearchResponse;
            callForSearchResponse = searchAPI.searchForTerm(title + " articletopic:\"food-and-drink\"").execute();
            Gson gson = new Gson();
            JsonObject jobj = gson.fromJson(callForSearchResponse.body(), JsonObject.class);
            JsonObject query = jobj.get("query").getAsJsonObject();
            Iterator<JsonElement> resultIterator = query.get("search").getAsJsonArray().iterator();
            JsonArray jsonResults = query.get("search").getAsJsonArray();
            JPopupMenu searchOptionsMenu = new JPopupMenu("Search Results");
            for (JsonElement je : jsonResults) {
                JsonObject searchResult = je.getAsJsonObject();
                String searchResultTitle = searchResult.get("title").getAsString();
                String searchResultPageId = searchResult.get("pageid").getAsString();
                String searchResultSnippet = searchResult.get("snippet").getAsString();

                SearchResult sr = new SearchResult(searchResultTitle, searchResultPageId, searchResultSnippet);
                results.add(sr);
            }
        }catch (IOException e1) {
            e1.printStackTrace();
        }
        return results;
    }

    public String getExtract(SearchResult searchResult){
        Response<String> callForPageResponse = null;
        try {
            if(completeArticle)
                callForPageResponse = fullPageAPI.getExtractByPageID(searchResult.pageID).execute();
            else
                callForPageResponse = pageAPI.getExtractByPageID(searchResult.pageID).execute();

        } catch (IOException e) {
            e.printStackTrace();
        }
        Gson gson = new Gson();
        JsonObject jobj2 = gson.fromJson(callForPageResponse.body(), JsonObject.class);
        JsonObject query2 = jobj2.get("query").getAsJsonObject();
        JsonObject pages = query2.get("pages").getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> pagesSet = pages.entrySet();
        Map.Entry<String, JsonElement> first = pagesSet.iterator().next();
        JsonObject page = first.getValue().getAsJsonObject();
        JsonElement extractResult = page.get("extract");
        if (extractResult == null) {
            text = "No Results";
        } else {
            text = "<h1>" + searchResult.title + "</h1>";
            selectedResultTitle = searchResult.title;
            text += extractResult.getAsString().replace("\\n", "\n");
            text = textToHtml(text);
            if(completeArticle)
                text+="\n" + "<a href=https://en.wikipedia.org/?curid=" + searchResult.pageID +">View Full Article</a>";
        }
        return text;
    }

    public boolean isCompleteArticle() {
        return completeArticle;
    }

    public void setCompleteArticle(boolean completeArticle) {
        this.completeArticle = completeArticle;
    }

    public static String textToHtml(String text) {
        StringBuilder builder = new StringBuilder();
        builder.append("<font face=\"arial\">");
        String fixedText = text.replace("'", "`");
        builder.append(fixedText);
        builder.append("</font>");
        return builder.toString();
    }
}