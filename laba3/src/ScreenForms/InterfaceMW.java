package ScreenForms;

import java.io.IOException;

public interface InterfaceMW {
	void show() throws IOException;
    void handleButtonClick(int buttonIndex);
    void exitApplication();
}
