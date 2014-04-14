Changements apportés aux services
=================================

- Bug commun à tous les services : manque `JpaUtil.creerEntityManager()` à chaque service
- Beaucoup de méthodes DAO n'ont pas de service correspondant, il est donc ~impossible de les utiliser
- Manque une méthode de recherche d'employé par son e-mail