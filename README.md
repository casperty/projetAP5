Logiciel de dessin vectoriel
=========

Auteurs du projet : François Lamothe, Guillaume Lecocq et Alexandre Ravaux

Résumé du sujet :
--
Vous devez produire un logiciel permettant de faire du dessin vectoriel. Le logiciel aura une interface graphique et devra répondre à quelques contraintes minimales spéciﬁées dans ce document. Vous devrez réaliser un logiciel permettant à l’utilisateur de composer et visualiser un dessin vectoriel dans une zone de dessin, en sélectionnant des formes, couleurs, etc. et en plaçant ces formes à l’endroit désiré. Vous devez réaliser votre application selon la décomposition MVC.

I- Fonctions principales du programme
--

Le logiciel propose la création de formes pleines : rectangle, rond, polygone. Il permet aussi de tracer des traits.
Les objets dessinés peuvent être déplacés et être redimmensionnés dans le canvas. On peut également choisir la couleur de l'objet que nous voulons dessiner avant et après l'avoir dessiné. 

II - Fonctions supplémentaires 
--
Le logiciel permet de choisir la couleur de la forme avant de la dessin via la palette proposée, mais l'utilisateur peut également choisir sa couleur en tapant les valeurs RGB de la couleur dans des champs texte mis à sa disposition.

Nous pouvons également exporter l'image au format SVG (format standard pour le dessin vectoriel). Exemple de dessin exporté en SVG : https://github.com/nerdyprog/projetAP5/blob/master/exportSVG.svg. Le code est validé par W3C (http://validator.w3.org/)

![Alt text](https://raw.github.com/nerdyprog/projetAP5/master/exportSVG.png "Dessin exporté en SVG")

[Github n'arrivant pas à afficher les SVG, l'image est en PNG pour vous montrer ce que donne le SVG.]

(L'import en SVG peut se faire mais uniquement avec les SVG créés avec le logiciel...)


III - Structures du logiciel
--
Le programme est composée de trois packages : models, views et controllers. On respecte ici une architecture MVC, à noter que les controleurs ne sont pas forcément dans le package "controllers".

* Dans models sont définies les formes (rectangle, polygone...).
* Dans views sont définies les fenêtres de logiciel.

IV - Répartition des tâches dans le groupe
--
*Conception générale de l'interface :*
On s'est mis d'accord pour la présentation de notre interface : 
* MainFrame (au centre): avec une barre de menu (idée d'Alexandre) et  une zone exclusivement réservée au dessin.
* Color (à droite) : la palette de couleur (ColorWheel : idée de François) et les champs de texte pour sélectionner les couleurs RGBA (idée de Guillaume) dans une fenêtre sur la droite. 
* Tools (à gauche) : les différents outils pour la conception.

*Développement :*
François et Guillaume se sont penchés sur la conception polygone ensemble, Alexandre et François ont travaillé sur les champs de texte pour le RGBA. François a également travaillé sur la palette de couleur (ColorWheel).
Alexandre a travaillé sur l'export en SVG.



Reste à implémenter :
* gestion undo/redo >> pour l'instant le undo/redo fait que supprimer/remettre la derniere forme créée et non la dernière action faite
* superposition des objets (> Systeme de calques par exemple)
* selection multiple !! 
* zoom (en cours)
* redimmensionnement du polygone (en cours)
* sauvegarde et ouverture du fichier en AFG (en cours)
* insertion images JPEG ou BMP



