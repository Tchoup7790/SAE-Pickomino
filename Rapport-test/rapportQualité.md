## <u>Rapport de la conception des test:</u>

### <u>Introduction:</u>

    Ce rapport de test vise à évaluer la fonctionnalité et la fiabilité de la librairie **pickomino-lib** utilisée dans le cadre du projet. L'objectif de ces test est de tester les différentes fonctionnalités et la fiabilité de la librairie qui permettent d'interagir avec le serveur gérant le jeu.

    La librairie "pickomino-lib" est un composant essentiel du projet, car elle offre une interface permettant à l'application de communiquer avec le serveur du jeu. Cette librairie fournit des méthodes et des fonctionnalités pour effectuer des actions telles que rejoindre une partie, lancer les dés, choisir une tuile, mettre à jour l'état du jeu...

Pour réaliser ces tests, nous avons utilisé les outils suivants:

1. Kotlin : Un langage de programmation moderne et polyvalent utilisé pour développer l'application.
2. JavaFX : Une bibliothèque graphique utilisée pour créer l'interface utilisateur de l'application.
3. JUnit : Un framework de test unitaire pour Java et Kotlin utilisé pour écrire et exécuter les tests.
4. "pickomino-lib" : La librairie testée qui facilite l'interaction avec le serveur du jeu.

    

    Le rapport de test est organisé en classes, chacune ciblant des aspects spécifiques de la librairie "pickomino-lib". L'objectif est d'identifier les éventuels problèmes, les erreurs de comportement ou les limitations de la librairie afin de garantir un fonctionnement fiable et conforme aux attentes du jeu Pickomino.

### <u>Description des test:</u>

    Nous avons fait des fonctions qui permettent de minimiser les lignes de code pour faire prendre des pickominos, perdre son tour a un joueur. Voici leurs descriptions.

---

**decompositionPickomino(nb : Int) : List<DICE>**

    Fonction qui prend en paramètre nb, la valeur d'un pickomino et décompose cette valeur en une addition de dés List<DICE>.

**choosePickomino(nb : Int)**

    Fonction qui prend en paramètre nb, la valeur d'un pickomino et fait les opérations nécessaire pour que le joueur courant prenne le pickomino corréspondant.

**joueurPerd()**

    Fonction qui fait perdre/passer son tour au joueur courant.

---

#### <u>takePickomino:</u>

    La fonction takePickomino réalise l'étape de prendre un pickomino et de l'ajouter à sa pile de pickomino. Nous avons identifié en tout 7 cas différent qui peuvent être testés.

- Prendre un pickomino que l'on peut bien prendre, il est disponible et le joueur a la bonne combinaison de dés.
  
  - On test en faisant prendre un pickomino au joueur 1 et en verrifiant qu'il est bien dans sa pile de pickomino.
    
    Le pickomino est bien dans la pile du joueur 1

- Prendre un pickomino que l'on ne peut pas prendre, il est disponible et le joueur n'a pas  la bonne combinaison de dés.
  
  - On test en faisant prendre un pickomino qu'il ne peut pas prendre avec sa combinaison de dés. 
    
    Le serveur renvoit l'erreur 'BadPickominoChosenException'

- Prendre un pickomino alors que le joueur a passé/perdu son tour.
  
  - On test en faisant prendre en tout 7 dés vers, il relance il tombe sur un dés vers puis il essaye de prendre le pickomino de valeur 35 qu'il pouvait prendre au tour d'avant.
    
    Le serveur renvoit l'erreur 'BadStepException'

- Prendre un pickomino qui a été retourné/caché, il n'est pas disponible.
  
  - On fait perdre/passer le tour au joueur 1, il retourne le pickomino 36, puis on fait en sorte que le joueur 2 essaye de prendre le pickomino 36.
    
     Le serveur renvoit l'erreur 'BadPickominoChosenException'

- Prendre un pickomino a un joueur qui est disponible.
  
  - On fait prendre le pickomino 36 au joueur 1 et on fait en sorte que le joueur 2 prennent le joueur 36 
    
    Le serveur ne renvoit pas d'erreur.

- Prendre un pickomino a un joueur qui n'est pas disponible.
  
  - On fait prendre le pickomino 36 au joueur 1, on fait en sorte que le joueur 2 passe son tour, on fait prendre un pickomino quelconque au joueur 1, on fait prendre le pickomino 36 au joueur 2.
    
    Le serveur renvoit l'erreur 'BadPickominoChosenException'

- Prendre un pickomino de la bonne valeur mais sans dés ver.
  
  - On fait en sorte que le joueur 1 ait une combinaison bonne pour avoir le pikomino 32 mais sans de dés vers.
    
    Le serveur renvoit l'erreur 'BadStepException'

Tout ces test se sont passé comme ils le devraitent

#### <u>keepDices:</u>

    La fonction keepDices réalise l'étape de garder un (ou plusieurs) dé(s) d'une valeur considérée. Nous avons identifié en tout 3 cas qui peuvent être testés.

- Prendre des dés que l'on peut prendre.
  
  - On fait choisir un dés vers au joueur 1, puis on verifie avec un assertEquals() que le dernier dés pris est bien un dés vers
    
    Le dernier dés pris est bien un dés vers

- Prendre des dés qui ne sont pas présents dans le lancé. 
  
  - On fait lancé sept dés 1 et un dés vers au joueur 1, on fait prendre les dés 2 au jouer 1.
    
    Le serveur renvoit l'erreur 'DiceNotInRollException'

- Prendre des dés que l'on a déja pris précédement dans le tour.
  
  - On fait lancé sept dés 1 et un dés vers au joueur 1, on fait prendre le dés vers au joueur 1, on fait lancé 6 dés 1 et un dés vers au joueur 1, on fait prendre le dés vers.
    
    Le serveur renvoit l'erreur 'DiceAlreadyKeptException'

Tout ces test se sont passé comme ils le devraitent

#### <u>finalScore:</u>

    La fonction finalScore donne le score final de la partie, quand le statut est 'GAME_FINISHED'.  Nous avons fait des test paramétriques, nous avons simuler en tout 5 parties. Tout d'abord nous allons montrer les pickomino que chacun des 2 joueurs ont pris puis le score attendu et le score donné par la donction final score.

(à coté de la valeur des pickomino possédé par chaque joueur sera le nombre de vers présent  sur ceux-ci)

Même si nous avons fait en tout 5 test voici uniquement le premier :

- Partie 1:
  
  - joueur 1: 36:4, 35:4, 34:4, 33:4, 32:3, 31:3, 30:3, 29:3
    
    4 + 4 + 4 + 4 + 3 + 3 + 3 + 3 = 28
  
  - joueur 2: 28:2, 27:2, 26:2, 25:2, 24:1, 23:1, 22:1, 21:1
    
    2 + 2 + 2 + 2 + 1 + 1 + 1 + 1 = 12

Tout les test se sont passé comme ils le devraitent

#### <u>rendPickomino:</u>

    Lorsqu'un joueur passe son tour, il doit rendre le dernier Pickomino visible qu’il
possède (s’il en possède au moins un). Un joueur qui doit rendre un Pickomino le replace, face visible, au centre de la table. De plus, il retourne dans la brochette, le Pickomino qui a la plus forte valeur : ce Pickomino ne peut plus être récupéré et reste face cachée jusqu'à la fin du jeu. Si le Pickomino rendu est celui qui a la plus forte valeur, il reste face visible. Nous allons tester cette fonctionnalité.

Nous avons fait des test paramétrique pour ces cas, la fonction fait prendre une liste de pickomino donné aux joueur 1 et 2, puis elle fait perdre le joueur courant et elle vérifie la valeur du pickomino accessible de plus forte valeur voir si il a bien été rendu.

- Partie 1:
  
  - joueur 1: 36, 32
  
  - joueur 2: 35, 34
    
    on fait perdre le joueur 1

On verifie maintenant avec AssertEquals que le joueur 1 a bien rendu son pickomino 32. Ce qui ne renvoit pas d'erreur.

#### <u>Status:</u>

Status représente à quelle étape d'un tour du jeu on en est.  Status est une énumération sérialisable, qui peut être utilisée pour représenter différents états ou statuts dans un programme.

Nous allons nous intérésser à ces Entries qui sont au nombre de 5:

- GAME_FINISHED()

-  TAKE_PICKOMINO()

- KEEP_DICE()

- ROLL_DICE_OR_TAKE_PICKOMINO()

- ROLL_DICE()

Nous avons recréer des partie pour vérifier si le "state" de la partie est bien le bon

---

##### <u>GAME_FINISHED():</u>

On fait prendre les pickominos suivant aux joueurs:

joueur 1: 36, 34, 32, 30, 28, 26, 24, 22

joueur 2: 35, 33, 31, 29, 27, 25, 23, 21

La partie est finie tout les pickomino on été pris le status devrait être GAME_FINISHED.^

On vérifie que le status courant est bien GAME_FINISHED avec assertEquals. Ce qui ne renvoit pas d'erreur.

---

##### <u>TAKE_PICKOMINO():</u>

On fait lancé sept dés vers et un dé 1 au joueur 1.

Puis on fait choisir les dés vers au joueur 1.

On fait lancé un dés 1 au joueur 1.

Puis on fait choisir le dés 1 au joueur 1.

Le joueur 1 n'as plus de dés et a le bon score de dés pour choisir le pickomino 36 le status devrait être TAKE_PICKOMINO.

On vérifie que le status courant est bien TAKE_PICKOMINO avec assertEquals. Ce qui ne renvoit pas d'erreur.

---

##### <u>KEEP_DICE():</u>

On fait lancé sept dés 2 et un dés vers au jouer 1.

Le joueur 1 ne peut que choisir de garder des dés le status devrait être KEEP_DICE.

On vérifie que le status courant est bien KEEP_DICE() avec assertEquals. Ce qui ne renvoit pas d'erreur.

---

##### <u>ROLL_DICE_OR_TAKE_PICKOMINO():</u>

On fait lancé sept dés vers et un dés 1 au joueur 1.

On lui fait choisir les dés vers.

Le joueur 1 as deux choix, il peut soit prendre le pickomino 35 car il a le bon score de dés pour avoir ce pickomino, soit il peut relancer le dés restant, le status devrait être ROLL_DICE_OR_TAKE_PICKOMINO.

On vérifie que le status courant est bien ROLL_DICE_OR_TAKE_PICKOMINO avec assertEquals. Ce qui ne renvoit pas d'erreur.

---

##### <u>ROLL_DICE():</u>

On fait lancé sept dés 2 et 1 dés  vers au joueur 1.

On lui fait choisir les dés 2.

Le Joueur 1 n'as pas assez de score de dés pour prendre un pickomino et il lui reste 1 dés il peut uniquement relancer les dés, le status devrait être ROLL_DICE.ROLL_DICEROLL_DICE

On vérifie que le status courant est bien ROLL_DICE avec assertEquals. Ce qui ne renvoit pas d'erreur.


