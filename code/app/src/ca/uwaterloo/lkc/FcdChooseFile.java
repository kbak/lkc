package ca.uwaterloo.lkc;

import java.io.FileNotFoundException;
import java.net.URI;

import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.FileChooserAction;
import org.gnome.gtk.FileChooserDialog;
import org.gnome.gtk.ResponseType;
import org.gnome.gtk.Stock;

public class FcdChooseFile {
	
	private final FileChooserDialog fcdChooseFile;
	
	public FcdChooseFile(final String gladeFile) throws FileNotFoundException {
		final XML xmlChooseFile = Glade.parse(gladeFile, "fcdChooseFile");
		fcdChooseFile = (FileChooserDialog) xmlChooseFile.getWidget("fcdChooseFile");
		
		// Adding OK and Cancel buttons (not added by default)
		fcdChooseFile.addButton(Stock.OK, ResponseType.OK);
        fcdChooseFile.addButton(Stock.CANCEL, ResponseType.CANCEL);
	}
	
	public void setFileChooserActionSave() {
		fcdChooseFile.setAction(FileChooserAction.SAVE);
	}
	
	public ResponseType run() {
		return fcdChooseFile.run();
	}
	
	public URI getURI() {
		return fcdChooseFile.getURI();
	}
	
	public void hide() {
		fcdChooseFile.hide();
	}

}
