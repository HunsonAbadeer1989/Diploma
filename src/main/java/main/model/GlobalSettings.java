package main.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Entity

@NoArgsConstructor
@Table(name = "global_settings")
public class GlobalSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String code;

    @Column
    private String name;

    @Column
    private String value;

    public GlobalSettings(String code, String name, String value) {
        this.code = code;
        this.name = name;
        this.value = value;
    }
}
