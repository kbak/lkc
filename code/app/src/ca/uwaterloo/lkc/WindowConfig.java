package ca.uwaterloo.lkc;

import java.io.FileNotFoundException;
import java.net.URI;

import org.gnome.gdk.Event;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

public class WindowConfig {
    
   public final Window w;
    
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
        
    }

}
