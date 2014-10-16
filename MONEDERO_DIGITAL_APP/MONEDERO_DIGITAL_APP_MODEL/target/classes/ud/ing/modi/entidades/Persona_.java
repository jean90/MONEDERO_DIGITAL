package ud.ing.modi.entidades;

import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@StaticMetamodel(Persona.class)
public abstract class Persona_ {

	public static volatile SingularAttribute<Persona, String> apellido;
	public static volatile SingularAttribute<Persona, String> nombre;
	public static volatile SingularAttribute<Persona, String> numTelFijo;
	public static volatile SingularAttribute<Persona, TipoDocumento> tipoDocumento;
	public static volatile SingularAttribute<Persona, String> numCelular;
	public static volatile SingularAttribute<Persona, Integer> idPersona;

}

