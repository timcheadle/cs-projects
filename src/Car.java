/*
 * Created on Apr 6, 2003
 *
 * To change this generated comment go to 
 * Window>Preferences>Java>Code Generation>Code Template
 */

/**
 * @author Tim Cheadle
 */
public class Car implements Comparable {
	private String manufacturer;
	private String model;
	private int modelYear;
	private String color;
	private int horsepower;
	private int torque;
	private int cylinders;
	
	public Car() {
		this.manufacturer = new String();
		this.model        = new String();
		this.modelYear    = 0;
		this.color        = new String();
		this.horsepower   = 0;
		this.torque       = 0;
		this.cylinders    = 0;
	}
	
	public Car (
		String manufacturer,
		String model,
		int modelYear,
		String color,
		int horsepower,
		int torque,
		int cylinders)
	{
		this.manufacturer = manufacturer;
		this.model        = model;
		this.modelYear    = modelYear;
		this.color        = color;
		this.horsepower   = horsepower;
		this.torque       = torque;
		this.cylinders    = cylinders;
	}
	
	/**
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	public int compareTo(Object o) {
		if (o == null) {
			return -1;
		}
		if (!(o instanceof Car)) {
			return -1;
		}
		
		Car c = (Car)o;
		
		if (this.manufacturer.compareTo(c.getManufacturer()) != 0) {
			return this.manufacturer.compareTo(c.getManufacturer());
		}
		if (this.model.compareTo(c.getModel()) != 0) {
			return this.model.compareTo(c.getModel());
		}
		if (this.modelYear != c.getModelYear()) {
			return this.modelYear - c.getModelYear();
		}
		if (this.color.compareTo(c.getColor()) != 0) {
			return this.color.compareTo(c.getColor());
		}
		if (this.horsepower != c.getHorsepower()) {
			return this.horsepower - c.getHorsepower();
		}
		if (this.torque != c.getTorque()) {
			return this.torque - c.getTorque();
		}
		if (this.cylinders != c.getCylinders()) {
			return this.cylinders - c.getCylinders();
		}
		
		return 0;
	}

	public String toString() {
		return manufacturer + "," +
			model + "," +
			modelYear + "," +
			color + "," +
			horsepower + "," +
			torque + "," +
			cylinders;
	}

	/**
	 * @return
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @return
	 */
	public int getCylinders() {
		return cylinders;
	}

	/**
	 * @return
	 */
	public int getHorsepower() {
		return horsepower;
	}

	/**
	 * @return
	 */
	public String getManufacturer() {
		return manufacturer;
	}

	/**
	 * @return
	 */
	public String getModel() {
		return model;
	}

	/**
	 * @return
	 */
	public int getModelYear() {
		return modelYear;
	}

	/**
	 * @return
	 */
	public int getTorque() {
		return torque;
	}

	/**
	 * @param string
	 */
	public void setColor(String string) {
		color = string;
	}

	/**
	 * @param i
	 */
	public void setCylinders(int i) {
		cylinders = i;
	}

	/**
	 * @param i
	 */
	public void setHorsepower(int i) {
		horsepower = i;
	}

	/**
	 * @param string
	 */
	public void setManufacturer(String string) {
		manufacturer = string;
	}

	/**
	 * @param string
	 */
	public void setModel(String string) {
		model = string;
	}

	/**
	 * @param i
	 */
	public void setModelYear(int i) {
		modelYear = i;
	}

	/**
	 * @param i
	 */
	public void setTorque(int i) {
		torque = i;
	}
}
