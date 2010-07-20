package ca.uwaterloo.lkc;

import java.io.FileNotFoundException;
import java.net.URI;

import org.gnome.gdk.Event;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.ResponseType;
import org.gnome.gtk.ToolButton;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

public class WindowConfig {
    
   public final Window w;
   public final ToolButton tbtnOpen;
    
    WindowConfig(final String gladeFile, URI configFile) throws FileNotFoundException
    {
        final XML xmlWndConfig = Glade.parse(gladeFile, "wndConfig");
        
        w = (Window) xmlWndConfig.getWidget("wndConfig");
        
        w.connect(new Window.DeleteEvent() {
            
            @Override
            public boolean onDeleteEvent(Widget arg0, Event arg1) {
                Gtk.mainQuit();
                return false;
            }
        });
        
        // Adding evens for tool bar buttons
        tbtnOpen = (ToolButton) xmlWndConfig.getWidget("tbtnOpen");
        tbtnOpen.connect(new ToolButton.Clicked() {
			
			@Override
			public void onClicked(ToolButton arg0) {
				try {
					FcdChooseFile fcdChooseFile;
					fcdChooseFile = new FcdChooseFile(gladeFile);
					ResponseType responseType = fcdChooseFile.run();
					
					/*
					 *  If a file is chosen, hide the current WindowConfig,
					 *  instantiate a new one with the new configuration.
					 */
					if(responseType == ResponseType.OK) {
						w.hide();
						new WindowConfig(gladeFile, fcdChooseFile.getURI()).w.show();
					} else if(responseType == ResponseType.CANCEL) {
						fcdChooseFile.hide();
					}
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
			}
		});
        
    }

}
