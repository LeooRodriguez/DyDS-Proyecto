package Model;
import Model.Wikipedia.WikipediaSearch;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
public class CatalogModelImp implements CatalogModel {

    String lastSearchedText = "";
    ArrayList<SearchResult> resultsOfWikipedia = new ArrayList<>();
    WikipediaSearch searcher = new WikipediaSearch();
    private ArrayList<CatalogModelListeners> listeners = new ArrayList<>();
    private boolean isActiveDataBase = false;



    private String extract;

    public String getExtract(){
        return extract;
    }

    @Override
    public void setSearchMode(boolean isComplete) {
        searcher.setCompleteArticle(isComplete);
    }

    @Override
    public void initDataBase() {
        try {
            if(!isActiveDataBase) {
                boolean isLoad = DataBase.loadDatabase();
                isActiveDataBase = true;
                if (isLoad)
                    notifySuccessfulLoadDBListener();
            }
        } catch (Exception e) {
            notifyErrorLoadDBListener();
        }

    }

    @Override
    public void searchOnWiki(String title){
        resultsOfWikipedia = searcher.searchOnWikipedia(title);
        notifySearchListener();
    }

    @Override
    public void notifyListener(SearchResult result){
        extract = searcher.getExtract(result);
        notifySelectSearchListener();
    }

    @Override
    public String getSave(String title) {
        String infoLocally = null;
        try {
            infoLocally = DataBase.getInfoSelectedSavedLocally(title);
        } catch (SQLException e) {
            notifyERRORSaveSelectedListener();
        }
        return infoLocally;
    }


    @Override
    public String getSearchResult(){
        return lastSearchedText;
    }

    @Override
    public ArrayList<SearchResult> getPreliminaryResults() {
        SearchResult dummy = new SearchResult("Hola","1","see");
        SearchResult[] searchResults = {dummy};
        return resultsOfWikipedia;
    }

    @Override
    public void saveArticleChanges(String title, String body) {
        try {
            boolean isSave = DataBase.saveInfoLocally(title.replace("'", "`"), body);
            if(isSave)
                notifySuccessfulSaveListener();
        } catch (SQLException e) {
            notifyErrorSaveListener();
        } catch (Exception e) {
        }
        notifySaveListener();
    }

    @Override
    public void saveArticle(String title, String body) {
        try {
            boolean isSave = DataBase.saveInfoLocally(title.replace("'", "`"), body);
            if(isSave)
                notifySuccessfulSaveListener();
        } catch (Exception e) {
            if(e.getMessage()=="ERROR")
                notifyErrorSaveListener();
        }
        notifySaveListener();
    }

    @Override
    public void deleteArticle(String title) {
        try {
            boolean isDelete = DataBase.deleteSelectedInfoSavedLocally(title);
            if(isDelete)
                notifySuccessfulDeleteListener();
        } catch (Exception e) {
            if(e.getMessage()=="ERROR")
                notifyErrorDeleteListener();
        }
        notifyDeleteListener();
    }

    @Override
    public String[] getLocallyResults() {
        Object[] locallySavedResults = new Object[0];
        try {
            locallySavedResults = DataBase.getLocallySaveResults().stream().sorted().toArray();
        } catch (SQLException e) {
            notifyErrorLocallyResultsListener();
        }
        String[] locallyResults = Arrays.copyOf(locallySavedResults, locallySavedResults.length, String[].class);
        return locallyResults;
    }
    @Override
    public void addListener(CatalogModelListeners listener){
        this.listeners.add(listener);
    }

    private void notifySearchListener(){
        for(CatalogModelListeners listener: listeners){
            listener.searchOnWiki();
        }
    }
    private void notifySelectSearchListener(){
        for(CatalogModelListeners listener: listeners){
            listener.searchArticle();
        }
    }
    private void notifySaveListener(){
        for(CatalogModelListeners listener: listeners){
            listener.saveLocally();
        }
    }
    private void notifySelectSaveListener(){
        for(CatalogModelListeners listener: listeners){
            listener.selectSavedSearch();
        }
    }
    private void notifyDeleteListener(){
        for(CatalogModelListeners listener: listeners){
            listener.deleteSave();
        }
    }
    private void notifyErrorSaveListener() {
        for(CatalogModelListeners listener: listeners){
            listener.printErrorSave();
        }
    }
    private void notifySuccessfulSaveListener() {
        for(CatalogModelListeners listener: listeners){
            listener.printSuccessfulSave();
        }
    }
    private void notifySuccessfulDeleteListener() {
        for(CatalogModelListeners listener: listeners){
            listener.printSuccessfulDelete();
        }
    }

    private void notifyErrorDeleteListener() {
        for(CatalogModelListeners listener: listeners){
            listener.printErrorDelete();
        }
    }

    private void notifySuccessfulLoadDBListener() {
        for(CatalogModelListeners listener: listeners){
            listener.printSuccessfulDelete();
        }
    }
    private void notifyErrorLoadDBListener() {
        for(CatalogModelListeners listener: listeners){
            listener.printErrorDelete();
        }
    }

    private void notifyErrorLocallyResultsListener() {
        for(CatalogModelListeners listener: listeners){
            listener.printErrorLocallyResults();
        }
    }
    private void notifyERRORSaveSelectedListener() {
        for(CatalogModelListeners listener: listeners){
            listener.printERRORSaveSelected();
        }
    }

    public WikipediaSearch getSearcher() {
        return searcher;
    }

    public void setSearcher(WikipediaSearch searcher) {
        this.searcher = searcher;
    }
}
