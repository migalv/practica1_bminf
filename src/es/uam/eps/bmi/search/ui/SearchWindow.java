package ui;

import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.GridLayout;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.AbstractListModel;
import javax.swing.BorderFactory;
import javax.swing.Box;

public class SearchWindow {

	private JFrame frame;
	private JTextField fieldSearch;
	private JButton btnSearch;
	private JLabel lblBminfSearcher;
	private JList resultList;
	private String[] documentNames = {"ola", "q", "ase", };
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchWindow window = new SearchWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SearchWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		fieldSearch = new JTextField();
		fieldSearch.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		fieldSearch.setBounds(25, 45, 300, 30);
		frame.getContentPane().add(fieldSearch);
		fieldSearch.setColumns(10);
		
		btnSearch = new JButton("Search");
		btnSearch.setBounds(335, 49, 89, 23);
		frame.getContentPane().add(btnSearch);
		
		lblBminfSearcher = new JLabel("BMINF Searcher");
		lblBminfSearcher.setFont(new Font("Trebuchet MS", Font.BOLD, 20));
		lblBminfSearcher.setBounds(25, 5, 158, 29);
		frame.getContentPane().add(lblBminfSearcher);
		
		resultList = new JList();
		resultList.setModel(new AbstractListModel() {
			public int getSize() {
				return documentNames.length;
			}
			public Object getElementAt(int index) {
				return documentNames[index];
			}
		});
		resultList.addMouseListener(new MouseAdapter() {
		    public void mouseClicked(MouseEvent evt) {
		        JList list = (JList)evt.getSource();
		        if (evt.getClickCount() == 2) {
		        	JFrame documentWindow = new JFrame(list.getSelectedValue().toString());
		        	documentWindow.add(new DocumentWindow(list.getSelectedValue().toString()));
		        	documentWindow.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		        	documentWindow.setVisible(true);
		        }
		    }
		});
		resultList.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		resultList.setBounds(25, 86, 300, 164);
		frame.getContentPane().add(resultList);
	}
}