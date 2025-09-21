package laba2;

import list.interfaceList;
import list.List;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.*;
import javax.swing.*;

public class DogList 
{
	public static void main(String[] args)
	{
		JFrame frame = new JFrame("Dog List");
		
		ImageIcon icon = new ImageIcon("src/laba2/dogIcon.png");
		frame.setIconImage(icon.getImage());
		
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setLayout(new GridLayout(6, 1, 10, 10));
		
		JButton[] buttons = {
		new JButton("Pull"),
		new JButton("Push"),
		new JButton("Print"),
		new JButton("Add"),
		new JButton("Search"),
		new JButton("Remove")
		};
		
		for(JButton button : buttons)
		{
			buttonsPanel.add(button);
			button.setFont(new Font("Arial", Font.PLAIN, 30));
		}
		
		frame.getContentPane().add(BorderLayout.CENTER, buttonsPanel);
		frame.setVisible(true);
		
		frame.setSize(500, 600);
		
	}
}
