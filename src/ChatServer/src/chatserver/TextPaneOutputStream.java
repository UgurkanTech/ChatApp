package chatserver;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.JScrollBar;
import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

public class TextPaneOutputStream extends OutputStream {

   @SuppressWarnings("unused")
private final JTextPane textPane;
   private final StringBuilder sb = new StringBuilder();
   private String title;

   public TextPaneOutputStream(final JTextPane textPane, String title) {
      this.textPane = textPane;
      this.title = title;
      document = textPane.getDocument();
      sb.append(title + "> ");
   }

   @Override
   public void flush() {
   }

   @Override
   public void close() {
   }
   Document document;
   @Override
   public void write(int b) throws IOException {

      if (b == '\r')
         return;

      if (b == '\n') {
         final String text = sb.toString() + "\n";
         SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
					document.insertString(document.getLength(), text, null);
					JScrollBar sb = ServerWindow.jsp.getVerticalScrollBar();
         			sb.setValue( sb.getMaximum() );
				} catch (BadLocationException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            }
         });
         sb.setLength(0);
         sb.append(title + "> ");

         return;
      }

      sb.append((char) b);
   }
}