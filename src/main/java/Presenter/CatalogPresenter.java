package Presenter;

import Model.CatalogModel;
import View.CatalogView;

public interface CatalogPresenter {

    public void start();

    public void searchInWikipedia();

    public void showSaved();

    public void deleteArticle();

    public void saveChanges();

    public void saveArticle();

    public void loadArticle();

    public void selectComplete();

    public void setModel(CatalogModel model);

    public void setView(CatalogView view);
}
