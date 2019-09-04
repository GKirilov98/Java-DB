package gamestore.services;

import gamestore.domain.Messages;
import gamestore.domain.dtos.GameAddDto;
import gamestore.domain.entities.Game;
import gamestore.repositories.GameRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class GameServiceImpl implements GameService {

    private final GameRepository gameRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public GameServiceImpl(GameRepository gameRepository) {
        this.gameRepository = gameRepository;
        modelMapper = new ModelMapper();
    }


    @Override
    public String addGame(GameAddDto gameAddDto) {
        String vm = validateMessageAddGame(gameAddDto);
        if (vm != null) {
            return vm;
        }

        Game gameNew = this.modelMapper.map(gameAddDto, Game.class);
        this.gameRepository.saveAndFlush(gameNew);

        return String.format(Messages.SUCCESS_GAME_ADDED, gameNew.getTitle());
    }

    @Override
    public String editGame(String[] tokens) {
        int id = Integer.parseInt(tokens[1]);
        Game game = this.gameRepository.findById(id).orElse(null);
        if (game == null) {
            return String.format(Messages.GAME_DOESNT_EXIST, id);
        }

        for (int i = 2; i < tokens.length; i++) {
            String[] command = tokens[i].split("=");
            switch (command[0]) {
                case "title":
                    game.setTitle(command[1]);
                    break;
                case "price":
                    if (game.getPrice().compareTo(BigDecimal.ZERO) < 0
                            || game.getPrice().scale() != 2) {
                        return Messages.PRICE_CANNOT_BE_NULL;
                    }

                    game.setPrice(new BigDecimal(command[1]));
                    break;
                case "size":
                    int floadLength = 0;
                    try {
                        floadLength = game.getSize().toString().split("\\.")[1].length();
                    } catch (IndexOutOfBoundsException ex) {
                        ex.getMessage();
                        return Messages.SIZE_CANNOT_BE_NULL;
                    }

                    if (game.getSize() < 0 || floadLength != 1) {
                        return Messages.SIZE_CANNOT_BE_NULL;
                    }
                    game.setSize(Double.parseDouble(command[1]));
                    break;
                case "thumbnail":
                    game.setImageThumbnail(command[1]);
                    break;
                case "trailer":
                    game.setTrailer(command[1]);
                    break;
                case "description":
                    game.setDescription(command[1]);
                    break;
                case "date":
                    game.setReleaseDate(LocalDate.parse(command[1], DateTimeFormatter.ofPattern("dd-M-yyyy")));
                    break;
                default:
                    System.out.println("Invalid command");
            }
        }


        this.gameRepository.saveAndFlush(game);
        return String.format(Messages.EDITED_GAME, game.getTitle());
    }

    private String validateMessageAddGame(GameAddDto gameAddDto) {
        StringBuilder sb = new StringBuilder();
        Validator validator = Validation
                .byDefaultProvider()
                .configure()
                .buildValidatorFactory()
                .getValidator();
        Set<ConstraintViolation<GameAddDto>> validations = validator.validate(gameAddDto);
        if (validations.size() > 0) {
            for (ConstraintViolation<GameAddDto> validation : validations) {
                sb.append(validation.getMessage()).append(System.lineSeparator());
            }
            return sb.toString().trim();
        }


        int floadLength = 0;
        try {
            floadLength = gameAddDto.getSize().toString().split("\\.")[1].length();
        } catch (IndexOutOfBoundsException ex) {
            ex.getMessage();
            return Messages.SIZE_CANNOT_BE_NULL;
        }

        if (gameAddDto.getPrice().compareTo(BigDecimal.ZERO) < 0
                || gameAddDto.getPrice().scale() != 2) {
            return Messages.PRICE_CANNOT_BE_NULL;
        } else if (gameAddDto.getSize() < 0 || floadLength != 1) {
            return Messages.SIZE_CANNOT_BE_NULL;
        }

        Game gameExist = this.gameRepository.findByTitle(gameAddDto.getTitle()).orElse(null);
        if (gameExist != null) {
            return String.format(Messages.GAME_ALREADY_EXIST, gameExist.getTitle());
        }

        return null;
    }

    @Override
    public String deleteGame(int id) {
        Game game = this.gameRepository.findById(id).orElse(null);
        if (game == null) {
            return Messages.GAME_DOESNT_EXIST;
        } else {
            String title = game.getTitle();
            this.gameRepository.delete(game);
            return String.format(Messages.DELETED_GAME, title);
        }
    }

    @Override
    public List<String> getAllGames() {
        return this.gameRepository.findAll()
                .stream()
                .map(g -> String.format("%s %.2f", g.getTitle(), g.getPrice()))
                .collect(Collectors.toList());
    }

    @Override
    public String getDetailGame(String title) {
        Game game = this.gameRepository.findByTitle(title).orElse(null);
        if (game == null) {
            return Messages.GAME_DOESNT_EXIST;
        }

        return String.format(
                "Title: %s\n" +
                        "Price: %.2f \n" +
                        "Description: %s \n" +
                        "Release date: %s", game.getTitle(),
                game.getPrice(), game.getDescription(), game.getReleaseDate());

    }

    @Override
    public List<String> getOwnedGamesDetail(String email) {
        return this.gameRepository.findAllByOwner(email)
                .stream()
                .map(g -> String.format("%s\n", g.getTitle()))
                .collect(Collectors.toList());
    }
}
