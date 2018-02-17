package com.frank.api.controller.setup;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.Branch;
import com.frank.api.service.BranchService;
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

    // Get All Branches
    @GetMapping("/branches")
    public List<Branch> getAllBranches() {
        return branchService.getAllBranches();
    }

    //Get all Branches - paginated
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/branches/paginated")
    public Page<Branch> getPaginatedBranches(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return branchService.getPaginatedBranches(page,perPage);
    }

    // Create a new Branch
    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping("/branches")
    public Branch createBranch(@Valid @RequestBody Branch branch) {
        return branchService.createBranch(branch);
    }

    // Get a Single Branch
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    @GetMapping("/branches/{id}")
    public ResponseEntity<Branch> getBranchById(@PathVariable(value = "id") Long branchId) {
        Branch branch = branchService.getBranchById(branchId);
        if (branch == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(branch);
    }

    // Update a Branch
    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping("/branches/{id}")
    public ResponseEntity<Branch> updateBranch(@PathVariable(value = "id") Long branchId, @Valid @RequestBody Branch branchDetails) {
        Branch branch = branchService.getBranchById(branchId);
        if (branch == null) {
            return ResponseEntity.notFound().build();
        }

        branch.setName(branchDetails.getName());
        branch.setCode(branchDetails.getCode());
        branch.setHeadquarter(branchDetails.getHeadquarter());
        branch.setEmail(branchDetails.getEmail());
        branch.setLocation(branchDetails.getLocation());
        branch.setShop(branchDetails.getShop());
        Branch updatedBranch = branchService.updateBranch(branch);
        return ResponseEntity.ok(updatedBranch);
    }

    // Delete a Branch
    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/branches/{id}")
    public ResponseEntity<Branch> deleteBranch(@PathVariable(value = "id") Long branchId) {
        Branch branch = branchService.getBranchById(branchId);
        if (branch == null) {
            return ResponseEntity.notFound().build();
        }

        branchService.deleteBranch(branch);
        return ResponseEntity.ok().build();
    }

}