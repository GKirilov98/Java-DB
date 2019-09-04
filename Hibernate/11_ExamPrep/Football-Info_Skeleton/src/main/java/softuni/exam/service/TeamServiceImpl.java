package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.entities.Team;
import softuni.exam.domain.dtos.team.TeamSeedDto;
import softuni.exam.domain.dtos.team.TeamSeedRootDto;
import softuni.exam.repository.PictureRepository;
import softuni.exam.repository.TeamRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
public class TeamServiceImpl implements TeamService {

    private final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\xml\\teams.xml";

    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;

    private final PictureRepository pictureRepository;
    private final TeamRepository teamRepository;

    public TeamServiceImpl(FileUtil fileUtil, ValidatorUtil validatorUtil, XmlParser xmlParser, ModelMapper modelMapper, PictureRepository pictureRepository, TeamRepository teamRepository) {
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.pictureRepository = pictureRepository;
        this.teamRepository = teamRepository;
    }

    @Override
    public String importTeams() {
        StringBuilder sb = new StringBuilder();
        TeamSeedRootDto teamSeedRootDto = this.xmlParser
                .objectFromFile(TeamSeedRootDto.class, READ_FILE_PATH);
        for (TeamSeedDto seedDto : teamSeedRootDto.getTeamSeedDtos()) {
            if (!validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> sb.append("Invalid team!")
                                .append(System.lineSeparator()));
                continue;
            }

            Picture picture = pictureRepository
                    .findPictureByUrl(seedDto.getPicturesSeedDto().getUrl()).orElse(null);
            if (picture == null) {
                continue;
            }

            Team team = this.modelMapper.map(seedDto, Team.class);
            team.setPicture(picture);
            this.teamRepository.saveAndFlush(team);

            picture.getTeams().add(team);
            this.teamRepository.saveAndFlush(team);

            sb.append(String.format("Successfully imported team - %s", team.getName()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.teamRepository.count() != 0;
    }

    @Override
    public String readTeamsXmlFile() {
        String content = null;
        try {
            content = this.fileUtil.readFile(READ_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
