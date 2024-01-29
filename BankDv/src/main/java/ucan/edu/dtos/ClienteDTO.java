package ucan.edu.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
/**
 *
 * @author jussyleitecode
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClienteDTO {

    private Integer pkCliente;
    private String nif;
    private Integer fkOrganizacao;
    private Integer fkParticular;
}
