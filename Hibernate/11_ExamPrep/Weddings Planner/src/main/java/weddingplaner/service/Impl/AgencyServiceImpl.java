package weddingplaner.service.Impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import weddingplaner.domain.dto.agency.AgencySeedDto;
import weddingplaner.domain.entity.Agency;
import weddingplaner.repository.AgencyRepository;
import weddingplaner.service.AgencyService;
import weddingplaner.util.FileUtil;
import weddingplaner.util.ValidatorUtil;

@Service
public class AgencyServiceImpl implements AgencyService {
    private final String READ_FILE_PATH = ".\\src\\main\\resources\\files\\json\\input\\agencies.json";

    private final ValidatorUtil validatorUtil;
    private final FileUtil fileUtil;
    private final ModelMapper modelMapper;
    private final Gson gson;

    private final AgencyRepository agencyRepository;

    public AgencyServiceImpl(ValidatorUtil validatorUtil, FileUtil fileUtil, ModelMapper modelMapper, Gson gson, AgencyRepository agencyRepository) {
        this.validatorUtil = validatorUtil;
        this.fileUtil = fileUtil;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.agencyRepository = agencyRepository;
    }

    @Override
    public void seedAgency() {
        if (agencyRepository.count() != 0){
            return;
        }

        String content = this.fileUtil.fileReader(READ_FILE_PATH);
        AgencySeedDto[] agencySeedDtos = this.gson.fromJson(content, AgencySeedDto[].class);
        for (AgencySeedDto seedDto : agencySeedDtos) {
            if (!this.validatorUtil.isValid(seedDto)){
                this.validatorUtil.violations(seedDto)
                        .forEach( e -> System.out.println(e.getMessage()));
                continue;
            }

            try {
                Agency agency = this.modelMapper.map(seedDto, Agency.class);
                this.agencyRepository.saveAndFlush(agency);
            } catch (Exception e){
                System.out.println(e.getMessage());
            }

        }
    }
}
