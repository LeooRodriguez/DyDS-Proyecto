package Model;

public interface CatalogModelListeners {

    public void searchOnWiki();

    public void selectSearchOption();

    public void saveLocally();

    public void selectSavedSearch();

    public void deleteSave();

    public void searchArticle();

    public void printErrorSave();

    public void printSuccessfulSave();

    public void printErrorDelete();

    public void printSuccessfulDelete();

    public void printErrorLoadDB();

    public void printSuccessfulLoadDB();

    public void printErrorLocallyResults();

    public void printERRORSaveSelected();
}
