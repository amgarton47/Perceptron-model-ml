package ml;

import java.awt.*;
import javax.swing.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.Random;

/**
 * Simple graphing program adapted from below sources.
 * 
 * @author Kamalpreet Singh source:
 *         https://www.codespeedy.com/plot-graph-in-java/
 *
 */
public class G extends JPanel {
	private ArrayList<Point> coordinates = new ArrayList<Point>();
	private double domain = 100, range = 100;
	private String xLabel = "x-axis", yLabel = "y-axis";

	public G(double domain, double range, ArrayList<Point> points) {
//		Point p1 = new Point(50, 50);
//		this.coordinates.add(p1);
		coordinates = points;
		this.domain = domain;
		this.range = range;
	}

	int mar = 125;

	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g1 = (Graphics2D) g;
		g1.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		int width = getWidth();
		int height = getHeight();

		double scaleX = (double) (width - 2 * mar) / domain;
		double scaleY = (double) (height - 2 * mar) / range;

		g1.draw(new Line2D.Double(mar, mar, mar, height - mar));
		g1.drawString(yLabel, mar / 4, height / 2);
		g1.drawString(xLabel, width / 2 - xLabel.length() * (int) scaleX, height - mar / 2);

		g1.drawString(range + "", mar / 2, mar - 5);
		g1.drawString(domain + "", width - mar, height - mar);

		g1.draw(new Line2D.Double(mar, height - mar, width - mar, height - mar));

		// draw x-axis step hash-marks
		int step = 10;
		for (int i = 0; i <= domain / step; i++) {
			g1.draw(new Line2D.Double(mar + step * i * scaleX, height - mar + 5, mar + step * i * scaleX,
					height - mar - 5));
		}

		// draw y-axis step hash-marks
		for (int i = 0; i <= range / step; i++) {
			g1.draw(new Line2D.Double(mar - 5, height - mar - i * step * scaleY, mar + 5,
					height - mar - i * step * scaleY));
		}

		g1.setPaint(Color.BLUE);
		for (int i = 0; i < coordinates.size(); i++) {
			double x1 = mar + coordinates.get(i).getX() * scaleX;
			double y1 = height - mar - scaleY * coordinates.get(i).getY();

			g1.fill(new Ellipse2D.Double(x1 - 2, y1 - 2, 4, 4));
		}

	}

	public void addPoint(Point p) {
		coordinates.add(p);
	}

	public void setXLabel(String xLabel) {
		this.xLabel = xLabel;
	}

	public void setYLabel(String yLabel) {
		this.yLabel = yLabel;
	}

	public void graph(int width, int height) {
		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(this);
		frame.setSize(width, height);
		frame.setLocation(200, 200);
		frame.setVisible(true);
	}

	public static void main(String args[]) {
		final double domain = 100, range = 100;

		JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ArrayList<Point> points = new ArrayList<Point>();
		for (int i = 0; i < 20; i++) {
			Random r = new Random();

			double x = r.nextDouble() * domain;
			double y = r.nextDouble() * range;

			points.add(new Point(x, y));
		}

		G g = new G(domain, range, points);
//		g.setXLabel("x");
//		g.setYLabel("y");
		frame.add(g);
		frame.setSize(400, 400);
		frame.setLocation(200, 200);
		frame.setVisible(true);
	}
}