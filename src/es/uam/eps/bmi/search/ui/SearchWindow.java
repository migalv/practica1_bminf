package es.uam.eps.bmi.search.ui;

import es.uam.eps.bmi.search.SearchEngine;
import es.uam.eps.bmi.search.index.Index;
import es.uam.eps.bmi.search.index.IndexBuilder;
import es.uam.eps.bmi.search.index.lucene.LuceneBuilder;
import es.uam.eps.bmi.search.index.lucene.LuceneIndex;
import es.uam.eps.bmi.search.lucene.LuceneEngine;
import es.uam.eps.bmi.search.ranking.SearchRanking;
import es.uam.eps.bmi.search.ranking.SearchRankingDoc;
import java.awt.Desktop;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JList;
import javax.swing.border.BevelBorder;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Paths;
import java.sql.Array;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.swing.AbstractListModel;
import javax.swing.JOptionPane;

public class SearchWindow {

    private JFrame frameWindow;
    private JTextField fieldSearch;
    private JButton btnSearch;
    private JLabel lblBminfSearcher;
    private JList resultList;
    private final Index index;
    private final String indexPath = "index/docs";
    private SearchEngine engine = null;
    private SearchRankingDoc results [];

    /**
     * Launch the application.
     * @param args
     */
    public static void main(String[] args){
        EventQueue.invokeLater(() -> {
            try {
                SearchWindow window = new SearchWindow();
                window.frameWindow.setVisible(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the application.
     * @throws java.io.IOException
     */
    public SearchWindow() throws IOException {
        this.results = new SearchRankingDoc [0];
        index = new LuceneIndex(indexPath);
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frameWindow = new JFrame();
        frameWindow.setBounds(100, 100, 1000, 350);
        frameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frameWindow.getContentPane().setLayout(null);

        fieldSearch = new JTextField();
        fieldSearch.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        fieldSearch.setBounds(25, 45, 300, 30);
        frameWindow.getContentPane().add(fieldSearch);
        fieldSearch.setColumns(10);

        resultList = new JList();
        
        btnSearch = new JButton("Search");
        btnSearch.setBounds(335, 49, 89, 23);
        btnSearch.addActionListener((ActionEvent e) -> {
            // Recuperamos el valor del search field
            try{
                if(engine == null)
                    engine = new LuceneEngine(indexPath);
                String query = fieldSearch.getText();
                SearchRanking ranking = engine.search(query, 10);
                results = new SearchRankingDoc[ranking.size()];
                int i = 0;
                for (SearchRankingDoc result : ranking)
                    results[i++] = result;
                resultList.setModel(new AbstractListModel() {
                public int getSize() {
                    return results.length;
                }
                public Object getElementAt(int index) {
                    SearchRankingDoc ele = results[index];
                    try {
                        return ele.getScore() + " - " + ele.getPath();
                    } catch (IOException ex) {
                        Logger.getLogger(SearchWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }

                    return "FAIL";
                }
            });
            }catch(NullPointerException exception){
                // Algo salió mal. Mostramos mensaje
                JOptionPane.showMessageDialog(frameWindow,
                        "You need to insert some text to search.",
                        "Search warning",
                        JOptionPane.WARNING_MESSAGE);
            } catch (IOException ex) {
                Logger.getLogger(SearchWindow.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
        
        frameWindow.getContentPane().add(btnSearch);

        lblBminfSearcher = new JLabel("BMINF Searcher");
        lblBminfSearcher.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
        lblBminfSearcher.setBounds(25, 5, 158, 29);
        frameWindow.getContentPane().add(lblBminfSearcher);
        
        resultList.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                JList list = (JList)evt.getSource();
                if (evt.getClickCount() == 2) {
                    String listEle = list.getSelectedValue().toString();
                    String path = listEle.substring(listEle.indexOf(" - ") + 3);
                    JFrame documentWindow = new JFrame();
                    try {
                        showFileOnBrowser(path);
                    } catch (IOException ex) {
                        Logger.getLogger(SearchWindow.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        });
        resultList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        resultList.setBounds(25, 86, 850, 200);
        frameWindow.getContentPane().add(resultList);
    }
    
    private void showFileOnBrowser(String documentPath) throws IOException{
        String pFileName = documentPath.substring(documentPath.lastIndexOf(File.separator)+1);
        String zipPath = documentPath.replace(File.separator + pFileName, "");
        int len = 0;
        
        try (ZipFile zipFile = new ZipFile(new File(zipPath))) {
            ZipEntry currentEntry = zipFile.getEntry(pFileName);
            File newFile = new File(Paths.get("temp") + File.separator + pFileName);
            FileOutputStream fos = new FileOutputStream(newFile);
            // Abrimos el achivo
            InputStream stream = zipFile.getInputStream(currentEntry);
            byte[] buffer = new byte[1024];
            // Leemos el fichero
            while ((len = stream.read(buffer, 0, buffer.length)) > 0) {
                    fos.write(buffer, 0, len);
            }
            fos.close();
            Desktop.getDesktop().browse(newFile.toURI());
        }
    }
}