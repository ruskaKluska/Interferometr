package pl.edu.pw.fizyka.pojava.projekt6;
//część Igi
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;

import javax.swing.JPanel;

public class CenterPanel extends JPanel  {

	private static final long serialVersionUID = 1L;
	String A,B,C,D;
	int x1 = 375, y1 = 580; //linia wychodzaca z geneartora
	int x2 = 375, y2 = 302;
	int x3 = 375, y3 = 297; //linia do mirror pionowa góra
	int x4 = 375, y4 = 30;
	int x5 = 385, y5 = 30; //linia do mirror pionowa dol
	int x6 = 385, y6 = 296;
	int x7 = 371, y7 = 305; //linia do mirror pozioma w lewo
	int x8 =  90, y8 = 305;
	int x9 = 385, y9 = 305;//linia od mirror 50 w prawo (gorna)
	int x10 = 650, y10 = 296;
	int x11 = 395, y11 = 315;// linia od mirror 50 w prawo (dolna)
	int x12 = 650, y12 = 315;
	double x13 =  90, y13 = 315; //linia do mirro pozioma w prawo
	int x14 = 385, y14 = 315;
	Picture picture;
	int n = 279;
	int m = 285; //verUP
	int o = 281;//horLeft
	int p = 275; //verDown
	int r = 295;//horRight
	int s = 265; //end1
	int t = 265; //end2
	int mirr = 90;
	ArrayList<Line2D> lineP;
	ArrayList<Line2D> line50VerUP;
	ArrayList<Line2D> line50VerDOWN;
	ArrayList<Line2D> line50HorLEFT;
	ArrayList<Line2D> line50HorRIGHT;
	ArrayList<Line2D> lineEnd1, lineEnd2;
	boolean check = true;
	public CenterPanel(String a, String b, String c, String d) {
		A = a; //generator
		B = b; //detektor
		C = c; //mirror 100
		D = d; // mirror 50
	/*	lineP = new ArrayList<Line2D>(279);	
		line50VerUP = new ArrayList<Line2D>(285);
		line50VerDOWN = new ArrayList<Line2D>(275);
		line50HorLEFT = new ArrayList<Line2D>(281);
		line50HorRIGHT = new ArrayList<Line2D>(295);
		lineEnd1 =  new ArrayList<Line2D>(265);
		lineEnd2 =  new ArrayList<Line2D>(265);
		
		
		int y=580;
		for(int i=0; i< 279;i++) {
			lineP.add(new Line2D.Double(375, 580, 375, y)); //linia wychodzaca z geneartora
			y--;
		}
		int y1 = 309;
		for(int i=0; i<285;i++) {
			line50VerUP.add(new Line2D.Double(375, 309, 375, y1));//linia do mirror pionowa góra
			y1--;
		}
		int x = 371;
		for(int i=0; i< 281 ;i++) {
			line50HorLEFT.add(new Line2D.Double(371, 305, x, 305));//linia do mirror pozioma w lewo
			x--;
		}
		int y2 = 30;
		for(int i=0; i< 275;i++) {
			line50VerDOWN.add(new Line2D.Double(385, 30, 385, y2));//linia do mirror pionowa dol
			y2++;
		}
		int x2 = mirr;
		for(int i=0; i< 295;i++) {
			line50HorRIGHT.add(new Line2D.Double(mirr, 315, x2, 315));//linia do mirror pozioma w prawo
			x2++;
		}
		int x3 = 385;
		for(int i=0; i< 265;i++) {
			lineEnd1.add(new Line2D.Double(385, 305, x3, 305));//linia od mirror 50 w prawo (gorna)
			x3++;
		}
		int x4 = 395;
		for(int i=0; i< 265;i++) {
			lineEnd2.add(new Line2D.Double(385, 315, x4, 315));// linia od mirror 50 w prawo (dolna)
			x4++;
		}*/
		
	}
	void setN(int n) {this.n=n;} //promien glowny
	void setM(int m) {this.m=m;} //verUP
	void setO(int o) {this.o=o;}//horLeft
	void setP(int p) {this.p=p;}//VerDown
	void setR(int r) {this.r=r;}//horRight
	void setS(int s) {this.s=s;}//end1
	void setT(int t) {this.t=t;}//end2
	void setPic(int mirr) {this.mirr=mirr;}
	
	void setData() {
		lineP = new ArrayList<Line2D>(279);	
		line50VerUP = new ArrayList<Line2D>(285);
		line50VerDOWN = new ArrayList<Line2D>(275);
		line50HorLEFT = new ArrayList<Line2D>(281);
		line50HorRIGHT = new ArrayList<Line2D>(295);
		lineEnd1 =  new ArrayList<Line2D>(265);
		lineEnd2 =  new ArrayList<Line2D>(265);
		
		n = 279;
		m = 285; //verUP
		o = 281;//horLeft
		p = 275; //verDown
		r = 295;//horRight
		s = 265; //end1
		t = 265; //end2
		mirr = 90;
		
		
		int y=580;
		for(int i=0; i< 279;i++) {
			lineP.add(new Line2D.Double(375, 580, 375, y)); //linia wychodzaca z geneartora
			y--;
		}
		int y1 = 309;
		for(int i=0; i<285;i++) {
			line50VerUP.add(new Line2D.Double(375, 309, 375, y1));//linia do mirror pionowa góra
			y1--;
		}
		int x = 371;
		for(int i=0; i< 281;i++) {
			line50HorLEFT.add(new Line2D.Double(371, 305, x, 305));//linia do mirror pozioma w lewo
			x--;
		}
		int y2 = 30;
		for(int i=0; i< 275;i++) {
			line50VerDOWN.add(new Line2D.Double(385, 30, 385, y2));//linia do mirror pionowa dol
			y2++;
		}
		int x2 = mirr;
		for(int i=0; i< 295;i++) {
			line50HorRIGHT.add(new Line2D.Double(mirr, 315, x2, 315));//linia do mirror pozioma w prawo
			x2++;
		}
		int x3 = 385;
		for(int i=0; i< 265;i++) {
			lineEnd1.add(new Line2D.Double(385, 305, x3, 305));//linia od mirror 50 w prawo (gorna)
			x3++;
		}
		int x4 = 395;
		for(int i=0; i< 265;i++) {
			lineEnd2.add(new Line2D.Double(385, 315, x4, 315));// linia od mirror 50 w prawo (dolna)
			x4++;
		}
	}
	
	void SetA (String a) { //bo był problem z nazwami
		A=a;
	}
	void SetB(String b) {
		B=b;
	}
	void SetC(String c) {
		C=c;
	}
	void SetD(String d) {
		D=d;
	}
	
	void startAnimation() {
		setData(); // ustawia ArrayListy
		check = true;
		Thread a = new Thread() {
			public void run() {
				int a = 279;  //linia wychodzaca z geneartora
				int b = 285;  //linia do mirror pionowa góra
				int c = 371-mirr; //linia do mirror pozioma w lewo
				int ee = 381-mirr; //linia do mirror pozioma w prawo
				int g = 265;  // linia od mirror 50 w prawo (dolna)
				while(a>0) {
					setN(a);  
					System.out.println("a="+a);
					a--;
					try {         //linia wychodzaca z geneartora
						Thread.sleep(5);
					} catch (InterruptedException e) {   
						e.printStackTrace();
					}
					repaint();
				}
				int k = picture.getXHor2();
				while( a > -(371-k) && a <= 0) {
					if (a > -377) {
						setO(c);
						System.out.println("c="+c);
						c--;//linia do mirror pozioma w lewo
						b--;		   //linia do mirror pionowa góra
						a--;
						System.out.println("b="+b);
						System.out.println("aaaaa="+a);
						setM(b);
						if(b == 5) {
							Thread aa = new Thread() {
								int dd = 275; //linia do mirror pionowa dół
								int ff = 265; // linia od mirror 50 w prawo (gorna)
								public void run() {
									while( dd>0) {
										setP(dd);
										System.out.println("d="+dd);
										dd--;
										try {           
											Thread.sleep(5);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										repaint();
									}
									while( dd > -265 && dd <= 0 ) {
										setS(ff);
										System.out.println("f="+ff);
										ff--;           //linia od mirror 50 w prawo (gorna)
										dd--;
										try {
											Thread.sleep(5);
										} catch (InterruptedException e) {
											e.printStackTrace();
										}
										repaint();
									}
									dd = 275;
									ff = 265;
								}
							};
							aa.start();
						}
					
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
							repaint();
				}else {
						setO(c);
						System.out.println("c="+c);
						c--;           //linia do mirror pozioma w lewo
						a--;
						try {
							Thread.sleep(5);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
							repaint();
					}}
				while( c > -(381-k) && c <= 0) {
					setR(ee);
					System.out.println("e="+ee);
					ee--;           //linia do mirror pozioma w prawo
					c--;
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
				}
				int z = picture.getXHor1();
				while( c > -255-(381-z) && c <= -(381-z)) {
					setT(g);
					System.out.println("g="+g);
					g--;           // linia od mirror 50 w prawo (dolna)
					c--;
					try {
						Thread.sleep(5);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					repaint();
				}
				try {
					Thread.sleep(5);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				repaint();
	
			}
		};
		a.start();
	}
	
	
	public void paintComponent(Graphics g) { //tworzymy obraz interferometru oraz linie do animacji 
		super.paintComponent(g);
		picture=new Picture();
		picture.setAdd(Interferometr.add);
		picture.setNames(A,B,C,D); 
		picture.paint(g);
		setPic(picture.getXHor1());
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.red);
		g2d.setStroke(new BasicStroke(6));
		start(g2d);
	}

	public void start(Graphics2D g2d) {
		if(check == false) {
			
		}else {
			for(int i = 0;i<Math.abs(n-lineP.size());i++) {
				g2d.draw(lineP.get(i));	
				repaint();
			}
			g2d.setStroke(new BasicStroke(3));
			for(int i = 0;i<Math.abs(m-line50VerUP.size());i++) {
				g2d.draw(line50VerUP.get(i));
				repaint();
			}
			for(int i = 0;i<Math.abs(p-line50VerDOWN.size());i++) {
				g2d.draw(line50VerDOWN.get(i));
				repaint();
			}
			for(int i = 0;i<Math.abs(o-line50HorLEFT.size());i++) {
				g2d.draw(line50HorLEFT.get(i));
				repaint();
			}
			for(int i = 0;i<Math.abs(r-line50HorRIGHT.size());i++) {
				g2d.draw(line50HorRIGHT.get(i));
				repaint();
			}
			for(int i = 0;i<Math.abs(s-lineEnd1.size());i++) {
				g2d.draw(lineEnd1.get(i));
				repaint();
			}
			for(int i = 0;i<Math.abs(t-lineEnd2.size());i++) {
				g2d.draw(lineEnd2.get(i));
				repaint();
			}
		}
	}

	public void delete() {

		lineP = new ArrayList<Line2D>(279);	
		line50VerUP = new ArrayList<Line2D>(285);
		line50VerDOWN = new ArrayList<Line2D>(275);
		line50HorLEFT = new ArrayList<Line2D>(281);
		line50HorRIGHT = new ArrayList<Line2D>(295);
		lineEnd1 =  new ArrayList<Line2D>(265);
		lineEnd2 =  new ArrayList<Line2D>(265);
		repaint();

		check=false;
		repaint();
	}
	
}
