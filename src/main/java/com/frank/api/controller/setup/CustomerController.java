package com.frank.api.controller.setup;

import com.frank.api.config.Config;
import com.frank.api.controller.RestBaseController;
import com.frank.api.model.pos.Customer;
import com.frank.api.service.sale.CustomerService;
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
public class CustomerController extends RestBaseController {

    @Autowired
    private CustomerService customerService;

    @GetMapping("/customers")
    public List<Customer> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SALES_PERSON') or hasAuthority('MANAGER')")
    @GetMapping("/customers/paginated")
    public Page<Customer> getPaginatedCustomers(@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        return customerService.getPaginatedCustomers(page,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SALES_PERSON') or hasAuthority('MANAGER')")
    @PostMapping("/customers")
    public Page<Customer> createCustomer(@Valid @RequestBody Customer customer, @RequestParam("perPage") Integer perPage) {
        customerService.createCustomer(customer);
        return customerService.getPaginatedCustomers(0,perPage);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SALES_PERSON') or hasAuthority('MANAGER')")
    @GetMapping("/customers/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable(value = "id") Long id) {
        Customer customer = customerService.getCustomerById(id);
        if (customer == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(customer);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('SALES_PERSON') or hasAuthority('MANAGER')")
    @PutMapping("/customers/{id}")
    public Page<Customer> updateCustomer(@PathVariable(value = "id") Long id, @Valid @RequestBody Customer customerDetails,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Customer customer = customerService.getCustomerById(id);
        customer.setName(customerDetails.getName());
        customer.setEmail(customerDetails.getEmail());
        customer.setMobile(customerDetails.getMobile());
        customerService.updateCustomer(customer);
        return customerService.getPaginatedCustomers(page,perPage);
    }

    // Delete a Customer
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('MANAGER')")
    @DeleteMapping("/customers/{id}")
    public Page<Customer> deleteCustomer(@PathVariable(value = "id") Long id,@RequestParam("page") Integer page, @RequestParam("perPage") Integer perPage) {
        Customer customer = customerService.getCustomerById(id);
        customerService.deleteCustomer(customer);
        return customerService.getPaginatedCustomers(page,perPage);
    }

}