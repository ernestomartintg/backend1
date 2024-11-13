package pe.joedayz.backend.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import pe.joedayz.backend.model.cart.CartItem;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false,length = 35)
    private String usermame;

    @Column(nullable = false,length = 128)
    private String password;

    @Column(unique = true,nullable = false,length = 100)
    private String email;

    @Column(nullable = false,length = 100)
    private String name;

    @Column(nullable = false,length = 128)
    private String address;

    @Column(nullable = false,length = 15)
    private String phone;

    @JsonManagedReference
    //se borra un user, se borran todos sus carritos
    //un usuario tiene muchos carritos
    @OneToMany(mappedBy = "pk.user",  cascade = CascadeType.ALL)
    private List<CartItem> cartItems=new ArrayList<>();

    public User() {
    }

    public User(String usermame, String password, String email, String name, String address, String phone) {
        this.usermame = usermame;
        this.password = password;
        this.email = email;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsermame() {
        return usermame;
    }

    public void setUsermame(String usermame) {
        this.usermame = usermame;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public List<CartItem> getCartItems() {
        return cartItems;
    }

    public void setCartItems(List<CartItem> cartItems) {
        this.cartItems = cartItems;
    }
}
