import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTextArea;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.awt.Font;

import java.time.LocalDateTime; 
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import javax.swing.JScrollPane;

public class test {

	private JFrame frmTextEditor;
	private File filename;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					test window = new test();
					window.frmTextEditor.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public test() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmTextEditor = new JFrame();
		frmTextEditor.setTitle("Text Editor 1000");
		frmTextEditor.setBounds(400,600,400,600);
		frmTextEditor.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmTextEditor.getContentPane().setLayout(null);
		
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
		
		JPanel panel = new JPanel();
		panel.setForeground(Color.BLACK);
		panel.setBounds(0, 0, 378, 544);
		frmTextEditor.getContentPane().add(panel);
		panel.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("Text Editor 1000");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 16, 180, 20);
		panel.add(lblNewLabel);
		
		JLabel SuccessMessage = new JLabel("");
		SuccessMessage.setForeground(new Color(60,179,113));
		SuccessMessage.setBounds(10, 206, 348, 20);
		panel.add(SuccessMessage);
		
		JButton btnHelp = new JButton("Help");
		btnHelp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String menu = "If no commands are entered the files will default to ";
				menu += "single space, single column, and left justified.";
				menu += "\n\nCommands are to be entered into the .txt file previous to processing.";
				menu += "\nOnce all commands have been entered, load your file and click process to make changes.";
				menu += "\n\nJustification Commands: ";
				menu += "\n-r    right justification";
				menu += "\n-c    center justification";
				menu += "\n-l    left justification";
				menu += "\n\nSpacing Commands:";
				menu += "\n-d    double space";
				menu += "\n-s    single space";
				menu += "\n\nIndentation Commands:";
				menu += "\n-i    indent on the first line (left justified only)";
				menu += "\n-b    indent for multiple lines";
				menu += "\n\nColumn Commands:";
				menu += "\n-1    one column";
				menu += "\n-2    two columns";
				menu += "\n\nExtra commands:";
				menu += "\n-e    adds a blank line where the command is placed";
				menu += "\n-n    reset back to default";
				JOptionPane.showMessageDialog(null, menu);
			}
		});
		btnHelp.setBounds(10, 47, 115, 29);
		panel.add(btnHelp);
		
		JButton btnLoad = new JButton("Load File");
		btnLoad.setBounds(128, 47, 115, 29);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File file = null;
				String temp = JOptionPane.showInputDialog("Input filename if in same directory or full filepath:");
				if(temp != null && !temp.isEmpty())
					file = new File(temp);
				
				if(file != null && file.exists()) {
					filename = file;
					SuccessMessage.setForeground(new Color(60, 179, 113));
					SuccessMessage.setText("File Successfully Found");
				} else {
					filename = null;
					SuccessMessage.setForeground(new Color(255,69,0));
					SuccessMessage.setText("File Not Found");
				}
			}
		});
		panel.add(btnLoad);
		
		JLabel lblErrorLog = new JLabel("Error Log");
		lblErrorLog.setFont(new Font("Tahoma", Font.PLAIN, 20));
		lblErrorLog.setBounds(10, 224, 103, 40);
		panel.add(lblErrorLog);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 270, 353, 258);
		
		JTextArea errorLog = new JTextArea();
		scrollPane.setViewportView(errorLog);
		errorLog.setEditable(false);
		panel.add(scrollPane);
		
		JButton btnFilePreview = new JButton("File Preview");
		btnFilePreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(filename == null) {					
					String formattedDate = LocalDateTime.now().format(myFormatObj);
					errorLog.append(formattedDate + " - No File Specified\n");
				} else {
					File file = new File("C:/Dev/Fall19CSE205TA/ExtraCreditQuizReflection.txt");
					
			        //first check if Desktop is supported by Platform or not
			        if(!Desktop.isDesktopSupported()){
			            System.out.println("Desktop is not supported");
			            return;
			        }
			        
			        try {
				        Desktop desktop = Desktop.getDesktop();
				        if(filename.exists()) desktop.open(filename);
			        } catch (IOException ex) {
			        	ex.printStackTrace();
			        }
				}
			}
		});
		btnFilePreview.setBounds(10, 85, 350, 29);
		panel.add(btnFilePreview);
		
		JButton btnProcessFile = new JButton("Process File");
		btnProcessFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(filename != null && filename.exists()) {
					if(filename.toString().lastIndexOf(".") != -1 && filename.toString().lastIndexOf(".") != 0) {
				       String fileExtension = filename.toString().substring(filename.toString().lastIndexOf(".")+1);
				       if(fileExtension.equals("txt")) {
				    	   processFile(filename);
				       } else {
				    	   String formattedDate = LocalDateTime.now().format(myFormatObj);
						   errorLog.append(formattedDate + " - File extension is not .txt cannot proceed\n");
				       }
					}
				} else {
					SuccessMessage.setForeground(new Color(255,69,0));
					SuccessMessage.setText("Cannot Process... No File Specified");
				}
			}
		});
		btnProcessFile.setBounds(245, 47, 115, 29);
		panel.add(btnProcessFile);
		
		JButton btnClearLog = new JButton("Clear");
		btnClearLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				errorLog.setText("");
			}
		});
		btnClearLog.setBounds(245, 232, 115, 29);
		panel.add(btnClearLog);			
	}
	
	public void processFile(File file) {
		
	}
}
