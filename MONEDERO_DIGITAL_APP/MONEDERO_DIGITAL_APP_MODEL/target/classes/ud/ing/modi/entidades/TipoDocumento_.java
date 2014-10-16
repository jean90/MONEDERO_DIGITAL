package ud.ing.modi.entidades;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(TipoDocumento.class)
public abstract class TipoDocumento_ {

	public static volatile SingularAttribute<TipoDocumento, String> desDocumento;
	public static volatile SingularAttribute<TipoDocumento, Integer> codigotipoDocumento;

}

