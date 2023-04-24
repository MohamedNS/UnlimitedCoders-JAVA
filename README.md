# UnlimitedCoders-3A36-JAVA
![image](https://user-images.githubusercontent.com/123477447/217036967-6d1d717f-2fc4-473a-8b51-ca4ec336796d.png)

## Nom Projet : Healthified
- Une application (Desktop/Web/Mobile) dans le domaine de santé.
## Nom Groupe: Unlimited Coders
## Membres Groupes:
-   Bali Mohamed Omar
-   Ben Farhat Mootaz
-   Hmaidi Arij
-   Naddari Mouheb
-   Ridha Ahmed
-   Sakouhi Takwa
-   Soussi Mohamed Nour

## Liste des modules:
- Gestion `Utilisateurs`
- Gestion `Pharmacie`
- Gestion `Consultation`
- Gestion `Blog`
- Gestion `Rendez-Vous`
- Gestion `Assurances`
- Gestion `Produit`

## Description des Classes

### Classe **Medicament**
- int `id`
- int `code`
- String `libelle`
- float `prix`
- String `type`
- int `dosage`
- String `description`
- String `nom`

### Classe **Ordonnance**
- int `id`
- int `consultation_id`
- int `validite`

## Classe **Consultation**
- int `id`
- String `matriculeMedecin`
- String `idPatient`
- Date `DateConsultation`
- float `montant`
- ## Classe **RendezVous **
 - int id
 - Timestamp date
 - Utilisateur medecin
 - Utilisateur patient
 - String description
 - String etat
- ## Classe **Calendrier **
 -    private int id
 - Timestamp heure_debut
 - Timestamp heure_fin
 - Utilisateur medecin
