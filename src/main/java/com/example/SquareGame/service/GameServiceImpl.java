package com.example.SquareGame.service;

import org.springframework.stereotype.Service;
import com.example.SquareGame.model.Game;
import com.example.SquareGame.model.GameCreationParams;
import com.example.SquareGame.model.GameService;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {
    
    private Map<Long, Game> games = new HashMap<>();
    private Long gameIdCounter = 1L;
    
    /**
     * Crée une nouvelle partie selon le type de jeu demandé
     * Supporte deux types : "SQUARE" et "MEMORY"
     */
    @Override
    public String createGame(GameCreationParams params) {
        Long gameId = gameIdCounter++;
        
        Game newGame = new Game();
        newGame.setId(gameId);
        newGame.setType(params.getGameType());
        newGame.setNumberOfPlayers(params.getNumberOfPlayers());
        newGame.setBoardSize(params.getBoardSize());
        newGame.setCreatedAt(LocalDateTime.now());
        newGame.setState("IN_PROGRESS");
        
        // Initialiser selon le type de jeu
        if ("SQUARE".equalsIgnoreCase(params.getGameType())) {
            newGame.setState("IN_PROGRESS - SQUARE GAME");
        } else if ("MEMORY".equalsIgnoreCase(params.getGameType())) {
            newGame.setState("IN_PROGRESS - MEMORY GAME");
        } else {
            return "Type de jeu non supporté: " + params.getGameType();
        }
        
        games.put(gameId, newGame);
        
        return "Partie " + gameId + " créée ! Type: " + params.getGameType() 
               + ", Joueurs: " + params.getNumberOfPlayers() 
               + ", Plateau: " + params.getBoardSize() + "x" + params.getBoardSize();
    }
    
    /**
     * Récupère l'état d'une partie
     */
    @Override
    public String getGameState(Long gameId) {
        Game game = games.get(gameId);
        
        if (game == null) {
            return "Partie " + gameId + " non trouvée";
        }
        
        return "État: " + game.getState() + " | Type: " + game.getType() 
               + " | Joueurs: " + game.getNumberOfPlayers();
    }
    
    /**
     * Récupère l'objet Game complet
     */
    @Override
    public Game getGame(Long gameId) {
        return games.get(gameId);
    }
}
