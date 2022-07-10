package Model;

import java.util.ArrayList;

public interface CatalogModel {

    public void addListener(CatalogModelListeners listener);

    public String getSearchResult();

    public ArrayList<SearchResult> getPreliminaryResults();

    public String getSave(String name);

    public String[] getLocallyResults();

    public String getExtract();

    public void saveArticleChanges(String title, String body);

    public void saveArticle(String title, String body);

    public void deleteArticle(String title);

    public void searchOnWiki(String title);

    public void notifyListener(SearchResult searchResult);

    public void setSearchMode(boolean isComplete);

    public void initDataBase();
}
