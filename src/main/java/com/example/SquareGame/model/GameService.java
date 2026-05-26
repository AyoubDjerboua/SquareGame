package com.example.SquareGame.model;

public interface GameService {
    
    /**
     * Crée une nouvelle partie avec les paramètres fournis
     */
    String createGame(GameCreationParams params);
    
    /**
     * Récupère l'état d'une partie par son ID
     */
    String getGameState(Long gameId);
    
    /**
     * Récupère les détails complets d'une partie
     */
    Game getGame(Long gameId);
}
