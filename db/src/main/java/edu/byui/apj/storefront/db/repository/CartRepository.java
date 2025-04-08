package edu.byui.apj.storefront.db.repository;

import edu.byui.apj.storefront.db.model.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

//@CrossOrigin(origins = "*")
//@RepositoryRestResource(collectionResourceRel = "cart", path = "cart")
public interface CartRepository extends JpaRepository<Cart, String> {
}
