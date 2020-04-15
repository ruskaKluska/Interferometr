package wstepne;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import javax.imageio.ImageIO;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.Shape;
import javax.imageio.ImageIO;

import javax.imageio.ImageIO;

import javax.print.DocFlavor.URL;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.List;
import java.util.ArrayList;
import java.io.*;


import java.awt.event.ActionListener;

public class Inter extends JFrame implements ActionListener,MouseListener, MouseMotionListener {
	
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
    
	int add, oldX,currentX;
	static BufferedImage image;//uzywane objekty implementuje na poczatku
	JFrame parentFrame;
	static File outputFile;
	static String fname;
	
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
        
        
        
        /*dowland.addActionListener(new ActionListener() 
	       {
	           @Override
	           public void actionPerformed(ActionEvent e) 
	           {
	        	   JFileChooser pick = new JFileChooser();				
				    FileNameExtensionFilter ffilter = new FileNameExtensionFilter("PNG", "png" );
				    pick.setFileFilter(ffilter);
				    int open = pick.showDialog(null,"Choose project");
	           }
	       });*/
        
        save.addActionListener(new ActionListener() 
	       {
	           @Override
	           public void actionPerformed(ActionEvent e) 
	           {
	        	   save();
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
        
        slider=new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);//slider do ustawiania dlugosci fali
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
        
        ImageIcon iconPlay = new ImageIcon("./images/play.png");
        ImageIcon iconStop = new ImageIcon("./images/stop.png");
        
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
        
        Color color2=new Color(120, 20, 161);//ten panel ma byc zapisywany do pliku w formie png 
        rightPanel.setBackground(color2);       //bo na nim pozniej bedziemy wyswietlac wykres

        this.add(rightPanel, BorderLayout.LINE_END);
//--centerPanel---------------------------------------------------------
        
        centerPanel=new JPanel(){
        	
        	
        	private static final long serialVersionUID = 1L;
        	public void paintComponent (Graphics intG) {
// rysunek interferometru
        		Graphics2D intG2 = (Graphics2D) intG;
        		Line2D line1 = new Line2D.Double(175,100,175,480);
        		Line2D line1A = new Line2D.Double(185,100,185,290);
        		Line2D line2 = new Line2D.Double(40+(add),285,320,285);//add potrzebne do zmiany wspolrzednych
        		Line2D line2A = new Line2D.Double(40+(add),295,320,295);
        		Line2D line3 = new Line2D.Double(140,240,220,340);
        		Rectangle2D rec1 = new Rectangle2D.Double(140, 90, 80, 10);
        		Rectangle2D rec2 = new Rectangle2D.Double(30+(add), 250, 10, 80);
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
        		intG2.drawString("Z2",25+(add), 240);
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
        		intG.drawLine(72+add,277,80+add,285);//strzalki lewo
        		intG.drawLine(80+add,285,72+add,292);
        		intG.drawLine(70+add,287,62+add,295);
        		intG.drawLine(62+add,295,70+add,302);
        		intG.drawLine(250,277,258,285);//strzalki prawo
        		intG.drawLine(258,285,250,292);
        		intG.drawLine(250,287,258,295);
        		intG.drawLine(258,295,250,302);
        		}
    	};
        
    	centerPanel.addMouseListener(this);
    	centerPanel.addMouseMotionListener(this);
    	
        this.add(centerPanel, BorderLayout.CENTER);
	}
	
	public void save()//funkcja do zapisu obrazka
	{
	  BufferedImage image = new BufferedImage(rightPanel.getWidth(), rightPanel.getHeight(), BufferedImage.TYPE_INT_RGB);
	  rightPanel.paintAll(image.getGraphics());
	                                                    //pobieramy obraz z prawego panelu,
	                                                  //na ktorym kiedys bedzie wykres
	// parent component of the dialog
	  parentFrame = new JFrame();
	   
	  JFileChooser fileChooser = new JFileChooser();
	  fileChooser.setDialogTitle("Zapisz wykres");   
	   
	  int userSelection = fileChooser.showSaveDialog(parentFrame);
	   
	  if (userSelection == JFileChooser.APPROVE_OPTION) {
		  
	      outputFile = fileChooser.getSelectedFile();

          fname = outputFile.getAbsolutePath();//to dodalam po to zeby zapisany plik byl w formacie png
          if(!fname.endsWith(".png") ) {
        	  outputFile = new File(fname + ".png");
          }
	      
	      try {
	    		ImageIO.write(image, "png", outputFile);
	    	} catch (IOException e) {
	    		System.out.println(e.getMessage());
	    	}
	      
	      System.out.println("Save as file: " + outputFile.getAbsolutePath());
	  }
	}

	
	public static void main(String[] args) {
		Inter frame = new Inter("Interferometr Michelsona");
		frame.setVisible(true);

	}
	
	public class SliderChangeListener implements ChangeListener 
    {

        @Override
        public void stateChanged(ChangeEvent arg0) 
        {
            String value = String.format("%d", slider.getValue());
            textLabel.setText(value);
        }

    }

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseDragged(MouseEvent e) {//pobieramy wspolrzedne zeby sie zwierciadlo ruszalo
		// TODO Auto-generated method stub
		currentX=e.getX();
		add=currentX;
		
		if(currentX<95)
		{
			repaint();
			oldX=currentX;
		}
		
	}


	@Override
	public void mouseMoved(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseClicked(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		oldX=e.getX();
	}


	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
}

