package ca.uwaterloo.lkc;

import java.io.FileNotFoundException;

import org.gnome.gdk.Event;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.gnome.gtk.WindowPosition;

public class WindowSearch {

    public final Window w;
    
    WindowSearch(final String gladeFile) throws FileNotFoundException
    {
        final XML xmlWndWelcome = Glade.parse(gladeFile, "wndSearch");
        
        w = (Window) xmlWndWelcome.getWidget("wndSearch");
        final Button btnCancel = (Button) xmlWndWelcome.getWidget("btnCancel2");
        
        w.connect(new Window.DeleteEvent() {
            
            @Override
            public boolean onDeleteEvent(Widget arg0, Event arg1) {
                Gtk.mainQuit();
                return false;
            }
        });
        
        btnCancel.connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                w.hide();
            }
        });
        w.setPosition(WindowPosition.CENTER);
    }
}
