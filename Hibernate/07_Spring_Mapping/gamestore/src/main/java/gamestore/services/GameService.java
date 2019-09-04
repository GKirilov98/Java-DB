package gamestore.services;

import gamestore.domain.dtos.GameAddDto;

import java.util.List;

public interface GameService {
    String addGame(GameAddDto gameAddDto);

    String editGame(String[] tokens);

    String deleteGame(int id);

    List<String> getAllGames();

    String getDetailGame(String token);

    List<String> getOwnedGamesDetail(String email);
}
