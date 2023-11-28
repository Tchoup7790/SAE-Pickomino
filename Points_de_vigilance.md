## <u>Points de vigilance vis-à-vis de la testabilité:</u>

### <u>Connector:</u>

- Faire attention aux modes de Connector utilisé durant la conception des tests car ils influencent le résultat. Par exemple la méthode choiceDices renvoie la liste de dès considérée en mode normal sinon elle renvoie une liste de dés obtenue aléatoirement auprès du serveur de jeu.

- La méthode finalScore doit être appelé lorsque le statut de la partie est 'GAME_FINISHED', l'erreur BadStepException n'assure pas cela car elle est utilisé lorsque finalScore est appelé au mauvais moment du tour et pas de la partie.

- La méthode newGame de Connector ne renvoie pas d'erreur si la connection echoue. 

- La méthode newGame ne renvoie pas d'erreur si le nombre de joueurs est invalide (quand il n'est pas compris dans l'ensemble [2,4] ), elle renvoit uniquement la paire (-1, -1). 

- La méthode rollDices doit renvoyer une liste dont la taille doit être entre 1 et 8.

- La méthode rollDices ne peut pas être appelé si il n'y a plus de dés.

- La méthode takePickomino doit renvoyer une erreur si le pickomino choisit n'existe pas.

### <u>Game:</u>

- La méthode pickosStackTops renvoie une liste de la valeur des pickomino en haut de la pile de chaque joueur, donc si un ou plusieurs joueur n'ont pas de pickomino dans leurs pile alors il y aura soit un None a la place du Int ce qui va créer une erreur soit rien, ce qui va créer des problèmes pour savoir a qui les pickominos appartiennent. 

- La méthode accessiblePickos donne la liste des pickomino accesible, il faut faire attention que durant la conception des test il y ait des pickomino accessible ou alors qu'il y ait une erreur qui soit envoyée quand c'est le cas.

- La méthode score de Game retourne une liste avec le score de chaque joueur, il faut faire attention que le nombre de joueur soit pareil que la longeur de la liste ou créer une erreur pour ce cas.

### <u>Pikomino:</u>

- La méthode Pickomino créer les pickomino, elle a pour paramètre : value et nbWorms qui correspondent a leurs valeurs et le nombre de petit vers, seulement leurs valeurs sont prédéfinies par exemple value doit être compris entre 21 et 36 et le nombre de petit vers doit être comme ceci 1 entre [21,24], 2 entre [25 et 28], 3 entre [29, 32] et 4 entre [33, 36] Ils faut renvoyer des erreurs différentes pour tout les cas.

- La méthode Pickomino créer les pickomino, mais elle doit les créer en un seul exemplaire. Elle doit renvoyer une erreur si deux pikomino pareil sont créer.


