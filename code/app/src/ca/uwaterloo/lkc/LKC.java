package ca.uwaterloo.lkc;

import java.io.FileNotFoundException;

import org.gnome.glib.*;
import org.gnome.gtk.Gtk;

public final class LKC extends Glib {

	public static void main(String args[]) throws FileNotFoundException {
		Gtk.init(args);
		LKC t = new LKC();

		t.fun();
		Gtk.main();
	}

	void fun() throws FileNotFoundException {
		WindowWelcome wndWelcome = new WindowWelcome("gui.glade");
		wndWelcome.w.show();
	}
}