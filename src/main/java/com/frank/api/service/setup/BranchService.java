package com.frank.api.service.setup;

import com.frank.api.model.setup.Branch;
import com.frank.api.repository.setup.BranchRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class BranchService {

    @Autowired
    private BranchRepository branchRepository;

    public Branch createBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public Branch getBranchById(Long id){
        return branchRepository.findOne(id);
    }

    public Branch updateBranch(Branch branch) {
        return branchRepository.save(branch);
    }

    public List<Branch> getAllBranches() {
        return branchRepository.findAll();
    }

    public Page<Branch> getPaginatedBranches(Integer page, Integer perPage){
        return branchRepository.findAll(new PageRequest(page,perPage));
    }

    public void deleteBranch(Branch branch){
        branchRepository.delete(branch);
    }
}
