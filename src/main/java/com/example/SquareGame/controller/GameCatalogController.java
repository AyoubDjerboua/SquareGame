package com.example.SquareGame.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.SquareGame.model.GameCatalog;

import java.util.Collection;

@RestController
@RequestMapping("/api/games")
public class GameCatalogController {
    
    private final GameCatalog gameCatalog;
    
    @Autowired
    public GameCatalogController(GameCatalog gameCatalog) {
        this.gameCatalog = gameCatalog;
    }
    
    @GetMapping
    public Collection<String> getAvailableGames() {
        return gameCatalog.getAvailableGames();
    }
}
