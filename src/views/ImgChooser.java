package views;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.Model;

public class ImgChooser {
	
	public static String getPath(){
		JFileChooser chooser = new JFileChooser();
		FileNameExtensionFilter filter = new FileNameExtensionFilter("jpg, jpeg,png", "jpg", "jpeg", "png");//on ne veut pouvoir ouvrir que des fichiers AFG ou SVG
		chooser.setFileFilter(filter);
		
		if(chooser.showOpenDialog(chooser)!=JFileChooser.APPROVE_OPTION) return null; 
		File selectedFile = chooser.getSelectedFile();
		return selectedFile.getPath();
	}

}
