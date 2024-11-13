package pe.joedayz.backend.service;

import org.springframework.stereotype.Service;
import pe.joedayz.backend.exceptions.CartItemAlreadyExistsException;
import pe.joedayz.backend.exceptions.CartItemDoesNotExistException;
import pe.joedayz.backend.model.cart.CartItem;
import pe.joedayz.backend.repo.CarItemRepository;

import java.util.List;

@Service
public class CartItemService {

    private CarItemRepository repo;

    public CartItemService(CarItemRepository repo) {
        this.repo = repo;
    }

    public List<CartItem> getCartItems() {
        return repo.findAll();
    }

    public CartItem getCartItem(Long userId,Long productId) {
        for(CartItem item:getCartItems()) {
            if(item.getPk().getUser().getId()==userId && item.getPk().getProduct().getId()==productId) {
                return item;
            }
        }
        throw new CartItemDoesNotExistException(
                "CartItem by userId "+userId+" and productId " +productId+" was not found");
    }

    public CartItem addCartItem(CartItem cartItem) {
        for(CartItem item:getCartItems()) {
            if(item.equals(cartItem)) {
                throw new CartItemAlreadyExistsException(
                        "CartItem by userId "+cartItem.getPk().getUser().getId() + " and productId "
                        +cartItem.getPk().getProduct().getId() + " already exists");
            }
        }
        return this.repo.save(cartItem);
    }

    public CartItem updateCartItem(CartItem cartItem) {
        for (CartItem item:getCartItems()) {
            if(item.equals(cartItem)) {
                item.setQuantity(cartItem.getQuantity());
                return repo.save(item);
            }
        }
        throw new CartItemDoesNotExistException(
                "Cart item w/ user id " + cartItem.getPk().getUser().getId() + " and product id "
                + cartItem.getPk().getProduct().getId() + " does not exist"
        );
    }

    public void deleteCartItem(Long userId,Long productId) {
        for(CartItem item:getCartItems()) {
            if(item.getPk().getUser().getId()==userId && item.getPk().getProduct().getId()==productId) {
                repo.delete(item);
                return;
            }
        }

        throw new CartItemDoesNotExistException(
                "Cart item w/ user id " + userId + " and product id " + productId + " does not exist."
        );
    }
}
