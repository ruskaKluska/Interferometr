package interferometr;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;
import java.util.Locale;
import java.util.ResourceBundle;

public class ObrazHalendyWiater {
	
	static int add;
	private ResourceBundle resourceBundle = ResourceBundle.getBundle("interferometr/labels", new Locale("pl"));
	String generatorName = "Generator"; 
	String detectorName = "Detektor";
	String mirror100Name = "Zwierciadło 100%";
	String mirror50Name = "Zwierciadło 50%";

	public void setAdd(int add) {
		this.add = add;//pobieramy wartosc o jaka ma sie zmieniac polozenie zwierciadla
	}
	
	public void setNames(String g, String d, String m100, String m50) {
		generatorName = g;
		detectorName = d;
		mirror100Name = m100;
		mirror50Name = m50;
	}
	
	public void paint (Graphics intG) {
			// rysunek interferometru
		Graphics2D intG2 = (Graphics2D) intG;
		
		Color colorGD1=new Color(181, 200, 201);
		Color colorGD2=new Color(58, 59, 61);
		GradientPaint gradientPaintG=new GradientPaint(300,530,colorGD1,400,630,colorGD2);
		GradientPaint gradientPaintD=new GradientPaint(610,285,colorGD1,710,285,colorGD2);
		
		Color colorZZ1=new Color(105, 196, 207);
		Color colorZZ2=new Color(65, 130, 138);
		GradientPaint gradientPaintZ1=new GradientPaint(340,420,colorZZ2,240,320,colorZZ1);
		GradientPaint gradientPaintZ2=new GradientPaint(340,30,colorZZ1,420,20,colorZZ2);
		
		Color colorL=new Color(116, 119, 120);
		
		Line2D lineVer1 = new Line2D.Double(375,30,375,580);
		Line2D lineVer2 = new Line2D.Double(385,30,385,315);
		Line2D lineHor1 = new Line2D.Double(90+(add),305,650,305);
		Line2D lineHor2 = new Line2D.Double(90+(add),315,650,315);
		Line2D mirror50 = new Line2D.Double(340,265,420,345);
		
		Rectangle2D mirror100A = new Rectangle2D.Double(340, 20, 80, 10);
		Rectangle2D mirror100B = new Rectangle2D.Double(80+(add), 270, 10, 80);
		Rectangle2D generator = new Rectangle2D.Double(350, 580, 50, 50);
		Rectangle2D detector = new Rectangle2D.Double(650, 285, 50, 50);
		
		intG2.setColor(colorL);
	    intG2.setStroke(new BasicStroke(2));
		intG2.draw(lineVer1);
		intG2.draw(lineVer2);
		intG2.draw(lineHor1);
		intG2.draw(lineHor2);
		
		intG2.setPaint(gradientPaintZ1);
		intG2.setStroke(new BasicStroke(8));
		intG2.draw(mirror50);
		
		intG2.setPaint(gradientPaintZ2);
		intG2.fill(mirror100A);
		
		intG2.setPaint(colorZZ2);
		intG2.fill(mirror100B);
		
		intG2.setFont(new Font("Verdana", Font.BOLD , 15));
		intG2.drawString(mirror100Name,210, 55);
		intG2.drawString(mirror100Name,40+(add), 255);
		intG2.drawString(mirror50Name,410, 280);
		intG2.drawString(generatorName,270, 565);
		intG2.drawString(detectorName,645, 270);
		
		intG2.setPaint(gradientPaintG);
		intG2.fill(generator);
		
		intG2.setPaint(gradientPaintD);
		intG2.fill(detector);
	}
	

}
