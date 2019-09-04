package softuni.exam.service;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.domain.entities.Picture;
import softuni.exam.domain.dtos.picture.PicturesSeedDto;
import softuni.exam.domain.dtos.picture.PicturesSeedRootDto;
import softuni.exam.repository.PictureRepository;
import softuni.exam.util.FileUtil;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.transaction.Transactional;
import java.io.IOException;

@Service
@Transactional
public class PictureServiceImpl implements PictureService {
    private final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\xml\\pictures.xml";

    private final FileUtil fileUtil;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    private final PictureRepository pictureRepository;

    public PictureServiceImpl(FileUtil fileUtil, ValidatorUtil validatorUtil, XmlParser xmlParser, ModelMapper modelMapper, PictureRepository pictureRepository) {
        this.fileUtil = fileUtil;
        this.validatorUtil = validatorUtil;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.pictureRepository = pictureRepository;
    }


    @Override
    public String importPictures() {
        StringBuilder sb = new StringBuilder();
        PicturesSeedRootDto picturesSeedRootDto = this.xmlParser
                .objectFromFile(PicturesSeedRootDto.class, READ_FILE_PATH);
        for (PicturesSeedDto seedDto : picturesSeedRootDto.getPicturesSeedDtos()) {
            if (!validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> sb.append("Invalid picture!")
                                .append(System.lineSeparator()));
                continue;
            }

            Picture picture = this.modelMapper.map(seedDto, Picture.class);
            this.pictureRepository.saveAndFlush(picture);
            sb.append(String.format("Successfully imported picture - %s", picture.getUrl()))
                    .append(System.lineSeparator());
        }

        return sb.toString().trim();
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() != 0;
    }

    @Override
    public String readPicturesXmlFile() {
        String content = null;
        try {
            content = this.fileUtil.readFile(READ_FILE_PATH);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }

}
