
# Airbus API - Backend

## Description du projet

Ce projet est une API de gestion de la maintenance des avions, construite en utilisant **Java 11+**, **Spring Boot**, et **Maven**. L'objectif de cette application est de gérer les maintenances des avions, les ingénieurs responsables, et les différents types de maintenance associés.

L'API permet de :

- Ajouter, récupérer et gérer les maintenances des avions.
- Gérer les ingénieurs et les types de maintenance.
- Suivre le statut de chaque maintenance et de chaque avion.
- Implémenter une architecture **MVC** avec une gestion propre des entités via **DTO** et **Mapper**.

## Technologies utilisées

- **Java 11+**
- **Spring Boot** (Version 2.7.x)
- **Spring Data JPA** et **Hibernate** pour l'accès à la base de données.
- **Microsoft SQL Server** pour la gestion des données.
- **Maven** comme outil de gestion de projet.
- **Swagger/OpenAPI** pour la documentation de l'API.
- **Spring Security** pour l'authentification et l'autorisation des utilisateurs (via un utilisateur en mémoire pour la démo).

## Fonctionnalités principales

### 1. Gestion des maintenances

- Ajout d'une nouvelle maintenance pour un avion.
- Consultation des maintenances existantes.
- Mise à jour du statut des maintenances (ex : planifiée, en cours, terminée).
- Gestion des types de maintenance (préventive, corrective).

### 2. Gestion des ingénieurs

- Création et gestion des ingénieurs.
- Attribution des ingénieurs aux maintenances spécifiques.

### 3. Sécurité

- Authentification via **Basic Authentication** avec un utilisateur admin en mémoire.
- Toutes les ressources de l'API nécessitent une authentification pour y accéder.

## Instructions d'installation

### Prérequis

Avant de commencer, assurez-vous que vous avez installé :

- **Java 11+** (version requise).
- **Maven** pour gérer le projet.
- **Microsoft SQL Server** (ou une autre base de données compatible si nécessaire).

### Étapes d'installation

1. Clonez ce dépôt sur votre machine :
   ```bash
   git clone https://github.com/Mojtaba-Kazmi/airbus.git
   cd airbus
   ```

2. Construisez le projet avec **Maven** :
   ```bash
   mvn clean install
   ```

3. Configurez la base de données **SQL Server** dans `application.properties` :

   ```properties
   spring.datasource.url=jdbc:sqlserver://localhost:1433;databaseName=airbusdb
   spring.datasource.username=your_username
   spring.datasource.password=your_password
   spring.jpa.hibernate.ddl-auto=update
   ```

4. Lancez l'application avec la commande suivante :
   ```bash
   mvn spring-boot:run
   ```

   L'API sera disponible à l'adresse suivante : `http://localhost:8080`.

## Endpoints de l'API

Voici quelques endpoints principaux disponibles dans l'API :

### 1. Créer une maintenance

**POST** `/api/maintenances`

- **Corps de la requête :**
  ```json
  {
    "date": "2025-03-20",
    "type": "PREVENTIVE",
    "statut": "PLANIFIEE",
    "avionId": 4,
    "ingenieurId": 3
  }
  ```

- **Réponse :**
  ```json
  {
    "id": 17,
    "date": "2025-03-20",
    "type": "PREVENTIVE",
    "statut": "PLANIFIEE",
    "avionId": 4,
    "ingenieurId": 3,
    "_links": {
      "self": {
        "href": "http://localhost:8080/api/maintenances/17"
      }
    }
  }
  ```

### 2. Récupérer une maintenance par ID

**GET** `/api/maintenances/{id}`

### 3. Mettre à jour une maintenance

**PUT** `/api/maintenances/{id}`

### 4. Supprimer une maintenance

**DELETE** `/api/maintenances/{id}`

## Tests

L'application inclut des tests unitaires pour chaque service et couche de l'API. Vous pouvez exécuter les tests en utilisant Maven :

```bash
mvn test
```

## Documentation API

La documentation complète de l'API est générée à l'aide de **Swagger/OpenAPI** et est accessible à l'adresse suivante :

```
http://localhost:8080/swagger-ui.html
```

## Architecture

L'architecture du projet suit le modèle **MVC** (Model-View-Controller) pour assurer une séparation claire des responsabilités :

- **Model** : Entités et logique métier, comme `Maintenance`, `Avion`, `Ingenieur`.
- **View** : La présentation est réalisée via des DTO (Data Transfer Objects).
- **Controller** : Contrôleurs REST pour gérer les requêtes HTTP et exposer les ressources.

## CI/CD et DevOps

Ce projet est configuré pour le déploiement sur un environnement **Docker** avec une intégration **CI/CD** via **GitLab**. Les étapes suivantes permettent d'automatiser les tests et le déploiement de l'application sur un serveur distant.

## Contributions

Si vous souhaitez contribuer à ce projet, veuillez suivre ces étapes :

1. Fork le projet.
2. Créez une branche pour vos modifications (`git checkout -b feature/ma-nouvelle-feature`).
3. Committez vos modifications (`git commit -am 'Ajout d'une nouvelle fonctionnalité'`).
4. Poussez la branche (`git push origin feature/ma-nouvelle-feature`).
5. Créez une Pull Request.

## Auteurs

- **Said Kazmi** : Développeur principal.
