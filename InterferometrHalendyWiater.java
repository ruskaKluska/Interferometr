package interferometr;

import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.ActionEvent;
import javax.swing.Timer;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GradientPaint;
import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.BorderLayout;
import java.awt.HeadlessException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Locale;
import java.util.ResourceBundle;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;


public class InterferometrHalendyWiater extends JFrame implements ActionListener, KeyListener {
	
	private static final long serialVersionUID = 1L;
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("interferometr/labels", new Locale("pl"));
	
	JPanel leftPanel, rightPanel, centerPanel, topPanel, bottomPanel;
	JMenuBar menuBar;
	JMenu fileMenu, generatorMenu, instructionMenu;
	JMenuItem save, download, amp2, amp5, amp10, instruction;
	
	JLabel labelLenght, labelCm, textLabel;
	JSlider slider;
	JButton buttonEn, buttonPl, buttonStart, buttonStop, buttonEnd;
	String selectedButton = "B";
	
	static final int SLIDER_MIN = 1;//dane do slidera
    static final int SLIDER_MAX = 30;
    static final int SLIDER_INIT = 15;
    
    Dimension minSize=new Dimension(1200,800);
	int add, velX;
	static BufferedImage image;
	JFrame parentFrame;
	static File outputFile;
	static String fname;
	
	XYSeries dataSet, dataSet2;
	XYSeriesCollection xyDataset, xyDataset2;
	
	static int lambda=15;
	static int amplitude=2;
	static int scale=8;
	JFreeChart lineGraph, lineGraph2;
	static BufferedImage imageChart=null;
	static BufferedImage imageOscy=null;
	JLabel lblChart = new JLabel(); 
	JLabel lblOscy = new JLabel();
	Document document=new Document();
	
	static Color colorLight=new Color(23,163,163);
	static Color colorDark=new Color(9,61,61);
	static GradientPaint colorBG=new GradientPaint(0,400,colorLight,1200,400,colorDark);
	static Color colorComponent=new Color(58, 59, 61);
	static Color colorText=new Color(12, 196, 166);
	
	Timer timer=new Timer(100,this);
	String pictureGenerator = resourceBundle.getString("source");
	String pictureDetector = resourceBundle.getString("det");
	String pictureMirror100 = resourceBundle.getString("mirr100");
	String pictureMirror50 = resourceBundle.getString("mirr50");
	String place = resourceBundle.getString("place");
	String intensity = resourceBundle.getString("int");
	String timeGraph = resourceBundle.getString("time");
	String voltage = resourceBundle.getString("vol");
	
	public InterferometrHalendyWiater(String title) throws HeadlessException
	{		
		super(title);
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setMinimumSize(minSize);
        this.setSize(1200,800);
        this.setLayout(new BorderLayout());       
        
//--menu----------------------------------------------------------------
        
        menuBar=new JMenuBar();
        
        generatorMenu=new JMenu(resourceBundle.getString("genSettings"));
        amp2=new JMenuItem("2 V/m");
        amp2.addActionListener(this);
        amp2.setSelected(true);
        amp5=new JMenuItem("5 V/m");
        amp5.addActionListener(this);
        amp10=new JMenuItem("10 V/m");
        amp10.addActionListener(this);
        
        ButtonGroup group = new ButtonGroup();
		group.add(amp2);
		group.add(amp5);
		group.add(amp10);
        
        fileMenu=new JMenu(resourceBundle.getString("Fille"));
        download=new JMenuItem(resourceBundle.getString("menu.dow"));
        download.addActionListener(this);
        save=new JMenuItem(resourceBundle.getString("menu.save"));
        save.addActionListener(this);
        
        instructionMenu = new JMenu(resourceBundle.getString("instru"));
        instruction = new JMenuItem(resourceBundle.getString("manual"));
	       
        generatorMenu.add(amp2);
        generatorMenu.add(amp5);  
        generatorMenu.add(amp10);
        menuBar.add(generatorMenu);
        
        fileMenu.add(download);
        fileMenu.addSeparator();
        fileMenu.add(save);
        menuBar.add(fileMenu);
        
        instructionMenu.add(instruction);
        menuBar.add(instructionMenu);
        
        this.setJMenuBar(menuBar);
        
//--topPanel------------------------------------------------------------
        
        topPanel=new JPanel() { //robimy gradientowe tlo
        	public void paintComponent(Graphics g) {
        		Graphics2D g2d=(Graphics2D) g;
        		g2d.setPaint(colorBG);
        		g2d.fillRect(0,0,getWidth(),getHeight());
        	}
        };
        topPanel.setLayout(new FlowLayout());
        
        labelLenght=new JLabel(resourceBundle.getString("waveLenght"));
        labelLenght.setForeground(colorText);
        
        slider=new JSlider(JSlider.HORIZONTAL, SLIDER_MIN, SLIDER_MAX, SLIDER_INIT);//slider do ustawiania dlugosci fali
        slider.setMajorTickSpacing(29);
        slider.setPaintTicks(true);
		slider.setPaintLabels(true);
		slider.setBackground(colorComponent);
		slider.setForeground(colorText);
		slider.addChangeListener(new SliderChangeListener());
		
		textLabel=new JLabel(String.format("%d", slider.getValue()));
		textLabel.setForeground(colorText);
		
		labelCm=new JLabel(" cm                      ");
		labelCm.setForeground(colorText);
		
		buttonEn=new JButton("English");
		buttonPl=new JButton("Polish");
		
		buttonEn.setBackground(colorComponent);
		buttonEn.setForeground(colorText);
		buttonEn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resourceBundle = ResourceBundle.getBundle("interferometr/labels", new Locale("en"));
				download.setText(resourceBundle.getString("menu.dow"));
	        	save.setText(resourceBundle.getString("menu.save"));
	        	pictureDetector = resourceBundle.getString("det");
	        	pictureMirror100 = resourceBundle.getString("mirr100");
	        	pictureMirror50 = resourceBundle.getString("mirr50");
	        	fileMenu.setText(resourceBundle.getString("Fille"));
	        	labelLenght.setText(resourceBundle.getString("waveLenght"));
	        	generatorMenu.setText(resourceBundle.getString("genSettings"));
	        	lineGraph.setTitle(resourceBundle.getString("graphup"));
	        	lineGraph2.setTitle(resourceBundle.getString("graphdown"));
	        	place = resourceBundle.getString("place");
	        	intensity = resourceBundle.getString("int");
	        	timeGraph = resourceBundle.getString("time");
	        	voltage = resourceBundle.getString("vol");
	        	instructionMenu.setText(resourceBundle.getString("instru"));
	        	instruction.setText(resourceBundle.getString("manual"));
	        	buttonEnd.setText(resourceBundle.getString("end"));
	        	repaint();
			}
			
		});
	        
		buttonPl.setBackground(colorComponent);
		buttonPl.setForeground(colorText);
		buttonPl.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resourceBundle = ResourceBundle.getBundle("interferometr/labels", new Locale("pl"));
				download.setText(resourceBundle.getString("menu.dow"));
	        	save.setText(resourceBundle.getString("menu.save"));
	        	pictureDetector = resourceBundle.getString("det");
	        	pictureMirror100 = resourceBundle.getString("mirr100");
	        	pictureMirror50 = resourceBundle.getString("mirr50");
	        	fileMenu.setText(resourceBundle.getString("Fille"));
	        	labelLenght.setText(resourceBundle.getString("waveLenght"));
	        	generatorMenu.setText(resourceBundle.getString("genSettings"));
	        	lineGraph.setTitle(resourceBundle.getString("graphup"));
	        	lineGraph2.setTitle(resourceBundle.getString("graphdown"));
	        	place = resourceBundle.getString("place");
	        	intensity = resourceBundle.getString("int");
	        	timeGraph = resourceBundle.getString("time");
	        	voltage = resourceBundle.getString("vol");
	        	instructionMenu.setText(resourceBundle.getString("instru"));
	        	instruction.setText(resourceBundle.getString("manual"));
	        	buttonEnd.setText(resourceBundle.getString("end"));
	        	repaint();
			}
			
		});
        
		topPanel.add(labelLenght);
		topPanel.add(slider);
		topPanel.add(textLabel);
        topPanel.add(labelCm);
        topPanel.add(buttonEn);
        topPanel.add(buttonPl);
        
        this.add(topPanel, BorderLayout.PAGE_START);
//--bottomPanel---------------------------------------------------------
        
        bottomPanel=new JPanel() {
        	public void paintComponent(Graphics g) {
        		Graphics2D g2d=(Graphics2D) g;
        		g2d.setPaint(colorBG);
        		g2d.fillRect(0,0,getWidth(),getHeight());
        	}
        };
        topPanel.setLayout(new FlowLayout());
        
        ImageIcon iconPlay = new ImageIcon("./images/play.png");
        ImageIcon iconStop = new ImageIcon("./images/stop.png");
        
        buttonStart=new JButton("Start",iconPlay);
        buttonStop=new JButton("Stop",iconStop);
        buttonEnd = new JButton(resourceBundle.getString("end"));
        
        buttonStart.setBackground(colorComponent);
        buttonStart.setForeground(colorText);
        buttonStart.setActionCommand("A");
		buttonStop.setActionCommand("B");
        
        buttonStop.setBackground(colorComponent);
        buttonStop.setForeground(colorText);
        buttonStop.setSelected(true);
        
        buttonEnd.setBackground(colorComponent);
        buttonEnd.setForeground(colorText);
        
        
        
        buttonStart.addActionListener(new ActionListener() //uruchamiamy symulacje
	       {
	           @Override
	           public void actionPerformed(ActionEvent e) 
	           {
	        	   slider.disable();//slider sie deaktywuje
	        	   requestFocus();//ustawiamy focus na frame zeby zwierciadlo sie ruszalo
	        	   timer.start();//timer tez jest po to zeby zwierciadlo sie ruszalo
	        	   selectedButton = "A";//ustawiamy aby pojawil sie oscykoskop
	           }
	       });
        
        buttonStop.addActionListener(new ActionListener() //wylaczamy symulacje
	       {
	           @Override
	           public void actionPerformed(ActionEvent e) 
	           {
	        	   slider.enable();//teraz mozemy zmienic wartosc na sliderze
	        	   timer.stop();
	        	   selectedButton = "B";// dane z oscyloskopu znikaja
	           }
	       });
        
        buttonEnd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(1);
				
			}
        	
        });
        
        ButtonGroup group2 = new ButtonGroup();
		group2.add(buttonStart);
		group2.add(buttonStop);
        
        bottomPanel.add(buttonStart);
        bottomPanel.add(buttonStop);
        bottomPanel.add(buttonEnd);
        
        this.add(bottomPanel, BorderLayout.PAGE_END);
//--leftPanel-----------------------------------------------------------
        
        leftPanel=new JPanel() {
        	public void paintComponent(Graphics g) {
        		Graphics2D g2d=(Graphics2D) g;
        		g2d.setPaint(colorBG);
        		g2d.fillRect(0,0,getWidth(),getHeight());
        	}
        };
    	
        this.add(leftPanel, BorderLayout.LINE_START);
//--rightPanel----------------------------------------------------------
        
        rightPanel=new JPanel() {
        	public void paintComponent(Graphics g) {
        		Graphics2D g2d=(Graphics2D) g;
        		g2d.setPaint(new Color(12, 82, 82));
        		g2d.fillRect(100,0,getWidth(),getHeight());
        	}
        };
        rightPanel.setLayout(new GridLayout(2,1));
        
		createChart(lambda,amplitude,add);//zebysmy widzieli wykresy od samego poczatku dzialania programu

        this.add(rightPanel, BorderLayout.LINE_END);
//--centerPanel---------------------------------------------------------
        
        centerPanel=new JPanel(){

        	private static final long serialVersionUID = 1L;
        	public void paintComponent(Graphics g) { //tworzymy obraz interferometru
        		super.paintComponent(g);
        		ObrazHalendyWiater obraz=new ObrazHalendyWiater();
        		obraz.setAdd(add);
        		obraz.setNames(pictureGenerator,pictureDetector,pictureMirror100,pictureMirror50);// resourceBundle.getString("detector"), resourceBundle.getString("mirr100"), resourceBundle.getString("mirr50"));;
        		obraz.paint(g);
        		}
    	};
        
    	addKeyListener(this);//dodajemy key listner do frame'a
    	
        this.add(centerPanel, BorderLayout.CENTER);
	}
	
	public void createChart(int lambda, int amplitude, int currentX)//funkcja do tworzenia wykresu i oscyloskopa
	{
		//--wykres----------------------------------------------------------------------------
		
		dataSet = new XYSeries("");
        
        for (double i=0; i <30.05; i+=0.05) dataSet.add(i, //korzystamy ze wzoru na natezenie interferujacych fal
        		(amplitude*amplitude)/2+(amplitude*amplitude)/2+amplitude*amplitude*Math.cos((2*Math.PI*i)/lambda));
        
        xyDataset = new XYSeriesCollection(dataSet); 
        
        lineGraph = ChartFactory.createXYLineChart 
                (resourceBundle.getString("graphup"),  // Title Zaleznosc natezenia od polozenia
                  place,           // X-Axis label polozenie
                  intensity,           // Y-Axis label natezenie
                  xyDataset,          // Dataset 
                  PlotOrientation.VERTICAL,        //Plot orientation 
                  false,                //show legend 
                  false,                // Show tooltips 
                  false               //url show 
                 ); 
	    
	    imageChart = lineGraph.createBufferedImage(400,300);       
        lblChart.setIcon(new ImageIcon(imageChart));
        rightPanel.add(lblChart);
        
        //--oscyloskop-------------------------------------------------------------------------

        int getCm=0;//zmienna okreslajaca polozenie zwierciadla w interferometrze
        //zakladamy ze ramiona interferomatru maja po 30 cm
        
        if(add>=0&&add<=240)
        getCm = add/8;
        
        double I[]=new double[40];//bedziemy liczyc natezenie dla kazdego polozenia zwierciadla i zapisywac w tabeli
        for (int z=0;z<40;z++) {
        	I[z]=(amplitude*amplitude)/2+(amplitude*amplitude)/2+amplitude*amplitude*Math.cos((2*Math.PI*z)/lambda);
        }
        	
   
        dataSet2 = new XYSeries("");
        dataSet2.add(0,scale);//po to zeby skala sie nie zmieniala przy kazdej zmianie natezenia
        if(selectedButton =="A") {
        for (double i=0.05; i <5.05; i+=0.05)//zeby przebieg byl prostokatny
        	dataSet2.add(i,0);
        for (double i=5.05; i <10.05; i+=0.05)
        	dataSet2.add(i,I[getCm]);
        for (double i=10.05; i <15.05; i+=0.05)
        	dataSet2.add(i,0);
        for (double i=15.05; i <20.05; i+=0.05)
        	dataSet2.add(i,I[getCm]);
        for (double i=20.05; i <25.05; i+=0.05)
        	dataSet2.add(i,0);
        for (double i=25.05; i <30.05; i+=0.05)
        	dataSet2.add(i,I[getCm]);
        for (double i=30.05; i <33.25; i+=0.05)
        	dataSet2.add(i,0);
        }
        
        xyDataset2 = new XYSeriesCollection(dataSet2); 
        
        lineGraph2 = ChartFactory.createXYLineChart 
                (resourceBundle.getString("graphdown"),  // Title Oscyloskop
                  timeGraph,           // X-Axis label os czasu
                  voltage,           // Y-Axis label os napiecia
                  xyDataset2,          // Dataset 
                  PlotOrientation.VERTICAL,        //Plot orientation 
                  false,                //show legend 
                  true,                // Show tooltips 
                  false               //url show 
                 ); 
	    
	    imageOscy = lineGraph2.createBufferedImage(400,300);       
        lblOscy.setIcon(new ImageIcon(imageOscy));
        rightPanel.add(lblOscy);
        
        System.out.println("getCm: "+getCm);//to zeby moc sledzic obliczenia, gdy program bedzie skonczony to usuniemy
        System.out.println("Natezenie: "+I[getCm]);
        System.out.println("Lambda: "+lambda);
        System.out.println("Amplituda: "+amplitude);
        System.out.println("Cos: "+Math.cos((2*Math.PI*getCm)/lambda));
        
        this.repaint(); 
	}
	
	public void save() throws DocumentException, MalformedURLException, IOException//funkcja zapisujaca wykres do plikow png i pdf
	{

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
	    	  ImageIO.write(imageChart, "png", outputFile);//tutaj zapisujemy png
	    	} catch (IOException e) {
	    		System.out.println(e.getMessage());
	    	}
	      
	      System.out.println("Save as file: " + outputFile.getAbsolutePath());
	  }
	  
	  //tutaj zapisujemy pdf
	  try {
		  PdfWriter.getInstance(document, new FileOutputStream(fname + ".pdf"));
		  document.open();
		  document.add(new Paragraph("Dlugosc fali: "+Integer.toString(lambda)+" cm"));
		  document.add(new Paragraph("Amplituda fali: "+Integer.toString(amplitude)+" V/m"));
		  Image chartFile=Image.getInstance(fname + ".png");
		  document.add(chartFile);
		  document.close();
		  
	  } catch(FileNotFoundException e) {
		  System.out.println(e.getMessage());
	  }
	  
	}

	
	public static void main(String[] args) {
		InterferometrHalendyWiater frame = new InterferometrHalendyWiater("Interferometr Michelsona");
		frame.setVisible(true);

	}
	
	public class SliderChangeListener implements ChangeListener 
    {
        @Override
        public void stateChanged(ChangeEvent arg0) 
        {
            String value = String.format("%d", slider.getValue());
            textLabel.setText(value);
            
            lambda=slider.getValue();
            createChart(lambda,amplitude,add);
        }
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		
		//--nasluchujemy przyciskow----------------------------------------------------------------
		
		Object choice=e.getSource();

		if(choice==amp2) {
			amplitude=2;
			scale=8;
			createChart(lambda,amplitude,add);
		}
		else if(choice==amp5) {
			amplitude=5;
			scale=50;
			createChart(lambda,amplitude,add);
		}
		else if(choice==amp10) {
			amplitude=10;
			scale=200;
			createChart(lambda,amplitude,add);
		}
		else if(choice==download) {

		}
		else if(choice==save) {
			try {
				save();
			} catch (FileNotFoundException | DocumentException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (MalformedURLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		//--nasluchujemy zmian polozenia zwierciadla------------------------------------------------

		if(add<0)
		{
			velX=0;//velX to wartosc o jaka zmieniamy polozenie przy kazdym kliknieciu
			add=0;//add to polozenie zwierciadla, zmienia sie od 0 do 240 bo zwierciadlo moze sie maksymalnie przemiescic o 240 pikseli
			System.out.println("AC1: "+add);
		}
		if(add>240)
		{
			velX=0;
			add=240;
			System.out.println("AC2: "+add);
		}
		
		add=add+velX;
		createChart(lambda,amplitude,add);//aktualizujemy oscyloskop 
		
		System.out.println("AC3: "+add);//to zeby sledzic wspolrzedne, do usuniecia gdy program bedzie skonczony
		
		repaint();
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int c=e.getKeyCode();
		
		if(c==KeyEvent.VK_LEFT)//sprawdzamy ktory przycisk zostal nacisniety
		{
			velX=-8;//zmieniamy polozenie zwierciadla o 8 pikseli w lewo
			System.out.println("Key Pressed: "+add);
		}
		if(c==KeyEvent.VK_RIGHT)
		{
			velX=8;//zmieniamy polozenie zwierciadla o 8 pikseli w prawo
		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		velX=0;//zeby zwierciadlo sie nie przemieszczalo po puszczeniu przycisku
		System.out.println("Key Released: "+add);
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	
}
