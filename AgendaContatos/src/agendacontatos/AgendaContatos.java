package agendacontatos;

import javax.swing.SwingUtilities;

/**
 *
 * @author bebel
 */
public class AgendaContatos {

    public static void main(String[] args) {
        //InterfaceConsole console = new InterfaceConsole();
        //console.iniciar();
        
        SwingUtilities.invokeLater(() -> {
            new InterfaceSwing().setVisible(true);
        });
    }  
}
