package es.uam.eps.bmi.search.ui;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

class DocumentWindow extends JPanel{

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    public DocumentWindow(String documentPath) throws IOException {
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));

        String initialText = "";
        
        String pFileName = documentPath.substring(documentPath.lastIndexOf(File.separator)+1);
        String zipPath = documentPath.replace(File.separator + pFileName, "");
        int len = 0;
        
        try (ZipFile zipFile = new ZipFile(new File(zipPath))) {
            ZipEntry currentEntry = zipFile.getEntry(pFileName);
            // Abrimos el achivo
            InputStream stream = zipFile.getInputStream(currentEntry);
            ByteArrayOutputStream result = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            // Leemos el fichero
            while ((len = stream.read(buffer, 0, buffer.length)) > 0) {
                    result.write(buffer, 0, len);
            }
            initialText = result.toString("UTF-8");
        }
        

        JTextArea content = new JTextArea(initialText);
        JScrollPane scrollPane = new JScrollPane(content);

        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(scrollPane);

        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(leftPanel);
    }
}
