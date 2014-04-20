À propos des services
=====================

## TP reçu (Predict'IF)

### Points positifs

- Les interfaces spécifiées sont efficaces
- Les ICARs sont précis et détaillés
- Le code est commenté

### Points négatifs

- Un bug (commun à *tous* les services) les rendait inutilisables tels que fournis (aucun `EntityManager` n'était créé). Il a donc fallu modifier chaque service utilisé.
- Beaucoup de méthodes DAO n'ont pas de service correspondant, il a donc fallu compléter les services
- Plusieurs services étaient manquants :
  - Recherche d'employé par son e-mail
  - Générer l'e-mail à envoyer aux partenaires
  - Obtenir les clients dont il faut faire l'horoscope par employé

## TP fourni (IF'Routard)

### Points positifs

- Les interfaces sont spécifiées de manière précise, un prototype interactif a été fourni
- Le diagramme de cas d'utilisation fourni permet d'avoir une vue globale des services
- Le code est propre et commenté


### Points négatifs

- Les interfaces spécifiées mettent en jeu des services qui n'ont pas été fournis (par exemple, l'utilisation d'images)
- Certains services étaient manquants. De plus, certains services superflus ont été fournis.
- Certaines fonctionnalités spécifiées (apparaissant dans les interfaces) étaient superflues