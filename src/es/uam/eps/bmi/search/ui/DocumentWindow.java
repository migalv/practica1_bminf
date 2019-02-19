package ui;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

class DocumentWindow extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public DocumentWindow(String documentPath) {
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		
		String initialText = "<html>\n" +
	                "Color and font test:\n" +
	                "<ul>\n" +
	                "<li><font color=red>red</font>\n" +
	                "<li><font color=blue>blue</font>\n" +
	                "<li><font color=green>green</font>\n" +
	                "<li><font size=-2>small</font>\n" +
	                "<li><font size=+2>large</font>\n" +
	                "<li><i>italic</i>\n" +
	                "<li><b>bold</b>\n" +
	                "</ul>\n"+"<html>\n";
        
		JLabel theLabel = new JLabel(initialText);
		JScrollPane scrollPane = new JScrollPane(theLabel);
		
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.PAGE_AXIS));
        leftPanel.add(scrollPane);
        
        setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        add(leftPanel);
	}
}
