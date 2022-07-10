package Presenter;

import Model.*;
import View.CatalogView;
import View.CatalogViewImpl;

import java.util.ArrayList;
public class CatalogPresenterImp implements CatalogPresenter {

    private CatalogView view;
    private CatalogModel model;

    public CatalogPresenterImp() {
        this.view = new CatalogViewImpl(this);
        this.model = new CatalogModelImp();
        this.view.setLocallyList(model.getLocallyResults());
    }

    public CatalogView getView() {
        return view;
    }

    public void setView(CatalogView catalogView) {
        this.view = catalogView;
    }

    public CatalogModel getModel() {
        return model;
    }

    public void setModel(CatalogModel model) {
        this.model = model;
    }

    @Override
    public void start() {
        model.initDataBase();
        initListeners();
        view.showView();
    }

    @Override
    public void searchInWikipedia() {
        model.searchOnWiki(view.getSearchTitle());
    }

    @Override
    public void showSaved(){
        String body = model.getSave(view.getLocallySelectedOption());
        view.setLocallyContent(body);
    }

    @Override
    public void deleteArticle() {
        model.deleteArticle(view.getLocallySelectedOption());
    }

    @Override
    public void selectComplete() {
        model.setSearchMode(view.isCompleteEnabled());
    }

    @Override
    public void saveChanges() {
        model.saveArticleChanges(view.getLocallySelectedOption(), view.getLocallyArticle());
    }
    @Override
    public void saveArticle() {
        model.saveArticle(view.getSearchTitle(), view.getSearchedContent());
    }

    @Override
    public void loadArticle() {
        model.notifyListener(view.getSearchSelection());
    }


    private void initListeners(){
        model.addListener(new CatalogModelListeners() {
            @Override
            public void searchOnWiki() {
                ArrayList<SearchResult> titles = model.getPreliminaryResults();
                view.displaySearchOptions(titles);
            }
            @Override
            public void selectSearchOption() {
                view.setLocallyContent(model.getSave(view.getLocallySelectedOption()));
            }
            @Override
            public void saveLocally() {
                String[] titles = model.getLocallyResults();
                view.setLocallyList(titles);
            }
            @Override
            public void selectSavedSearch() {
                view.setResultOfWikipedia(model.getExtract());
            }
            @Override
            public void searchArticle() {
                view.setResultOfWikipedia(model.getExtract());
            }

            @Override
            public void printErrorSave() {
                view.printErrorSave();
            }

            @Override
            public void printSuccessfulSave() {
                view.printSuccessfulSave();
            }

            @Override
            public void printErrorDelete() {
                view.printErrorDelete();
            }

            @Override
            public void printSuccessfulDelete() {
                view.printSuccessfulDelete();
            }

            @Override
            public void printErrorLoadDB() {
                view.printErrorLoadDB();
            }

            @Override
            public void printSuccessfulLoadDB() {
                view.printSuccessfulLoadDB();
            }

            @Override
            public void printErrorLocallyResults() {
                view.printErrorLocallyResults();
            }

            @Override
            public void printERRORSaveSelected() {
                view.printERRORSaveSelected();
            }

            @Override
            public void deleteSave() {
                view.setLocallyContent("");
                view.setLocallyList(model.getLocallyResults());
            }
        });
    }

}
