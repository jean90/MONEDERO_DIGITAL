/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package ud.ing.modi.entidades;

import java.io.Serializable;
import java.util.Date;


/**
 *
 * @author user
 */
public class Monedero implements Serializable{
    //OJO: este antes no era serializable, lo puse para poder utilizar la clase al crear un nuevo monedero
    public String divisa;
    public String saldo;
    public String fecha;
    public String codigo;

    public Monedero(String divisa, String saldo) {
        this.divisa = divisa;
        this.saldo = saldo;
        this.fecha=(new Date()).toString();
        this.codigo=Integer.toString((int)(Math.random()*10000));
    }
    
    public void cargarMonedero(Monedero moned) {
        this.divisa = moned.divisa;
        this.saldo = moned.saldo;
        this.fecha=moned.fecha;
        this.codigo=moned.codigo;
    }
    
    public Monedero(){
        this.saldo="0";
        this.fecha=(new Date()).toString();
        this.codigo=Integer.toString((int)(Math.random()*10000));
    }

    public String getDivisa() {
        return divisa;
    }

    public void setDivisa(String divisa) {
        this.divisa = divisa;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
    
    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
    
    public String getCodigo() {
        return codigo;
    }
    
}
