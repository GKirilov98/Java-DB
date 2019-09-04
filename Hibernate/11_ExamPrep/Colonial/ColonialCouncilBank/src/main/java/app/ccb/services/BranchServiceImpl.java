package app.ccb.services;

import app.ccb.domain.dtos.braches.BrachSeedDto;
import app.ccb.domain.entities.Branch;
import app.ccb.repositories.BranchRepository;
import app.ccb.util.FileUtil;
import app.ccb.util.ValidatorUtil;
import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class BranchServiceImpl implements BranchService {

    private final FileUtil fileUtil;
    private final Gson gson;
    private final ValidatorUtil validatorUtil;
    private final ModelMapper modelMapper;

    private final BranchRepository  branchRepository;

    public BranchServiceImpl(FileUtil fileUtil, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper, BranchRepository branchRepository) {
        this.fileUtil = fileUtil;
        this.gson = gson;
        this.validatorUtil = validatorUtil;
        this.modelMapper = modelMapper;
        this.branchRepository = branchRepository;
    }

    @Override
    public Boolean branchesAreImported() {
        return this.branchRepository.count() != 0;
    }

    @Override
    public String readBranchesJsonFile() {
        final String READ_PATH_FILE = ".\\src\\main\\resources\\files\\json\\branches.json";
        return this.fileUtil.fileReader(READ_PATH_FILE);
    }

    @Override
    public String importBranches(String branchesJson) {
        StringBuilder errors = new StringBuilder();

        BrachSeedDto[] brachSeedDtos = this.gson.fromJson(branchesJson, BrachSeedDto[].class);
        for (BrachSeedDto seedDto : brachSeedDtos) {
            if (!this.validatorUtil.isValid(seedDto)) {
                this.validatorUtil
                        .violations(seedDto)
                        .forEach(v -> errors.append(v.getMessage()).append(System.lineSeparator()));

                continue;
            }


            Branch branch = this.modelMapper.map(seedDto, Branch.class);
            this.branchRepository.saveAndFlush(branch);
        }

        return errors.toString().trim();
    }
}
