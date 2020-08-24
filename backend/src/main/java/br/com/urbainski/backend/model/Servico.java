package br.com.urbainski.backend.model;

import lombok.*;

import javax.persistence.*;

/**
 * @author Cristian Urbainski
 * @since 19/08/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@Entity
@Table(name = "servico")
public class Servico {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "ds_nome")
    private String nome;

}