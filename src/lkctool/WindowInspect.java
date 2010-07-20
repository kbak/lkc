package lkctool;

import java.io.FileNotFoundException;

import org.gnome.gdk.Event;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Alignment;
import org.gnome.gtk.Button;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.ToggleButton;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

public class WindowInspect {

    public final Window w;

    WindowInspect(String gladeFile) throws FileNotFoundException
    {
        final XML xmlWndInspect = Glade.parse(gladeFile, "wndInspect");
        
        w = (Window) xmlWndInspect.getWidget("wndInspect");
        final Button btnCancel = (Button) xmlWndInspect.getWidget("btnCancel");
        final ToggleButton tbtnInfo = (ToggleButton) xmlWndInspect.getWidget("tbtnInfo");
        final Alignment alignInfo = (Alignment) xmlWndInspect.getWidget("alignInfo");
        
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
                Gtk.mainQuit();
            }
        });
        
        tbtnInfo.connect(new ToggleButton.Toggled() {
            
            @Override
            public void onToggled(ToggleButton arg0) {
                if (tbtnInfo.getActive())
                {
                    alignInfo.show();
                }
                else
                {
                    alignInfo.hide();
                }
            }
        });
    }
}
