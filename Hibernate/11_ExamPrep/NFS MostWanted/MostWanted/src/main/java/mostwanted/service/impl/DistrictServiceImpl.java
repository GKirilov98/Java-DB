package mostwanted.service.impl;

import com.google.gson.Gson;
import mostwanted.domain.dtos.districts.DistrictSeedDto;
import mostwanted.domain.entities.District;
import mostwanted.repository.DistrictRepository;
import mostwanted.repository.TownRepository;
import mostwanted.service.DistrictService;
import mostwanted.util.FileUtil;
import mostwanted.util.ValidatorUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class DistrictServiceImpl implements DistrictService {
    private final FileUtil fileUtil;
    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    private final DistrictRepository districtRepository;
    private final TownRepository townRepository;

    public DistrictServiceImpl(FileUtil fileUtil, Gson gson, ModelMapper modelMapper, ValidatorUtil validatorUtil, DistrictRepository districtRepository, TownRepository townRepository) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
        this.districtRepository = districtRepository;
        this.townRepository = townRepository;
    }


    @Override
    public Boolean districtsAreImported() {
        return this.districtRepository.count() > 0;
    }

    @Override
    public String readDistrictsJsonFile() throws IOException {
        final String READ_PATH = ".\\src\\main\\resources\\files\\districts.json";
        return this.fileUtil.fileReader(READ_PATH);
    }

    @Override
    public String importDistricts(String districtsFileContent) {
        DistrictSeedDto[] districtSeedDtos = this.gson.fromJson(districtsFileContent, DistrictSeedDto[].class);
        for (DistrictSeedDto districtSeedDto : districtSeedDtos) {
            if (!this.validatorUtil.isValid(districtSeedDto)) {
                this.validatorUtil
                        .violations(districtSeedDto)
                        .forEach(v -> System.out.println(v.getMessage()));

                continue;
            }

            District district = this.modelMapper.map(districtSeedDto, District.class);
            district.setTown(this.townRepository.findTownByName(districtSeedDto.getTown()));
            this.districtRepository.saveAndFlush(district);
        }
        return "Success";
    }
}
