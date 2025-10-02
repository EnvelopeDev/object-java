package ScreenForms;

import javax.swing.*;
import java.io.IOException;

public interface InterfaceEEW {
	String[] getDataForEdit(int rowNumber) throws IOException;
    void EditRowByNumber(int rowNumber) throws IOException;
}
