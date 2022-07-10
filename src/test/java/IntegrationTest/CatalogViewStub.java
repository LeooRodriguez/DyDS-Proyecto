package IntegrationTest;

import Presenter.CatalogPresenter;
import View.CatalogViewImpl;

public class CatalogViewStub extends CatalogViewImpl {

    public CatalogViewStub(CatalogPresenter catalogPresenter) {
        super(catalogPresenter);
    }

    public void setLocallySelectedOption(String selectedOption){

    }
    public void setSearchTitle(String title){
        searchInWikipedia.setText(title);
    }

}
