package laba2;

import fileManager.interfaceFM;
import fileManager.FileManager;

import list.interfaceList;
import list.List;

import object.dog.*;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.*;
import javax.swing.*;
import java.awt.Image;

public class DogList 
{
	public static void main(String[] args) throws IOException
	{
		FileManager fm = new FileManager();
		List<Dog> dogs = new List<>();
		
		dogs = fm.inputFromCSV("src/data/dogs.csv");
		dogs.print();
		fm.outputToCSV("src/data/dogs.csv", dogs);
		 
		JFrame frame = new JFrame("Dog Festival");
		ImageIcon icon = new ImageIcon("src/laba2/dogIcon.png");
		frame.setIconImage(icon.getImage());

		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(1, 7, 10, 10));

		String[] imagePaths = {
		    "src/laba2/save.png",
		    "src/laba2/folder_documents.png",
		    "src/laba2/cloud.png",
		    "src/laba2/plus.png",
		    "src/laba2/minus.png",
		    "src/laba2/edit.png",
		    "src/laba2/print.png" 
		};

		String[] tooltips = {
		    "Save",
		    "Open",
		    "Backup",
		    "Add",
		    "Remove",
		    "Edit",
		    "Print"
		};

		JButton[] buttons = new JButton[7];

		for(int i = 0; i < 7; i++) {
		    ImageIcon buttonIcon = new ImageIcon(imagePaths[i]);
		    
		    Image scaledImage = buttonIcon.getImage().getScaledInstance(32, 32, Image.SCALE_SMOOTH);
		    ImageIcon scaledIcon = new ImageIcon(scaledImage);
		    
		    buttons[i] = new JButton(scaledIcon);
		    buttons[i].setToolTipText(tooltips[i]); 
	
		    buttons[i].setBorderPainted(false);
		    buttons[i].setContentAreaFilled(false);
		    buttons[i].setFocusPainted(false);
		    
		    buttonsPanel.add(buttons[i]);
		}

		frame.getContentPane().add(BorderLayout.NORTH, buttonsPanel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 600); 
		frame.setVisible(true);
	}
}
