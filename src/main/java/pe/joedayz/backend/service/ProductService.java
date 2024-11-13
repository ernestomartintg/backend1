package pe.joedayz.backend.service;

import org.springframework.stereotype.Service;
import pe.joedayz.backend.exceptions.ProductNotFoundException;
import pe.joedayz.backend.model.Product;
import pe.joedayz.backend.repo.ProductRepository;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository repo;

    public ProductService(ProductRepository repo) {
        this.repo = repo;
    }

    public List<Product> getProducts(){
        return repo.findAll();
    }

    public Product getProduct(Long id){
        return repo.findById(id).orElseThrow(()->
                new ProductNotFoundException("Product by id "+id+" was not found"));
    }

    public void deleteProduct(Long id){
        repo.deleteById(id);
    }

    public Product updateProduct(Long id, Product product){
        Product oldProduct = getProduct(id);

        oldProduct.setName(product.getName());
        oldProduct.setDescription(product.getDescription());
        oldProduct.setPrice(product.getPrice());
        oldProduct.setImage(product.getImage());

        return repo.save(oldProduct);
    }

    public Product addProduct(Product product){
        return repo.save(product);
    }
}
