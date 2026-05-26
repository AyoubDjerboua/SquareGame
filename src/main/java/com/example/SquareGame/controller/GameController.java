package com.example.SquareGame.controller;

import org.springframework.web.bind.annotation.*;
import com.example.SquareGame.model.Game;
import com.example.SquareGame.model.GameCreationParams;
import com.example.SquareGame.model.GameService;

@RestController
@RequestMapping("/games")
public class GameController {
    
    private final GameService gameService;
    
    // Injection par constructeur (meilleure pratique)
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }
    
    /**
     * Crée une nouvelle partie
     * POST /games
     */
    @PostMapping
    public String createGame(@RequestBody GameCreationParams params) {
        return gameService.createGame(params);
    }
    
    /**
     * Récupère l'état d'une partie
     * GET /games/{gameId}
     */
    @GetMapping("/{gameId}")
    public String getGameState(@PathVariable Long gameId) {
        return gameService.getGameState(gameId);
    }
    
    /**
     * Récupère les détails complets d'une partie
     * GET /games/{gameId}/details
     */
    @GetMapping("/{gameId}/details")
    public Game getGameDetails(@PathVariable Long gameId) {
        Game game = gameService.getGame(gameId);
        
        if (game == null) {
            return null; // En production, utiliser une exception personnalisée
        }
        
        return game;
    }
}
