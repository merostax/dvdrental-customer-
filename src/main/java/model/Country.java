package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.*;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Getter
@Setter
@EqualsAndHashCode
public class Country {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "country_id", nullable = false)
    private int countryId;
    @Column(name = "country", nullable = false, length = 50)
    private String country;
    @Column(name = "last_update", nullable = false)
    private Timestamp lastUpdate;
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdate = new Timestamp(System.currentTimeMillis());
    }
    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<City> cities;
}
