/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dur;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author thufir
 */
@ManagedBean
@RequestScoped
public class Foo {


    private String bar = "foo";

    public Foo() {
    }

    public String getBar() {
        return "fobarbaz";
    }

    public void setBar(String bar) {
        this.bar = bar;
    }
}
