package edu.byui.apj.storefront.tutorial101.accessingdatajpa;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

    List<Customer> findByLastName(String lastName);

    Customer findById(long id);

    // New method: search first or last name, case-insensitive
    List<Customer> findByFirstNameIgnoreCaseOrLastNameIgnoreCase(String firstName, String lastName);
}
