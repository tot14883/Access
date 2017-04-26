package main;

import java.awt.Desktop;
import java.awt.Dimension;
import java.sql.*;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.awt.event.ActionEvent;

public class Operator extends JFrame {
	Connection conn = null;
	Statement s = null;
	String strView;
	JTable table;
	ResultSet set;
	JLabel show;
	String input = null;
	int row;
	private JTextField ID;
	private JTextField Name;
	private JTextField Phone;
	private JTextField Email;
	private JTextField Company;
	
	public static void main(String[] args) throws SQLException {
		Operator op = new Operator();
		op.setVisible(true);
	}

	public Operator() throws SQLException {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 580, 412);
		setTitle("Employer");
		setResizable(false);
		getContentPane().setLayout(null);

		// Customer Label
		JLabel lblCustomer = new JLabel("Employer List");
		lblCustomer.setBounds(231, 28, 95, 14);
		getContentPane().add(lblCustomer);
		
		// ScrollPane for Table
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(33, 61, 519, 93);
		getContentPane().add(scrollPane);
		
		// Table
		JTable table = new JTable();
				
		// Model for Table
		DefaultTableModel model = (DefaultTableModel)table.getModel();
		model.addColumn("ID");
		model.addColumn("Name");
		model.addColumn("Phone");
		model.addColumn("Email");
		model.addColumn("Company");	
		try{
		//String location = Operator.class.getResource("Operator.mdb").getPath().toString();
		String connString = "jdbc:ucanaccess://D:/Operator.mdb";//กำหนดชื่อไฟล์ที่จะต้องการใช้
		//conn = DriverManager.getConnection("jdbc:ucanaccess:/"+location);
		conn = DriverManager.getConnection(connString);//นำไฟล์ที่จะใช้ในAttribute connString มาเปิดในMethod ตัวนี้
		s = conn.createStatement();//ทำการสร้างไฟล์ขึ้นและเรียกใช้
	    strView = "SELECT * FROM trader";//เลือกที่ต้องการโชว์โดย SELECT * FROM และชื่อ Table ใน Attribute
	    set = s.executeQuery(strView);//ทำการเรียกมาใช้	   
	    row = 0;		
		scrollPane.setViewportView(table);		
		}catch(Exception e){
			JOptionPane.showMessageDialog(null, "Dowload and File must in Drive D only !!!");
		}
		JButton Github = new JButton("Github");
		Github.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				 try {
					Desktop.getDesktop().browse(new URL("https://github.com/tot14883/Access").toURI());
				} catch (IOException | URISyntaxException e1) {
					
					e1.printStackTrace();
				}
				
			}
		});
		Github.setBounds(379, 274, 89, 23);
		getContentPane().add(Github);
		
		JButton Show = new JButton("Show");
		Show.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
				try {
					while((set!=null) && (set.next()))
					{			
						model.addRow(new Object[0]);
						model.setValueAt(set.getString(1), row, 0);
						model.setValueAt(set.getString(2), row, 1);
						model.setValueAt(set.getString(3), row, 2);
						model.setValueAt(set.getString(4), row, 3);
						model.setValueAt(set.getString(5), row, 4);		
						row++;
						
						
					}
					set.close();
				} catch (SQLException e) {				
					e.printStackTrace();
				}
				
			}  
		
		});
		Show.setBounds(267, 274, 89, 23);
		getContentPane().add(Show);
		
		JButton Delete = new JButton("Delete");
		Delete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try{
				String del = String.valueOf(ID.getText());
				String Del = "DELETE FROM trader WHERE ID = '"+del+"' ";
			    
					s.execute(Del);
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}
			    show.setText("Delete Completely Please Open new Program for see information");	
			}
		});
		Delete.setBounds(148, 274, 89, 23);
		getContentPane().add(Delete);
		
		JLabel lblId = new JLabel("ID");
		lblId.setBounds(65, 250, 46, 14);
		getContentPane().add(lblId);
		
		ID = new JTextField();
		ID.setBounds(36, 275, 86, 20);
		getContentPane().add(ID);
		ID.setColumns(10);
		
		Name = new JTextField();
		Name.setBounds(36, 207, 86, 20);
		getContentPane().add(Name);
		Name.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(65, 182, 46, 14);
		getContentPane().add(lblNewLabel);
		
		Phone = new JTextField();
		Phone.setBounds(151, 207, 86, 20);
		getContentPane().add(Phone);
		Phone.setColumns(10);
		
		Email = new JTextField();
		Email.setBounds(267, 207, 86, 20);
		getContentPane().add(Email);
		Email.setColumns(10);
		
		Company = new JTextField();
		Company.setBounds(379, 207, 86, 20);
		getContentPane().add(Company);
		Company.setColumns(10);
		
		JButton Insert = new JButton("Insert");
		Insert.setBounds(475, 206, 89, 23);
		Insert.addActionListener(new ActionListener() {			
			public void actionPerformed(ActionEvent arg0) {
				
				String N = String.valueOf(Name.getText());//ใส่ค่าที่ต้องการเพิ่ม		
				String P = String.valueOf(Phone.getText()); 
				String E = String.valueOf(Email.getText());				
				String C = String.valueOf(Company.getText());
						
				if(N.isEmpty() || P.isEmpty()  || E.isEmpty() || C.isEmpty()){
			    	show.setText("Please Input Information");
			    }
			    else{
				try {
					String insert = "insert into trader(Name,Phone,Email,Company) VALUES('"+N+"','"+P+"','"+E+"','"+C+"')";//เพิ่มชื่อเข้าไป
					s.executeUpdate(insert);
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}//Update ข้อมูล
				 show.setText("Insert Completely Please Open new Program for see information");	
			    }				
			}
		});
		getContentPane().add(Insert);
		
	    show = new JLabel("");
		show.setBounds(23, 318, 500, 14);
		getContentPane().add(show);
		
		JLabel lblNewLabel_1 = new JLabel("Phone");
		lblNewLabel_1.setBounds(170, 182, 46, 14);
		getContentPane().add(lblNewLabel_1);
		
		JLabel lblNewLabel_2 = new JLabel("Email");
		lblNewLabel_2.setBounds(290, 182, 46, 14);
		getContentPane().add(lblNewLabel_2);
		
		JLabel lblNewLabel_3 = new JLabel("Company");
		lblNewLabel_3.setBounds(401, 182, 46, 14);
		getContentPane().add(lblNewLabel_3);
		
		JLabel lblNewLabel_4 = new JLabel("Dowload File in Github");
		lblNewLabel_4.setBounds(417, 358, 135, 14);
		getContentPane().add(lblNewLabel_4);
		
	}
}


