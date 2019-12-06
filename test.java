/** 
 *  Group:       8
 *  Name: 		 Ziad Abdelkarim, Maxim Tolea, Angel Flores, Chad Lutz
 *  Class:		 CSE360
 *  Section: 	 Wednesday 9:40 am
 *  Assignment:  Text Editor Final Project
 */

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
import java.awt.Font;
import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.awt.Color;
import javax.swing.JScrollPane;

/*
	This class holds all of the elements of the window interface for the Text Editor 1000. This also contains all of the processing 
	calculations to be done on a file. 
*/
public class test {

	private JFrame frmTextEditor;
	private File filename;
	// The following variables are changed depending on the commands within the file
	// The variables are initialized to defaults to begin with
	// Justification booleans
	boolean isLeft = true; 
	boolean isCenter = false;

	// Spacing boolean
	boolean isSingle = true; 

	// Indentation booleans
	boolean isIndent = false; 
	boolean isI = false; // Indent
	boolean isB = false; // Multiple Indents

	// This boolean specifies whether the text should be a single or double column
	boolean is1 = true; // Single

	boolean isTitle = false; 
	
	//This will hold the contents of the file while it is being processed
	String st;
	String complete ="";
	String output = "OUTPUT.txt";

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
		frmTextEditor.setBounds(400, 70, 400, 600);
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

		//Success Label
		JLabel SuccessMessage = new JLabel("");
		SuccessMessage.setForeground(new Color(60, 179, 113));
		SuccessMessage.setBounds(10, 206, 348, 20);
		panel.add(SuccessMessage);

		//Help menu Component
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
				menu += "\n-t    Title (center justification)";
				JOptionPane.showMessageDialog(null, menu);
			}
		});
		btnHelp.setBounds(10, 47, 115, 29);
		panel.add(btnHelp);

		//The load file button prompts the user to enter a file name to be processed.
		JButton btnLoad = new JButton("Load File");
		btnLoad.setBounds(128, 47, 115, 29);
		btnLoad.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				File file = null;
				// Popup window 
				String temp = JOptionPane.showInputDialog("Input filename if in same directory or full filepath:");
				if (temp != null && !temp.isEmpty())
					file = new File(temp);

				if (file != null && file.exists()) {
					filename = file;
					SuccessMessage.setForeground(new Color(60, 179, 113));
					SuccessMessage.setText("File Successfully Found");
				} else {
					filename = null;
					SuccessMessage.setForeground(new Color(255, 69, 0));
					SuccessMessage.setText("File Not Found");
				}
			}
		});
		panel.add(btnLoad);

		//The following components are where the errors will be shown
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

		//Opens a window for viewing the File
		JButton btnFilePreview = new JButton("File Preview");
		btnFilePreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filename == null) { // if user inputs nothing then output error message
					String formattedDate = LocalDateTime.now().format(myFormatObj);
					errorLog.append(formattedDate + " - No File Specified\n");
				} else {
					// first check if Desktop is supported by Platform or not
					if (!Desktop.isDesktopSupported()) {
						return;
					}

					try {
						Desktop desktop = Desktop.getDesktop();
						if (filename.exists())
							desktop.open(filename);
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
			}
		});
		btnFilePreview.setBounds(245, 47, 118, 29);
		panel.add(btnFilePreview);
		
		//This button initiates the processing of the file and out puts it to OUTPUT.txt
		JButton btnProcessFile = new JButton("Process File and Save");
		btnProcessFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (filename != null && filename.exists()) {
					//Ensuring the file extension is .txt before continuing
					if (filename.toString().lastIndexOf(".") != -1 && filename.toString().lastIndexOf(".") != 0) {
						String fileExtension = filename.toString().substring(filename.toString().lastIndexOf(".") + 1);
						if (fileExtension.equals("txt")) {
							try {
								processFile(filename);
								if (filename != null && filename.exists()) {
									SuccessMessage.setForeground(new Color(60, 179, 113));
									SuccessMessage.setText("File Successfully Processed");
								}
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else { // Output Error to the Error log 
							String formattedDate = LocalDateTime.now().format(myFormatObj);
							errorLog.append(formattedDate + " - File extension is not .txt cannot proceed\n");
						}
					}
				} else {
					SuccessMessage.setForeground(new Color(255, 69, 0));
					SuccessMessage.setText("Cannot Process... No File Specified");
				}
			}
		});
		btnProcessFile.setBounds(10, 80, 353, 29);
		panel.add(btnProcessFile);
		
		// Button to clear the Error log
		JButton btnClearLog = new JButton("Clear");
		btnClearLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				errorLog.setText("");
			}
		});
		btnClearLog.setBounds(245, 232, 115, 29);
		panel.add(btnClearLog);
	}

	/**
	 * This method Reads the commands and contents of the file and manipulates the text 
	 * accordingly. 
	 * @param file
	 */
	public void processFile(File file) throws Exception {
		
		BufferedReader br = new BufferedReader(new FileReader(file));
		FileWriter tw = new FileWriter(output, false);

		while ((st = br.readLine()) != null) {
			if (st.length() == 2 && st.charAt(0) == '-') {
				char command = st.charAt(1);
				switch (command) {
				// Dealing with justification
				case 'l': {
					isLeft = true;
					isCenter = false;
					break;
				}
				case 'r': {
					isLeft = false;
					isCenter = false;
					break;
				}
				case 'c': {
					isLeft = false;
					isCenter = true;
					break;
				}
				// Spacing
				case 's': {
					isSingle = true;
					break;
				}
				case 'd': {
					isSingle = false;
					break;
				}
				// Indentation
				case 'i': {
					isIndent = true;
					isI = true;
					isB = false;
					break;
				}
				case 'b': {
					isIndent = true;
					isI = false;
					isB = true;
					break;
				}
				// Column
				case '1': {
					is1 = true;
					break;
				}
				case '2': {
					is1 = false;
					break;
				}
				// Misc Commands
				case 'e': {
					tw.write('\n');
					break;
				}//Resets all settings to default
				case 'n': {
					isLeft = true;
					isCenter = false;
					isSingle = true;
					is1 = true;
					break;
				}
				case 't': {
					isTitle = true;
					break;
				}

				} // end of switch

			} // end of if
			if(st.charAt(0) == '-') {
				complete+='\n';
				complete = check(complete);
				tw.write(complete);
				complete ="";
			}else {
				complete += st;			
			}	
			
			
		}
		tw.close();
		br.close();
	}
	
	/**
	 * This method manipulates the passed in string depending on the values of the
	 * boolean variables and returns the manipulated string
	 * @param line 
	 */
	public String check(String line) {
		String I ="     ";// indent with 5 spaces
		String B = "          ";//indent with 10 spaces
		String temp;
		if (isIndent == true && (isLeft == true || isCenter != true)) {
			if (isI) {
				if (is1 == true) {
					if (isSingle == true) {// single spaced
						line =I +line;
						for(int i = 80; i<line.length(); i+=80) {
							line = line.substring(0,i) + " \n" + line.substring(i, line.length());
						}
						
					} // end of isSingle
					else { // is Doubled
						line =I +line;
						//System.out.println(line);
						for(int i = 80; i<line.length(); i+=80) {
							line = line.substring(0,i) + "\n\n" + line.substring(i, line.length());
						}
					}
				} // end of is1
				else {// if 2 columns
					if (isSingle == true) {
					} // end of isSingle
					else { // is Doubled

					}
				}
			} // end of isI
			if (isB) {
				if (is1 == true) {
					if (isSingle == true) {
					} // end of isSingle
					else { // is Doubled

					}
				} // end of is1
				else {// if 2 columns
					if (isSingle == true) {
					} // end of isSingle
					else { // is Doubled

					}
				}
			} // end of isB
		} // end of if isIndent
		if (is1 == true) { // COLUMN
			if (isSingle == true) { // SPACING
			} // end of isSingle
			else { // is Doubled SPACING

			}
		} // end of is1
		else {// if 2 columns COLUMN
			if (isSingle == true) { // SPACING
			} // end of isSingle
			else { // is Doubled SPACING

			}
		}
		return line;
	}
} // end of main