package model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@EqualsAndHashCode
public class City {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "city_id", nullable = false)
    private int cityId;
    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "last_update", nullable = false)
    private Timestamp lastUpdate;
    @PrePersist
    @PreUpdate
    protected void onUpdate() {
        lastUpdate = new Timestamp(System.currentTimeMillis());
    }
    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

}
