package models;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JOptionPane;

public class ExportAFG {

	public ExportAFG(Model model,FileWriter stream) throws IOException{
		PrintWriter out = new PrintWriter(stream);
	    try {
    		//entete du fichier
            // Contenu du fichier (liste des formes + leurs attributs)
            for(int i=0; i<model.getFormes().size();i++){
            	//System.out.println(model.getFormes().get(i).toString());
              	out.println(model.getFormes().get(i).toString());
            }
            out.close();
            if (out.checkError())
               throw new IOException("Output error.");
         }
         catch (Exception e) { // Echec de l'enregistrement des donnees
         }  
	}
	
}
