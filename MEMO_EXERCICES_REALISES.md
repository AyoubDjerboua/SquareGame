# MÉMO - Exercices Réalisés
## SquareGame Project - Iteration 1

---

## 📋 Résumé Global

Nous avons construit une **API REST complète** pour gérer des parties de jeux avec une architecture **3 couches** :
- **Controller** → Gère les requêtes HTTP
- **Service** → Contient la logique métier
- **Model** → Classes de données

---

## 1️⃣ **Comprendre Spring Boot**

### Ce qu'on a appris :
- Spring Boot = Spring sans chichi, configuration automatique
- `@SpringBootApplication` = point d'entrée
- Spring gère la **création d'objets** et l'**injection de dépendances**
- `@RestController` = endpoints qui retournent du JSON

### Concepts clés :
```
Framework Spring Boot
  ├─ Crée automatiquement les objets (beans)
  ├─ Les injecte via @Autowired ou constructeur
  └─ Lance un serveur Tomcat tout prêt
```

---

## 2️⃣ **Organisation des fichiers (Architecture en couches)**

### Structure adoptée :
```
com/example/SquareGame/
├── SquareGameApplication.java          ← Point d'entrée
├── controller/
│   ├── GameController.java             ✅ CRÉÉ
│   ├── GameCatalogController.java      (existant)
│   └── HeartbeatController.java        (existant)
├── service/
│   └── GameServiceImpl.java             ✅ CRÉÉ
└── model/
    ├── Game.java                        ✅ CRÉÉ
    ├── GameCreationParams.java          ✅ CRÉÉ
    ├── GameService.java                 ✅ CRÉÉ (interface)
    ├── GameCatalog.java                 (existant)
    ├── GameCatalogImpl.java              (existant)
    └── ...
```

### Pourquoi cette organisation ?
| Couche | Responsabilité |
|--------|----------------|
| **Controller** | Reçoit/envoie les requêtes HTTP |
| **Service** | Logique métier (créer, gérer les parties) |
| **Model** | Classes de données (entités, DTOs) |

---

## 3️⃣ **Concepts : Endpoint**

### Définition
Un **endpoint** = une URL accessible qui :
1. Reçoit une requête HTTP (GET, POST, etc.)
2. Exécute du code 
3. Retourne une réponse

### Analogie
```
Restaurant
  ├─ Adresse = URL de base (/games)
  ├─ Serveur = Controller
  ├─ Action = Endpoint (/games/list)
  └─ Réponse = JSON retourné
```

### Types d'endpoints créés
```
GET  /games/{gameId}              → Récupère l'état d'une partie
GET  /games/{gameId}/details      → Récupère les détails complets
POST /games                        → Crée une nouvelle partie
```

---

## 4️⃣ **Classe DTO : GameCreationParams**

### C'est quoi ?
**DTO** = Data Transfer Object = objet qui reçoit les données du client

### Pourquoi ?
Spring convertit automatiquement le JSON en objet Java :
```json
{
  "gameType": "SQUARE",
  "numberOfPlayers": 2,
  "boardSize": 8
}
```
↓ Spring ↓ (grâce à @RequestBody)
```java
GameCreationParams {
  gameType = "SQUARE",
  numberOfPlayers = 2,
  boardSize = 8
}
```

### Structure
```java
public class GameCreationParams {
    private String gameType;           // "SQUARE" ou "MEMORY"
    private Integer numberOfPlayers;   // 2, 4, etc.
    private Integer boardSize;         // 8, 16, etc.
    
    // Constructeur vide (nécessaire)
    // Getters/Setters
}
```

---

## 5️⃣ **Classe métier : Game**

### Représente quoi ?
Une **partie en cours** avec tous ses attributs

### Attributs
```java
private Long id;                    // ID unique (1, 2, 3...)
private String type;                // Type de jeu ("SQUARE", "MEMORY")
private Integer numberOfPlayers;    // Nombre de joueurs
private Integer boardSize;          // Taille du plateau
private String state;               // État ("IN_PROGRESS", "FINISHED")
private LocalDateTime createdAt;    // Quand elle a été créée
```

### Exemple d'une instance
```
Game {
  id: 1
  type: "SQUARE"
  numberOfPlayers: 2
  boardSize: 8
  state: "IN_PROGRESS - SQUARE GAME"
  createdAt: 2026-05-26 14:30:00
}
```

---

## 6️⃣ **Interface et Service : GameService & GameServiceImpl**

### Interface GameService
Définit les **contrats** (ce que le service promet de faire) :
```java
public interface GameService {
    String createGame(GameCreationParams params);    // Crée une partie
    String getGameState(Long gameId);                // Récupère l'état
    Game getGame(Long gameId);                       // Récupère la partie complète
}
```

### Implémentation GameServiceImpl
Contient la **logique réelle** :

```java
@Service  // ← Spring crée automatiquement cette instance
public class GameServiceImpl implements GameService {
    
    private Map<Long, Game> games = new HashMap<>();  // Stockage en mémoire
    private Long gameIdCounter = 1L;
    
    @Override
    public String createGame(GameCreationParams params) {
        Long gameId = gameIdCounter++;
        Game newGame = new Game();
        newGame.setId(gameId);
        newGame.setType(params.getGameType());
        newGame.setNumberOfPlayers(params.getNumberOfPlayers());
        newGame.setBoardSize(params.getBoardSize());
        newGame.setCreatedAt(LocalDateTime.now());
        
        // Supporte 2 types de jeux
        if ("SQUARE".equalsIgnoreCase(params.getGameType())) {
            newGame.setState("IN_PROGRESS - SQUARE GAME");
        } else if ("MEMORY".equalsIgnoreCase(params.getGameType())) {
            newGame.setState("IN_PROGRESS - MEMORY GAME");
        }
        
        games.put(gameId, newGame);
        return "Partie " + gameId + " créée ! Type: " + params.getGameType();
    }
    
    @Override
    public String getGameState(Long gameId) {
        Game game = games.get(gameId);
        if (game == null) return "Partie non trouvée";
        return "État: " + game.getState();
    }
}
```

### Deux types de jeux supportés
| Type | Détail |
|------|--------|
| **SQUARE** | Plateau carré NxN - joueur trouve les carrés |
| **MEMORY** | Plateau carré NxN - joueur retourne des cartes |

---

## 7️⃣ **Controller : GameController**

### Injection par constructeur (meilleure pratique)
```java
@RestController
@RequestMapping("/games")
public class GameController {
    
    private final GameService gameService;
    
    // Constructeur - Spring injecte automatiquement GameService
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
```

### Les 3 endpoints

#### 1️⃣ POST /games → Crée une partie
```java
@PostMapping
public String createGame(@RequestBody GameCreationParams params) {
    return gameService.createGame(params);
}
```

**Utilisation** :
```
POST http://localhost:8080/games
Body: {
  "gameType": "SQUARE",
  "numberOfPlayers": 2,
  "boardSize": 8
}

Response: "Partie 1 créée ! Type: SQUARE, Joueurs: 2, Plateau: 8x8"
```

#### 2️⃣ GET /games/{gameId} → État de la partie
```java
@GetMapping("/{gameId}")
public String getGameState(@PathVariable Long gameId) {
    return gameService.getGameState(gameId);
}
```

**Utilisation** :
```
GET http://localhost:8080/games/1

Response: "État: IN_PROGRESS - SQUARE GAME | Type: SQUARE | Joueurs: 2"
```

#### 3️⃣ GET /games/{gameId}/details → Détails complets
```java
@GetMapping("/{gameId}/details")
public Game getGameDetails(@PathVariable Long gameId) {
    return gameService.getGame(gameId);
}
```

**Utilisation** :
```
GET http://localhost:8080/games/1/details

Response: {
  "id": 1,
  "type": "SQUARE",
  "numberOfPlayers": 2,
  "boardSize": 8,
  "state": "IN_PROGRESS - SQUARE GAME",
  "createdAt": "2026-05-26T14:30:00"
}
```

---

## 🎯 **Points importants à retenir**

### ✅ Bonnes pratiques appliquées
1. **Injection par constructeur** (vs `@Autowired` sur attribut)
   - Plus immutable
   - Plus facile à tester
   - Plus explicite

2. **Séparation des responsabilités**
   - Controller = HTTP
   - Service = Logique métier
   - Model = Données

3. **DTO pour les paramètres**
   - Reço les données du client
   - Spring convertit JSON → Objet

4. **Interface pour le service**
   - Contrat clair
   - Facile à tester (mocker l'interface)
   - Flexibilité pour futurs changements

### ❌ À éviter
- Mettre la logique dans le Controller
- Utiliser `@Autowired` sur les attributs
- Oublier le constructeur vide dans les DTOs
- Mélanger HTTP et logique métier

---

## 🧪 **Comment tester**

### Avec Postman ou curl

**1. Créer une partie SQUARE**
```bash
curl -X POST http://localhost:8080/games \
  -H "Content-Type: application/json" \
  -d '{"gameType":"SQUARE","numberOfPlayers":2,"boardSize":8}'
```

**2. Récupérer l'état**
```bash
curl http://localhost:8080/games/1
```

**3. Récupérer les détails**
```bash
curl http://localhost:8080/games/1/details
```

**4. Créer une partie MEMORY**
```bash
curl -X POST http://localhost:8080/games \
  -H "Content-Type: application/json" \
  -d '{"gameType":"MEMORY","numberOfPlayers":4,"boardSize":16}'
```

---

## 📁 **Fichiers créés/modifiés**

| Fichier | Statut | Rôle |
|---------|--------|------|
| `GameCreationParams.java` | ✅ CRÉÉ | DTO pour recevoir les paramètres |
| `Game.java` | ✅ CRÉÉ | Entité représentant une partie |
| `GameService.java` | ✅ CRÉÉ | Interface du service |
| `GameServiceImpl.java` | ✅ CRÉÉ | Implémentation du service |
| `GameController.java` | ✅ MODIFIÉ | Endpoints REST |

---

## 🚀 **Prochaines étapes possibles**

1. Ajouter une **base de données** (H2, MySQL)
   - Remplacer `Map` par une vraie persistance

2. Ajouter des **exceptions personnalisées**
   - `GameNotFoundException` si partie inexistante

3. Ajouter des **validations**
   - `@Valid @RequestBody GameCreationParams`

4. Ajouter des **endpoints supplémentaires**
   - DELETE /games/{gameId} - Supprimer une partie
   - PUT /games/{gameId} - Mettre à jour une partie

5. Ajouter la **logique de jeu réelle**
   - Algorithme pour SQUARE Game
   - Algorithme pour MEMORY Game

6. Ajouter des **tests unitaires**
   - JUnit + Mockito

---

## 📝 **Résumé en une ligne**

**On a créé une API REST complète pour gérer des parties de jeux avec une architecture propre (3 couches), l'injection de dépendances, et le support de 2 types de jeux (SQUARE et MEMORY).**

✅ Fait !
