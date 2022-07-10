package IntegrationTest;

import Model.SearchResult;
import Model.Wikipedia.WikipediaSearch;

import java.util.ArrayList;

public class WikipediaSearchStub extends WikipediaSearch {

    @Override
    public ArrayList<SearchResult> searchOnWikipedia(String title) {
        ArrayList<SearchResult>hardCodeResults= new ArrayList<SearchResult>();
        SearchResult searchResult1 = new SearchResult("Pizza","1","Snippet");
        SearchResult searchResult2 = new SearchResult("Pizza hut","2","Snippet");
        SearchResult searchResult3 = new SearchResult("Pizza nice","3","Snippet");
        SearchResult searchResult4 = new SearchResult("Pizza god","4","Snippet");
        hardCodeResults.add(searchResult1);
        hardCodeResults.add(searchResult2);
        hardCodeResults.add(searchResult3);
        hardCodeResults.add(searchResult4);
        return hardCodeResults;
    }
}
