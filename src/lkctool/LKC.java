package lkctool;

import java.io.FileNotFoundException;

import org.gnome.glade.Glade;
import org.gnome.glade.XML;
import org.gnome.glib.*;
import org.gnome.gtk.Button;
import org.gnome.gtk.Gtk;
import org.gnome.gtk.Window;

public final class LKC extends Glib{
    XML glade;
    Window top;
    Button confirm;
    
    public static void main(String args[]) throws FileNotFoundException
    {
        Gtk.init(null);
        LKC t = new LKC();
        
        t.fun();
        Gtk.main();
    }
    
    void fun() throws FileNotFoundException
    {
        glade = Glade.parse("gui.glade", "wndConfig");
        Window w = (Window) glade.getWidget("wndConfig");
        w.show();
    }
}