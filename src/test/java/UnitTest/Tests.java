package UnitTest;

import Model.CatalogModel;
import Model.SearchResult;
import Presenter.CatalogPresenter;
import Presenter.CatalogPresenterImp;
import View.CatalogView;
import org.junit.Before;
import org.junit.Test;

import static org.mockito.Mockito.*;


public class Tests {

    private CatalogView view;
    private CatalogPresenter presenter;
    private CatalogModel model;

    @Before
    public void initVariables(){
        view = mock(CatalogView.class);
        model = mock(CatalogModel.class);
        presenter = new CatalogPresenterImp();
        presenter.setModel(model);
        presenter.setView(view);

    }

    @Test
    public void deletePresenterTest(){
        when(view.getLocallySelectedOption()).thenReturn("Pizza");
        presenter.deleteArticle();
        verify(model, times(1)).deleteArticle("Pizza");
    }
    @Test
    public void savePresenterTest(){
        when(view.getSearchTitle()).thenReturn("Pizza");
        when(view.getSearchedContent()).thenReturn("Pizza: La pizza es riquisima");
        presenter.saveArticle();
        verify(model, times(1)).saveArticle("Pizza","Pizza: La pizza es riquisima");
    }
    @Test
    public void saveUpdatesPresenterTest(){
        when(view.getLocallySelectedOption()).thenReturn("Pizza");
        when(view.getLocallyArticle()).thenReturn("Pizza: La pizza es riquisima");
        presenter.saveChanges();
        verify(model, times(1)).saveArticleChanges("Pizza","Pizza: La pizza es riquisima");
    }
    @Test
    public void searchInWikipediaPresenterTest(){
        when(view.getSearchTitle()).thenReturn("Pizza");
        presenter.searchInWikipedia();
        verify(model, times(1)).searchOnWiki("Pizza");
    }
    @Test
    public void loadArticlePresenterTest(){
        SearchResult searchResult = new SearchResult("Holis","1","2");
        when(view.getSearchSelection()).thenReturn(searchResult);
        presenter.loadArticle();
        verify(model, times(1)).notifyListener(searchResult);
    }
    @Test
    public void showSavedPresenterTest(){
        when(view.getLocallySelectedOption()).thenReturn("Pizza");
        presenter.showSaved();
        verify(model, times(1)).getSave("Pizza");
    }
}
