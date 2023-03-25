package kodlama.io.ecommerce.business.concretes;

import kodlama.io.ecommerce.business.abstracts.ProductService;
import kodlama.io.ecommerce.entities.Product;
import kodlama.io.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductManager implements ProductService {
    private ProductRepository productRepository;

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(int id) {
        checkIfBrandExists(id);
        return productRepository.findById(id).orElseThrow();
    }

    @Override
    public Product add(Product product) {
        validateProduct(product);
        return productRepository.save(product);
    }

    @Override
    public Product update(int id, Product product) {
        checkIfBrandExists(id);
        validateProduct(product);
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public void delete(int id) {
        checkIfBrandExists(id);
        productRepository.deleteById(id);
    }

    //Business Rules

    private void validateProduct(Product product) {
        checkIfPriceValid(product);
        checkIfQuantityValid(product);
        checkIfDescriptionLength(product);
    }

    private void checkIfPriceValid(Product product){
        if(product.getPrice() <= 0) throw new IllegalArgumentException("Price cannot be less than or equal to zero");
    }

    private void checkIfQuantityValid(Product product){
        if(product.getQuantity() < 0 ) throw new IllegalArgumentException("Quantity cannot be less than zero");
    }

    private void checkIfDescriptionLength(Product product){
        if(product.getDescription().length() < 10 || product.getDescription().length() > 50 )
            throw new IllegalArgumentException("Description must be between 10 and 50 characters");
    }
    private void checkIfBrandExists(int id){
        if(!productRepository.existsById(id)) throw new RuntimeException("There is no brand!");
    }
}
