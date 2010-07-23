package ca.uwaterloo.lkc;

import java.io.FileNotFoundException;

import org.gnome.gdk.Event;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Button;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.RadioButton;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;
import org.gnome.gtk.WindowPosition;

public class WindowWelcome {

    public final Window w;
    
    WindowWelcome(final String gladeFile) throws FileNotFoundException
    {
        final XML xmlWndWelcome = Glade.parse(gladeFile, "wndWelcome");
        
        w = (Window) xmlWndWelcome.getWidget("wndWelcome");
        final Button btnCancel = (Button) xmlWndWelcome.getWidget("btnCancel1");
        final Button btnNext = (Button) xmlWndWelcome.getWidget("btnNext");
        final RadioButton rbtnStartNew = (RadioButton) xmlWndWelcome.getWidget("rbtnStartNew");
        
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
        
        btnNext.connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                if (rbtnStartNew.getActive())
                {
                    w.hide();
                    WindowInspect wndInspect;
                    try {
                        wndInspect = new WindowInspect(gladeFile);
                        wndInspect.w.show();
                        wndInspect.start();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                else
                {
                    w.hide();
                    try {
                        WindowConfig wnd = new WindowConfig(gladeFile, null);
                        wnd.run();
                    } catch (FileNotFoundException e) {
                        // TODO Auto-generated catch block
                            e.printStackTrace();
                    }
                }
            }
        });
        w.setPosition(WindowPosition.CENTER);
    }
}