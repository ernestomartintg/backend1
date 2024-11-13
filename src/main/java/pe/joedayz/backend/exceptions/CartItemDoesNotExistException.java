package pe.joedayz.backend.exceptions;

public class CartItemDoesNotExistException extends RuntimeException{
    public CartItemDoesNotExistException(String message){
        super(message);
    }
}
