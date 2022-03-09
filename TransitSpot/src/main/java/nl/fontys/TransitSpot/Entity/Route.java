package nl.fontys.TransitSpot.Entity;
import lombok.Getter;
import lombok.Setter;
import nl.fontys.TransitSpot.Entity.Station;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.CascadeType.ALL;

@Entity
@Table(name ="route")
public class Route {
    @Id
    @Column(name = "Route_ID")
    @Type(type="org.hibernate.type.UUIDCharType")
    protected @Getter @Setter UUID ID;
    protected @Getter @Setter String name;
    protected @Getter @Setter int duration;
    protected @Getter @Setter String code;
    protected @Getter @Setter Double price;
    @ManyToOne
    private @Getter @Setter Station Origin;
    @ManyToOne
    private @Getter @Setter Station Destination;
    public Route( String name, int duration, String Code, Double Price ) {
        this.name = name;
        this.duration = duration;
        this.code = Code;
        this.price = Price;
    }
    public Route(){};
}
