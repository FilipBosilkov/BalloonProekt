package web.model;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
@Table(name = "manufacturers")
public class Manufacturer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String country;
    @Column(name = "manufacturer_address")
    private String address;


    public Manufacturer(String name, String country, String address) {
        this.name = name;
        this.country = country;
        this.address = address;
    }

    public Manufacturer() {

    }
}
