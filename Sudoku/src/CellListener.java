import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;

/**
 * TODO
 * References used listed at the bottom.
 * 
 * @author Tyler Egan
 */
public class CellListener implements DocumentListener {
	
	public CellListener() {
		super();
		
	}

	@Override
	public void insertUpdate(DocumentEvent documentEvent) {
		try {
			Document curDocument = documentEvent.getDocument();
			int length = curDocument.getLength();
			if (length == 1) {
				if (!("123456789".contains(curDocument.getText(0, 1)))) {
					removeText(curDocument, 0, 1);
				}
			} else if (length > 1) { // Force max length of 1
				removeText(curDocument, 1, curDocument.getLength() - 1);
			} else {
				// TODO
			}
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
//		System.out.println("Remove event");
		
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		System.out.println("Change event");
		
	}
	
	private void removeText(Document curDocument, int start, int end) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {
					curDocument.remove(start, end);
				} catch (BadLocationException e) {
					System.out.println("BAD LOCATION EXCEPTION ON INSERTUPDATE FROM CELL LISTENER");
				}
			}
		});
	}
}

/* ALL REFERENCES USED
 * For documentation: https://docs.oracle.com/javase/7/docs/api/javax/swing/package-summary.html
 * For quick and simple answers like forcing max length: stackoverflow.com
 */