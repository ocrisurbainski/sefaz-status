package br.com.urbainski.backend.model;

import br.com.urbainski.backend.model.enumerator.ServicoStatusEnum;
import lombok.*;

import javax.persistence.*;

/**
 * @author Cristian Urbainski
 * @since 21/08/2020
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
@Builder
@Entity
@Table(name = "servico_status_count")
public class ServicoStatusCount {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_servico_status", referencedColumnName = "id")
    private ServicoStatus servicoStatus;

    @Enumerated(EnumType.STRING)
    @Column(name = "st_status")
    private ServicoStatusEnum status;

    @Column(name = "qt_status")
    private Long count;

}