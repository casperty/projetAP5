Logiciel de dessin vectoriel
=========

**Auteurs du projet :** François Lamothe, Guillaume Lecocq et Alexandre Ravaux

<div align="center" markdown="1"><img src="https://raw.github.com/nerdyprog/projetAP5/master/res/splashscreen.png" /></div>
Résumé du sujet :
--
Vous devez produire un logiciel permettant de faire du dessin vectoriel. Le logiciel aura une interface graphique et devra répondre à quelques contraintes minimales spéciﬁées dans ce document. Vous devrez réaliser un logiciel permettant à l’utilisateur de composer et visualiser un dessin vectoriel dans une zone de dessin, en sélectionnant des formes, couleurs, etc. et en plaçant ces formes à l’endroit désiré. Vous devez réaliser votre application selon la décomposition MVC.

I- Fonctions principales du programme
--

Le logiciel propose la création de formes pleines : rectangle, rond, polygone. Il permet aussi de tracer des traits.
Les objets dessinés peuvent être déplacés et être redimmensionnés dans le canvas. On peut également choisir la couleur de l'objet que nous voulons dessiner avant et après l'avoir dessiné. 

II - Fonctions supplémentaires 
--
####*Couleur*####

Le logiciel permet de choisir la couleur de la forme avant de la dessin via la palette proposée, mais l'utilisateur peut également choisir sa couleur en tapant les valeurs RGB de la couleur dans des champs texte mis à sa disposition. On peut également définir la valeur alpha, c'est-à-dire l'opacité, de la forme dans ces champs. 

####*Zoom*####

On peut zoomer sur le canvas à l'aide de la molette de la souris ou grâce aux boutons + et - en bas de la fenêtre principale.

####*Sélection multiple*####

Pour sélectionner plusieurs objets à la fois, il faut sélectionner l'outil "Select", puis tout en appuyant sur la touche Maj (Shift), sélectionner les objets voulus.

####*Undo-redo*####

"Oh zut ! Je ne voulais pas supprimer ce rectangle !" Pas de panique, vous pouvez toujours revenir en arrière. 
Dans le menu, vous pouvez faire : Edit > Undo. Ou bien tapez le raccourci clavier : **Ctrl+Z**.

"Oh finalement, je voulais le supprimer ce rectangle" Vous pouvez également revenir en avant. 
Dans le menu, vous pouvez faire : Edit > Redo. Ou bien tapez le raccourci clavier : **Ctrl+Y**.

####*Copier-coller*####

Ctrl+C : Copier 

Ctrl+V : Coller

*Gestion de la profondeur des formes*

A disposition est mis un "Shape manager" (sorte de *layer manager*) permettant de gérer la profondeur des formes dans le dessin.

####*Enregistrement et ouverture*####

Nous pouvons enregistrer le dessin au format AFG (format propre au logiciel) mais aussi l'exporter en SVG (***Scalable Vector Graphics***, format standard pour le dessin vectoriel). 

Exemple de dessin exporté en AFG : https://github.com/nerdyprog/projetAP5/blob/master/exportAFG.afg

Exemple de dessin exporté en SVG : https://github.com/nerdyprog/projetAP5/blob/master/exportSVG.svg (la syntaxe SVG est validée par W3C : http://validator.w3.org/).

<div align="center" markdown="1"><img src="https://raw.github.com/nerdyprog/projetAP5/master/exportSVG.png" /></div>

[Github n'arrivant pas à afficher les SVG, l'image est en PNG pour vous montrer ce que donne le SVG, ainsi que le AFG.]

L'import en SVG peut se faire mais uniquement avec les SVG créés avec le logiciel...




III - Structures du logiciel
--
Le programme est composée de trois packages : models, views et controllers. On respecte ici une architecture MVC, à noter que les controleurs ne sont pas forcément dans le package "controllers".

* Dans models sont définies les formes (rectangle, polygone...).
* Dans views sont définies les fenêtres de logiciel.
* Dans controllers a été placé le controlleur du menu du logiciel.

IV - Répartition des tâches dans le groupe
--
####*Conception générale de l'interface :*####

On s'est mis d'accord pour la présentation de notre interface : 
* **MainFrame** : avec une barre de menu (idée d'Alexandre) et  une zone exclusivement réservée au dessin.
* Color : la palette de couleur (ColorWheel : idée de François) et les champs de texte pour sélectionner les couleurs RGBA (idée de Guillaume) dans une fenêtre sur la droite. 
* **Tools** : les différents outils pour la conception.
* **Shape manager** : gestionnaire de profondeur de l'objet.

####*Développement :*####

* François et Guillaume se sont penchés sur la conception polygone ensemble.
* Alexandre et François ont travaillé sur les champs de texte pour le RGBA et l'hexacode.
* Colorwheel développé par François exclusivement. 
* Export SVG et ouverture SVG développé exclusivement par Alexandre (complété par François pour la balise <image>). 
* Guillaume et Alexandre ont commencé l'enregistrement en AFG, et François l'a complété.
* Le reste a été conçu en trinôme.



###Reste à implémenter :###
* insertion images JPEG ou PNG
* export en JPG
