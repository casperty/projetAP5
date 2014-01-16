package views;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import models.Model;
/**
 * 
 * @author François Lamothe Guillaume Lecocq Alexandre Ravaux
 *
 */
public class ExportImg{
	
	private Model model;
	private DrawArea dr;
	
	private JFileChooser fileDialog;
	private File fileName,selectedFile; 
	/**
	 * Fenetre pour sauvegarder le fichier en JPG, 
	 * Se lance quand on clique sur Shift+E ou bien File > Export > Export to JPG
	 * Appelé dans MenuListener
	 * @param model
	 * @param dr
	 */
	public ExportImg(Model model, DrawArea dr){
		this.model=model;
		this.dr=dr;
		if(fileDialog==null){
			fileDialog = new JFileChooser();
			FileNameExtensionFilter filter = new FileNameExtensionFilter( "JPG", "jpg");
			fileDialog.setFileFilter(filter);
		} 
		
		if(fileName==null){
			selectedFile = new File("Untitled.jpg");
		}else{
			selectedFile = new File(fileName.getName());
		}
		
		fileDialog.setSelectedFile(selectedFile); 
	    fileDialog.setDialogTitle("Save the file");
	    int option = fileDialog.showSaveDialog(fileDialog);
		if (option != JFileChooser.APPROVE_OPTION) return;  // Annuler ou fermeture de la fenetre.
	         selectedFile = fileDialog.getSelectedFile();
	         if (selectedFile.exists()) {  // Le fichier existe dÃ©jÃ , devons nous ecraser le fichier existant ?
	            int response = JOptionPane.showConfirmDialog( fileDialog,
	                  "Le fichier \"" + selectedFile.getName()
	                  + "\" existe deja .\nVoulez-vous le remplacez ?", 
	                  "Remplacer",
	                  JOptionPane.YES_NO_OPTION, 
	                  JOptionPane.WARNING_MESSAGE );
	            if (response != JOptionPane.YES_OPTION) return;  // Annuler l'enregistrement
	         }
	    try{
	    	/*Si vous n'avez pas entré le format, le logiciel le fait pour vous*/
	    	if(!selectedFile.getName().endsWith(".jpg")){
	    		selectedFile = new File(selectedFile.getName()+".jpg");
	    	}
	    	createImg();
	    }catch(Exception e){ // Echec de l'ouverture du fihier
	    	JOptionPane.showMessageDialog(fileDialog, this,"Nous sommes desole mais une erreur s'est produite.", option);
	    }
	}
	/**
	 * Creation de l'image
	 */
	public void createImg(){
		BufferedImage b = new BufferedImage(model.getAreaSz().getX(), model.getAreaSz().getY(), BufferedImage.TYPE_INT_RGB);
		Graphics g = b.createGraphics();
		dr.paintComponent(g);
		g.dispose();
		try{
			ImageIO.write(b,"jpg",selectedFile);
		}catch (Exception e) {
			System.out.println("erreur export jpg "+e);
		}
		
	}

}
