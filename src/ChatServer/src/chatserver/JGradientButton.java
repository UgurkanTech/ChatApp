package chatserver;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import javax.swing.JButton;

public class JGradientButton extends JButton{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JGradientButton(String text){
        super(text);
        setContentAreaFilled(false);
    }
	public JGradientButton(){
        super();
        setContentAreaFilled(false);
    }
    @Override
    protected void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setPaint(new GradientPaint(
                new Point(0, 0), 
                getBackground().darker(), 
                new Point(0, getHeight()/3), 
                getBackground().brighter()));
        g2.fillRect(0, 0, getWidth(), getHeight()/3);
        g2.setPaint(new GradientPaint(
                new Point(0, getHeight()/3), 
                getBackground().brighter(), 
                new Point(0, getHeight()), 
                getBackground().darker()));
        g2.fillRect(0, getHeight()/3, getWidth(), getHeight());
        g2.dispose();

        super.paintComponent(g);
    }
}