# Gestion d'hôtel avec Spring Boot

Ce projet est une application de gestion d'hôtel développée avec Spring Boot. Il offre différentes fonctionnalités pour gérer les chambres, les réservations, les utilisateurs et les rôles.

## Fonctionnalités

- **Gestion des chambres** : Ajout, suppression, mise à jour, recherche par critères (prix, disponibilité, capacité, etc.).
- **Gestion des réservations** : Ajout, suppression, mise à jour, recherche par critères (date d'arrivée, date de départ, chambre, etc.).
- **Gestion des utilisateurs** : Ajout, suppression, mise à jour, recherche par nom d'utilisateur, attribution de rôles (réservé aux administrateurs).
- **Gestion des rôles** : Ajout, suppression, mise à jour, recherche par nom de rôle.
- **Gestion des avis** : Ajout, suppression, mise à jour, recherche par chambre ou par client.
- **Sécurité basée sur JWT** : Authentification et autorisation des utilisateurs.

## Technologies utilisées

- Java
- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL
- Lombok
- Springdoc OpenAPI (Swagger)
- JWT (JSON Web Token) pour la sécurité

## Comment exécuter le projet

1. Assurez-vous d'avoir une base de données MySQL configurée et accessible.
2. Modifiez les paramètres de la base de données dans le fichier `application.properties`.
3. Compilez et exécutez l'application en utilisant Maven ou votre IDE préféré.

