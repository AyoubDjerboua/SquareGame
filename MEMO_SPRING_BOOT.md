# MÉMO Spring Boot - Notions Apprises
## SquareGame Project

---

## 1. **Initialisation de l'application**

### @SpringBootApplication
- Annotation magique qui lance tout Spring Boot
- Combine 3 annotations : `@Configuration`, `@EnableAutoConfiguration`, `@ComponentScan`
- Permet à `SpringApplication.run()` de démarrer l'application
- **Exemple** : `public class SquareGameApplication`

### SpringApplication.run()
- Lance le serveur Web (Tomcat par défaut sur port 8080)
- Initialise toutes les beans et les dépendances automatiquement

---

## 2. **Architecture MVC avec REST**

### @RestController
- Crée un contrôleur qui retourne du JSON/données (pas du HTML)
- Combine `@Controller` + `@ResponseBody`
- Les méthodes retournent directement des données, pas des vues

### @GetMapping
- Route HTTP GET vers une méthode
- `@GetMapping("/heartbeat")` = endpoint accessible via `GET /heartbeat`
- Alternative : `@RequestMapping(method = RequestMethod.GET)`

### Exemple appliqué
```java
@RestController
public class HeartbeatController {
    @GetMapping("/heartbeat")
    public int getHeartbeat() { ... }
}
```

---

## 3. **Injection de Dépendances (DI)**

### Le problème résolu
Sans Spring : créer un objet à la main = couplage fort
```java
HeartbeatSensor sensor = new RandomHeartbeat();  // ❌ Couplé
```

Avec Spring : laisser Spring créer et injecter
```java
@Autowired
private HeartbeatSensor sensor;  // ✅ Découplé
```

### @Autowired
- Demande à Spring d'injecter automatiquement une bean compatible
- Cherche une classe qui implémente l'interface
- Rend le code flexible et testable

### @Service
- Marque une classe comme bean réutilisable
- Spring la crée une fois au démarrage
- La rend disponible pour injection via `@Autowired`
- **Exemple** : `@Service public class RandomHeartbeat implements HeartbeatSensor`

---

## 4. **Interfaces et Implémentations**

### Pattern utilisé
1. **Interface** (contrat) : définit les méthodes
   ```java
   public interface HeartbeatSensor {
       int get();
   }
   ```

2. **Implémentation** (réalisation) : concrète et annoté `@Service`
   ```java
   @Service
   public class RandomHeartbeat implements HeartbeatSensor {
       @Override
       public int get() { return random.nextInt(100); }
   }
   ```

3. **Contrôleur** : utilise l'interface, pas l'implémentation
   ```java
   @Autowired
   private HeartbeatSensor sensor;  // Injecter interface, pas RandomHeartbeat !
   ```

### Avantage
- Changer d'implémentation sans toucher au contrôleur
- Facilite les tests (mock objects)

---

## 5. **Package Structure (Maven)**

```
src/main/java/com/example/SquareGame/
├── SquareGameApplication.java   (point d'entrée)
├── HeartbeatController.java     (REST endpoint)
├── HeartbeatSensor.java         (interface)
├── RandomHeartbeat.java         (implémentation @Service)
├── GameCatalog.java             (interface)
├── GameCatalogImpl.java          (implémentation @Service)
└── GameCatalogController.java   (REST endpoint)

src/main/resources/
├── application.properties        (configuration de l'app)
```

---

## 6. **Configuration Maven**

### pom.xml
- Fichier de configuration Maven
- Déclare les dépendances nécessaires
- Définit la version de l'application

### Dépendances Spring essentielles
```xml
<!-- Web MVC + REST -->
<artifactId>spring-boot-starter-web</artifactId>

<!-- JPA pour base de données -->
<artifactId>spring-boot-starter-data-jpa</artifactId>

<!-- H2 Database (en mémoire pour développement) -->
<artifactId>h2</artifactId>
```

### Dépendance personnalisée
```xml
<!-- Moteur de jeux fourni -->
<groupId>fr.le-campus-numerique.square-games</groupId>
<artifactId>engine</artifactId>
<version>1.0-SNAPSHOT</version>
```

---

## 7. **Collections et Génériques**

### Collection<String>
- Interface générique pour une liste de données
- Plus flexible que `List`
- Permet retourner différents types (List, Set, etc.)

### List.of()
- Crée une liste immuable ("read-only")
- Plus performant que `new ArrayList<>()`
- Idéal pour des listes statiques

```java
Collection<String> games = List.of("TicTacToe", "Connect4");
```

---

## 8. **Flow d'un appel HTTP**

1. **Client** : `GET http://localhost:8080/heartbeat`
2. **Spring** : détecte la route `@GetMapping("/heartbeat")`
3. **Contrôleur** : appelle le service via `@Autowired`
4. **Service** : exécute la logique métier
5. **Réponse** : retourne JSON au client

```
[Client Request]
       ↓
[Routing via @GetMapping]
       ↓
[Controller invoque @Autowired]
       ↓
[Service exécute logique]
       ↓
[Response JSON au client]
```

---

## 9. **Concepts clés à retenir**

| Concept | Rôle |
|---------|------|
| **@SpringBootApplication** | Lance l'app |
| **@RestController** | Crée endpoints REST |
| **@GetMapping** | Route HTTP GET |
| **@Service** | Bean réutilisable |
| **@Autowired** | Injection de dépendance |
| **Interface** | Contrat (flexibilité) |
| **Implémentation** | Réalisation concrète |

---

## 10. **Prochaines notions à explorer**
- `@PostMapping` (créer des données)
- `@PutMapping` / `@DeleteMapping` (modifier/supprimer)
- `@RequestBody` (recevoir du JSON)
- `@PathVariable` (paramètres dans l'URL)
- `@Repository` (accès base de données)
- Gestion des erreurs (Exception handling)
- Tests unitaires avec Spring Test

---

**Date** : 21 mai 2026  
**Niveau** : Débutant Spring Boot  
**Projet** : SquareGame (Itération 1.2)
