package ca.uwaterloo.lkc;

import java.io.FileNotFoundException;

import org.gnome.gdk.Event;
import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.gtk.Alignment;
import org.gnome.gtk.Button;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.IconSize;
import org.gnome.gtk.Image;
import org.gnome.gtk.Layout;
import org.gnome.gtk.ProgressBar;
import org.gnome.gtk.Stock;
import org.gnome.gtk.ToggleButton;
import org.gnome.gtk.VBox;
import org.gnome.gtk.Widget;
import org.gnome.gtk.Window;

public class WindowInspect extends Thread {

    final String gladeFile;
    final XML xmlWndInspect;
    
    public final Window w;

    private final int nPartsConfiguration = 2;
    private final int nPartsHardware = 100;
    private final int nParts = nPartsConfiguration + nPartsHardware;
    private final int taskTime = 10;
    
    private final ProgressBar pg;
    
    WindowInspect(String gladeFile) throws FileNotFoundException
    {
        this.gladeFile = gladeFile;
        
        xmlWndInspect = Glade.parse(gladeFile, "wndInspect");
        
        w = (Window) xmlWndInspect.getWidget("wndInspect");
        final Button btnCancel = (Button) xmlWndInspect.getWidget("btnCancel");
        final ToggleButton tbtnInfo = (ToggleButton) xmlWndInspect.getWidget("tbtnInfo");
        final Alignment alignInfo = (Alignment) xmlWndInspect.getWidget("alignInfo");
        pg = (ProgressBar) xmlWndInspect.getWidget("pgInspect");
        final VBox vbox4 = (VBox) xmlWndInspect.getWidget("vboxInformation");
        
        w.connect(new Window.DeleteEvent() {
            
            @Override
            public boolean onDeleteEvent(Widget arg0, Event arg1) {
                Gtk.mainQuit();
                return false;
            }
        });
        
        final WindowInspect wi = this;
        
        btnCancel.connect(new Button.Clicked() {
            
            @Override
            public void onClicked(Button arg0) {
                wi.stop();
                Gtk.mainQuit();
            }
        });
        
        tbtnInfo.connect(new ToggleButton.Toggled() {
            
            @Override
            public void onToggled(ToggleButton arg0) {
                if (tbtnInfo.getActive())
                {
                    alignInfo.show();
                    alignInfo.setSizeRequest(-1, 300);
                }
                else
                {
                    alignInfo.hide();
                    w.resize(1, 1);
                }
            }
        });
    }
    
    public void run() {
        dumpConfiguration();
        dumpHardware();
        w.hide();
        try {
            WindowConfig wnd = new WindowConfig(gladeFile, null);
            wnd.run();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    
    void dumpConfiguration()
    {
        work((Image) xmlWndInspect.getWidget("imgConfiguration"), nPartsConfiguration);
    }
    
    void dumpHardware()
    {
        work((Image) xmlWndInspect.getWidget("imgHardware"), nPartsHardware);
    }
    
    void work(Image img, int steps)
    {
        img.setImage(Stock.FIND, IconSize.BUTTON);
        
        for (int i = 0; i < steps; ++i)
        {
            try {
                sleep(taskTime);
                pg.setFraction(Math.min(1.0, pg.getFraction() + (1.0 / nParts)));
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        
        img.setImage(Stock.APPLY, IconSize.BUTTON);
    }
}