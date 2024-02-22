import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

public class TextPaneOutputStream extends OutputStream {

   @SuppressWarnings("unused")
private final JTextPane textPane;
   private final StringBuilder sb = new StringBuilder();
   private String title;

   public TextPaneOutputStream(final JTextPane textPane, String title) {
      this.textPane = textPane;
      this.title = title;
      document = textPane.getDocument();
      sb.append(title);
      
      Style s = context.addStyle("default", null);
      StyleConstants.setForeground(s, Color.white);
   }

   @Override
   public void flush() {
   }

   @Override
   public void close() {
   }
   public static Document document;
   StyleContext context = new StyleContext();

   @Override
   public void write(int b) throws IOException {

      if (b == '\r')
         return;

      if (b == '\n') {
    	  String text = sb.toString() + "\n";
    	  if(sb.toString().contains("@@@")) {
    		  
    		  //19:29:49 - admin:> test@@@98,218,226
    		  
    		  String[] dataAndColor = text.split("@@@");
    		  if(dataAndColor.length < 2) return;
    		  String color = dataAndColor[1];
    		  String data = dataAndColor[0] + "\n";
    		  
    		  String[] parts = data.split(":");
    		  String[] colorParts = color.split(",");
    		  Color c = new Color(Integer.parseInt(colorParts[0]),
    				  Integer.parseInt(colorParts[1]),
    				  Integer.parseInt(colorParts[2].replace("\n", "").replace("\r", ""))
    				  );

    		  
 	         SwingUtilities.invokeLater(new Runnable() {
 	            public void run() {
 	            	try {
 	            		for (int i = 0; i < parts.length; i++) {
 	            			Style s;
 	            			
 	            			//22:16:36 | leo  (received from you) -> sdsd
 	            			if (i == 4) {
 	            				
								s = context.addStyle("test", null);
								StyleConstants.setForeground(s, Color.pink);
							}
 	            			else if (i == 3) {
 	            				s = context.addStyle("test", null);
								StyleConstants.setForeground(s, c);
							}
 	            			else if (i == 5) {
 	            				s = context.addStyle("test", null);
								StyleConstants.setForeground(s, Color.white);
							}
 	            			else if (i == 6) {
 	            				s = context.addStyle("test", null);
								StyleConstants.setForeground(s, Color.LIGHT_GRAY);
							}
 	            			else {
 	            				s = context.addStyle("test2", null);
 	            				StyleConstants.setForeground(s, Color.white);
							}
 
 	            			String semi = i < parts.length-1 ? ":" : "";
 	            			if(i == 2) semi = "|";
 	            			else if(i == 3 || i == 4 || i == 5) semi = " ";
 	            			document.insertString(document.getLength(), parts[i] + semi, s);
 	            			
 	            			JScrollBar sb = MainWindow.jsp.getVerticalScrollBar();
 	            			sb.setValue( sb.getMaximum() );

						}
 						
 						
 					} catch (BadLocationException e) {
 						// TODO Auto-generated catch block
 						e.printStackTrace();
 					}
 	            }
 	         });
    	  }
    	  else {
    	         
    	         SwingUtilities.invokeLater(new Runnable() {
    	            public void run() {
    	            	try {
    						document.insertString(document.getLength(), text, context.getStyle("default"));
 	            			JScrollBar sb = MainWindow.jsp.getVerticalScrollBar();
 	            			sb.setValue( sb.getMaximum() );
    					} catch (BadLocationException e) {
    						// TODO Auto-generated catch block
    						e.printStackTrace();
    					}
    	            }
    	         });
    	  }

         sb.setLength(0);
         sb.append(title);

         return;
      }

      sb.append((char) b);
   }
}