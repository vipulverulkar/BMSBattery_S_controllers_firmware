/*
This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software Foundation,
Inc., 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301  USA
 */

/**
 *
 * @author stancecoke
 */

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.io.*;
import java.awt.Desktop;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.text.JTextComponent;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.AbstractAction;
import java.awt.event.ActionEvent;
import javax.swing.Action;
import java.awt.event.ActionListener;
import java.awt.GridLayout;
import java.awt.Font;
import java.awt.Color;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.SwingConstants;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.DefaultListModel;
import javax.swing.event.ListDataListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.nio.file.Paths;
import java.util.Arrays;
import javax.swing.JCheckBox;
import javax.swing.ListModel;

public class Lishui_Parameter_Configurator extends javax.swing.JFrame {

    /**
     * Creates new form TSDZ2_Configurator
     */
    
    private File experimentalSettingsDir;
    private File lastSettingsFile = null;
    
    DefaultListModel provenSettingsFilesModel = new DefaultListModel();
    DefaultListModel experimentalSettingsFilesModel = new DefaultListModel();
    JList experimentalSettingsList = new JList(experimentalSettingsFilesModel);
    
    	public class FileContainer {

		public FileContainer(File file) {
			this.file = file;
		}
		public File file;

		@Override
		public String toString() {
			return file.getName();
		}
	}

    
    
 
public void loadSettings(File f) throws IOException {
    
     		BufferedReader in = new BufferedReader(new FileReader(f));
		//Parameter1.setText(in.readLine());
                TF_TRIGGER_OFFSET.setText(in.readLine());
                TF_TRIGGER_DEFAULT.setText(in.readLine());
                TF_TIMER_PERIOD.setText(in.readLine());
                TF_CAL_BAT_V.setText(in.readLine()); 
                TF_CAL_V.setText(in.readLine());
                TF_CAL_I.setText(in.readLine());
                TF_INDUCTANCE.setText(in.readLine());
                TF_RESISTANCE.setText(in.readLine());
                TF_FLUX_LINKAGE.setText(in.readLine());
                TF_GAMMA.setText(in.readLine());     
                TF_BATTERY_LEVEL_1.setText(in.readLine());
                TF_BATTERY_LEVEL_2.setText(in.readLine()); 
                TF_BATTERY_LEVEL_3.setText(in.readLine()); 
                TF_BATTERY_LEVEL_4.setText(in.readLine()); 
                TF_BATTERY_LEVEL_5.setText(in.readLine());
                TF_P_FACTOR_I_Q.setText(in.readLine());
                TF_I_FACTOR_I_Q.setText(in.readLine());
                TF_P_FACTOR_I_D.setText(in.readLine());
                TF_I_FACTOR_I_D.setText(in.readLine());
                RB_TORQUESENSOR.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_JLCD.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_KM5S.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_KUNTENG.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_BAFANG.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_DEBUG.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_DISABLE_DYN_ADC.setSelected(Boolean.parseBoolean(in.readLine()));
                RB_FAST_LOOP_LOG.setSelected(Boolean.parseBoolean(in.readLine()));

                
		in.close();
	}   

public void AddListItem(File newFile) {
        
        experimentalSettingsFilesModel.add(0, new FileContainer(newFile));
    
       // ListModel<String> Liste = expSet.getModel();
        expSet.repaint();
        JOptionPane.showMessageDialog(null,experimentalSettingsFilesModel.toString(),"Titel", JOptionPane.PLAIN_MESSAGE);
}
    
    public Lishui_Parameter_Configurator() {
        initComponents();

        // update lists
        
                        experimentalSettingsDir = new File(Paths.get(".").toAbsolutePath().normalize().toString());
		while (!Arrays.asList(experimentalSettingsDir.list()).contains("experimental settings")) {
			experimentalSettingsDir = experimentalSettingsDir.getParentFile();
		}
		File provenSettingsDir = new File(experimentalSettingsDir.getAbsolutePath() + File.separator + "proven settings");
		experimentalSettingsDir = new File(experimentalSettingsDir.getAbsolutePath() + File.separator + "experimental settings");



		for (File file : provenSettingsDir.listFiles()) {
			provenSettingsFilesModel.addElement(new Lishui_Parameter_Configurator.FileContainer(file));

			if (lastSettingsFile == null) {
				lastSettingsFile = file;
			} else {
				if(file.lastModified()>lastSettingsFile.lastModified()){
					lastSettingsFile = file;
				}
			}
		}
 		

                for (File file : experimentalSettingsDir.listFiles()) {
            experimentalSettingsFilesModel.addElement(new Lishui_Parameter_Configurator.FileContainer(file));
	}
        	experimentalSettingsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		experimentalSettingsList.setLayoutOrientation(JList.VERTICAL);
		experimentalSettingsList.setVisibleRowCount(-1); 
                
                expSet.setModel(experimentalSettingsFilesModel);
        
		JList provenSettingsList = new JList(provenSettingsFilesModel);
		provenSettingsList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		provenSettingsList.setLayoutOrientation(JList.VERTICAL);
		provenSettingsList.setVisibleRowCount(-1);
        
        provSet.setModel(provenSettingsFilesModel);
        jScrollPane2.setViewportView(provSet);
        

        expSet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                            	try {
                                int selectedIndex = expSet.getSelectedIndex();
                                experimentalSettingsList.setSelectedIndex(selectedIndex);
					loadSettings(((FileContainer) experimentalSettingsList.getSelectedValue()).file);
					experimentalSettingsList.clearSelection();
				} catch (IOException ex) {
					Logger.getLogger(Lishui_Parameter_Configurator.class.getName()).log(Level.SEVERE, null, ex);
				}
				experimentalSettingsList.clearSelection();
                                
				//updateDependiencies(false);
			}
		});
        
         provSet.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
                            	try {
                                int selectedIndex = provSet.getSelectedIndex();
                                provenSettingsList.setSelectedIndex(selectedIndex);
					loadSettings(((FileContainer) provenSettingsList.getSelectedValue()).file);
					provenSettingsList.clearSelection();
				} catch (IOException ex) {
					Logger.getLogger(Lishui_Parameter_Configurator.class.getName()).log(Level.SEVERE, null, ex);
				}
				provenSettingsList.clearSelection();
				//updateDependiencies(false);
			}
		});
         
         
         jButton2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				{

					int n = JOptionPane.showConfirmDialog(
							null,
							"If you run this function with a brand new controller, the original firmware will be erased. This can't be undone. Are you sure?",
							"",
							JOptionPane.YES_NO_OPTION);

					if (n == JOptionPane.YES_OPTION) {
						try {
							Process process = Runtime.getRuntime().exec("cmd /c start WriteOptionBytes");
						} catch (IOException e1) {
							Parameter1.setText("Error");
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null, "Goodbye");
					}

					// Saving code here
				}
			}
		});
         
                 
         
          jButton1.addActionListener(new ActionListener() {
          
          public void actionPerformed(ActionEvent arg0) {
          				PrintWriter iWriter = null;
                                PrintWriter pWriter = null;
				try {
					//FileWriter fw = new FileWriter("settings.ini");
					//BufferedWriter bw = new BufferedWriter(fw);
                                        
                                        
					File newFile = new File(experimentalSettingsDir + File.separator + new SimpleDateFormat("yyyyMMdd-HHmmssz").format(new Date()) + ".ini");
					//TSDZ2_Configurator ConfiguratorObject = new TSDZ2_Configurator();
                                        //ConfiguratorObject.AddListItem(newFile);
                                        experimentalSettingsFilesModel.add(0, new FileContainer(newFile)); //hier wird nur die neue Datei in die Liste geschrieben...

					iWriter = new PrintWriter(new BufferedWriter(new FileWriter(newFile)));
					pWriter = new PrintWriter(new BufferedWriter(new FileWriter("config.h")));
					pWriter.println("/*\r\n"
							+ " * config.h\r\n"
							+ " *\r\n"
							+ " *  Automatically created by OSEC Parameter Configurator\r\n"
							+ " *  Author: stancecoke\r\n"
							+ " */\r\n"
							+ "\r\n"
							+ "#ifndef CONFIG_H_\r\n"
							+ "#define CONFIG_H_\r\n"
                                                        + "#include \"stdint.h\"\r\n"
                                                        + "#define DISPLAY_TYPE_KINGMETER_618U (1<<4)                  // King-Meter 618U protocol (KM5s, EBS-LCD2, J-LCD, SW-LCD)\r\n"
                                                        + "#define DISPLAY_TYPE_KINGMETER_901U (1<<8)                  // King-Meter 901U protocol (KM5s)\r\n"
                                                        + "#define DISPLAY_TYPE_KINGMETER      (DISPLAY_TYPE_KINGMETER_618U|DISPLAY_TYPE_KINGMETER_901U)\r\n"
                                                        + "#define DISPLAY_TYPE_BAFANG (1<<2)							// For 'Blaupunkt' Display of Prophete Entdecker\r\n"
                                                        + "#define DISPLAY_TYPE_KUNTENG (1<<1)							// For ASCII-Output in Debug mode\r\n"
                                                        + "#define DISPLAY_TYPE_DEBUG (1<<0)							// For ASCII-Output in Debug mode);\r\n"
                                        );
                                        
                                        
                                        String text_to_save = "#define TRIGGER_OFFSET_ADC " + TF_TRIGGER_OFFSET.getText();
				        iWriter.println(TF_TRIGGER_OFFSET.getText());
					pWriter.println(text_to_save); 
                                        
                                        text_to_save = "#define TRIGGER_DEFAULT " + TF_TRIGGER_DEFAULT.getText();
				        iWriter.println(TF_TRIGGER_DEFAULT.getText());
					pWriter.println(text_to_save); 
                                             
                                        text_to_save = "#define _T " + TF_TIMER_PERIOD.getText();
				        iWriter.println(TF_TIMER_PERIOD.getText());
					pWriter.println(text_to_save);                                         
                                              
                                        text_to_save = "#define CAL_BAT_V " + TF_CAL_BAT_V.getText();
				        iWriter.println(TF_CAL_BAT_V.getText());
					pWriter.println(text_to_save); 
                                              
                                        text_to_save = "#define CAL_V " + TF_CAL_V.getText();
				        iWriter.println(TF_CAL_V.getText());
					pWriter.println(text_to_save);                                         
                                              
                                        text_to_save = "#define CAL_I " + TF_CAL_I.getText();
				        iWriter.println(TF_CAL_I.getText());
					pWriter.println(text_to_save);  
                                              
                                        text_to_save = "#define INDUCTANCE " + TF_INDUCTANCE.getText();
				        iWriter.println(TF_INDUCTANCE.getText());
					pWriter.println(text_to_save); 
                                              
                                        text_to_save = "#define RESISTANCE " + TF_RESISTANCE.getText();
				        iWriter.println(TF_RESISTANCE.getText());
					pWriter.println(text_to_save); 
                                               
                                        text_to_save = "#define FLUX_LINKAGE " + TF_FLUX_LINKAGE.getText();
				        iWriter.println(TF_FLUX_LINKAGE.getText());
					pWriter.println(text_to_save);
                                               
                                        text_to_save = "#define GAMMA " + TF_GAMMA.getText();
				        iWriter.println(TF_GAMMA.getText());
					pWriter.println(text_to_save);
                                               
                                        text_to_save = "#define BATTERY_LEVEL_1 " + TF_BATTERY_LEVEL_1.getText();
				        iWriter.println(TF_BATTERY_LEVEL_1.getText());
					pWriter.println(text_to_save);                                        
                                               
                                        text_to_save = "#define BATTERY_LEVEL_2 " + TF_BATTERY_LEVEL_2.getText();
				        iWriter.println(TF_BATTERY_LEVEL_2.getText());
					pWriter.println(text_to_save); 
                                               
                                        text_to_save = "#define BATTERY_LEVEL_3 " + TF_BATTERY_LEVEL_3.getText();
				        iWriter.println(TF_BATTERY_LEVEL_3.getText());
					pWriter.println(text_to_save); 
                                               
                                        text_to_save = "#define BATTERY_LEVEL_4 " + TF_BATTERY_LEVEL_4.getText();
				        iWriter.println(TF_BATTERY_LEVEL_4.getText());
					pWriter.println(text_to_save); 
                                               
                                        text_to_save = "#define BATTERY_LEVEL_5 " + TF_BATTERY_LEVEL_5.getText();
				        iWriter.println(TF_BATTERY_LEVEL_5.getText());
					pWriter.println(text_to_save); 
                                               
                                        text_to_save = "#define P_FACTOR_I_Q " + TF_P_FACTOR_I_Q.getText();
				        iWriter.println(TF_P_FACTOR_I_Q.getText());
					pWriter.println(text_to_save);
                                               
                                        text_to_save = "#define I_FACTOR_I_Q " + TF_I_FACTOR_I_Q.getText();
				        iWriter.println(TF_I_FACTOR_I_Q.getText());
					pWriter.println(text_to_save);
                                               
                                        text_to_save = "#define P_FACTOR_I_D " + TF_P_FACTOR_I_D.getText();
				        iWriter.println(TF_P_FACTOR_I_D.getText());
					pWriter.println(text_to_save);
                                               
                                        text_to_save = "#define I_FACTOR_I_D " + TF_I_FACTOR_I_D.getText();
				        iWriter.println(TF_I_FACTOR_I_D.getText());
					pWriter.println(text_to_save);                                        
                                        
                                        if (RB_TORQUESENSOR.isSelected()) {
						text_to_save = "#define TS_MODE";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_TORQUESENSOR.isSelected());
                                        
                                        if (RB_JLCD.isSelected()) {
						text_to_save = "#define DISPLAY_TYPE DISPLAY_TYPE_KINGMETER_618U \\J-LCD";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_JLCD.isSelected());
                                        
                                        if (RB_KM5S.isSelected()) {
						text_to_save = "#define DISPLAY_TYPE DISPLAY_TYPE_KINGMETER_901U \\KM5S";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_KM5S.isSelected());
                                        
                                        if (RB_KUNTENG.isSelected()) {
						text_to_save = "#define DISPLAY_TYPE DISPLAY_TYPE_KUNTENG \\Kunteng LCD3/5 etc.";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_KUNTENG.isSelected());                                        

                                        if (RB_BAFANG.isSelected()) {
						text_to_save = "#define DISPLAY_TYPE DISPLAY_TYPE_BAFANG \\Bafang Displays, including 'Blaupunkt' ";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_BAFANG.isSelected());
                                        
                                        if (RB_DEBUG.isSelected()) {
						text_to_save = "#define DISPLAY_TYPE DISPLAY_TYPE_DEBUG \\ASCII Printout for debugging";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_DEBUG.isSelected());                                        
                                        
                                        if (RB_DISABLE_DYN_ADC.isSelected()) {
						text_to_save = "#define DISABLE_DYNAMIC_ADC";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_DISABLE_DYN_ADC.isSelected());
                                        
                                        if (RB_FAST_LOOP_LOG.isSelected()) {
						text_to_save = "#define FAST_LOOP_LOG";
						pWriter.println(text_to_save);
					}
					iWriter.println(RB_FAST_LOOP_LOG.isSelected());   
                                        
                                        pWriter.println("\r\n#endif /* CONFIG_H_ */");

					iWriter.close();
 				} catch (IOException ioe) {
					ioe.printStackTrace();
				} finally {
					if (pWriter != null) {
						pWriter.flush();
						pWriter.close();

					}
				}  
                                try {
					Process process = Runtime.getRuntime().exec("cmd /c start Start_Compiling");
				} catch (IOException e1) {
					Parameter1.setText("Error");
					e1.printStackTrace();
				}
          
          }
          
          
          });
                  
         		if (lastSettingsFile != null) {
			try {
				loadSettings(lastSettingsFile);
			} catch (Exception ex) {

			}
			provenSettingsList.clearSelection();
			experimentalSettingsList.clearSelection();
			//updateDependiencies(false);
		}
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BG_DISPLAYS = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        TAB1 = new javax.swing.JPanel();
        Parameter1 = new javax.swing.JTextField();
        Label_Parameter1 = new javax.swing.JLabel();
        RB_JLCD = new javax.swing.JRadioButton();
        RB_KM5S = new javax.swing.JRadioButton();
        RB_BAFANG = new javax.swing.JRadioButton();
        RB_KUNTENG = new javax.swing.JRadioButton();
        RB_DEBUG = new javax.swing.JRadioButton();
        RB_TORQUESENSOR = new javax.swing.JRadioButton();
        jLabel21 = new javax.swing.JLabel();
        TAB2 = new javax.swing.JPanel();
        TF_TRIGGER_OFFSET = new javax.swing.JTextField();
        Label_Param3 = new javax.swing.JLabel();
        RB_FAST_LOOP_LOG = new javax.swing.JRadioButton();
        RB_DISABLE_DYN_ADC = new javax.swing.JRadioButton();
        TF_TRIGGER_DEFAULT = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        TF_TIMER_PERIOD = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        TF_CAL_BAT_V = new javax.swing.JTextField();
        TF_CAL_V = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        TF_CAL_I = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        TF_INDUCTANCE = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        TF_RESISTANCE = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        TF_FLUX_LINKAGE = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        TF_GAMMA = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        TF_BATTERY_LEVEL_1 = new javax.swing.JTextField();
        TF_BATTERY_LEVEL_2 = new javax.swing.JTextField();
        jLabel20 = new javax.swing.JLabel();
        TF_BATTERY_LEVEL_3 = new javax.swing.JTextField();
        TF_BATTERY_LEVEL_4 = new javax.swing.JTextField();
        TF_BATTERY_LEVEL_5 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        TF_P_FACTOR_I_Q = new javax.swing.JTextField();
        TF_I_FACTOR_I_Q = new javax.swing.JTextField();
        TF_P_FACTOR_I_D = new javax.swing.JTextField();
        TF_I_FACTOR_I_D = new javax.swing.JTextField();
        label1 = new java.awt.Label();
        jScrollPane1 = new javax.swing.JScrollPane();
        expSet = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        provSet = new javax.swing.JList<>();
        jLabel2 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        Parameter1.setText("100");

        Label_Parameter1.setLabelFor(Parameter1);
        Label_Parameter1.setText("Display");

        BG_DISPLAYS.add(RB_JLCD);
        RB_JLCD.setText("J-LCD");
        RB_JLCD.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RB_JLCDActionPerformed(evt);
            }
        });

        BG_DISPLAYS.add(RB_KM5S);
        RB_KM5S.setText("KM5S");

        BG_DISPLAYS.add(RB_BAFANG);
        RB_BAFANG.setText("Bafang");

        BG_DISPLAYS.add(RB_KUNTENG);
        RB_KUNTENG.setText("Kunteng");

        BG_DISPLAYS.add(RB_DEBUG);
        RB_DEBUG.setText("Debug");

        RB_TORQUESENSOR.setText("torquesensor");

        jLabel21.setText("Ride Mode");

        javax.swing.GroupLayout TAB1Layout = new javax.swing.GroupLayout(TAB1);
        TAB1.setLayout(TAB1Layout);
        TAB1Layout.setHorizontalGroup(
            TAB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TAB1Layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(TAB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel21)
                    .addComponent(RB_TORQUESENSOR)
                    .addComponent(RB_DEBUG)
                    .addComponent(RB_KUNTENG)
                    .addComponent(RB_BAFANG)
                    .addComponent(RB_KM5S)
                    .addComponent(RB_JLCD)
                    .addGroup(TAB1Layout.createSequentialGroup()
                        .addComponent(Label_Parameter1)
                        .addGap(269, 269, 269)
                        .addComponent(Parameter1, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(454, Short.MAX_VALUE))
        );
        TAB1Layout.setVerticalGroup(
            TAB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TAB1Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(TAB1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Parameter1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_Parameter1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RB_JLCD)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RB_KM5S)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RB_BAFANG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RB_KUNTENG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RB_DEBUG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 89, Short.MAX_VALUE)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(RB_TORQUESENSOR)
                .addGap(73, 73, 73))
        );

        RB_JLCD.getAccessibleContext().setAccessibleName("RB_J-LCD");

        jTabbedPane1.addTab("Basic Settings", TAB1);

        TF_TRIGGER_OFFSET.setText("50");

        Label_Param3.setText("Trigger offset ADC");

        RB_FAST_LOOP_LOG.setText("enable fast loop logging");

        RB_DISABLE_DYN_ADC.setText("disable dynamic ADC");

        TF_TRIGGER_DEFAULT.setText("2020");

        jLabel3.setText("Trigger default");

        jLabel4.setText("ADC Timing");

        jLabel5.setText("Timer period");

        TF_TIMER_PERIOD.setText("2028");

        jLabel6.setText("Calibration");

        jLabel7.setText("Battery Voltage");

        TF_CAL_BAT_V.setText("256");

        TF_CAL_V.setText("15LL<<8");

        jLabel8.setText("FOC Voltage");

        TF_CAL_I.setText("38LL<<8");

        jLabel9.setText("FOC Current");

        jLabel10.setText("Sensorless settings");

        jLabel11.setText("Inductance");

        TF_INDUCTANCE.setText("6LL");

        jLabel12.setText("Resistance");

        TF_RESISTANCE.setText("40LL");

        jLabel13.setText("Flux Linkage");

        TF_FLUX_LINKAGE.setText("1200LL");

        jLabel14.setText("Gamma");

        TF_GAMMA.setText("9LL");

        jLabel15.setText("Battery settings");

        jLabel16.setText("Bar 1");

        jLabel17.setText("Bar 2");

        jLabel18.setText("Bar 3");

        jLabel19.setText("Bar 4");

        TF_BATTERY_LEVEL_1.setText("323000");

        TF_BATTERY_LEVEL_2.setText("329000");

        jLabel20.setText("Bar 5");

        TF_BATTERY_LEVEL_3.setText("344000");

        TF_BATTERY_LEVEL_4.setText("368000");

        TF_BATTERY_LEVEL_5.setText("380000");

        jLabel22.setText("PI control settings");

        jLabel23.setText("Iq p-factor");

        jLabel24.setText("Iq i-factor");

        jLabel25.setText("Id p-factor");

        jLabel26.setText("Id i-factor");

        TF_P_FACTOR_I_Q.setText("0.1L");

        TF_I_FACTOR_I_Q.setText("0.01L");

        TF_P_FACTOR_I_D.setText("1");

        TF_I_FACTOR_I_D.setText("1");

        javax.swing.GroupLayout TAB2Layout = new javax.swing.GroupLayout(TAB2);
        TAB2.setLayout(TAB2Layout);
        TAB2Layout.setHorizontalGroup(
            TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, TAB2Layout.createSequentialGroup()
                .addGap(27, 27, 27)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TAB2Layout.createSequentialGroup()
                        .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(TAB2Layout.createSequentialGroup()
                                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(Label_Param3, javax.swing.GroupLayout.DEFAULT_SIZE, 93, Short.MAX_VALUE)
                                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(TF_TRIGGER_OFFSET)
                                    .addComponent(TF_TRIGGER_DEFAULT, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                                    .addComponent(TF_TIMER_PERIOD))))
                        .addGap(31, 31, 31)
                        .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel22)
                            .addGroup(TAB2Layout.createSequentialGroup()
                                .addComponent(jLabel23)
                                .addGap(18, 18, 18)
                                .addComponent(TF_P_FACTOR_I_Q, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(TAB2Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addGap(22, 22, 22)
                                .addComponent(TF_I_FACTOR_I_Q))
                            .addGroup(TAB2Layout.createSequentialGroup()
                                .addComponent(jLabel25)
                                .addGap(18, 18, 18)
                                .addComponent(TF_P_FACTOR_I_D))
                            .addGroup(TAB2Layout.createSequentialGroup()
                                .addComponent(jLabel26)
                                .addGap(22, 22, 22)
                                .addComponent(TF_I_FACTOR_I_D)))
                        .addGap(82, 82, 82)
                        .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(TAB2Layout.createSequentialGroup()
                                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13)
                                    .addComponent(jLabel14))
                                .addGap(18, 18, 18)
                                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(TF_RESISTANCE)
                                    .addComponent(TF_INDUCTANCE)
                                    .addComponent(TF_FLUX_LINKAGE)
                                    .addComponent(TF_GAMMA)))
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(TAB2Layout.createSequentialGroup()
                                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(TAB2Layout.createSequentialGroup()
                                        .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(TF_CAL_BAT_V)
                                            .addComponent(TF_CAL_V)
                                            .addComponent(TF_CAL_I, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addComponent(RB_DISABLE_DYN_ADC)
                    .addComponent(RB_FAST_LOOP_LOG))
                .addGap(75, 75, 75)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(TAB2Layout.createSequentialGroup()
                        .addComponent(jLabel20)
                        .addGap(18, 18, 18)
                        .addComponent(TF_BATTERY_LEVEL_5))
                    .addComponent(jLabel15)
                    .addGroup(TAB2Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addGap(18, 18, 18)
                        .addComponent(TF_BATTERY_LEVEL_2))
                    .addGroup(TAB2Layout.createSequentialGroup()
                        .addComponent(jLabel18)
                        .addGap(18, 18, 18)
                        .addComponent(TF_BATTERY_LEVEL_3))
                    .addGroup(TAB2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(18, 18, 18)
                        .addComponent(TF_BATTERY_LEVEL_4))
                    .addGroup(TAB2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(18, 18, 18)
                        .addComponent(TF_BATTERY_LEVEL_1, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(107, 107, 107))
        );
        TAB2Layout.setVerticalGroup(
            TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(TAB2Layout.createSequentialGroup()
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(TAB2Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jLabel22)))
                    .addGroup(TAB2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel10)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_TRIGGER_OFFSET, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Label_Param3)
                    .addComponent(jLabel11)
                    .addComponent(TF_INDUCTANCE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel23)
                    .addComponent(TF_P_FACTOR_I_Q, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_TRIGGER_DEFAULT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3)
                    .addComponent(jLabel12)
                    .addComponent(TF_RESISTANCE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(TF_I_FACTOR_I_Q, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(TF_TIMER_PERIOD, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(TF_FLUX_LINKAGE, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25)
                    .addComponent(TF_P_FACTOR_I_D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(TF_GAMMA, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel26)
                    .addComponent(TF_I_FACTOR_I_D, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(TF_CAL_BAT_V, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_CAL_V, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TF_CAL_I, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(23, 23, 23)
                .addComponent(RB_FAST_LOOP_LOG)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(RB_DISABLE_DYN_ADC)
                .addGap(40, 40, 40))
            .addGroup(TAB2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(TF_BATTERY_LEVEL_1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(TF_BATTERY_LEVEL_2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(TF_BATTERY_LEVEL_3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(TF_BATTERY_LEVEL_4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(TAB2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(TF_BATTERY_LEVEL_5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Advanced Settings", TAB2);

        label1.setFont(new java.awt.Font("Ebrima", 0, 24)); // NOI18N
        label1.setText("E-Bike Parameter Configurator");

        expSet.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(expSet);

        jLabel1.setText("Experimental Settings");

        provSet.setModel(new javax.swing.AbstractListModel<String>() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public String getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(provSet);

        jLabel2.setText("Proven Settings");

        jButton1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton1.setText("Compile & Flash");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jButton2.setText("Unlock controller");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(44, 44, 44)
                        .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 856, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel2))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 136, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane1))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(label1, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addGap(3, 3, 3)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(84, 84, 84))))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("MotorConfiguration");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed

    private void RB_JLCDActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RB_JLCDActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_RB_JLCDActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Lishui_Parameter_Configurator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Lishui_Parameter_Configurator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Lishui_Parameter_Configurator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Lishui_Parameter_Configurator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Lishui_Parameter_Configurator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup BG_DISPLAYS;
    private javax.swing.JLabel Label_Param3;
    private javax.swing.JLabel Label_Parameter1;
    private javax.swing.JTextField Parameter1;
    private javax.swing.JRadioButton RB_BAFANG;
    private javax.swing.JRadioButton RB_DEBUG;
    private javax.swing.JRadioButton RB_DISABLE_DYN_ADC;
    private javax.swing.JRadioButton RB_FAST_LOOP_LOG;
    private javax.swing.JRadioButton RB_JLCD;
    private javax.swing.JRadioButton RB_KM5S;
    private javax.swing.JRadioButton RB_KUNTENG;
    private javax.swing.JRadioButton RB_TORQUESENSOR;
    private javax.swing.JPanel TAB1;
    private javax.swing.JPanel TAB2;
    private javax.swing.JTextField TF_BATTERY_LEVEL_1;
    private javax.swing.JTextField TF_BATTERY_LEVEL_2;
    private javax.swing.JTextField TF_BATTERY_LEVEL_3;
    private javax.swing.JTextField TF_BATTERY_LEVEL_4;
    private javax.swing.JTextField TF_BATTERY_LEVEL_5;
    private javax.swing.JTextField TF_CAL_BAT_V;
    private javax.swing.JTextField TF_CAL_I;
    private javax.swing.JTextField TF_CAL_V;
    private javax.swing.JTextField TF_FLUX_LINKAGE;
    private javax.swing.JTextField TF_GAMMA;
    private javax.swing.JTextField TF_INDUCTANCE;
    private javax.swing.JTextField TF_I_FACTOR_I_D;
    private javax.swing.JTextField TF_I_FACTOR_I_Q;
    private javax.swing.JTextField TF_P_FACTOR_I_D;
    private javax.swing.JTextField TF_P_FACTOR_I_Q;
    private javax.swing.JTextField TF_RESISTANCE;
    private javax.swing.JTextField TF_TIMER_PERIOD;
    private javax.swing.JTextField TF_TRIGGER_DEFAULT;
    private javax.swing.JTextField TF_TRIGGER_OFFSET;
    private javax.swing.JList<String> expSet;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    private java.awt.Label label1;
    private javax.swing.JList<String> provSet;
    // End of variables declaration//GEN-END:variables
}
