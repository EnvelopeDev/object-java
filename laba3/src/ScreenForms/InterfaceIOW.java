package ScreenForms;

import java.io.IOException;

public interface InterfaceIOW {
	String[] getData();
    void successOperationWindow(String text);
    boolean confirmOperationWindow(String title_window, String text);
    int getRow() throws IOException;
    void show();
}
