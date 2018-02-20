package com.frank.api.controller.setup;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.setup.Branch;
import com.frank.api.service.setup.BranchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin(origins = Config.ORIGINS, maxAge = Config.MAX_AGE)
@RestController
@RequestMapping("/api")
public class BranchController extends RestBaseController {

    @Autowired
    private BranchService branchService;
    
    @GetMapping("/branches")
    public List<Branch> getAllBranches() {
        return branchService.getAllBranches();
    }
    
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/branches/paginated")
    public Page<Branch> getPaginatedBranches(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return branchService.getPaginatedBranches(page,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/branches")
    public Page<Branch> createBranch(@Valid @RequestBody Branch branch, @RequestParam("perPage") Integer perPage) {
        branchService.createBranch(branch);
        return branchService.getPaginatedBranches(0,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/branches/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable(value = "id") Long id) {
        Branch branch = branchService.getBranchById(id);
        if (branch == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(branch);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/branches/{id}")
    public Page<Branch> updateBranch(@PathVariable(value = "id") Long id, @Valid @RequestBody Branch branchDetails,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Branch branch = branchService.getBranchById(id);
        branch.setName(branchDetails.getName());
        branch.setCode(branchDetails.getCode());
        branch.setHeadquarter(branchDetails.getHeadquarter());
        branch.setShop(branchDetails.getShop());
        branch.setCity(branchDetails.getCity());
        branchService.updateBranch(branch);
        return branchService.getPaginatedBranches(page,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/branches/{id}")
    public Page<Branch> deleteBranch(@PathVariable(value = "id") Long id,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Branch branch = branchService.getBranchById(id);
        branchService.deleteBranch(branch);
        return branchService.getPaginatedBranches(page,perPage);
    }

}