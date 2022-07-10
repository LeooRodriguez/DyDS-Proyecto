package View;
import Model.SearchResult;
import Presenter.CatalogPresenter;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CatalogViewImpl implements CatalogView {
    protected JTextField searchInWikipedia;
    protected JPanel contentPane;
    protected JTextPane SearchedWikipediaArticle;
    protected JButton saveLocallyButton;
    protected JTabbedPane tabbedPane1;
    protected JPanel searchPanel;
    protected JPanel storagePanel;
    protected JComboBox<Object> resultsSavedLocally;
    protected JTextPane saveLocallyArticle;
    protected JButton deleteButton;
    protected JButton searchButton;
    protected JButton saveButton;
    protected JCheckBox completePageArticle;
    protected  JPopupMenu searchOptionsMenu;
    protected SearchResult selected;
    protected CatalogPresenter catalogPresenter;

    public CatalogViewImpl(CatalogPresenter catalogPresenter){
        this.setContentType();
        this.catalogPresenter = catalogPresenter;
        this.initListeners();
        this.completePageArticle.setSelected(true);
    }

    @Override
    public void showView() {
        JFrame frame = new JFrame("Gourmet Catalog");
        frame.setContentPane(contentPane);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        setFrameIcon(frame);
        frame.setVisible(true);
    }

    private void setFrameIcon(Frame frame) {
        frame.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("/catalogue.png")));
    }

    public String getSearchOfWikipedia() {
        return searchInWikipedia.getText();
    }

    @Override
    public String getSearchedContent() {
        return SearchedWikipediaArticle.getText();
    }

    public String getLocallySelectedOption(){
        String selectedOption=null;
        if(resultsSavedLocally.getItemCount()>0)
            selectedOption= resultsSavedLocally.getSelectedItem().toString();
        else{
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "INFO: No hay elementos guardados localmente","Información",JOptionPane.WARNING_MESSAGE);
        }

        return selectedOption;
    }
    public void setLocallySelectedOption(String selectedOption){
        resultsSavedLocally.addItem(selectedOption);
    }
    @Override
    public String getSearchTitle() {
        return searchInWikipedia.getText();
    }
    @Override
    public String getLocallyArticle() {
        return saveLocallyArticle.getText();
    }
    @Override
    public void setLocallyContent(String text) {
        saveLocallyArticle.setText(text);
    }
    @Override
    public void setResultOfWikipedia(String text) {
        SearchedWikipediaArticle.setText(text);
        SearchedWikipediaArticle.setCaretPosition(0);
    }
    @Override
    public void displaySearchOptions(ArrayList<SearchResult> preliminarResults) {
        searchOptionsMenu = new JPopupMenu("Search Results");
        for (SearchResult res : preliminarResults) {
            searchOptionsMenu.add(res);
            res.addActionListener(actionEvent -> {
                selected = res;
                catalogPresenter.loadArticle();
            });
        }
        searchOptionsMenu.show(searchInWikipedia, searchInWikipedia.getX(), searchInWikipedia.getY());
    }
    @Override
    public SearchResult getSearchSelection() {
        return selected;
    }
    @Override
    public boolean isCompleteEnabled() {
        return completePageArticle.isSelected();
    }
    @Override
    public void setLocallyList(String[] storedArticles) {
        resultsSavedLocally.setModel(new DefaultComboBoxModel<Object>(storedArticles));
    }
    @Override
    public void printErrorSave() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "INFO: No se pudo guardar localmente la pagina","Información",JOptionPane.ERROR_MESSAGE);
    }
    @Override
    public void printSuccessfulSave() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "INFO: Pagina guardada correctamente","Información",JOptionPane.INFORMATION_MESSAGE);
    }
    @Override
    public void printErrorDelete() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "INFO: No se pudo eliminar localmente la pagina","Información",JOptionPane.ERROR_MESSAGE);
    }
    @Override
    public void printSuccessfulDelete() {
        if(resultsSavedLocally.getItemCount()>0) {
            JFrame jFrame = new JFrame();
            JOptionPane.showMessageDialog(jFrame, "INFO: Pagina eliminada correctamente", "Información", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    @Override
    public void printErrorLoadDB() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "ERROR: No se pudo iniciar la base de datos","Información",JOptionPane.ERROR_MESSAGE);
    }
    @Override
    public void printSuccessfulLoadDB() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "INFO: Base de datos iniciada correctamente","Información",JOptionPane.INFORMATION_MESSAGE);
    }
    @Override
    public void printErrorLocallyResults() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "ERROR: No se pudo retornar la lista de elementos locales","Información",JOptionPane.ERROR_MESSAGE);
    }
    @Override
    public void printERRORSaveSelected() {
        JFrame jFrame = new JFrame();
        JOptionPane.showMessageDialog(jFrame, "ERROR: No se pudo retornar el elemento seleccionado de los elementos locales","Información",JOptionPane.ERROR_MESSAGE);
    }
    private void setContentType() {
        SearchedWikipediaArticle.setContentType("text/html");
        saveLocallyArticle.setContentType("text/html");
    }
    private void initListeners() {
        resultsSavedLocally.addActionListener(actionEvent -> catalogPresenter
                .showSaved());
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catalogPresenter.searchInWikipedia();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catalogPresenter.deleteArticle();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catalogPresenter.saveChanges();
            }
        });
        saveLocallyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catalogPresenter.saveArticle();
            }
        });
        completePageArticle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                catalogPresenter.selectComplete();
            }
        });
    }

}
