package photography.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import photography.domain.dto.len.LenSeedDto;
import photography.domain.entity.Len;
import photography.repository.LenRepository;
import photography.service.LenService;
import photography.util.FileUtil;
import photography.util.ValidatorUtil;

@Service
public class LenServiceImpl implements LenService {
    private final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\input\\lenses.json";

    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;

    private final LenRepository lenRepository;

    public LenServiceImpl(FileUtil fileUtil, ModelMapper modelMapper, Gson gson, ValidatorUtil validatorUtil, LenRepository lenRepository) {
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.lenRepository = lenRepository;
    }

    @Override
    public void seedLens() {
        if (this.lenRepository.count() != 0){
            return;
        }

        String content = this.fileUtil.fileReader(READ_FILE_PATH);
        LenSeedDto[] lenSeedDtos = this.gson.fromJson(content, LenSeedDto[].class);
        for (LenSeedDto seedDto : lenSeedDtos) {
            if (!validatorUtil.isValid(seedDto)) {
                this.validatorUtil.violations(seedDto)
                        .forEach(e -> System.out.println(e.getMessage()));
                continue;
            }

            Len len = this.modelMapper.map(seedDto, Len.class);
            this.lenRepository.saveAndFlush(len);
        }
    }
}
