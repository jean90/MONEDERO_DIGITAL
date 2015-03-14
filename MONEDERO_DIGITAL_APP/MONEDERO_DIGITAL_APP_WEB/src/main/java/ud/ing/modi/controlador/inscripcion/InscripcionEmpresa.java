/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ud.ing.modi.controlador.inscripcion;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import org.primefaces.event.FlowEvent;
import ud.ing.modi.entidades.ClienteJuridico;
import ud.ing.modi.entidades.EstadoCliente;
import ud.ing.modi.entidades.Persona;
import ud.ing.modi.entidades.TipoDocumento;
import ud.ing.modi.mapper.ClienteMapper;
import ud.ing.modi.mapper.DocumentoMapper;
import ud.ing.modi.mapper.EstadoClienteMapper;

/**
 *
 * @author Administrador
 */
@ManagedBean(name = "inscripcionEmpresa")
@ViewScoped
public class InscripcionEmpresa implements Serializable {

    
    private ClienteJuridico cJuridico;
    private Persona representante;
    private List<TipoDocumento> listaDocumentos;
    private TipoDocumento documento;
    private EstadoCliente eCliente;
    private String password;
    private String nick;


    /**
     * Creates a new instance of InscripcionEmpresa
     */
    public InscripcionEmpresa() {
        cJuridico=new ClienteJuridico();
        representante=new Persona();
        traerDocs();
    }    

    public ClienteJuridico getcJuridico() {
        return cJuridico;
    }

    public void setcJuridico(ClienteJuridico cJuridico) {
        this.cJuridico = cJuridico;
    }

    public Persona getRepresentante() {
        return representante;
    }

    public void setRepresentante(Persona representante) {
        this.representante = representante;
    }

    public List<TipoDocumento> getListaDocumentos() {
        return listaDocumentos;
    }

    public void setListaDocumentos(List<TipoDocumento> listaDocumentos) {
        this.listaDocumentos = listaDocumentos;
    }

    public TipoDocumento getDocumento() {
        return documento;
    }

    public void setDocumento(TipoDocumento documento) {
        this.documento = documento;
    }

    public EstadoCliente geteCliente() {
        return eCliente;
    }

    public void seteCliente(EstadoCliente eCliente) {
        this.eCliente = eCliente;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }
    
    
    
    public String onFlowProcess(FlowEvent event) {
        String evento = event.getNewStep();
        System.out.println("Evento: " + evento);
        if (evento.equals("confirma")) {
            this.asignarDoc();
        }
        return evento;
    }

    private void traerDocs() {
        DocumentoMapper mapDoc = new DocumentoMapper();
        this.listaDocumentos = mapDoc.obtenerDocs();
    }

    public void asignarDoc() {
        for (int i = 0; i < this.listaDocumentos.size(); i++) {
            if (this.listaDocumentos.get(i).getCodigotipoDocumento() == this.representante.getTipoDocumento().getCodigotipoDocumento()) {
                //this.persona.getTipoDocumento().setDesDocumento(this.documentos.get(i).getDesDocumento());
                this.representante.setTipoDocumento(this.listaDocumentos.get(i));
            }
        }
    }
    
    public void save() throws Exception{
        EstadoClienteMapper eClienteMapper = new EstadoClienteMapper();
        EstadoCliente eCliente = eClienteMapper.obtenerEstadoCliente("1");
        this.cJuridico.setEstadoCliente(eCliente);
        this.cJuridico.setRepresentante(this.representante);
        ClienteMapper cMapper = new ClienteMapper();
        cMapper.guardarClienteJuridico(this.cJuridico);
    }

}
