package br.com.fiap.abctechservice.model;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "assistances")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Assistance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String name;

    @Column(nullable = false, length = 1000)
    private String description;
}
