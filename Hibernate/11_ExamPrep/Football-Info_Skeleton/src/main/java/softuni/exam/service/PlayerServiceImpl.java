package softuni.exam.service;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.dtos.player.PlayerSeedDto;
import softuni.exam.domain.entities.*;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.PlayerRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.Objects;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {
    private final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\json\\players.json";

    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final PictureRepository pictureRepository;

    public PlayerServiceImpl(FileUtil fileUtil, ValidatorUtil validatorUtil, ModelMapper modelMapper, Gson gson, PlayerRepository playerRepository, TeamRepository teamRepository, PictureRepository pictureRepository) {
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
        this.pictureRepository = pictureRepository;
    }

    @Override
    public String importPlayers() {
        StringBuilder sb = new StringBuilder();

        String content = null;
        try {
            content = this.fileUtil.readFile(READ_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        PlayerSeedDto[] playerSeedDtos = this.gson.fromJson(content, PlayerSeedDto[].class);
        for (PlayerSeedDto seedDto : playerSeedDtos) {
            if (!validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e ->
                                        sb.append("Invalid Player!")
                                                .append(System.lineSeparator())
                                );
                continue;
            }

            Player player = this.modelMapper.map(seedDto, Player.class);
            Team team = this.teamRepository.findTeamByName(seedDto.getTeam().getName()).orElse(null);
            Picture picture = this.pictureRepository.findPictureByUrl(seedDto.getPicture().getUrl()).orElse(null);
            if (team == null || picture == null) {
                continue;
            }

            try {
                player.setPosition(Position.valueOf(seedDto.getPosition().toUpperCase()));
            } catch (IllegalArgumentException e) {

                continue;
            }

            player.setPicture(picture);
            player.setTeam(team);
            this.playerRepository.saveAndFlush(player);
            team.getPlayers().add(player);
            this.teamRepository.saveAndFlush(team);
            picture.getPlayers().add(player);
            this.pictureRepository.saveAndFlush(picture);

            sb.append(String.format("Successfully imported player - %s %s",
                    player.getFirstName(), player.getLastName()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return playerRepository.count() != 0;
    }

    @Override
    public String readPlayersJsonFile() {

        try {
            return this.fileUtil.readFile(READ_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String exportPlayersWhereSalaryBiggerThan() {
        StringBuilder sb = new StringBuilder();
        this.playerRepository.findPlayersBySalaryGreaterThan(BigDecimal.valueOf(100000))
                .stream()
                .sorted((a, b) -> b.getSalary().compareTo(a.getSalary()))
                .forEach( p -> sb.append(String.format(
                        "Player name: %s %s \n" +
                        "\tNumber: %d\n" +
                        "\tSalary: %.2f\n" +
                        "\tTeam: %s\n",
                        p.getFirstName(), p.getLastName(),
                        p.getNumber(), p.getSalary(),
                        p.getTeam().getName())));

        return sb.toString().trim();
    }

    @Override
    public String exportPlayersInATeam() {
        StringBuilder sb = new StringBuilder();

        Team team = this.teamRepository.findTeamByName("North Hub").orElse(null);
        sb.append("Team: North Hub").append(System.lineSeparator());
        Objects.requireNonNull(team).getPlayers()
                .stream()
                .sorted(Comparator.comparingInt(BaseEntity::getId))
                .forEach(p ->
                sb.append(String.format("\tPlayer name: %s %s - %s\n" + "\tNumber: %s\n",
                        p.getFirstName(), p.getLastName(), p.getPosition(), p.getNumber()))
        );

        return sb.toString().trim();
    }
}
