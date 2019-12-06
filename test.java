/** 
 *  Group:       8
 *  Name: 		 Ziad Abdelkarim, Maxim Tolea, Angel Flores, Chad Lutz
 *  Class:		 CSE360
 *  Section: 	 Wedneday 9:40 am
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
	
	// The following variables are for determining what command are called
  // The initialized variables are set to their default
	// Justification booleans

	// Just.
	boolean isLeft = true; // Left
	boolean isCenter = false; // Center

	// Spacing
	boolean isSingle = true; // Single

	// Indentation booleans
	boolean isIndent = false; // Is there indentation
	boolean isI = false; // Indent
	boolean isB = false; // Multiple Indents

	// Column boolean
	boolean is1 = true; // Single

	// Extra booleans
	boolean isT = false; // Title
	
	String st;

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
		// The component of the frame
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

		JLabel SuccessMessage = new JLabel("");
		SuccessMessage.setForeground(new Color(60, 179, 113));
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
				menu += "\n-t    Title (center justification)";
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
		
		//The file preview button for finding he file to be manipulated
		JButton btnFilePreview = new JButton("File Preview");
		btnFilePreview.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (filename == null) { // if user inputs nothing then output error message
					String formattedDate = LocalDateTime.now().format(myFormatObj);
					errorLog.append(formattedDate + " - No File Specified\n");
				} else {
					// first check if Desktop is supported by Platform or not
					if (!Desktop.isDesktopSupported()) {
						System.out.println("Desktop is not supported");
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
		btnFilePreview.setBounds(10, 85, 350, 29);
		panel.add(btnFilePreview);
		
		//Process file button which results in the saving of the file
		JButton btnProcessFile = new JButton("Process File and Save");
		btnProcessFile.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if (filename != null && filename.exists()) {
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
						} else {
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

	// processFile begins the reading, using the Buffered Reader.
	public void processFile(File file) throws Exception {
		int Tspace = 80; // total characters in a line
		int Uspace = 0; // unused space, the gaps filling in the line with as much text but still have
						// the remaining space
		// Just.
		boolean isLeft = true; // Left
		boolean isCenter = false; // Center

		// Spacing
		boolean isSingle = true; // Single

		// Indent
		boolean isIndent = false; // Is there indentation
		boolean isI = false; // Indent
		boolean isB = false; // Multiple Indents

		// Column
		boolean is1 = true; // Single

		// Extra
		boolean isT = false; // Title

		BufferedReader br = new BufferedReader(new FileReader(file));
	
		BufferedWriter writer = new BufferedWriter(new FileWriter("OUTPUT_test.txt"));// destination can be specified
																						// just like in the reader
		// using buffered reader
		while ((st = br.readLine()) != null) {
			
			if (st.length() == 2 && st.charAt(0) == '-') {
				char command = st.charAt(1);
				//switch to determine and act upon the commands
				switch (command) {
				// just.
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
				// spacing
				case 's': {
					isSingle = true;
					break;
				}
				case 'd': {
					isSingle = false;
					break;
				}
				// indent
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
				// Coloumn
				case '1': {
					is1 = true;
					break;
				}
				case '2': {
					is1 = false;
					break;
				}
				// Extra
				case 'e': {
					writer.write('\n');
					writer.write('\n');
					break;
				}
				case 'n': {
					isLeft = true;
					isCenter = false;

					isSingle = true;

					is1 = true;
					break;
				}
				case 't': {
					isT = true;
					break;
				}

				} // end of switch

			} // end of if
			
			while(st.charAt(0) != '-') {
				st+=st;		
			}
			for(int i = 79; i<st.length(); i+=79) {
				//st.replace(st.charAt(i),'\n');
				st = st.substring(0,i) + "\n" + st.substring(i, st.length());
			}
			st= st+'\n';
			
		}
		writer.close();
	}

	public String check(String line, File file) throws FileNotFoundException {
		BufferedReader br = new BufferedReader(new FileReader(file));
		String I ="     ";// indent with 5 spaces
		String B = "          ";//indent with 10 spaces
		if (isIndent == true && (isLeft == true || isCenter != true)) {
			if (isI) {
				if (is1 == true) {
					if (isSingle == true) {// single spaced
						line = I + line;
						//line = line.charAt()
						
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
