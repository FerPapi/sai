package simulator;

import javax.swing.ImageIcon;

public class AlertTangibleObject extends TangibleObject {
	
	public AlertTangibleObject(){
		super("alert_tangible_object", new ImageIcon(TangibleTableArt.class.getResource("/simulator/images/warning.gif")));
	}

}
