package pl.edu.pw.fizyka.pojava.projekt6;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class Instruction extends JFrame{

	private static final long serialVersionUID = 1L;
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("pl.edu.pw.fizyka.pojava.projekt6/labels", new Locale("ru")); //informacje dlatego ru znajdują się w "labels_ru.properties"
	JPanel centerPanel, topPanel;
	JButton englishButton, polishButton;
	JLabel chooseLabel;
	JTextArea instText;
	JScrollPane scroll;
	JTextArea textArea;
	String text = resourceBundle.getString("text.inst");
	
	String title = resourceBundle.getString("classnameInst");
	static Color colorComponent=new Color(58, 59, 61);
	static Color colorText=new Color(12, 196, 166);
	static Color colorLight=new Color(23,163,163);
	static Color colorDark=new Color(9,61,61);
	static GradientPaint colorBG=new GradientPaint(0,400,colorLight,1200,400,colorDark);
	
	
	public Instruction() {
		this.setTitle(resourceBundle.getString("classnameInst"));
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(350,500);
        this.setLayout(new BorderLayout());
        this.setLocationRelativeTo(null); //centrowanie 
        
        
//--top----------------------------------------------------------------
        topPanel=new JPanel() { //robimy gradientowe tlo
			private static final long serialVersionUID = 1L;

			public void paintComponent(Graphics g) {
        		Graphics2D g2d=(Graphics2D) g;
        		g2d.setPaint(colorBG);
        		g2d.fillRect(0,0,getWidth(),getHeight());
        	}
        };
        topPanel.setLayout(new FlowLayout());;
        chooseLabel = new JLabel(resourceBundle.getString("chooselang"));
        chooseLabel.setForeground(colorDark);
        
        englishButton = new JButton(resourceBundle.getString("eng.lang"));
        englishButton.setBackground(colorComponent);
        englishButton.setForeground(colorText);
        englishButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resourceBundle = ResourceBundle.getBundle("pl.edu.pw.fizyka.pojava.projekt6/labels", new Locale("en"));
				chooseLabel.setText(resourceBundle.getString("chooselang"));
				englishButton.setText(resourceBundle.getString("eng.lang"));
				polishButton.setText(resourceBundle.getString("pol.lang"));
				setTitle(resourceBundle.getString("classnameInst"));
				textArea.setText(resourceBundle.getString("text.inst"));
				
			}
        	
        });
        
        polishButton = new JButton(resourceBundle.getString("pol.lang"));
        polishButton.setBackground(colorComponent);
        polishButton.setForeground(colorText);
        polishButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resourceBundle = ResourceBundle.getBundle("pl.edu.pw.fizyka.pojava.projekt6/labels", new Locale("ru"));
				chooseLabel.setText(resourceBundle.getString("chooselang"));
				englishButton.setText(resourceBundle.getString("eng.lang"));
				polishButton.setText(resourceBundle.getString("pol.lang"));
				setTitle(resourceBundle.getString("classnameInst"));
				textArea.setText(resourceBundle.getString("text.inst"));
			}
        	
        });
        
        topPanel.add(chooseLabel);
        topPanel.add(englishButton);
        topPanel.add(polishButton);
        this.add(topPanel,BorderLayout.PAGE_START);
        
//--center----------------------------------------------------------------        
        centerPanel = new JPanel();
        centerPanel.setBackground(Color.white); 
        
        textArea = new JTextArea(" ");
        textArea.setEditable(false);
        textArea.setText(resourceBundle.getString("text.inst"));
        scroll = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER );
        this.getContentPane().add(scroll);
        centerPanel.add(scroll, BorderLayout.CENTER);
        this.add(centerPanel,BorderLayout.CENTER);
	}

/*	public static void main(String[] args) {
		Instruction frame = new Instruction();
		frame.setVisible(true);

	}*/

}
