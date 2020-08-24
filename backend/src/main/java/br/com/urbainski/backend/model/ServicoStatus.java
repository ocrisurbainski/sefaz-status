package br.com.urbainski.backend.model;

import br.com.urbainski.backend.model.enumerator.ServicoStatusEnum;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

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
@Table(name = "servico_status")
public class ServicoStatus {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_autorizador", referencedColumnName = "id")
    private Autorizador autorizador;

    @ManyToOne
    @JoinColumn(name = "id_servico", referencedColumnName = "id")
    private Servico servico;

    @Column(name = "dt_consulta")
    private LocalDate dataConsulta;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_status")
    private ServicoStatusEnum status;

}