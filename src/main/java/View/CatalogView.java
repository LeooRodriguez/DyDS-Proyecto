package View;

import Model.SearchResult;

import java.util.ArrayList;

public interface CatalogView {

    public void showView();

    public String getSearchedContent();

    public String getLocallySelectedOption();

    public String getLocallyArticle();

    public String getSearchTitle();

    public boolean isCompleteEnabled();

    public void setLocallyContent(String text);

    public void setLocallyList(String[] storedArticles);

    public void setResultOfWikipedia(String text);

    public void displaySearchOptions(ArrayList<SearchResult> resultOfWikipedia);

    public SearchResult getSearchSelection();

    public void printErrorSave();

    public void printSuccessfulSave();

    public void printErrorDelete();

    public void printSuccessfulDelete();

    public void printErrorLoadDB();

    public void printSuccessfulLoadDB();

    public void printErrorLocallyResults();

    public void printERRORSaveSelected();

    public String getSearchOfWikipedia();
}
