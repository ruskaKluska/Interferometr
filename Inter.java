package wstepne;

import javax.swing.JFrame;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSlider;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Shape;

import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.ActionListener;

public class Inter extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	JPanel leftPanel, rightPanel, centerPanel, topPanel, bottomPanel;
	JMenuBar menuBar;
	JMenu menu;
	JMenuItem save, dowland;
	
	JLabel labelDlugosc, labelCm, textLabel;
	JSlider slider;
	JButton buttonEn, buttonPl, buttonStart, buttonStop;
	
	static final int SLIDER_MIN = 0;//dane do slidera
    static final int SLIDER_MAX = 30;
    static final int SLIDER_INIT = 1;
	int add;
	
	
	public Inter(String title) throws HeadlessException
	{		
		super(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setSize(1200,800);
        this.setLayout(new BorderLayout());       
        
//--menu----------------------------------------------------------------
        
        menuBar=new JMenuBar();
        menu=new JMenu("Menu");
       dowland=new JMenuItem("Wprowadz dane z pliku");
        save=new JMenuItem("Zapisz dane do pliku");
        
        
        
        dowland.addActionListener(new ActionListener() 
	       {
	           @Override
	           public void actionPerformed(ActionEvent e) 
	           {
	        	   JFileChooser pick = new JFileChooser();				
				    FileNameExtensionFilter ffilter = new FileNameExtensionFilter("TXT", "txt" );
				    pick.setFileFilter(ffilter);
				    int open = pick.showDialog(null,"Choose project");
	           }
	       });
        
        save.addActionListener(new ActionListener() 
	       {
	           @Override
	           public void actionPerformed(ActionEvent e) 
	           {
	        	   
	           }
	       });
	       
	       
        
        menu.add(dowland);
        menu.addSeparator();
        menu.add(save);
        menuBar.add(menu);
        
        this.setJMenuBar(menuBar);
        
//--topPanel------------------------------------------------------------
        
        topPanel=new JPanel();
        topPanel.setLayout(new FlowLayout());
        
        Color color1=new Color(120, 161, 161);
        topPanel.setBackground(color1);
        
        labelDlugosc=new JLabel("Dlugosc fali: ");
        
        slider=new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);
        slider.setMinorTickSpacing(10);
        slider.setMajorTickSpacing(5);
        slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBackground(color1);
		slider.addChangeListener(new SliderChangeListener());
		
		textLabel=new JLabel(String.format("%d", slider.getValue()));
		
		labelCm=new JLabel(" cm                      ");
		
		buttonEn=new JButton("English");
		buttonPl=new JButton("Polish");
		
		/*
		
		buttonEn.addActionListener(new ActionListener() 
	       {
	           @Override
	           public void actionPerformed(ActionEvent e) 
	           {
	        	   
	           }
	       });
	       
	       buttonPl.addActionListener(new ActionListener() 
	       {
	           @Override
	           public void actionPerformed(ActionEvent e) 
	           {
	        	   
	           }
	       });
	       
	       */
        
		topPanel.add(labelDlugosc);
		topPanel.add(slider);
		topPanel.add(textLabel);
        topPanel.add(labelCm);
        topPanel.add(buttonEn);
        topPanel.add(buttonPl);
        
        this.add(topPanel, BorderLayout.PAGE_START);
//--bottomPanel---------------------------------------------------------
        
        bottomPanel=new JPanel();
        topPanel.setLayout(new FlowLayout());
        
        ImageIcon iconPlay = new ImageIcon("./image/play.png");
        ImageIcon iconStop = new ImageIcon("./image/stop.png");
        
        buttonStart=new JButton("Start",iconPlay);
        buttonStop=new JButton("Stop",iconStop);
        
        /*
        
        buttonStart.addActionListener(new ActionListener() 
	       {
	           @Override
	           public void actionPerformed(ActionEvent e) 
	           {
	        	   
	           }
	       });
        
        buttonStop.addActionListener(new ActionListener() 
	       {
	           @Override
	           public void actionPerformed(ActionEvent e) 
	           {
	        	   
	           }
	       });
        
        */
        
        bottomPanel.add(buttonStart);
        bottomPanel.add(buttonStop);
        
        this.add(bottomPanel, BorderLayout.PAGE_END);
//--leftPanel-----------------------------------------------------------
        
        leftPanel=new JPanel();
    	
        this.add(leftPanel, BorderLayout.LINE_START);
//--rightPanel----------------------------------------------------------
        
        rightPanel=new JPanel();

        this.add(rightPanel, BorderLayout.LINE_END);
//--centerPanel---------------------------------------------------------
        
        centerPanel=new JPanel(){
        	private static final long serialVersionUID = 1L;
        	public void paintComponent (Graphics intG) {
// rysunek interferometru
        		Graphics2D intG2 = (Graphics2D) intG;
        		Line2D line1 = new Line2D.Double(175,100,175,480);
        		Line2D line1A = new Line2D.Double(185,100,185,290);
        		Line2D line2 = new Line2D.Double(40+(add*3),285,320,285);
        		Line2D line2A = new Line2D.Double(40+(add*3),295,320,295);
        		Line2D line3 = new Line2D.Double(140,240,220,340);
        		Rectangle2D rec1 = new Rectangle2D.Double(140, 90, 80, 10);
        		Rectangle2D rec2 = new Rectangle2D.Double(30+(add*3), 250, 10, 80);
        		Rectangle2D rec3 = new Rectangle2D.Double(155, 480, 40, 40);
        		Rectangle2D rec4 = new Rectangle2D.Double(310, 260, 50, 70);
        		intG2.setColor(Color.orange);
        	    intG2.setStroke(new BasicStroke(4));
        		intG2.draw(line1);
        		intG2.draw(line1A);
        		intG2.draw(line2);
        		intG2.draw(line2A);
        		intG2.setColor(Color.blue); //kolory trzeba bedzie na jakies ladniejsze pozmieniac
        		intG2.draw(line3);
        		intG2.setColor(Color.black);
        		intG2.draw(rec1);
        		intG2.draw(rec2);
        		intG2.setFont(new Font("Verdana", Font.BOLD , 10));
        		intG2.drawString("Z1",160, 80);
        		intG2.drawString("Z2",25+(add*3), 240);
        		intG2.drawString("Zrodlo",110, 500);
        		intG2.drawString("Detektor",300, 250);
        		intG2.setColor(Color.red);
        		intG2.draw(rec3);
        		intG2.draw(rec4);
        		intG2.fill(rec4);
        		intG.setColor(Color.black);
        		intG2.setStroke(new BasicStroke(1));
        		intG.drawLine(167,400,175,390);//strzalka dol
        		intG.drawLine(175,390,183,400);
        		intG.drawLine(167,200,175,190);//strzalki groa
        		intG.drawLine(175,190,183,200);
        		intG.drawLine(177,170,185,180);
        		intG.drawLine(185,180,193,170);
        		intG.drawLine(72+add*3,277,80+add*3,285);//strzalki lewo
        		intG.drawLine(80+add*3,285,72+add*3,292);
        		intG.drawLine(70+add*3,287,62+add*3,295);
        		intG.drawLine(62+add*3,295,70+add*3,302);
        		intG.drawLine(250,277,258,285);//strzalki prawo
        		intG.drawLine(258,285,250,292);
        		intG.drawLine(250,287,258,295);
        		intG.drawLine(258,295,250,302);
        	}
    	};
        
        
        this.add(centerPanel, BorderLayout.CENTER);
	}

	
	public static void main(String[] args) {
		Inter frame = new Inter("Interferometr Michelsona");
		frame.setVisible(true);

	}
	
	public class SliderChangeListener implements ChangeListener 
    {
        @Override
        public void stateChanged(ChangeEvent arg0) {
            String value = String.format("%d", slider.getValue());
            textLabel.setText(value);
            add=slider.getValue();
            repaint();
        }

    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

